package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static int lastId;
    private int id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Task(String description){
        this.id = ++lastId;
        this.description = description;
        this.status = "todo";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Task() { }

    public void setId(int id) {
        this.id = id;

        // Keep the static lastId in sync so new tasks continue from the highest ID
        if (id > lastId) {
            lastId = id;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId(){
        return id;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int id) {
        lastId = id;
    }

    public String getDescription(){
        return description;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    public void updateDescription(String Description){
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void markInProgress(String status){
        this.status = "in-progress";
        this.updatedAt = LocalDateTime.now();
    }

    public void markDone(String status){
        this.status = "done";
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | %s | Created: %s | Updated: %s",
                id,
                description,
                status,
                createdAt,
                updatedAt);
    }
}