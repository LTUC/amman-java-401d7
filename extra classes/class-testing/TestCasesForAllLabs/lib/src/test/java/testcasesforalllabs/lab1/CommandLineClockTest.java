package testcasesforalllabs.lab1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static testcasesforalllabs.lab1.CommandLineClock.printClock;

class CommandLineClockTest {
    @Test
    public void TestWhenPrintingClock_thenOutputContainsFormattedTimePattern() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act
        printClock();
        String output = outputStream.toString();

        // Assert
        assertTrue(output.contains("HH:mm:ss"));  // Check for formatted time pattern
    }

    @Test
    public void TestWhenPrintingClock_thenOutputHasCorrectTimeFormat() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act
        printClock();
        String output = outputStream.toString();

        // Assert
        assertTrue(output.matches("\\d{2}:\\d{2}:\\d{2}.*"));  // Check for HH:mm:ss format
    }

    @Test
    public void TestWhenPrintingClock_thenOutputContainsSequentiallyIncrementedSeconds() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act
        printClock();
        String output = outputStream.toString();

        // Extract the seconds from the output and check if they are sequential
        String[] lines = output.split(System.lineSeparator());
        int previousSecond = -1;
        for (String line : lines) {
            int second = extractSeconds(line);
            if (previousSecond >= 0) {
                assertEquals(previousSecond + 1, second);
            }
            previousSecond = second;
        }
    }

    // Helper method to extract seconds from a line
    private int extractSeconds(String line) {
        String[] parts = line.split(":");
        /*
          * 12:15:19
          * 12 // 0
          * 15 // 1
          * 19 // 2
         */
        return Integer.parseInt(parts[2]);
    }
}

