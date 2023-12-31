/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ioinjava;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testReadFromFileWithScanner() throws IOException {
        // Arrange
        Path filePath = createTestFile("Word1 Word2 Word3");

        // Act & Assert
        App.readFromFileWithScanner(filePath); // Since this method prints to console, no return value to assert
    }

    @Test
    void testReadFromFileWithFilesInterface() throws IOException {
        // Arrange
        Path filePath = createTestFile("Line1\nLine2\nLine3");

        // Act
        List<String> lines = App.readFromFileWithFilesInterface(filePath);

        // Assert
        assertEquals(3, lines.size());
        assertEquals("Line1", lines.get(0));
        assertEquals("Line2", lines.get(1));
        assertEquals("Line3", lines.get(2));
    }

    @Test
    void testReadFromFileWithBufferedReader() throws IOException {
        // Arrange
        String expectedLine = "Buffered Reading Test";
        Path filePath = createTestFile(expectedLine);

        // Redirect System.out to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Act
        App.readFromFileWithBufferedReader(filePath);

        // Restore original System.out
        System.setOut(originalSystemOut);

        // Assert
        String capturedOutput = outputStream.toString().trim();
        assertEquals("The reading using Buffered: " + expectedLine, capturedOutput);
    }

    // Helper method to create a temporary test file (don't depend on the actual file mock it)
    private Path createTestFile(String content) throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".txt");
        Files.write(tempFile, content.getBytes());
        return tempFile;
    }
}
