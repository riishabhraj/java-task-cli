package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() throws IOException {
        this.tasks = loadTasks();
    }

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private List<Task> loadTasks() throws IOException {
        File file = new File("tasks.json");

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();  // return an empty list
        }

        String json = Files.readString(Path.of("tasks.json"));

        json = json.trim();
        if (json.startsWith("[")) json = json.substring(1);
        if (json.endsWith("]")) json = json.substring(0, json.length() - 1);

        String[] objects = json.split("\\},\\s*\\{");

        List<Task> loaded = new ArrayList<>();

        for (String obj : objects) {
            obj = obj.replace("{", "").replace("}", "").trim();
            String[] pairs = obj.split(",\\s*");

            int id = 0;
            String description = null;
            String status = null;
            String createdAt = null;
            String updatedAt = null;

            for (String pair : pairs) {
                String[] kv = pair.split(":", 2);
                String key = kv[0].trim().replaceAll("\"", "");
                String value = kv[1].trim().replaceAll("\"", "");

                switch (key) {
                    case "id"        -> id = Integer.parseInt(value);
                    case "description" -> description = value;
                    case "status"      -> status = value;
                    case "createdAt"   -> createdAt = value;
                    case "updatedAt"   -> updatedAt = value;
                }
            }

            if (id > Task.getLastId()) {
                Task.setLastId(id);
            }


            Task t = new Task();  // no-arg constructor
            t.setId(id);
            t.setDescription(description);
            t.setStatus(status);
            t.setCreatedAt(LocalDateTime.parse(createdAt, formatter));
            t.setUpdatedAt(LocalDateTime.parse(updatedAt, formatter));

            loaded.add(t);
        }

        return loaded;
    }

    public void saveTasks() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);

            sb.append("  {")
                    .append("\"id\":").append(t.getId()).append(", ")
                    .append("\"description\":\"").append(t.getDescription()).append("\", ")
                    .append("\"status\":\"").append(t.getStatus()).append("\", ")
                    .append("\"createdAt\":\"").append(t.getCreatedAt().format(formatter)).append("\", ")
                    .append("\"updatedAt\":\"").append(t.getUpdatedAt().format(formatter)).append("\"")
                    .append("}");

            if (i < tasks.size() - 1) {
                sb.append(",\n");
            } else {
                sb.append("\n");
            }
        }

        sb.append("]");

        Files.writeString(Path.of("tasks.json"), sb.toString());
    }


    public void addTask(String description){
        Task newTask = new Task(description);
        tasks.add(newTask);
        System.out.println("Task added Successfully (ID: " + newTask.getId() +")");
    }

    public void updateTask(String id, String description) {
        Task taskToUpdate = tasks.stream()
                .filter(task -> task.getId() == Integer.parseInt(id))
                .findFirst()
                .orElse(null);

        if (taskToUpdate != null) {
            taskToUpdate.setDescription(description);
            taskToUpdate.setUpdatedAt(LocalDateTime.now());
            System.out.println("Task updated Successfully (ID: " + taskToUpdate.getId() +")");

        } else {
            System.out.println("Task with id " + id + " not found!");
        }
    }

    public void deleteTask(String id) {
        Task taskToDelete = tasks.stream()
                .filter(task -> task.getId() == Integer.parseInt(id))
                .findFirst()
                .orElse(null);

        if (taskToDelete != null) {
            tasks.remove(taskToDelete);
            System.out.println("Task " + id + " deleted successfully.");
        } else {
            System.out.println("Task with id " + id + " not found.");
        }
    }

    public void markInProgress(String id){
        Task taskToMarkInProgress = tasks.stream()
                .filter(task -> task.getId() == Integer.parseInt(id))
                .findFirst()
                .orElse(null);       // unwrap Optional

        if (taskToMarkInProgress != null) {
            taskToMarkInProgress.markInProgress("in-progress");
            System.out.println("Task " + id + " marked in-progress successfully.");
        } else {
            System.out.println("Task with id " + id + " not found.");
        }
    }

    public void markDone(String id){
        Task taskToMarkDone = tasks.stream()
                .filter(task -> task.getId() == Integer.parseInt(id))
                .findFirst()
                .orElse(null);

        if (taskToMarkDone != null) {
            taskToMarkDone.markDone("done");
            System.out.println("Task " + id + " marked done successfully.");
        } else {
            System.out.println("Task with id " + id + " not found.");
        }
    }

    public Task findTask(String id) {
        int intId = Integer.parseInt(id);
        return tasks.stream()
                .filter(task -> task.getId() == intId)
                .findFirst()
                .orElse(null);
    }

    public void listTasks(String type){
        for (Task task: tasks){
            String status = task.getStatus().strip();
            if(type.equals("All") || type.equalsIgnoreCase(status)){
                System.out.println(task);
            }
        }
    }
}