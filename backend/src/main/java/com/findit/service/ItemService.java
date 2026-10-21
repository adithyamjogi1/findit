package com.findit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.findit.entity.Item;
import com.findit.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item saveItem(Item item) {
        if (item.getStatus() == null || item.getStatus().isBlank()) {
            item.setStatus("OPEN");
        }
        if (item.getDate() == null) {
            item.setDate(LocalDate.now());
        }
        return itemRepository.save(item);
    }

    public Item reportLostItem(Item item) {
        item.setType("LOST");
        if (item.getStatus() == null || item.getStatus().isBlank()) {
            item.setStatus("OPEN");
        }
        if (item.getDate() == null) {
            item.setDate(LocalDate.now());
        }
        return itemRepository.save(item);
    }

    public Item reportFoundItem(Item item) {
        item.setType("FOUND");
        if (item.getStatus() == null || item.getStatus().isBlank()) {
            item.setStatus("OPEN");
        }
        if (item.getDate() == null) {
            item.setDate(LocalDate.now());
        }
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public List<Item> getItems(String type, String category, String status, String search) {
        String cleanType = (type != null && !type.isBlank()) ? type.trim() : null;
        String cleanCategory = (category != null && !category.isBlank()) ? category.trim() : null;
        String cleanStatus = (status != null && !status.isBlank()) ? status.trim() : null;
        String cleanSearch = (search != null && !search.isBlank()) ? search.trim() : null;

        if (cleanType == null && cleanCategory == null && cleanStatus == null && cleanSearch == null) {
            return itemRepository.findAll();
        }
        return itemRepository.searchItems(cleanType, cleanCategory, cleanStatus, cleanSearch);
    }

    public List<Item> getItemsByUserId(Long userId) {
        return itemRepository.findByUser_Id(userId);
    }

    public Item updateItemStatus(Long id, String newStatus) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            return null;
        }
        Item item = optionalItem.get();
        item.setStatus(newStatus.toUpperCase().trim());
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item item) {
        Item existingItem = itemRepository.findById(id).orElse(null);

        if (existingItem == null) {
            return null;
        }

        if (item.getItemName() != null) {
            existingItem.setItemName(item.getItemName());
        }
        if (item.getCategory() != null) {
            existingItem.setCategory(item.getCategory());
        }
        if (item.getDescription() != null) {
            existingItem.setDescription(item.getDescription());
        }
        if (item.getLocation() != null) {
            existingItem.setLocation(item.getLocation());
        }
        if (item.getDate() != null) {
            existingItem.setDate(item.getDate());
        }
        if (item.getType() != null) {
            existingItem.setType(item.getType());
        }
        if (item.getStatus() != null) {
            existingItem.setStatus(item.getStatus());
        }
        if (item.getImageUrl() != null) {
            existingItem.setImageUrl(item.getImageUrl());
        }
        if (item.getUser() != null) {
            existingItem.setUser(item.getUser());
        }

        return itemRepository.save(existingItem);
    }

    public boolean deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            return false;
        }
        itemRepository.deleteById(id);
        return true;
    }
}
