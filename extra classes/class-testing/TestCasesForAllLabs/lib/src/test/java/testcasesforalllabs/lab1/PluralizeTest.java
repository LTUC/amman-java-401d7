package testcasesforalllabs.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static testcasesforalllabs.lab1.Pluralize.pluralize;

import org.junit.jupiter.api.Test;

class PluralizeTest {
    @Test
    public void TestWhenSingularWordAndCountIsOne_thenWordRemainsUnchanged() {
        // Arrange
        String singularWord = "dog";
        int count = 1;

        // Act
        String result = pluralize(singularWord, count);

        // Assert
        assertEquals(singularWord, result);
    }

    @Test
    public void TestWhenPluralWordAndCountIsGreaterThanOne_thenWordIsPluralized() {
        // Arrange
        String pluralWord = "cat";
        int count = 3;
        String expectedPlural = "cats";

        // Act
        String result = pluralize(pluralWord, count);

        // Assert
        assertEquals(expectedPlural, result);
    }

    @Test
    public void TestWhenWordAndCountIsZero_thenWordIsPluralized() {
        // Arrange
        String word = "turtle";
        int count = 0;
        String expectedPlural = "turtles";

        // Act
        String result = pluralize(word, count);

        // Assert
        assertEquals(expectedPlural, result);
    }

    @Test
    public void whenCustomPluralIsGiven_thenPluralIsUsed() {
        // Arrange
        String singularWord = "child";
        int count = 77;
        String customPlural = "children";

        // Act
        String result = pluralize(singularWord, count, customPlural);

        // Assert
        assertEquals(customPlural, result);
    }
}

