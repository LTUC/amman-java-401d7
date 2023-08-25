package testcasesforalllabs.lab2;

import java.util.HashMap;
import java.util.Random;

public class Library {

    public static final int NumberOfDiceSides = 6;

    public int[] roll(int timesToRoll)
    {
        var randomGenerator = new Random();
        int[] rolls = new int[timesToRoll];
        for(int i = 0; i < timesToRoll; i++)
        {
            rolls[i] = randomGenerator.nextInt(NumberOfDiceSides) + 1;
        }

        return rolls;
    }

    public boolean arrayContainsDuplicates(Integer[] array)
    {
        HashMap<Integer, Boolean> dupeCheckingHashMap = new HashMap<Integer, Boolean>();
        for (Integer element : array)
        {
            if (dupeCheckingHashMap.containsKey(element))
            {
                return true;
            }
            else
            {
                dupeCheckingHashMap.put(element, true);
            }
        }

        return false;
    }

    public double calculateArrayAverage(Integer[] arrayToAverage)
    {
        int runningTotal = 0;

        for (int numberElement : arrayToAverage)
        {
            runningTotal += numberElement;
        }

        return (double) runningTotal / (double) arrayToAverage.length;
    }

    public double calculate2DArrayAverageAndReturnLowest(Integer[][] twoDArrayToAverage)
    {
        Double lowestAverage = null;

        for (Integer[] arrayToAverage : twoDArrayToAverage)
        {
            double arrayAverage = calculateArrayAverage(arrayToAverage);
            if (lowestAverage == null || lowestAverage > arrayAverage)
            {
                lowestAverage = arrayAverage;
            }
        }

        if (lowestAverage == null)
        {
            throw new IllegalArgumentException("All integer arrays that were passed in are empty!");
        }
        return lowestAverage;
    }
}
