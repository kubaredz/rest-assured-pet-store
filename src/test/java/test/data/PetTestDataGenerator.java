package test.data;

import io.qameta.allure.Step;
import pojo.pet.Category;
import pojo.pet.Pet;
import pojo.pet.enums.PetCategory;
import pojo.pet.enums.PetStatus;
import pojo.pet.Tags;
import pojo.pet.enums.PetTag;

import java.util.Collections;
import java.util.Random;

public class PetTestDataGenerator extends TestDataGenerator {

    public Pet generatePet() {
        PetCategory petCategory = randomPetCategory();
        PetStatus petStatus = randomPetStatus();
        PetTag petTag = randomPetTag();

        Category category = new Category();
        category.setId(petCategory.getId());
        category.setName(petCategory.getName());

        Tags tag = new Tags();
        tag.setId(petTag.getId());
        tag.setName(petTag.getName());

        Pet pet = new Pet();
        pet.setId(faker().number().numberBetween(1, 9999));
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList(faker().internet().url()));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus(petStatus.randomStatus().toString());
        return pet;
    }

    private PetStatus randomPetStatus() {
        int pick = new Random().nextInt(PetStatus.values().length);
        return PetStatus.values()[pick];
    }

    private PetTag randomPetTag() {
        int pick = new Random().nextInt(PetTag.values().length);
        return PetTag.values()[pick];
    }

    private PetCategory randomPetCategory() {
        int pick = new Random().nextInt(PetCategory.values().length);
        return PetCategory.values()[pick];
    }
}
