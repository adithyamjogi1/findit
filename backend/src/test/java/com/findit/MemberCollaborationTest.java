package com.findit;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
public class MemberCollaborationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemController itemController;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFullTeamWorkflow_Member1AndMember2() {
        // --- STEP 1: MEMBER 1 (User Registration) ---
        Map<String, String> aliceReg = Map.of(
                "name", "Alice Smith",
                "email", "alice@college.edu",
                "password", "SecurePass123!"
        );
        ResponseEntity<?> regResponse = userController.register(aliceReg);
        assertEquals(HttpStatus.CREATED, regResponse.getStatusCode());

        Map<String, Object> regData = (Map<String, Object>) regResponse.getBody();
        assertNotNull(regData);
        Long aliceId = (Long) regData.get("id");
        assertNotNull(aliceId);

        // --- STEP 2: MEMBER 1 (User Login) ---
        Map<String, String> aliceLogin = Map.of(
                "email", "alice@college.edu",
                "password", "SecurePass123!"
        );
        ResponseEntity<?> loginResponse = userController.login(aliceLogin);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

        User aliceUser = userRepository.findById(aliceId).orElseThrow();

        // --- STEP 3: MEMBER 2 (Alice reports a Lost Item) ---
        Item lostItem = new Item();
        lostItem.setItemName("Blue HP Spectre Laptop");
        lostItem.setCategory("Electronics");
        lostItem.setDescription("13-inch dark blue laptop in a black case");
        lostItem.setLocation("Main Library 3rd Floor");
        lostItem.setImageUrl("https://example.com/laptop.jpg");
        lostItem.setUser(aliceUser);

        ResponseEntity<Item> lostResponse = itemController.reportLostItem(lostItem);
        assertEquals(HttpStatus.CREATED, lostResponse.getStatusCode());
        Item reportedLost = lostResponse.getBody();
        assertNotNull(reportedLost);
        assertEquals("LOST", reportedLost.getType());
        assertEquals("OPEN", reportedLost.getStatus());
        assertEquals("Alice Smith", reportedLost.getUser().getName());

        // --- STEP 4: MEMBER 2 (Bob reports a Found Item) ---
        Item foundItem = new Item();
        foundItem.setItemName("HP Laptop Charger");
        foundItem.setCategory("Electronics");
        foundItem.setDescription("Found near study desks");
        foundItem.setLocation("Main Library 3rd Floor");
        foundItem.setImageUrl("https://example.com/charger.jpg");

        ResponseEntity<Item> foundResponse = itemController.reportFoundItem(foundItem);
        assertEquals(HttpStatus.CREATED, foundResponse.getStatusCode());
        Item reportedFound = foundResponse.getBody();
        assertNotNull(reportedFound);
        assertEquals("FOUND", reportedFound.getType());

        // --- STEP 5: MEMBER 2 (Query items with filters for Member 3's frontend) ---
        ResponseEntity<List<Item>> searchLostResponse = itemController.getItems("LOST", "Electronics", "OPEN", "Spectre");
        assertEquals(HttpStatus.OK, searchLostResponse.getStatusCode());
        List<Item> searchResults = searchLostResponse.getBody();
        assertNotNull(searchResults);
        assertEquals(1, searchResults.size());
        assertEquals("Blue HP Spectre Laptop", searchResults.get(0).getItemName());

        // --- STEP 6: MEMBER 2 + MEMBER 1 (Alice checks items she reported) ---
        ResponseEntity<List<Item>> aliceItemsResponse = itemController.getItemsByUser(aliceId);
        assertEquals(HttpStatus.OK, aliceItemsResponse.getStatusCode());
        List<Item> aliceItems = aliceItemsResponse.getBody();
        assertNotNull(aliceItems);
        assertEquals(1, aliceItems.size());
        assertEquals("Blue HP Spectre Laptop", aliceItems.get(0).getItemName());

        // --- STEP 7: MEMBER 2 (Item is recovered -> Status changed to RETURNED) ---
        ResponseEntity<?> statusUpdateResponse = itemController.updateStatus(
                reportedLost.getItemId(),
                Map.of("status", "RETURNED")
        );
        assertEquals(HttpStatus.OK, statusUpdateResponse.getStatusCode());
        Item updatedItem = (Item) statusUpdateResponse.getBody();
        assertNotNull(updatedItem);
        assertEquals("RETURNED", updatedItem.getStatus());

        // --- STEP 8: MEMBER 4 (Smart Matching validation) ---
        // Member 4 will match lost items with found items based on name, category, and location
        assertTrue(reportedLost.getCategory().equalsIgnoreCase(reportedFound.getCategory()));
        assertTrue(reportedLost.getLocation().equalsIgnoreCase(reportedFound.getLocation()));
    }
}
