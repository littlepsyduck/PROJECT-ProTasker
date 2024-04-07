package application;

public class Task {
    String title;
    String description;
    String priority;
    String dueDate;
    String status;
    String id;

    public Task(String id, String title, String description, String dueDate, String priority, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
    }
}