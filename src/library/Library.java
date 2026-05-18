package library;

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * A library management class. Has a simple shell that users can interact with
 * to add/remove/checkout/list books in the library. Also allows saving the
 * library state to a file and reloading it from the file.
 */
public class Library {

    // Using HashMap as the data structure for storing books in the library
    HashMap<String, Book> books = new HashMap<>(); // key is ISBN of the book

    /**
     * @return the number of books (not number of copies) in the library.
     */
    public int getNumberOfBooks() {
        return books.size(); // returns the number of books, excluding multiple
                             // copies of each book.
    }

    /**
     * Adds a book to the library. If the library already has this book then it
     * adds the number of copies the library has.
     */
    public void addBook(Book book) {
        String isbn = book.getISBN();

        if (books.containsKey(isbn)) {
            // book already exists, increment the number of copies
            Book existingBook = books.get(isbn);
            existingBook.addCopies(book.getNumberOfCopies());
        } else {
            // book doesnt exist in the library. Add it to the library.
            books.put(isbn, book);
        }
    }

   /**
 * Removes a book from the library using its ISBN.
 *
 * @param isbn the ISBN of the book to remove
 */
    public void removeBook(String isbn) {
        if (!books.containsKey(isbn)) {
            throw new java.util.NoSuchElementException(
                    "Book with ISBN " + isbn + " does not exist.");
        }

        books.remove(isbn);
    }

    /**
     * Checks out the given book from the library. Throw the appropriate
     * exception if book doesnt exist or there are no more copies available.
     */
    public void checkout(String isbn) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Returns a book to the library
     */
    public void returnBook(String isnb) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Finds this book in the library. Throws appropriate exception if the book
     * doesnt exist.
     */
    public Book findByTitleAndAuthor(String title, String author) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Finds this book in the library. Throws appropriate exception if the book
     * doesnt exist.
     */
    public Book findByISBN(String isbn) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Saves the contents of this library to the given file.
     */
    public void save(String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename);

            for (Book book : booksByIsbn.values()) {
                writer.println(book.toSerialized());
            }

            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(
                    "Could not save file: " + filename);
        }
    }
    
    /**
     * Loads the contents of this library from the given file. All existing data
     * in this library is cleared before loading from the file.
     */
    public void load(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            booksByIsbn.clear();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Book book = Book.fromSerialized(line);
                booksByIsbn.put(book.getIsbn(), book);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(
                    "Could not load file: " + filename);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.print("library> ");
            String line = scanner.nextLine();
            // TODO: Implement code
            if (line.startsWith("add")) {
                // The format of the line is
                // add title author isbn publicationYear numberOfCopies
                // e.g. add Star_Trek Gene_Roddenberry ISBN-1234 1965 10
                // NOTE: If a book already exists in the library, then the
                // number of copies should be incremented by this amount.

                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length != 6) {
                        System.out.println(
                                "Error: add format is add title author "
                                + "isbn publicationYear numberOfCopies");
                    } else {
                        String title = parts[1];
                        String author = parts[2];
                        String isbn = parts[3];

                        int publicationYear =
                                Integer.parseInt(parts[4]);

                        int numberOfCopies =
                                Integer.parseInt(parts[5]);

                        Book book = new Book(
                                title,
                                author,
                                isbn,
                                publicationYear,
                                numberOfCopies);

                        library.addBook(book);

                        System.out.println(
                                "Book added successfully.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Error: publication year and number "
                            + "of copies must be numbers.");
                } catch (RuntimeException e) {
                    System.out.println(
                            "Error: " + e.getMessage());
                }

            } else if (line.startsWith("remove")) {
                // Format of the line is
                // remove <isbn>
                // e.g. remove ISBN-1234

                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length != 2) {
                        System.out.println(
                                "Error: remove format is remove isbn");
                    } else {
                        String isbn = parts[1];

                        library.removeBook(isbn);

                        System.out.println(
                                "Book removed successfully.");
                    }

                } catch (RuntimeException e) {
                    System.out.println(
                            "Error: " + e.getMessage());
                }

            } else if (line.startsWith("checkout")) {
                // TODO: Implement this case.
                // The format of the line is
                // checkout isbn
                // e.g. checkout ISBN-1234
                // NOTE: If the book doesnt exist in the library, then the code
                // should print an error.

            } else if (line.startsWith("findByTitleAndAuthor")) {
                // TODO: Implement this case.
                // The format of the line is
                // findByTitleAndAuthor <title> <author>
                // e.g. findByTitleAndAuthor Star_Trek Gene_Roddenberry
                // NOTE: If the book doesnt exist in the library, then the code
                // should print an error.
                // If the book exists in the library, this code should print the
                // ISBN, number of copies in the library, and the number of
                // copies availabvle

            } else if (line.startsWith("return")) {
                // TODO: Implement this case.
                // Format of the line is
                // return <isbn>
                // e.g. return ISBN-1234
                // NOTE: If the book was never checked out, this code should
                // print an error.

            } else if (line.startsWith("list")) {
                // TODO: Implement this case.
                // Format of the line is
                // list <isnb>
                // e.g. list ISBN-1234
                // NOTE: This code should print out the number of copies in the
                // library and the number of copies available.

            } else if (line.startsWith("save")) {
                // TODO: Implement this case.
                // Format of the line is
                // save <filename>
                // e.g. save LbraryFile.dat

                String[] parts = line.split(" ");
                String filename = parts[1];

                if (!filename.endsWith(".json")) {
                    filename += ".json";
                }
                library.save(filename);

            } else if (line.startsWith("load")) {
                // TODO: Implement this case.
                // Format of the line is:
                // load <filename>
                // e.g. load LibraryFile.dat
                String[] parts = line.split(" ");
                String filename = parts[1];

                if (!filename.endsWith(".json")) {
                    filename += ".json";
                }

                library.load(filename);

            } else if (line.startsWith("exit")) {
                break;
            }
        }
    }
}
