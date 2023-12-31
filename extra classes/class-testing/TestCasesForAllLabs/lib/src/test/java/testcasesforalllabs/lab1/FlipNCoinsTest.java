package testcasesforalllabs.lab1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static testcasesforalllabs.lab1.FlipNCoins.flipNHeads;

class FlipNCoinsTest {
    @Test
    public void TestWhenFlippingUntilOneHead_thenOutputIndicatesCorrectFlipCount() {
        //Arrange
        /*
         * Initialize the variable n with the value 1, indicating that you want to flip 1 heads in a row.
         * Create a ByteArrayOutputStream named outputStream to capture the printed output.
         * Redirect the standard output (System.out) to the outputStream using System.setOut(new PrintStream(outputStream)).
         * This way, any output generated by the flipNHeads method will be captured in the outputStream.
         */
        int n = 1;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act
        /*
         * Call the flipNHeads method with the value of n (which is 1) to simulate coin flips until 1 consecutive heads
           are flipped.
         * the output generated by the method is captured in the outputStream.
         */
        flipNHeads(n);
        String output = outputStream.toString();

        // Assert
        /*
         * Split the captured output into an array of lines using String[] lines = output.split(System.lineSeparator()).
           This allows you to work with individual lines of the output.
         * Use assertTrue(lines.length >= 1) to ensure that there are at least three lines in the captured output.
           This is a basic check to make sure the output is being generated.
         * Use assertTrue(lines[lines.length - 1].contains("heads")) to check that the last line of the output contains
           the term "heads." This confirms that the last flip was a head, as expected.
         */
        String[] lines = output.split(System.lineSeparator());
        assertTrue(lines.length >= 1);  // At least one lines in the output
        assertTrue(lines[lines.length - 1].contains("heads"));  // Check last line for "heads"

        // Count the number of consecutive lines containing "heads"
        /*
         * Initialize an integer variable consecutiveHeadsCount to keep track of the number of consecutive lines containing "heads."
         * Iterate through each line of the captured output using a for loop.
         * If a line contains the term "heads," increment the consecutiveHeadsCount by 1.
           If consecutive heads are found (consecutiveHeadsCount reaches the value of n), break out of the loop.
         */
        int consecutiveHeadsCount = 0;
        for (String line : lines) {
            if (line.contains("heads")) {
                consecutiveHeadsCount++;
                break;  // Stop when n consecutive heads are found
            }

        }

        /*
         * Finally, use assertEquals(n, consecutiveHeadsCount) to compare the counted number of consecutive
           heads (consecutiveHeadsCount) with the expected number (n). This confirms that the method indeed
           produced the desired number of sequent heads.
         */
        assertEquals(n, consecutiveHeadsCount);  // Should have n heads in a row
    }

    @Test
    public void TestWhenFlippingUntilTwoHeads_thenOutputIndicatesCorrectFlipCount() {
        //Arrange
        /*
         * Initialize the variable n with the value 2, indicating that you want to flip 2 heads in a row.
         * Create a ByteArrayOutputStream named outputStream to capture the printed output.
         * Redirect the standard output (System.out) to the outputStream using System.setOut(new PrintStream(outputStream)).
         * This way, any output generated by the flipNHeads method will be captured in the outputStream.
         */
        int n = 2;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act
        /*
         * Call the flipNHeads method with the value of n (which is 2) to simulate coin flips until 2 consecutive heads
           are flipped.
         * the output generated by the method is captured in the outputStream.
         */
        flipNHeads(n);
        String output = outputStream.toString();

        // Assert
        /*
         * Split the captured output into an array of lines using String[] lines = output.split(System.lineSeparator()).
           This allows you to work with individual lines of the output.
         * Use assertTrue(lines.length >= 2) to ensure that there are at least three lines in the captured output.
           This is a basic check to make sure the output is being generated.
         * Use assertTrue(lines[lines.length - 1].contains("heads")) to check that the last line of the output contains
           the term "heads." This confirms that the last flip was a head, as expected.
         */
        String[] lines = output.split(System.lineSeparator());
        assertTrue(lines.length >= 2);  // At least two lines in the output
        assertTrue(lines[lines.length - 1].contains("heads"));  // Check last line for "heads"

        // Count the number of consecutive lines containing "heads"
        /*
         * Initialize an integer variable consecutiveHeadsCount to keep track of the number of consecutive lines containing "heads."
         * Iterate through each line of the captured output using a for loop.
         * If a line contains the term "heads," increment the consecutiveHeadsCount by 1.
           If consecutive heads are found (consecutiveHeadsCount reaches the value of n), break out of the loop.
         * If a line contains "tails" or doesn't contain "heads," reset consecutiveHeadsCount to 0 to start counting again.
         */
        int consecutiveHeadsCount = 0;
        for (String line : lines) {
            if (line.contains("heads")) {
                consecutiveHeadsCount++;
                if (consecutiveHeadsCount >= n) {
                    break;  // Stop when n consecutive heads are found
                }
            } else {
                consecutiveHeadsCount = 0;  // Reset if tails are encountered
            }
        }

        /*
         * Finally, use assertEquals(n, consecutiveHeadsCount) to compare the counted number of consecutive
           heads (consecutiveHeadsCount) with the expected number (n). This confirms that the method indeed
           produced the desired number of sequent heads.
         */
        assertEquals(n, consecutiveHeadsCount);  // Should have n heads in a row
    }

    @Test
    public void TestWhenFlippingUntilThreeHeads_thenOutputIndicatesCorrectCounts() {
        // Arrange
        /*
         * Initialize the variable n with the value 3, indicating that you want to flip 3 heads in a row.
         * Create a ByteArrayOutputStream named outputStream to capture the printed output.
         * Redirect the standard output (System.out) to the outputStream using System.setOut(new PrintStream(outputStream)).
         * This way, any output generated by the flipNHeads method will be captured in the outputStream.
         */
        int n = 3;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act
        /*
         * Call the flipNHeads method with the value of n (which is 3) to simulate coin flips until 3 consecutive heads
           are flipped.
         * the output generated by the method is captured in the outputStream.
         */
        flipNHeads(n);
        String output = outputStream.toString();

        // Assert
        /*
         * Split the captured output into an array of lines using String[] lines = output.split(System.lineSeparator()).
           This allows you to work with individual lines of the output.
         * Use assertTrue(lines.length >= 3) to ensure that there are at least three lines in the captured output.
           This is a basic check to make sure the output is being generated.
         * Use assertTrue(lines[lines.length - 1].contains("heads")) to check that the last line of the output contains
           the term "heads." This confirms that the last flip was a head, as expected.
         */
        String[] lines = output.split(System.lineSeparator());
        assertTrue(lines.length >= 3);  // At least three lines in the output
        assertTrue(lines[lines.length - 1].contains("heads"));  // Check last line for "heads"

        // Count the number of consecutive lines containing "heads"
        /*
         * Initialize an integer variable consecutiveHeadsCount to keep track of the number of consecutive lines containing "heads."
         * Iterate through each line of the captured output using a for loop.
         * If a line contains the term "heads," increment the consecutiveHeadsCount by 1.
           If consecutive heads are found (consecutiveHeadsCount reaches the value of n), break out of the loop.
         * If a line contains "tails" or doesn't contain "heads," reset consecutiveHeadsCount to 0 to start counting again.
         */
        int consecutiveHeadsCount = 0;
        for (String line : lines) {
            if (line.contains("heads")) {
                consecutiveHeadsCount++;
                if (consecutiveHeadsCount >= n) {
                    break;  // Stop when n consecutive heads are found
                }
            } else {
                consecutiveHeadsCount = 0;  // Reset if tails are encountered
            }
        }

        /*
         * Finally, use assertEquals(n, consecutiveHeadsCount) to compare the counted number of consecutive
           heads (consecutiveHeadsCount) with the expected number (n). This confirms that the method indeed
           produced the desired number of sequent heads.
         */
        assertEquals(n, consecutiveHeadsCount);  // Should have n heads in a row
    }


}

