import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the type of the backend perfect hashing (e.g., quadratic/linear):");
        String backendType = scanner.nextLine();

        EnglishDictionary dictionary = new EnglishDictionary(backendType);

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Insert a string");
            System.out.println("2. Delete a string");
            System.out.println("3. Search for a string");
            System.out.println("4. Batch insert strings from a file");
            System.out.println("5. Batch delete strings from a file");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter the string to insert:");
                    String insertString = scanner.nextLine();
                    int[] result = dictionary.insert(insertString);
                    if (result[0]==1) {
                        System.out.println("String inserted successfully.");
                        System.out.println("Number of rehashings: " + result[1]);
                    } else {
                        System.out.println("String already exists in the dictionary.");
                    }
                    break;
                case 2:
                    System.out.println("Enter the string to delete:");
                    String deleteString = scanner.nextLine();
                    result = dictionary.delete(deleteString);
                    if (result[0]==1) {
                        System.out.println("String deleted successfully.");
                        System.out.println("Number of rehashings: " + result[1]);
                    } else {
                        System.out.println("String doesn't exist in the dictionary.");
                    }
                    break;
                case 3:
                    System.out.println("Enter the string to search:");
                    String searchString = scanner.nextLine();
                    if (dictionary.search(searchString)) {
                        System.out.println("String exists in the dictionary.");
                    } else {
                        System.out.println("String doesn't exist in the dictionary.");
                    }
                    break;
                case 4:
                    System.out.println("Enter the path of the file containing strings to insert:");
                    String insertFilePath = scanner.nextLine();
                {
                    ArrayList<String> insertStrings = CSVParser.parseCSVFromFile(insertFilePath);
                    System.out.println(insertStrings);
                    result = dictionary.batchInsertFromFile(insertStrings);
                    System.out.println("Number of newly added strings: " + result[0]);
                    System.out.println("Number of already existing strings: " + result[1]);
                    System.out.println("Number of rehashings: " + result[2]);
                }
                    break;
                case 5:
                    System.out.println("Enter the path of the file containing strings to delete:");
                    String deleteFilePath = scanner.nextLine();
                    ArrayList<String> deleteStrings = CSVParser.parseCSVFromFile(deleteFilePath);
                    result = dictionary.batchInsertFromFile(deleteStrings);
                    System.out.println("Number of deleted strings: " + result[0]);
                    System.out.println("Number of non-existing strings: " + result[1]);
                    System.out.println("Number of rehashings: " + result[2]);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }
}
