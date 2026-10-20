package com.findit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.findit.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String category;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String location;

    private LocalDate date;

    @Column(nullable = false)
    private String type;   // "LOST" or "FOUND"

    @Column(nullable = false)
    private String status; // "OPEN", "CLAIMED", "RETURNED", "CLOSED"

    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    private String imageUrl; // Photo / image URL or online base64 data

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    @JsonIgnoreProperties({"password"}) // Exclude password from item JSON responses
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Item() {
    }

    public Item(String itemName, String category, String description, String location,
                LocalDate date, String type, String status, String imageUrl, User user) {
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.location = location;
        this.date = date;
        this.type = type;
        this.status = status;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.date == null) {
            this.date = LocalDate.now();
        }
        if (this.status == null || this.status.isBlank()) {
            this.status = "OPEN";
        }
    }

    // Primary ID getter & setter
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    // Compatibility alias for id
    public Long getId() {
        return itemId;
    }

    public void setId(Long id) {
        this.itemId = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // Compatibility alias for title
    public String getTitle() {
        return itemName;
    }

    public void setTitle(String title) {
        this.itemName = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Compatibility alias for photo
    public String getPhoto() {
        return imageUrl;
    }

    public void setPhoto(String photo) {
        this.imageUrl = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
