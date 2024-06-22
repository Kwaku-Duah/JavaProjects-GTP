package dao;

import model.Book;
import java.util.List;

public interface BookDAO {
    List<Book> getAllBooks();
    Book getBookById(int id);
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int id);
    void borrowBook(int id);
    List<Book> searchBooks(String searchText);
}
