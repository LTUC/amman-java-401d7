package testcasesforalllabs.lab2;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {


    //This test case ensures that the size of the output array (the number of rolls) matches the input value timesToRoll.
    @Test
    public void TestWhenRollingDice_thenOutputArraySizeMatchesInput() {
        // Arrange
        int timesToRoll = 5;
        Library diceRoller = new Library();

        // Act
        int[] rolls = diceRoller.roll(timesToRoll);

        // Assert
        assertEquals(rolls.length, timesToRoll);
    }

    //This test case checks if all the values in the output array are within the valid range of 1 to NumberOfDiceSides
    @Test
    public void TestWhenRollingDice_thenOutputValuesAreWithinValidRange() {
        // Arrange
        int timesToRoll = 10_000;
        Library diceRoller = new Library();

        // Act
        int[] rolls = diceRoller.roll(timesToRoll);

        // Assert
        for (int roll : rolls) {
            assertTrue(roll >= 1 && roll <= Library.NumberOfDiceSides);
        }
    }

    //This test case checks if the method correctly identifies an array containing duplicate values and returns true.
    @Test
    public void TestWhenArrayContainsDuplicates_thenReturnsTrue() {
        // Arrange
        Library checker = new Library();
        Integer[] array = {1, 2, 3, 4, 1}; // Contains duplicate 1

        // Act
        boolean result = checker.arrayContainsDuplicates(array);

        // Assert
        assertTrue(result);
    }

    //This test case checks if the method correctly identifies an array without duplicate values and returns false.
    @Test
    public void TestWhenArrayDoesNotContainDuplicates_thenReturnsFalse() {
        // Arrange
        Library checker = new Library();
        Integer[] array = {1, 2, 3, 4, 5}; // No duplicates

        // Act
        boolean result = checker.arrayContainsDuplicates(array);

        // Assert
        assertFalse(result);
    }

    //This test case checks if the method handles an empty array correctly and returns false.
    @Test
    public void TestWhenEmptyArray_thenReturnsFalse() {
        // Arrange
        Library checker = new Library();
        Integer[] array = {}; // Empty array

        // Act
        boolean result = checker.arrayContainsDuplicates(array);

        // Assert
        assertFalse(result);
    }

    @Test
    public void TestWhenCalculatingAverageOfPositiveNumbers_thenReturnsCorrectAverage() {
        // Arrange
        Library calculator = new Library();
        Integer[] array = {2, 4, 6, 8}; // Average = 5

        // Act
        double result = calculator.calculateArrayAverage(array);

        // Assert
        assertEquals(5.0, result);
    }

    @Test
    public void TestWhenCalculatingAverageOfNegativeNumbers_thenReturnsCorrectAverage() {
        // Arrange
        Library calculator = new Library();
        Integer[] array = {-3, -1, -4, -2}; // Average = -2.5

        // Act
        double result = calculator.calculateArrayAverage(array);

        // Assert
        assertEquals(-2.5, result);
    }

    @Test
    public void TestWhenCalculatingAverageOfMixedNumbers_thenReturnsCorrectAverage() {
        // Arrange
        Library calculator = new Library();
        Integer[] array = {0, 2, -4, 6}; // Average = 1

        // Act
        double result = calculator.calculateArrayAverage(array);

        // Assert
        assertEquals(1.0, result);
    }

    @Test
    public void TestWhenCalculatingAverageOfEmptyArray_thenReturnsNaN() {
        // Arrange
        Library calculator = new Library();
        Integer[] array = {}; // Empty array

        // Act
        double result = calculator.calculateArrayAverage(array);

        // Assert
        assertTrue(Double.isNaN(result));
    }

    @Test
    public void TestWhenCalculatingAverageOfPositive2DArray_thenReturnsLowestAverage() {
        // Arrange
        Library calculator = new Library();
        Integer[][] twoDArray = {
                {2, 4, 6, 8},
                {3, 5, 7, 9},
                {1, 2, 3, 4}
        };
        // Averages: 5, 6, 2.5; Lowest: 2.5

        // Act
        double result = calculator.calculate2DArrayAverageAndReturnLowest(twoDArray);

        // Assert
        assertEquals(2.5, result);
    }

    @Test
    public void TestWhenCalculatingAverageOfNegative2DArray_thenReturnsLowestAverage() {
        // Arrange
        Library calculator = new Library();
        Integer[][] twoDArray = {
                {-3, -1, -4, -2},
                {-5, -2, -6, -1},
                {-2, -3, -1, -4}
        };
        // Averages: -2.5, -3.5, -2.5; Lowest: -3.5

        // Act
        double result = calculator.calculate2DArrayAverageAndReturnLowest(twoDArray);

        // Assert
        assertEquals(-3.5, result);
    }

}

