package model;

import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class Patron {
    private int id;
    private String name;
    private String email;
    private String phone;
    private Stack<String> recentActivities; // Stack to track recent activities
    private Queue<String> bookRequests; // Queue to manage book requests

    public Patron(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.recentActivities = new Stack<>();
        this.bookRequests = new LinkedList<>();
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }

    // Stack methods for recent activities
    public void addActivity(String activity) {
        recentActivities.push(activity);
    }

    public String getLastActivity() {
        return recentActivities.isEmpty() ? "No recent activities" : recentActivities.pop();
    }

    public Stack<String> getRecentActivities() {
        return recentActivities;
    }

    // Queue methods for book requests
    public void requestBook(String bookTitle) {
        bookRequests.offer(bookTitle);
    }

    public String getNextBookRequest() {
        return bookRequests.isEmpty() ? "No book requests" : bookRequests.poll();
    }

    public Queue<String> getBookRequests() {
        return bookRequests;
    }
}
