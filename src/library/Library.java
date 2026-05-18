public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Library library = new Library();

    while (true) {
        System.out.print("library> ");
        String line = scanner.nextLine().trim();

        if (line.isEmpty()) {
            System.out.println("Error: please enter a command.");
        } else {
            String[] parts = line.split("\\s+");
            String command = parts[0];

            if (command.equals("add")) {
                try {
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

            } else if (command.equals("remove")) {
                try {
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

            } else if (command.equals("checkout")) {
                // TODO: Implement this case.

            } else if (command.equals("findByTitleAndAuthor")) {
                // TODO: Implement this case.

            } else if (command.equals("return")) {
                // TODO: Implement this case.

            } else if (command.equals("list")) {
                // TODO: Implement this case.

            } else if (command.equals("save")) {
                try {
                    if (parts.length != 2) {
                        System.out.println(
                                "Error: save format is save filename");
                    } else {
                        String filename = parts[1];

                        if (!filename.endsWith(".json")) {
                            filename += ".json";
                        }

                        library.save(filename);
                        System.out.println("Library saved.");
                    }
                } catch (RuntimeException e) {
                    System.out.println(
                            "Error: " + e.getMessage());
                }

            } else if (command.equals("load")) {
                try {
                    if (parts.length != 2) {
                        System.out.println(
                                "Error: load format is load filename");
                    } else {
                        String filename = parts[1];

                        if (!filename.endsWith(".json")) {
                            filename += ".json";
                        }

                        library.load(filename);
                        System.out.println("Library loaded.");
                    }
                } catch (RuntimeException e) {
                    System.out.println(
                            "Error: " + e.getMessage());
                }

            } else if (command.equals("exit")) {
                break;

            } else {
                System.out.println("Error: unknown command.");
            }
        }
    }

    scanner.close();
}
