package model;

import java.util.LinkedList;

/** Book inherits from the LibraryItem class */
public class Book extends LibraryItem {
    private String title;
    private String author;
    private String isbn;
    private LinkedList<String> borrowHistory;

    public Book(int id, String title, String author, String isbn, boolean isAvailable) {
        super(id, isAvailable);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.borrowHistory = new LinkedList<>();
    }

    // Getters and setters specific to Book class
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /** linkedlist implementation */
    public LinkedList<String> getBorrowHistory() {
        return borrowHistory;
    }

    // Add a borrow date to the history
    public void addBorrowDate(String date) {
        borrowHistory.add(date);
    }
}
