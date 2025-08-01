package view;

import java.util.Scanner;

public class LibraryView {
    private Scanner scanner = new Scanner(System.in);

    public int displayMenu() {
        System.out.println("\nLibrary Menu:");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Remove Book");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String getBookDetail(String field) {
        System.out.print("Enter " + field + ": ");
        return scanner.nextLine();
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
