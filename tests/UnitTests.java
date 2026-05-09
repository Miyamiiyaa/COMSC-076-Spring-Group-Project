
/**
 * This is a set of Unit tests that cover adding, deleting, check in/out, and searching of books.
 * 
 * Used Resources:
 * https://docs.junit.org/6.0.3/overview.html
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Nested;
import src.Book;

@DisplayName("All Unit Tests for the Project")
public class UnitTests {

    @BeforeAll
    static void initAll() {

    }

    @BeforeEach
    void init() {

    }

    @Nested
    class BookTests {

        @Test @DisplayName("Enforce non-null / non-empty constructor for Book")
        void testBookConstructor() {

        }

        @Test @DisplayName("Enforce reasonable book year")
        void testBookYear() {

        }

        @Test @DisplayName("Enforce >= 0 no. Copies")
        void testPlausibleCopies() {

        }

        /* Do we test ISBN format? */
        // @Test @DisplayName("Enforce plausible ISBN")
        // void testPlausibleISBN() {}

        @Test @DisplayName("Test getting proper available copies ")
        void testGetCopies() {

        }

        @Test @DisplayName("Test setting available copies properly")
        void testSetCopies() {

        }

        @Test @DisplayName("Test proper checkout")
        void testProperCheckout() {

        }

        @Test @DisplayName("Test available copies increments down on check out")
        void testCheckoutIncrement() {

        }

        @Test @DisplayName("Test check out an unavailable book")
        void testNoAvailableCheckout() {

        }

        @Test @DisplayName("Test proper check in book")
        void testProperCheckIn() {

        }

        @Test @DisplayName("Test available copies increments up on check in")
        void testCheckInIncrement() {

        }

        @Test @DisplayName("Test check in when none are checked out")
        void testMoreCopiesCheckIn() {

        }

        @Test @DisplayName("Test adding new copies")
        void testAddCopiesProper() {

        }

        @Test @DisplayName("Test adding negative copies")
        void testAddNegativeCopies() {

        }

        @Test @DisplayName("Test Book Equality")
        void testBookDoesEqual() {

        }

        @Test @DisplayName("Test Book Inequality")
        void testBookDoesNotEqual() {

        }

        @Test @DisplayName("Test that equal objects have same hash")
        void testHashCodeEquality() {

        }

        @Test @DisplayName("Test compare book with Null")
        void testNullBookEquality() {

        }

        @Test @DisplayName("Test proper book string print out")
        void testBooktoString() {

        }

    }

    @Nested
    class LibraryTests {
        @Test @DisplayName("Test adding a new unique book")
        void testAddNewBook() {

        }

        /* Agree on limits */
        @Test @DisplayName("Test adding Adversal Book: Too Long")
        void testAddLongBook() {

        }

        /* Agree on limits */
        @Test @DisplayName("Test add Adversal Book: Junk Data")
        void testAddJunk() {

        }

        @Test @DisplayName("Test add Adversal Book: Null book")
        void testAddNull() {

        }

        @Test @DisplayName("Test adding an existing book increments copies and doesn't create duplicate")
        void testAddExistingBook() {

        }

        @Test @DisplayName("Test finding a book by title searches properly")
        void testFindTitlebyAuthorProper() {

        }

        @Test @DisplayName("Test find by author when it doesn't exist")
        void testFindByAuthorNegative() {

        }

        @Test @DisplayName("Test finding a null author throws proper exception")
        void testFindByAuthorNull() {
        }

        @Test @DisplayName("Test find by ISBN searches properly")
        void testFindbyISBNProper() {

        }

        @Test @DisplayName("Test find by ISBN when it doesn't exist")
        void testFindByISBNNegative() {

        }

        @Test @DisplayName("Test finding a null ISBN throws proper exception")
        void testFindByISBNNull() {
        }

    }

    @Nested
    class IOTests {

    }

    @Nested
    class SerializationTests {

    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void tearDownAll() {

    }

}