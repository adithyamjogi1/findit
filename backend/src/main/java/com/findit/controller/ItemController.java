package com.findit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.findit.entity.Item;
import com.findit.service.ItemService;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*") // Cross-origin access for Angular frontend and development tools
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // POST /api/items/lost - Report a lost item
    @PostMapping("/lost")
    public ResponseEntity<Item> reportLostItem(@RequestBody Item item) {
        Item created = itemService.reportLostItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // POST /api/items/found - Report a found item
    @PostMapping("/found")
    public ResponseEntity<Item> reportFoundItem(@RequestBody Item item) {
        Item created = itemService.reportFoundItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // POST /api/items - General item reporting (type specified in payload)
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item created = itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/items - Get items with optional query filters (?type=LOST&category=Electronics&status=OPEN&search=key)
    @GetMapping
    public ResponseEntity<List<Item>> getItems(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {
        List<Item> items = itemService.getItems(type, category, status, search);
        return ResponseEntity.ok(items);
    }

    // GET /api/items/{id} - Get specific item details
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Item not found with id: " + id));
        }
        return ResponseEntity.ok(item);
    }

    // GET /api/items/user/{userId} - Get all items reported by a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Item>> getItemsByUser(@PathVariable Long userId) {
        List<Item> items = itemService.getItemsByUserId(userId);
        return ResponseEntity.ok(items);
    }

    // PATCH /api/items/{id}/status - Update status (e.g. RETURNED, CLAIMED, CLOSED)
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        if (newStatus == null || newStatus.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Field 'status' is required."));
        }

        Item updated = itemService.updateItemStatus(id, newStatus);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Item not found with id: " + id));
        }
        return ResponseEntity.ok(updated);
    }

    // PUT /api/items/{id} - Full update of an item
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId,
            @RequestBody Item item) {
        Item existing = itemService.getItemById(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Item not found with id: " + id));
        }

        if (userId != null && existing.getUser() != null && existing.getUser().getId() != null) {
            if (!existing.getUser().getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "Permission denied. You can only edit items you reported."));
            }
        }

        Item updated = itemService.updateItem(id, item);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/items/{id} - Delete an item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId) {
        Item existing = itemService.getItemById(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Item not found with id: " + id));
        }

        if (userId != null && existing.getUser() != null && existing.getUser().getId() != null) {
            if (!existing.getUser().getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "Permission denied. You can only delete items you reported."));
            }
        }

        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> updateItem(Long id, Item item) {
        return updateItem(id, null, item);
    }

    public ResponseEntity<?> deleteItem(Long id) {
        return deleteItem(id, null);
    }
}
