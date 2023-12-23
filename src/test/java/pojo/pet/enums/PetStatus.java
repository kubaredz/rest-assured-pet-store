package pojo.pet.enums;

import java.util.Random;

public enum PetStatus {
    available("available"),
    pending("pending"),
    sold("sold");

    private static final Random random = new Random();
    private String status;

    PetStatus(String status) {
        this.status = status;
    }

    public static PetStatus randomStatus() {
        PetStatus[] petStatuses = values();
        return petStatuses[random.nextInt(petStatuses.length)];
    }
}
