package model;

import java.time.LocalDate;


public class Transaction {
    private int id;
    private int bookId;
    private int patronId;
    private LocalDate issueDate;
    private LocalDate returnDate;


    public Transaction(int id, int bookId, int patronId, LocalDate issueDate, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    
    /** 
     * @return int
     */
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
