package controller;

import model.Book;
import dao.BookDAO;
import java.util.List;

/**
 * Controller class for managing operations related to books.
 * Uses Object-Oriented Programming (OOP) concepts such as encapsulation
 */
public class BookController {
    private BookDAO bookDAO;

    /**
     * Constructor to initialize BookController with a BookDAO instance.
     * 
     * @param bookDAO The Data Access Object for books.
     */
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    /**
     * Retrieves all books from the data source.
     * 
     * @return List of all books.
     */
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    /**
     * Retrieves a book by its ID from the data source.
     * 
     * @param id The ID of the book to retrieve.
     * @return The book with the specified ID.
     */
    public Book getBookById(int id) {
        return bookDAO.getBookById(id);
    }

    /**
     * Adds a new book to the data source.
     * 
     * @param book The book object to add.
     */
    public void addBook(Book book) {
        bookDAO.addBook(book);
    }

    /**
     * Updates an existing book in the data source.
     * 
     * @param book The updated book object.
     */
    public void updateBook(Book book) {
        bookDAO.updateBook(book);
    }

    /**
     * Deletes a book from the data source based on its ID.
     * 
     * @param id The ID of the book to delete.
     */
    public void deleteBook(int id) {
        bookDAO.deleteBook(id);
    }
}
