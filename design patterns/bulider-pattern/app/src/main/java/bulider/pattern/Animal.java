package bulider.pattern;

public class Animal {

    // first time i just have 3 attributes
    private String name;
    private double wight;
    private String type;

    // then tomy come and say it just 3 attributes i can add 3 more
    private String typeOfFood;
    private String favoriteToys;
    private boolean isAPet;


    //another developer come and say it just 6 attributes it's fine if i add another 3
    private int age;
    private boolean canAdopted;
    private String species;

    public Animal(){

    }

    public Animal(String name, double wight, String type) {
        // first time i have a constructor with 3 attributes
        this.name = name;
        this.wight = wight;
        this.type = type;

    }

    public Animal(String name, double wight, String type, String typeOfFood, String favoriteToys, boolean isAPet) {
        this.name = name;
        this.wight = wight;
        this.type = type;
        this.typeOfFood = typeOfFood;
        this.favoriteToys = favoriteToys;
        this.isAPet = isAPet;
    }

    public Animal(String name, double wight, String type, String typeOfFood, String favoriteToys, boolean isAPet, int age, boolean canAdopted, String species) {
        this.name = name;
        this.wight = wight;
        this.type = type;
        this.typeOfFood = typeOfFood;
        this.favoriteToys = favoriteToys;
        this.isAPet = isAPet;
        this.age = age;
        this.canAdopted = canAdopted;
        this.species = species;
    }
}
