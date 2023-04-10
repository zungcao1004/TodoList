package com.example.todolist;

public class TodoItem {
    private int id;
    private String title;
    private boolean completed;

    public TodoItem(){

    }

    public TodoItem(String title) {
        this.id = -1; // initialize with invalid id
        this.title = title;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
