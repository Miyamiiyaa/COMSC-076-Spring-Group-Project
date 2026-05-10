
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
import library.Book;

@DisplayName("All Unit Tests for the Project")
public class BookTests {

    @BeforeAll
    static void initAll() {

    }

    @BeforeEach
    void init() {

    }

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

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void tearDownAll() {

    }

}