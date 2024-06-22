package dao;

import model.Patron;
import java.util.List;

public interface PatronDAO {
    List<Patron> getAllPatrons();
    Patron getPatronById(int id);
    void addPatron(Patron patron);
    void updatePatron(Patron patron);
    void deletePatron(int id);
}
