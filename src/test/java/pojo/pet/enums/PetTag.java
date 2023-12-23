package pojo.pet.enums;

public enum PetTag {

    YOUNG_PET(1, "young-pet"),
    ADULT_PET(2, "adult-pet");

    private int id;
    private String name;

    PetTag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
