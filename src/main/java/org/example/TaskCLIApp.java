package org.example;

import java.io.IOException;
import java.time.LocalDateTime;

public class TaskCLIApp {
    public static void main(String[] args) throws IOException {

        TaskManager taskManager = new TaskManager();

        switch (args[0]){
            case "add":
                StringBuilder newTask = new StringBuilder();
                for (int i = 1; i < args.length-1; i++) {
                    newTask.append(args[i]).append(" ");
                }

                newTask.append(args[args.length - 1]);
                taskManager.addTask(newTask.toString());

                break;

            case "update":
                Task taskToUpdate = taskManager.findTask(args[1]);

                StringBuilder newDescription = new StringBuilder();
                for (int i = 2; i < args.length-1; i++) {
                    newDescription.append(args[i]).append(" ");
                }

                taskToUpdate.setDescription(newDescription.toString());
                taskToUpdate.setUpdatedAt(LocalDateTime.now());

                break;
            case "delete":
                taskManager.deleteTask(args[1]);

                break;

            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli mark-in-progress <id>");
                    return;
                }
                taskManager.markInProgress(args[1]);
                System.out.println("Task marked as in progress (ID: " + args[1] + ")");
                break;

            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli mark-done <id>");
                    return;
                }
                taskManager.markDone(args[1]);
                System.out.println("Task marked as done (ID: " + args[1] + ")");
                break;

            case "list":
                if (args.length < 2) {
                    taskManager.listTasks("All");
                } else {
                    taskManager.listTasks(args[1]);
                }
        }
        taskManager.saveTasks();
    }
}
