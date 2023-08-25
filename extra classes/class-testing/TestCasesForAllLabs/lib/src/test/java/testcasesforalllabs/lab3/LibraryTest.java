package testcasesforalllabs.lab3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class LibraryTest {
    @Test
    public void testFindMissingTemperatures() {
        //Arrange
        Library analyzer = new Library();
        int[][] weeklyMonthTemperatures = {
                {66, 64, 58, 65, 71, 57, 60},
                {57, 65, 65, 70, 72, 65, 51},
                {55, 54, 60, 53, 59, 57, 61},
                {65, 56, 55, 52, 55, 62, 57}
        };

        //Act
        String missingTemps = analyzer.findMissingTemperatures(weeklyMonthTemperatures);

        //Assert
        assertEquals("63 67 68 69", missingTemps);
    }

    @Test
    public void testLintFileForMissingSemicolon() throws IOException {

        //Arrange
        Library linter = new Library();
        // Create a temporary file with sample content
        String content = "function example() {\n" +
                "  console.log(\"Hello\");\n" +
                "  if (true) {\n" +
                "    console.log(\"Condition is true\")\n" +
                "  }\n" +
                "}";
        Path tempFile = Files.createTempFile("testFile", ".js");
        Files.writeString(tempFile, content);

        //Act
        // Call the lintFile method on the temporary file
        String errors = linter.lintFile(tempFile.toString());

        // Check if the errors match the expected output
        String expectedErrors = "Line 4: Missing semicolon.\n";

        //Assert
        assertEquals(expectedErrors, errors);


        // Clean up: Delete the temporary file
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void testLintFileForMissingDoubleQuotes() throws IOException {
        // Create a temporary file with sample content
        String content = "function example() {\n" +
                "  console.log('Hello');\n" +
                "  if (true) {\n" +
                "    console.log(\"Condition is true\");\n" +
                "  }\n" +
                "}";
        Path tempFile = Files.createTempFile("testFile", ".js");
        Files.writeString(tempFile, content);

        // Call the lintFile method on the temporary file
        Library linter = new Library();
        String errors = linter.lintFile(tempFile.toString());

        // Check if the errors match the expected output
        String expectedErrors = "Line 2: Single quote(s) found. Please use double quotes only.\n";
        assertEquals(expectedErrors, errors);

        // Clean up: Delete the temporary file
        Files.deleteIfExists(tempFile);
    }

    //ToDO: add a test cas for no errors

}

