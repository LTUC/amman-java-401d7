/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
public class App {
    public static void main(String[] args) {


        //Tell me the students who have answered a question (without duplicates)
        ArrayList<String> studentNames = new ArrayList<>();

        studentNames.add("Thaer"); // Duplicate
        studentNames.add("Bob");
        studentNames.add("Thaer"); // Duplicate
        studentNames.add("Jehad");
        studentNames.add("David");
        studentNames.add("John");

        Set<String> uniqueStudentNames= getUniquesStudentNames(studentNames);

        System.out.println("Unique Students Names:");
        for (String name:uniqueStudentNames) {
            System.out.println(name);
        }

        // ================= starting next problem domain============================
        // (how many times a person said a particular answer)
        HashMap<String,Integer> answerCounts = new HashMap<>();

        giveAnswer(answerCounts, "apple");
        giveAnswer(answerCounts, "banana");
        giveAnswer(answerCounts, "apple");
        giveAnswer(answerCounts, "apple");
        giveAnswer(answerCounts, "orange");
        giveAnswer(answerCounts, "banana");

        for(String answer : answerCounts.keySet()){
            int count= answerCounts.get(answer);
            System.out.println(answer +" : "+ count +"times");
        }
    }

    public static Set<String> getUniquesStudentNames(ArrayList<String> studentNames) {
        Set<String> uniqueNames = new HashSet<>();
        uniqueNames.addAll(studentNames); // this automatically removes duplicates
        return uniqueNames;
    }


    public static void giveAnswer(HashMap<String,Integer> answerCounts, String answer){

        if(answerCounts.containsKey(answer)){

            int count = answerCounts.get(answer);
            answerCounts.put(answer, count+1);
        }
        else {
            answerCounts.put(answer,1);
        }
    }
}
