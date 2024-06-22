package model;

public class LibraryItem {
    private int id;
    private boolean isAvailable;

    public LibraryItem(int id, boolean isAvailable) {
        this.id = id;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
