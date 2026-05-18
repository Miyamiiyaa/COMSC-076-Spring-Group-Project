
/**
 * This is a set of unit tests for the Library class. Covers add/remove,
 * checkout/return, find methods, getNumberOfBooks, and save/load
 * integration.
 * 
 * Tests assume IllegalArgumentException for invalid
 * input and NoSuchElementException for "not found" cases.
 *
 * Used Resources:
 * https://docs.junit.org/6.0.3/overview.html
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import library.Book;
import library.Library;

@DisplayName("All Unit Tests for Library")
public class LibraryTests {

    // sample book 1
    private static final String TITLE = "Valid_Title";
    private static final String AUTHOR = "Valid_Author";
    private static final String ISBN = "ISBN-1234";
    private static final int YEAR = 2000;
    private static final int COPIES = 10;

    // sample book 2
    private static final String TITLE_2 = "Other_Title";
    private static final String AUTHOR_2 = "Other_Author";
    private static final String ISBN_2 = "ISBN-9999";
    private static final int YEAR_2 = 1985;
    private static final int COPIES_2 = 3;

    private Library library;
    private Book book;
    private Book otherBook;

    @BeforeEach
    void init() {
        library = new Library();
        book = new Book(TITLE, AUTHOR, ISBN, YEAR, COPIES);
        otherBook = new Book(TITLE_2, AUTHOR_2, ISBN_2, YEAR_2, COPIES_2);
    }

    @Test @DisplayName("Empty library has zero books")
    void testEmptyLibrary() {
        assertEquals(0, library.getNumberOfBooks(),
                "a fresh library should have no books");
    }

    @Test @DisplayName("getNumberOfBooks counts unique titles, not copies")
    void testNumberOfBooksCountsUnique() {
        library.addBook(book);
        library.addBook(otherBook);
        assertEquals(2, library.getNumberOfBooks(),
                "two distinct books should give a count of 2");

        // adding more copies doesnt change unique book count
        Book moreCopiesOfSameBook = new Book(TITLE, AUTHOR, ISBN, YEAR, 5);
        library.addBook(moreCopiesOfSameBook);
        assertEquals(2, library.getNumberOfBooks(),
                "adding copies of an existing book must not increase the "
                        + "unique-book count");
    }

    @Test @DisplayName("Test adding a new unique book")
    void testAddNewBook() {
        library.addBook(book);
        assertEquals(1, library.getNumberOfBooks(),
                "adding one book should give a count of 1");

        // also can be retrieved
        assertEquals(book, library.findByISBN(ISBN),
                "the added book should be findable by its ISBN");
    }

    @Test @DisplayName("Test add Adversal Book: Null book")
    void testAddNull() {
        assertThrows(IllegalArgumentException.class,
                () -> library.addBook(null),
                "adding a null book should be rejected");
        assertEquals(0, library.getNumberOfBooks(),
                "failed add should not change library");
    }

    @Test @DisplayName("Test adding an existing book increments copies "
            + "and doesn't create duplicate")
    void testAddExistingBook() {
        library.addBook(book);
        assertEquals(COPIES, library.findByISBN(ISBN).getNumberOfCopies());

        // match equality convention in Book (same title, author, ISBN but with
        // a different copy count.
        Book sameIdentityMoreCopies = new Book(TITLE, AUTHOR, ISBN, YEAR,
                7);
        library.addBook(sameIdentityMoreCopies);

        Book found = library.findByISBN(ISBN);
        assertEquals(1, library.getNumberOfBooks(),
                "adding a book with the same identity must not create a "
                        + "duplicate entry");
        assertEquals(book, found,
                "the stored book should equal the original");
        assertEquals(COPIES + 7, found.getNumberOfCopies(),
                "copies should be summed when adding the same book again");
    }

    @Test @DisplayName("Adding a book with an existing ISBN but mismatched "
            + "identity should be rejected")
    void testDuplicateIsbnDifferentBook() {
        library.addBook(book);

        // Same isbn different authors or titles should be rejected.
        Book collision = new Book("Wrong_Title", "Wrong_Author", ISBN,
                YEAR, 5);

        assertThrows(IllegalArgumentException.class,
                () -> library.addBook(collision),
                "addBook should reject an ISBN collision with mismatched "
                        + "identity");

        Book found = library.findByISBN(ISBN);
        assertEquals(book, found,
                "rejected add must not modify the existing book");
        assertEquals(COPIES, found.getNumberOfCopies(),
                "rejected add must not change copy count");
    }

    @Test @DisplayName("Removing an existing book"
            + "decreases the unique book count")
    void testRemoveExistingBook() {
        library.addBook(book);
        library.addBook(otherBook);
        assertEquals(2, library.getNumberOfBooks());

        library.removeBook(ISBN);
        assertEquals(1, library.getNumberOfBooks(),
                "removing a book should decrease the count by 1");
        assertThrows(NoSuchElementException.class,
                () -> library.findByISBN(ISBN),
                "the removed book should no longer be findable");
    }

    @Test @DisplayName("Removing a non-existent ISBN throws")
    void testRemoveMissingBook() {
        // check on empty lib
        assertThrows(NoSuchElementException.class,
                () -> library.removeBook(ISBN),
                "removing a non-existent ISBN should throw "
                        + "NoSuchElementException");

        // check on non-empty lib
        library.addBook(otherBook);
        assertThrows(NoSuchElementException.class,
                () -> library.removeBook(ISBN),
                "removing a missing ISBN should throw even when library "
                        + "is non-empty");
        assertEquals(1, library.getNumberOfBooks(),
                "failed remove must not change library");
    }

    @Test @DisplayName("Removing with null ISBN throws")
    void testRemoveNullIsbn() {
        assertThrows(IllegalArgumentException.class,
                () -> library.removeBook(null),
                "removing with a null ISBN should be rejected");
    }

    @Test @DisplayName("Checkout decrements available copies")
    void testCheckoutProper() {
        library.addBook(book);
        library.checkout(ISBN);

        Book found = library.findByISBN(ISBN);
        assertEquals(COPIES - 1, found.getAvailableCopies(),
                "checkout should decrement available copies");
        assertEquals(COPIES, found.getNumberOfCopies(),
                "checkout must not change total copies");
    }

    @Test @DisplayName("Checkout on non-existent"
            + " ISBN throws NoSuchElementException")
    void testCheckoutMissingBook() {
        assertThrows(NoSuchElementException.class,
                () -> library.checkout(ISBN),
                "checkout of a non-existent book should throw "
                        + "NoSuchElementException");
    }

    @Test @DisplayName("Checkout when no copies available throws")
    void testCheckoutNoCopiesAvailable() {
        library.addBook(book);
        library.findByISBN(ISBN).setAvailableCopies(0);

        assertThrows(IllegalStateException.class,
                () -> library.checkout(ISBN),
                "checkout with zero available copies should throw "
                        + "IllegalStateException");
    }

    @Test @DisplayName("Checkout with null ISBN throws")
    void testCheckoutNullIsbn() {
        assertThrows(IllegalArgumentException.class,
                () -> library.checkout(null),
                "checkout with null ISBN should be rejected");
    }

    @Test @DisplayName("ReturnBook increments available copies")
    void testReturnBookProper() {
        library.addBook(book);
        library.checkout(ISBN);
        library.returnBook(ISBN);

        Book found = library.findByISBN(ISBN);
        assertEquals(COPIES, found.getAvailableCopies(),
                "returnBook should restore available copies");
    }

    @Test @DisplayName("ReturnBook on non-existent ISBN throws")
    void testReturnMissingBook() {
        assertThrows(NoSuchElementException.class,
                () -> library.returnBook(ISBN),
                "returning a non-existent book should throw "
                        + "NoSuchElementException");
    }

    @Test @DisplayName("ReturnBook when none are checked out throws")
    void testReturnNoneCheckedOut() {
        library.addBook(book);
        assertThrows(IllegalStateException.class,
                () -> library.returnBook(ISBN),
                "returnBook when no copies are checked out should throw "
                        + "IllegalStateException");
    }

    @Test @DisplayName("ReturnBook with null ISBN throws")
    void testReturnBookNullIsbn() {
        assertThrows(IllegalArgumentException.class,
                () -> library.returnBook(null),
                "returnBook with null ISBN should be rejected");
    }

    @Test @DisplayName("Test find by ISBN searches properly")
    void testFindbyISBNProper() {
        library.addBook(book);
        library.addBook(otherBook);

        Book found = library.findByISBN(ISBN);
        assertEquals(book, found,
                "findByISBN should return the stored Book");
    }

    @Test @DisplayName("Test find by ISBN when it doesn't exist")
    void testFindByISBNNegative() {
        library.addBook(otherBook);
        assertThrows(NoSuchElementException.class,
                () -> library.findByISBN(ISBN),
                "findByISBN should throw when the ISBN is not present");
    }

    @Test @DisplayName("Test finding a null ISBN throws proper exception")
    void testFindByISBNNull() {
        assertThrows(IllegalArgumentException.class,
                () -> library.findByISBN(null),
                "findByISBN(null) should be rejected with "
                        + "IllegalArgumentException");
    }

    @Test @DisplayName("Test find by title and author searches properly")
    void testFindByTitleAndAuthorProper() {
        library.addBook(book);
        library.addBook(otherBook);

        Book found = library.findByTitleAndAuthor(TITLE, AUTHOR);
        assertEquals(book, found,
                "findByTitleAndAuthor should return the stored Book");
    }

    @Test @DisplayName("Test find by title and author when it doesn't exist")
    void testFindByTitleAndAuthorNegative() {
        library.addBook(otherBook);
        assertThrows(NoSuchElementException.class,
                () -> library.findByTitleAndAuthor(TITLE, AUTHOR),
                "findByTitleAndAuthor should throw when no match exists");

        // Partial match should miss
        library.addBook(book);
        assertThrows(NoSuchElementException.class,
                () -> library.findByTitleAndAuthor(TITLE, "Wrong_Author"),
                "require both title and author"
                        + " to match in findByTitleAndAuthor");
        assertThrows(NoSuchElementException.class,
                () -> library.findByTitleAndAuthor("Wrong_Title", AUTHOR),
                "require both title and author"
                        + " to match in findByTitleAndAuthor");
    }

    @Test @DisplayName("Test finding with null title or author throws")
    void testFindByTitleAndAuthorNull() {
        assertThrows(IllegalArgumentException.class,
                () -> library.findByTitleAndAuthor(null, AUTHOR),
                "null title should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> library.findByTitleAndAuthor(TITLE, null),
                "null author should be rejected");
    }

    // INTEGRATION TESTS FOR SAVE/LOAD TO JSON FILE //

    @Test @DisplayName("Save then load preserves multiple books")
    void testSaveLoadRoundTrip(@TempDir Path tempDir) {
        library.addBook(book);
        library.addBook(otherBook);

        String filename = tempDir.resolve("testlibrary.json").toString();
        library.save(filename);

        Library loaded = new Library();
        loaded.load(filename);

        assertEquals(2, loaded.getNumberOfBooks(),
                "all books should be present after load");
        assertEquals(book, loaded.findByISBN(ISBN),
                "original book should be findable after load");
        assertEquals(otherBook, loaded.findByISBN(ISBN_2),
                "other book should be findable after load");
        assertEquals(filename, loaded);
        assertEquals(book, loaded.findByISBN(ISBN),
                "restored book should equal the original");
    }

    @Test @DisplayName("Load clears any existing data first")
    void testLoadClearsExisting(@TempDir Path tempDir) {
        library.addBook(book);
        String filename = tempDir.resolve("testlibrary.json").toString();
        library.save(filename);

        // make new library and load over it
        Library target = new Library();
        target.addBook(otherBook);
        assertEquals(1, target.getNumberOfBooks());

        target.load(filename);

        assertEquals(1, target.getNumberOfBooks(),
                "load should clear existing data before populating");
        assertEquals(book, target.findByISBN(ISBN));
        assertThrows(NoSuchElementException.class,
                () -> target.findByISBN(ISBN_2),
                "pre-existing books should be cleared by load");
    }

    @Test @DisplayName("Save then load on empty"
            + " library produces empty library")
    void testSaveLoadEmptyLibrary(@TempDir Path tempDir) {
        String filename = tempDir.resolve("testlibrary.json").toString();
        library.save(filename);

        Library loaded = new Library();
        loaded.load(filename);

        assertEquals(0, loaded.getNumberOfBooks(),
                "loading an empty save file should give an empty library");
    }

    @Test @DisplayName("Load from missing file throws")
    void testLoadMissingFile(@TempDir Path tempDir) {
        String missing = tempDir.resolve("fake.json").toString();
        assertThrows(java.io.UncheckedIOException.class,
                () -> library.load(missing),
                "loading a non-existent file should throw "
                        + "UncheckedIOException");
    }

    @Test @DisplayName("Load from broken file throws")
    void testLoadMalformedFile(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("broken.json");
        java.nio.file.Files.writeString(file,
                "this is not a valid serialized library\n");

        assertThrows(IllegalArgumentException.class,
                () -> library.load(file.toString()),
                "loading a malformed file should throw "
                        + "IllegalArgumentException");
    }
}