package controller;

import model.Patron;
import dao.PatronDAO;
import java.util.List;

/**
 * Controller class for managing operations related to patrons.
 * Encapsulates PatronDAO interactions through public methods.
 * Supports CRUD operations on patrons.
 */
public class PatronController {
    private PatronDAO patronDAO;

    /**
     * Constructor to initialize PatronController with a specific PatronDAO implementation.
     * @param patronDAO The data access object responsible for patron operations.
     */
    public PatronController(PatronDAO patronDAO) {
        this.patronDAO = patronDAO;
    }

    /**
     * Retrieves all patrons stored in the data source.
     * @return List of all patrons.
     */
    public List<Patron> getAllPatrons() {
        return patronDAO.getAllPatrons();
    }

    /**
     * Retrieves a patron by their unique identifier.
     * @param id The identifier of the patron to retrieve.
     * @return The patron object corresponding to the given ID.
     */
    public Patron getPatronById(int id) {
        return patronDAO.getPatronById(id);
    }

    /**
     * Adds a new patron to the data source.
     * @param patron The patron object to be added.
     */
    public void addPatron(Patron patron) {
        patronDAO.addPatron(patron);
    }

    /**
     * Updates an existing patron in the data source.
     * @param patron The updated patron object.
     */
    public void updatePatron(Patron patron) {
        patronDAO.updatePatron(patron);
    }

    /**
     * Deletes a patron from the data source by their ID.
     * @param id The ID of the patron to be deleted.
     */
    public void deletePatron(int id) {
        patronDAO.deletePatron(id);
    }
}
