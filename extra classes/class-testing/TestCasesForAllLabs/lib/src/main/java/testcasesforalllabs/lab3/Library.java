package testcasesforalllabs.lab3;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Library {
    public String findMissingTemperatures(int[][] temperatures) {
        HashSet<Integer> uniqueTemperatures = extractUniqueTemperatures(temperatures);
        int minTemp = uniqueTemperatures.stream().mapToInt(Integer::intValue).min().orElse(Integer.MAX_VALUE);
        int maxTemp = uniqueTemperatures.stream().mapToInt(Integer::intValue).max().orElse(Integer.MIN_VALUE);

        String missingTemps = IntStream.rangeClosed(minTemp, maxTemp)
                .filter(temp -> !uniqueTemperatures.contains(temp))
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));

        return missingTemps;
    }

    private HashSet<Integer> extractUniqueTemperatures(int[][] temperatures) {
        HashSet<Integer> uniqueTemperatures = new HashSet<>();
        for (int[] weekTemps : temperatures) {
            for (int temp : weekTemps) {
                uniqueTemperatures.add(temp);
            }
        }
        return uniqueTemperatures;
    }

    public String lintFile(String filePathString) throws IOException {
        Path fileToBeLinted = Path.of(filePathString);
        Scanner fileScanner = new Scanner(fileToBeLinted);
        String errors = "";
        int lineNumber = 0;

        while(fileScanner.hasNextLine())
        {
            String line = fileScanner.nextLine();
            lineNumber++;
            if(line.isEmpty())
            {
                continue;
            }
            else if(line.endsWith("{") || line.endsWith("}"))
            {
                continue;
            }
            else if (line.contains("if") || line.contains("else"))
            {
                continue;
            }

            // All exceptions to rule handled, now check for line ending
            if(!line.endsWith(";"))
            {
                errors += "Line " + lineNumber + ": Missing semicolon.\n";
            }
            if(line.contains("'"))
            {
                errors += "Line " + lineNumber + ": Single quote(s) found. Please use double quotes only.\n";
            }
        }

        if (errors.equals(""))
        {
            errors = "No errors found.\n";
        }

        return errors;
    }

}
