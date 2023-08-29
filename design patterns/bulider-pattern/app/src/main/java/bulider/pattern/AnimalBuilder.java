package bulider.pattern;

import bulider.pattern.Animal;

public class AnimalBuilder {
    // All the attributes of the Animal class
    private String name;
    private double weight;
    private String type;
    private String typeOfFood;
    private String favoriteToys;
    private boolean isAPet;
    private int age;
    private boolean canAdopted;
    private String species;

    // Setters for each attribute
    public AnimalBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AnimalBuilder setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public AnimalBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public AnimalBuilder setTypeOfFood(String typeOfFood) {
        this.typeOfFood = typeOfFood;
        return this;
    }

    public AnimalBuilder setFavoriteToys(String favoriteToys) {
        this.favoriteToys = favoriteToys;
        return this;
    }

    public AnimalBuilder setAPet(boolean isAPet) {
        this.isAPet = isAPet;
        return this;
    }

    public AnimalBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public AnimalBuilder setCanAdopted(boolean canAdopted) {
        this.canAdopted = canAdopted;
        return this;
    }

    public AnimalBuilder setSpecies(String species) {
        this.species = species;
        return this;
    }

    // Build method to create the Animal object
    public Animal build() {
        return new Animal(name, weight, type, typeOfFood, favoriteToys, isAPet, age, canAdopted, species);
    }
}
