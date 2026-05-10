
/**
 * This is a set of unit tests for the library class
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

@DisplayName("All Unit Tests for the Project")
public class LibraryTests {

    @BeforeAll
    static void initAll() {

    }

    @BeforeEach
    void init() {
    }

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

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void tearDownAll() {

    }

}