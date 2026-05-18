package library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A library management class. Has a simple shell that users can interact with
 * to add/remove/checkout/list books in the library. Also allows saving the
 * library state to a file and reloading it from the file.
 *
 * @author Balaji Srinivasan
 */
public class Library {

    HashMap<String, Book> books = new HashMap<>();

    /**
     * @return the number of books, not the number of copies, in the library
     */
    public int getNumberOfBooks() {
        return books.size();
    }

    /**
     * Adds a book to the library. If the library already has this book then it
     * adds the number of copies the library has.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if book is null or the ISBN already
     *         belongs to a different book
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }

        String isbn = book.getISBN();

        if (books.containsKey(isbn)) {
            Book existingBook = books.get(isbn);

            if (!existingBook.equals(book)) {
                throw new IllegalArgumentException(
                        "ISBN already belongs to a different book");
            }

            existingBook.addCopies(book.getNumberOfCopies());
        } else {
            books.put(isbn, book);
        }
    }

    /**
     * Removes the book with the given ISBN from the library.
     *
     * @param isbn the ISBN of the book to remove
     */
    public void removeBook(String isbn) {
        findByISBN(isbn);
        books.remove(isbn);
    }

    /**
     * Checks out one copy of the book with the given ISBN.
     *
     * @param isbn the ISBN of the book to check out
     */
    public void checkout(String isbn) {
        findByISBN(isbn).checkout();
    }

    /**
     * Returns one copy of the book with the given ISBN.
     *
     * @param isbn the ISBN of the book to return
     */
    public void returnBook(String isbn) {
        findByISBN(isbn).checkin();
    }

    /**
     * Finds this book in the library. O(n) time complexity because it must
     * traverse the entire library.
     *
     * @param title the title of the book to find
     * @param author the author of the book to find
     * @return the book if both the title and author match
     */
    public Book findByTitleAndAuthor(String title, String author) {
        if (title == null || author == null) {
            throw new IllegalArgumentException(
                    "Title and Author must not be null");
        }

        for (Book book : books.values()) {
            if (book.getTitle().equals(title)
                    && book.getAuthor().equals(author)) {
                return book;
            }
        }

        throw new java.util.NoSuchElementException("Book with title "
                + title + " and author " + author + " does not exist.");
    }

    /**
     * Finds a book in the library by its ISBN.
     *
     * @param isbn the ISBN to search for
     * @return the Book with the given ISBN
     */
    public Book findByISBN(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("isbn must not be null");
        }

        if (!books.containsKey(isbn)) {
            throw new java.util.NoSuchElementException(
                    "Book with ISBN " + isbn + " does not exist.");
        }

        return books.get(isbn);
    }

    /**
     * Saves the contents of this library to the given file.
     *
     * @param filename the name of the file to save to
     */
    public void save(String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename);

            for (Book book : books.values()) {
                writer.println(book.toSerialized());
            }

            writer.close();
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(
                    "Could not save file: " + filename, e);
        }
    }

    /**
     * Loads the contents of this library from the given file. All existing data
     * in this library is cleared before loading from the file.
     *
     * @param filename the name of the file to load from
     */
    public void load(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            books.clear();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Book book = Book.fromSerialized(line);
                books.put(book.getISBN(), book);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(
                    "Could not load file: " + filename, e);
        }
    }

    /**
     * The main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        boolean keepRunning = true;

        while (keepRunning) {
            System.out.print("library> ");
            String line = scanner.nextLine().trim();

            if (line.startsWith("add")) {
                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length != 6) {
                        System.out.println(
                                "Error: add format is add title author "
                                        + "isbn publicationYear "
                                        + "numberOfCopies");
                    } else {
                        String title = parts[1];
                        String author = parts[2];
                        String isbn = parts[3];

                        int publicationYear = Integer.parseInt(parts[4]);
                        int numberOfCopies = Integer.parseInt(parts[5]);

                        Book book = new Book(title, author, isbn,
                                publicationYear, numberOfCopies);

                        library.addBook(book);

                        System.out.println("Book added successfully.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Error: publication year and number "
                                    + "of copies must be numbers.");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (line.startsWith("remove")) {
                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length != 2) {
                        System.out.println(
                                "Error: remove format is remove isbn");
                    } else {
                        library.removeBook(parts[1]);

                        System.out.println(
                                "Book removed successfully.");
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (line.startsWith("checkout")) {
                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length != 2) {
                        System.out.println(
                                "Error: checkout format is "
                                        + "checkout isbn");
                    } else {
                        library.checkout(parts[1]);

                        System.out.println(
                                "Book checked out successfully.");
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (line.startsWith("findByTitleAndAuthor")) {
                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length != 3) {
                        System.out.println(
                                "Error: findByTitleAndAuthor "
                                        + "format is "
                                        + "findByTitleAndAuthor "
                                        + "title author");
                    } else {
                        Book book = library.findByTitleAndAuthor(
                                parts[1], parts[2]);

                        System.out.println(book);
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (line.startsWith("return")) {
                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length != 2) {
                        System.out.println(
                                "Error: return format is return isbn");
                    } else {
                        library.returnBook(parts[1]);

                        System.out.println(
                                "Book returned successfully.");
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (line.startsWith("list")) {
                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length != 2) {
                        System.out.println(
                                "Error: list format is list isbn");
                    } else {
                        Book book = library.findByISBN(parts[1]);

                        System.out.println(book);
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (line.startsWith("save")) {
                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length != 2) {
                        System.out.println(
                                "Error: save format is "
                                        + "save filename");
                    } else {
                        String filename = parts[1];

                        if (!filename.endsWith(".json")) {
                            filename += ".json";
                        }

                        library.save(filename);

                        System.out.println(
                                "Library saved successfully.");
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (line.startsWith("load")) {
                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length != 2) {
                        System.out.println(
                                "Error: load format is "
                                        + "load filename");
                    } else {
                        String filename = parts[1];

                        if (!filename.endsWith(".json")) {
                            filename += ".json";
                        }

                        library.load(filename);

                        System.out.println(
                                "Library loaded successfully.");
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (line.startsWith("exit")) {
                keepRunning = false;

            } else if (line.length() == 0) {
                System.out.println(
                        "Error: please enter a command.");

            } else {
                System.out.println(
                        "Error: unknown command");
            }
        }

        scanner.close();
    }
}
