package ioinjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException {
        Path filePath = Path.of("D:/Gradle projects/IOInJava/app/src/main/resources", "alice_in_wonderland.txt");

        readFromFileWithScanner(filePath);

        readFromFileWithFilesInterface(filePath);

        readFromFileWithBufferedReader(filePath);

        readFromFileWithTryCatch(filePath);

        readFromFileWithTryWithResource(filePath);
    }

    public static void readFromFileWithScanner(Path filePath) throws IOException {
        System.out.println("==============starting print the whole file==============");
        try (Scanner scanner = new Scanner(filePath)) {
            while (scanner.hasNextLine()) {
                String word = scanner.next();
                System.out.println("Word From the text file: " + word);
            }
        }
        System.out.println("==============ending print the whole file==============");
    }

    public static List<String> readFromFileWithFilesInterface(Path filePath) throws IOException {
        return Files.readAllLines(filePath);
    }


    public static void readFromFileWithBufferedReader(Path filePath) throws IOException {
        try (BufferedReader lineReader = Files.newBufferedReader(filePath)) {
            String readLine = lineReader.readLine();
            System.out.println("The reading using Buffered: " + readLine);
        }
    }

    public static void readFromFileWithTryCatch(Path filePath) {
        try {
            BufferedReader readerForTryCatch = Files.newBufferedReader(filePath);
            String theLine = readerForTryCatch.readLine();
            while (theLine != null) {
                theLine = readerForTryCatch.readLine();
            }
        } catch (IOException ioe) {
            handleException(ioe);
        }
    }

    public static void readFromFileWithTryWithResource(Path filePath) {
        try (BufferedReader readerForTryWithResource = Files.newBufferedReader(filePath)) {
            String theLine = readerForTryWithResource.readLine();
            while (theLine != null) {
                theLine = readerForTryWithResource.readLine();
            }
        } catch (IOException ioe) {
            handleException(ioe);
        }
    }

    public static void handleException(IOException ioe) {
        System.out.println("Error reading file: " + ioe.getMessage());
        ioe.printStackTrace();
    }
}
