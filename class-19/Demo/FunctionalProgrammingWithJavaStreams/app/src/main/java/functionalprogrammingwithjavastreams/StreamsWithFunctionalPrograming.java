package functionalprogrammingwithjavastreams;

public class StreamsWithFunctionalPrograming implements Comparable<StreamsWithFunctionalPrograming>{
    String name;
    boolean isTired;
    int numOfPets;
    int numOfEmptyFiveHourEnergiesCurrently;
    public String[] petNames;
    boolean isCurrentlyConfused;
    String hobby;
    boolean isSmart;

    public StreamsWithFunctionalPrograming(String name, boolean isTired, int numOfPets, int numOfEmptyFiveHourEnergiesCurrently, String[] petNames, boolean isCurrentlyConfused, String hobby, boolean isSmart) {
        this.name = name;
        this.isTired = isTired;
        this.numOfPets = numOfPets;
        this.numOfEmptyFiveHourEnergiesCurrently = numOfEmptyFiveHourEnergiesCurrently;
        this.petNames = petNames;
        this.isCurrentlyConfused = isCurrentlyConfused;
        this.hobby = hobby;
        this.isSmart = isSmart;
    }


    @Override
    public int compareTo(StreamsWithFunctionalPrograming o)
    {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString()
    {
        return name;
    }

    public String getName()
    {
        return name;
    }
}
