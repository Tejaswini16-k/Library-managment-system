package controller;

import model.Book;
import view.LibraryView;

import java.io.*;
import java.util.*;

public class LibraryController {
    private static final String FILE_NAME = "books.txt";
    private LibraryView view = new LibraryView();

    public void start() {
        while (true) {
            int choice = view.displayMenu();
            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> removeBook();
                case 4 -> {
                    view.showMessage("Exiting...");
                    return;
                }
                default -> view.showMessage("Invalid choice. Try again.");
            }
        }
    }

    private void addBook() {
        String title = view.getBookDetail("Title");
        String author = view.getBookDetail("Author");
        String isbn = view.getBookDetail("ISBN");

        Book book = new Book(title, author, isbn);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(book.toString());
            bw.newLine();
            view.showMessage("Book added successfully.");
        } catch (IOException e) {
            view.showMessage("Error writing to file.");
        }
    }

    private void viewBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                Book book = Book.fromString(line);
                if (book != null) {
                    System.out.println("Title: " + book.getTitle() +
                            ", Author: " + book.getAuthor() +
                            ", ISBN: " + book.getIsbn());
                    found = true;
                }
            }
            if (!found) view.showMessage("No books found.");
        } catch (IOException e) {
            view.showMessage("Error reading file.");
        }
    }

    private void removeBook() {
        String isbnToRemove = view.getBookDetail("ISBN to remove");

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp_books.txt");

        boolean removed = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Book book = Book.fromString(line);
                if (book != null && !book.getIsbn().equals(isbnToRemove)) {
                    writer.write(book.toString());
                    writer.newLine();
                } else if (book != null) {
                    removed = true;
                }
            }
        } catch (IOException e) {
            view.showMessage("Error processing file.");
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }

        view.showMessage(removed ? "Book removed successfully." : "Book not found.");
    }
}
