package library;

import java.util.Objects;

/**
 * Encapusulates information about a book.
 * 
 * @author Balaji Srinivasan
 */
public class Book {
    String title;
    String author;
    String isbn;
    int publicationYear;
    // number of copies in the library
    // NOTE: This is not the number of copies available in the library
    int numberOfCopies;
    int availableCopies;

    /**
     * Constructor. Most properties (except number of copies are read only)
     * 
     * @param title the title of the book
     * @param author the author of the book
     * @param isbn the ISBN of the book
     * @param publicationYear the publication year
     * @param numberOfCopies total number of copies
     */
    public Book(String title, String author, String isbn,
            int publicationYear, int numberOfCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.numberOfCopies = numberOfCopies;
        this.availableCopies = numberOfCopies;
    }

    /**
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The author of the book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the ISBN for this book.
     */
    public String getISBN() {
        return isbn;
    }

    /**
     * @return The publication year of this book.
     */
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * @return The number of copies of this book.
     */
    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    /**
     * @return The number of available copies of this book.
     */
    public int getAvailableCopies() {
        return availableCopies;
    }

    /**
     * Sets the number of available copies. Just used for testing.
     */
    public void setAvailableCopies(int numCopies) {
        if (numCopies < 0 || numCopies > numberOfCopies) {
            throw new IllegalArgumentException(
                    "Available copies must be between 0 and total copies");
        }

        availableCopies = numCopies;
    }

    /**
     * Adds the given mumber of copies of this book to the library.
     */
    public void addCopies(int numCopiesToAdd) {
        numberOfCopies += numCopiesToAdd;
    }

    /**
     * Checks out a book (decrements number of copies available in the library)
     * 
     * @throws RuntimeException if no copies are available to check out.
     */
    public void checkout() {
        if (availableCopies <= 0) {
            throw new RuntimeException(
                    "No copies available to checkout");
        }
    }

    /**
     * Checks in a book into the library.
     * 
     * @throws RuntimeException if no copies have been checked out.
     */
    public void checkin() {
        if (availableCopies >= numberOfCopies) {
            throw new RuntimeException(
                    "No checked out copies to return");
        }
        
        availableCopies++;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null || getClass() != that.getClass()) {
            return false;
        }
        Book other = (Book) that;
        return Objects.equals(this.title, other.title)
                && Objects.equals(this.author, other.author)
                && Objects.equals(this.isbn, other.isbn);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not implemented");
    }
    /**
     * Creates a Book from serialized data
     * 
     * @param line serialized book data
     * @return reconstructed Book object
     */
    public static Book fromSerialized(String line) {
        if (line == null) {
            throw new IllegalArgumentException(
                    "Serialized data cannot be null");
        }
        
        String [] parts = line.split("\\|");
        
        if (parts.length != 6) {
            throw new IllegalArgumentException(
                    "Invalid serialized book format");
        }
        
        String title = parts[0];
        String author = parts[1];
        String isbn = parts[2];
        
        int publicationYear = Integer.parseInt(parts[3]);
        int numberOfCopies = Integer.parseInt(parts[4]);
        int availableCopies = Integer.parseInt(parts[5]);
        
        Book book = new Book(
                title,
                author,
                isbn,
                publicationYear,
                numberOfCopies);
        
        book.setAvailableCopies(availableCopies);
        
        return book;
    }
    /**
     * Serializes this book into a string
     * 
     * @return serialized representation of this book
     */
    public String toSerialized() {
        return title + "|"
                + author + "|"
                + isbn + "|"
                + publicationYear + "|"
                + numberOfCopies + "|"
                + availableCopies;
    }
}
