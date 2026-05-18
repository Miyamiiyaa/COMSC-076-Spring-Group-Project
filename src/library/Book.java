package library;

import java.util.Objects;

/**
 * Encapusulates information about a book.
 * 
 * @author our names
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
     * 
     * @throws IllegalArgumentException if numCopies is negative or greater than
     * total number of copies.
     * @param numCopies the number of available copies to set
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
     * 
     * @throws IllegalArgumentException if numCopiesToAdd is negative.
     * @param numCopiesToAdd the number of copies to add
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
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Checks in a book into the library.
     * 
     * @throws RuntimeException if no copies have been checked out.
     */
    public void checkin() {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Returns the hash code for this book.
     * 
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn);
    }

    /**
     * Checks if this book is equal to another object.
     * 
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
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

    /**
     * Returns a string representation of this book.
     * 
     * @return a string representation of the book
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Creates a book instance from a JSON string.
     * 
     * @param json the JSON string representing the book
     * @return a book instance
     */
    public static Book fromSerialized(String json) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Returns a JSON string representation of this book.
     * 
     * @return a JSON string representation of the book
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
