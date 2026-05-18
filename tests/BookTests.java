
/**
 * This is a set of Unit tests that cover adding, deleting, check in/out, and searching of books.
 * 
 * Used Resources:
 * https://docs.junit.org/6.0.3/overview.html
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterAll;

import library.Book;

@DisplayName("All Unit Tests for the Project")
public class BookTests {

    private static final String TITLE = "Valid_Title";
    private static final String AUTHOR = "Valid_Author";
    private static final String ISBN = "ISBN-1234";
    private static final int YEAR = 2000;
    private static final int COPIES = 10;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(TITLE, AUTHOR, ISBN, YEAR, COPIES);
    }

    @Test @DisplayName("Test Good Book Constructor")
    void testGoodBookConstructor() {
        assertEquals(TITLE, book.getTitle(),
                "Book title should be set correctly");
        assertEquals(AUTHOR, book.getAuthor(),
                "Book author should be set correctly");
        assertEquals(ISBN, book.getISBN(),
                "Book ISBN should be set correctly");
        assertEquals(YEAR, book.getPublicationYear(),
                "Book year should be set correctly");
        assertEquals(COPIES, book.getNumberOfCopies(),
                "Book copies should be set correctly");
        assertEquals(COPIES, book.getAvailableCopies(),
                "Available copies should be initialized to total copies");
    }

    @Test @DisplayName("Enforce non-null / non-empty constructor for Book")
    void testBookConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(null, AUTHOR, ISBN, YEAR, COPIES),
                "Null titles should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> new Book("", AUTHOR, ISBN, YEAR, COPIES),
                "Empty titles should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> new Book(TITLE, null, ISBN, YEAR, COPIES),
                "Null authors should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> new Book(TITLE, "", ISBN, YEAR, COPIES),
                "Empty authors should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> new Book(TITLE, AUTHOR, null, YEAR, COPIES),
                "Null ISBN should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> new Book(TITLE, AUTHOR, "", YEAR, COPIES),
                "Empty ISBN should be rejected");
    }

    @Test @DisplayName("Enforce reasonable book year")
    void testBookYear() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(TITLE, AUTHOR, ISBN, -1, COPIES),
                "negative year should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> new Book(TITLE, AUTHOR, ISBN, 0, COPIES),
                "year 0 should be rejected");
    }

    @Test @DisplayName("Enforce >= 0 no. Copies")
    void testPlausibleCopies() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(TITLE, AUTHOR, ISBN, YEAR, -1),
                "negative copies should be rejected");

        // Zero copies should be allowed
        Book zero = new Book(TITLE, AUTHOR, ISBN, YEAR, 0);
        assertEquals(0, zero.getNumberOfCopies(),
                "number of copies should be set to 0");
        assertEquals(0, zero.getAvailableCopies(),
                "available copies should be initialized to total copies, even if 0");
    }

    /* Do we test ISBN format? */
    // @Test @DisplayName("Enforce plausible ISBN")
    // void testPlausibleISBN() {}

    @Test @DisplayName("Test setting available copies properly")
    void testSetCopies() {
        book.setAvailableCopies(5);
        assertEquals(5, book.getAvailableCopies(),
                "setAvailableCopies should update available copies");
        assertEquals(COPIES, book.getNumberOfCopies(),
                "setAvailableCopies must not affect total copies");

        // ensure available copies cannot be set outside of 0 and total copies
        book.setAvailableCopies(0);
        assertEquals(0, book.getAvailableCopies(),
                "available copies should be able to be set to 0");
        book.setAvailableCopies(COPIES);
        assertEquals(COPIES, book.getAvailableCopies(),
                "available copies should be able to be set to total copies");

        // Out of range should throw.
        assertThrows(IllegalArgumentException.class,
                () -> book.setAvailableCopies(-1),
                "negative available copies should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> book.setAvailableCopies(COPIES + 1),
                "available > total should be rejected");
    }

    @Test @DisplayName("Test proper checkout")
    void testProperCheckout() {
        book.checkout();
        assertEquals(COPIES - 1, book.getAvailableCopies(),
                "checkout should decrement available copies");
        assertEquals(COPIES, book.getNumberOfCopies(),
                "checkout must not change total copies");
    }

    @Test @DisplayName("Test available copies increments down on check out")
    void testCheckoutIncrement() {
        for (int i = 1; i <= 3; i++) {
            book.checkout();
            assertEquals(COPIES - i, book.getAvailableCopies(),
                    "checkout should decrement available copies");
        }
    }

    @Test @DisplayName("Test check out an unavailable book")
    void testNoAvailableCheckout() {
        book.setAvailableCopies(0);
        assertThrows(RuntimeException.class, () -> book.checkout(),
                "checkout with no available copies should throw");
    }

    @Test @DisplayName("Test proper check in")
    void testProperCheckIn() {
        book.checkout();
        book.checkin();
        assertEquals(COPIES, book.getAvailableCopies(),
                "checkin should increment available copies");
        assertEquals(COPIES, book.getNumberOfCopies(),
                "checkin must not change total copies");
    }

    @Test @DisplayName("Test available copies increments up on check in")
    void testCheckInIncrement() {
        book.checkout();
        book.checkout();

        for (int i = 2; i >= 0; i--) {
            book.checkin();
            assertEquals(COPIES - i, book.getAvailableCopies(),
                    "checkin should increment available copies");
        }
    }

    @Test @DisplayName("Test check in when none are checked out")
    void testMoreCopiesCheckIn() {
        // Error should be thrown if we try to check in more copies than total
        // since should be no copies checked out.
        assertThrows(RuntimeException.class, () -> book.checkin(),
                "checkin with no copies checked out should throw");
    }

    @Test @DisplayName("Test adding new copies")
    void testAddCopiesProper() {
        book.addCopies(5);
        assertEquals(COPIES + 5, book.getNumberOfCopies(),
                "addCopies should increase total copies");
        assertEquals(COPIES + 5, book.getAvailableCopies(),
                "addCopies should increase available copies");
    }

    @Test @DisplayName("Test adding negative copies")
    void testAddNegativeCopies() {
        assertThrows(IllegalArgumentException.class,
                () -> book.addCopies(-1),
                "addCopies must reject negative input");
        // State should be unchanged after the failed call.
        assertEquals(COPIES, book.getNumberOfCopies(),
                "addCopies should not change total copies on failure");
        assertEquals(COPIES, book.getAvailableCopies(),
                "addCopies should not change available copies on failure");
    }

    @Test @DisplayName("Test Book Equality")
    void testBookDoesEqual() {
        // copy counts don't matter for equality
        Book sameIdentity = new Book(TITLE, AUTHOR, ISBN, YEAR,
                COPIES + 5);
        sameIdentity.setAvailableCopies(0);
        assertEquals(book, sameIdentity,
                "books with the same identity fields should be equal "
                        + "regardless of copy counts");
    }

    @Test @DisplayName("Test Book Inequality")
    void testBookDoesNotEqual() {
        Book diffTitle = new Book("Other_Title", AUTHOR, ISBN, YEAR,
                COPIES);
        Book diffAuthor = new Book(TITLE, "Other_Author", ISBN, YEAR,
                COPIES);
        Book diffIsbn = new Book(TITLE, AUTHOR, "ISBN-9999", YEAR, COPIES);
        Book diffYear = new Book(TITLE, AUTHOR, ISBN, YEAR + 1, COPIES);

        assertNotEquals(book, diffTitle,
                "books with different titles should not be equal");
        assertNotEquals(book, diffAuthor,
                "books with different authors should not be equal");
        assertNotEquals(book, diffIsbn,
                "books with different ISBNs should not be equal");
        assertNotEquals(book, diffYear,
                "books with different years should not be equal");
    }

    @Test @DisplayName("Test that equal objects have same hash")
    void testHashCodeEquality() {
        // ignore copy counts
        Book sameIdentity = new Book(TITLE, AUTHOR, ISBN, YEAR,
                COPIES + 5);
        sameIdentity.setAvailableCopies(2);
        assertEquals(book.hashCode(), sameIdentity.hashCode(),
                "equal books must have the same hashCode");
    }

    @Test @DisplayName("Test compare book with Null or unrelated type")
    void testNullBookEquality() {
        assertNotEquals(book, null, "a Book should never equal null");
        assertNotEquals(book, "not a book",
                "a Book should not equal an unrelated type");
    }

    @Test @DisplayName("Test proper book string print out")
    void testBooktoString() {
        String s = book.toString();
        assertNotNull(s);

        assertTrue(s.contains(TITLE), "toString should include the title");
        assertTrue(s.contains(AUTHOR),
                "toString should include the author");
        assertTrue(s.contains(ISBN), "toString should include the ISBN");
        assertTrue(s.contains(String.valueOf(YEAR)),
                "toString should include the publication year");
        assertTrue(s.contains(String.valueOf(COPIES)),
                "toString should include the number of copies");
        assertFalse(s.matches("library\\.Book@[0-9a-f]+"),
                "toString must be overridden, not inherited from Object");
    }

    @Test @DisplayName("Test toSerialized produces non-empty JSON with all fields")
    void testToSerialized() {
        String json = book.toSerialized();
        assertNotNull(json);
        assertFalse(json.isEmpty(), "serialized output must not be empty");

        assertTrue(json.contains(TITLE),
                "serialized form should include title");
        assertTrue(json.contains(AUTHOR),
                "serialized form should include author");
        assertTrue(json.contains(ISBN),
                "serialized form should include isbn");
        assertTrue(json.contains(String.valueOf(YEAR)),
                "serialized form should include publication year");
        assertTrue(json.contains(String.valueOf(COPIES)),
                "serialized form should include number of copies");
    }

    @Test @DisplayName("Test serdes round-trip preserves all fields")
    void testSerializationRoundTrip() {
        // ensure available copies is preserved.
        book.setAvailableCopies(3);

        String json = book.toSerialized();
        Book restored = Book.fromSerialized(json);

        assertEquals(book.getTitle(), restored.getTitle(),
                "title must survive a serdes round-trip");
        assertEquals(book.getAuthor(), restored.getAuthor(),
                "author must survive a serdes round-trip");
        assertEquals(book.getISBN(), restored.getISBN(),
                "isbn must survive a serdes round-trip");
        assertEquals(book.getPublicationYear(),
                restored.getPublicationYear(),
                "publication year must survive a serdes round-trip");
        assertEquals(book.getNumberOfCopies(),
                restored.getNumberOfCopies(),
                "numberOfCopies must survive a serdes round-trip");
        assertEquals(book.getAvailableCopies(),
                restored.getAvailableCopies(),
                "availableCopies must survive a serdes round-trip");

        assertEquals(book, restored);
    }

    @Test @DisplayName("Test fromSerialized rejects malformed input")
    void testFromSerializedMalformed() {
        assertThrows(IllegalArgumentException.class,
                () -> Book.fromSerialized(null),
                "null input should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> Book.fromSerialized(""),
                "empty input should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> Book.fromSerialized("not json at all"),
                "non-JSON input should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> Book.fromSerialized("{\"title\": \"only_title\"}"),
                "JSON missing required fields should be rejected");
    }

    @Test @DisplayName("Test serdes must handle escaping special characters")
    void testSerializationSpecialChars() {
        Book tricky = new Book("Title with \"quotes\" and \\ backslash",
                "Author, with commas", "ISBN-special", YEAR, COPIES);
        Book restored = Book.fromSerialized(tricky.toSerialized());

        assertEquals(tricky.getTitle(), restored.getTitle(),
                "title with special characters should survive serdes");
        assertEquals(tricky.getAuthor(), restored.getAuthor(),
                "author with special characters should survive serdes");
        assertEquals(tricky.getISBN(), restored.getISBN(),
                "isbn with special characters should survive serdes");
    }
}