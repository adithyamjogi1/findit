package com.findit;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.findit.controller.ItemController;
import com.findit.entity.Item;
import com.findit.repository.ItemRepository;
import com.findit.service.ItemService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
public class ItemModuleTest {

    @Autowired
    private ItemController itemController;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void testReportLostItem() {
        Item lostItem = new Item();
        lostItem.setItemName("Blue Backpack");
        lostItem.setCategory("Bags");
        lostItem.setDescription("Navy blue American Tourister bag with water bottle");
        lostItem.setLocation("Main Library 2nd Floor");
        lostItem.setImageUrl("https://example.com/backpack.jpg");

        ResponseEntity<Item> response = itemController.reportLostItem(lostItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getItemId());
        assertEquals("Blue Backpack", response.getBody().getItemName());
        assertEquals("LOST", response.getBody().getType());
        assertEquals("OPEN", response.getBody().getStatus());
        assertEquals("https://example.com/backpack.jpg", response.getBody().getImageUrl());
    }

    @Test
    void testReportFoundItem() {
        Item foundItem = new Item();
        foundItem.setItemName("Scientific Calculator");
        foundItem.setCategory("Electronics");
        foundItem.setDescription("Casio fx-991EX found on desk");
        foundItem.setLocation("Room 304, Academic Block");

        ResponseEntity<Item> response = itemController.reportFoundItem(foundItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getItemId());
        assertEquals("Scientific Calculator", response.getBody().getItemName());
        assertEquals("FOUND", response.getBody().getType());
        assertEquals("OPEN", response.getBody().getStatus());
    }

    @Test
    void testGetItemsWithFilterAndSearch() {
        Item item1 = new Item("Dell Laptop", "Electronics", "Grey XPS 13", "Cafeteria", LocalDate.now(), "LOST", "OPEN", null, null);
        Item item2 = new Item("MacBook Pro", "Electronics", "Space grey 16 inch", "Auditorium", LocalDate.now(), "FOUND", "OPEN", null, null);
        Item item3 = new Item("Campus ID Card", "Documents", "ID for John Doe", "Gate 1", LocalDate.now(), "LOST", "OPEN", null, null);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        // Filter by type=LOST
        ResponseEntity<List<Item>> lostResponse = itemController.getItems("LOST", null, null, null);
        assertEquals(HttpStatus.OK, lostResponse.getStatusCode());
        assertNotNull(lostResponse.getBody());
        assertEquals(2, lostResponse.getBody().size());

        // Filter by type=LOST and category=Electronics
        ResponseEntity<List<Item>> filteredResponse = itemController.getItems("LOST", "Electronics", null, null);
        assertEquals(HttpStatus.OK, filteredResponse.getStatusCode());
        assertNotNull(filteredResponse.getBody());
        assertEquals(1, filteredResponse.getBody().size());
        assertEquals("Dell Laptop", filteredResponse.getBody().get(0).getItemName());

        // Search by keyword "MacBook"
        ResponseEntity<List<Item>> searchResponse = itemController.getItems(null, null, null, "MacBook");
        assertEquals(HttpStatus.OK, searchResponse.getStatusCode());
        assertNotNull(searchResponse.getBody());
        assertEquals(1, searchResponse.getBody().size());
        assertEquals("MacBook Pro", searchResponse.getBody().get(0).getItemName());
    }

    @Test
    void testUpdateItemStatus() {
        Item item = new Item("Keys with red keychain", "Keys", "3 keys on a red ring", "Sports Complex", LocalDate.now(), "FOUND", "OPEN", null, null);
        Item saved = itemRepository.save(item);

        ResponseEntity<?> response = itemController.updateStatus(saved.getItemId(), Map.of("status", "RETURNED"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Item updated = (Item) response.getBody();
        assertNotNull(updated);
        assertEquals("RETURNED", updated.getStatus());
    }

    @Test
    void testGetItemByIdAndNotFound() {
        Item item = new Item("Water Bottle", "Other", "Steel bottle", "Gym", LocalDate.now(), "FOUND", "OPEN", null, null);
        Item saved = itemRepository.save(item);

        ResponseEntity<?> response = itemController.getItemById(saved.getItemId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseEntity<?> notFoundResponse = itemController.getItemById(99999L);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
    }

    @Test
    void testDeleteItem() {
        Item item = new Item("Notebook", "Stationery", "Maths notes", "Library", LocalDate.now(), "LOST", "OPEN", null, null);
        Item saved = itemRepository.save(item);

        ResponseEntity<?> deleteResponse = itemController.deleteItem(saved.getItemId());
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        assertFalse(itemRepository.existsById(saved.getItemId()));
    }
}
