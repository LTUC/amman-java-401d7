package testcasesforalllabs.lab1;

public class Pluralize {
    public static void main(String[] args) {
        int dogCount = 1;
        System.out.println("I own " + dogCount + " " + pluralize("dog", dogCount) + ".");

        int catCount = 2;
        System.out.println("I own " + catCount + " " + pluralize("cat", catCount) + ".");

        int turtleCount = 0;
        System.out.println("I own " + turtleCount + " " + pluralize("turtle", turtleCount) + ".");
    }

    public static String pluralize(String word, int count) {
        return pluralize(word, count, word + "s");
    }

    public static String pluralize(String word, int count, String plural) {
        if (count == 1) {
            return word;
        }
        return plural;
    }
}
