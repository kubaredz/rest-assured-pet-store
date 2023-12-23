package tests.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import pojo.ApiResponse;
import pojo.pet.Pet;
import pojo.pet.enums.PetStatus;
import test.data.PetTestDataGenerator;
import tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;

public class CreatePetTests extends SuiteTestBase {
    private Pet pet;
    private String status;
    private String name;

    public CreatePetTests(String status, String name) {
        this.status = status;
        this.name = name;
    }

    @Test
    @TmsLink("ID-1")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Creating pet with generated pet object using POST method. Verify that Response is same as Request")
    public void givenPetWhenPostPetThenPetIsCreatedTest() {
        PetTestDataGenerator petTestDataGenerator = new PetTestDataGenerator();
        pet = petTestDataGenerator.generatePet();

        Pet actualPet = given().body(pet).contentType(ContentType.JSON)
                .when().post("pet")
                .then().statusCode(HttpStatus.SC_OK).extract().as(Pet.class);

        Assertions.assertThat(actualPet).describedAs("Send Pet was different than received by API").usingRecursiveComparison().isEqualTo(pet);
    }

    @TmsLink("ID-2")
    @Test(dataProvider = "petStatus")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Creating pet with generated pet object using POST method with XML @DataProvider with three statuses. Verify that Response is same as Request")
    public void givenPetWithSpecificStatusWhenPostPetThenPetIsCreatedWithSpecificTest(String petStatus) {
        PetTestDataGenerator petTestDataGenerator = new PetTestDataGenerator();
        pet = petTestDataGenerator.generatePet();
        pet.setStatus(petStatus);

        Pet actualPet = given().contentType(ContentType.JSON).body(pet)
                .when().post("pet")
                .then().statusCode(HttpStatus.SC_OK).extract().as(Pet.class);

        Assertions.assertThat(actualPet).describedAs("Send Pet was different than received by API").usingRecursiveComparison().isEqualTo(pet);
    }

    @Test
    @TmsLink("ID-3")
    @Severity(SeverityLevel.MINOR)
    @Description("Creating pet with generated pet object using POST method with XML @Factory with three statuses and names. Verify that Response is same as Request")
    public void givenPetWithSpecificPetStatusAndPetNameWhenPostPetThenPetIsCreatedWithSpecificStatusAndNameTest() {
        PetTestDataGenerator petTestDataGenerator = new PetTestDataGenerator();
        pet = petTestDataGenerator.generatePet();

        pet.setStatus(status);
        pet.setName(name);

        Pet response = given().contentType(ContentType.JSON).body(pet)
                .when().post("pet")
                .then().statusCode(HttpStatus.SC_OK).extract().as(Pet.class);

        Assertions.assertThat(response).describedAs("Response should looks like request").usingRecursiveComparison().isEqualTo(pet);

        Assert.assertEquals(response.getStatus(), pet.getStatus());
        Assert.assertEquals(response.getName(), pet.getName());

    }

    @AfterMethod
    @Description("After method clean up after PET tests")
    public void cleanUpAfterTest() {
        ApiResponse deletePet = given().contentType(ContentType.JSON)
                .when().delete("pet/{petId}", pet.getId())
                .then().statusCode(HttpStatus.SC_OK).extract().as(ApiResponse.class);

        ApiResponse expectedResponse = new ApiResponse();
        expectedResponse.setCode(HttpStatus.SC_OK);
        expectedResponse.setType("unknown");
        expectedResponse.setMessage(pet.getId().toString());

        Assertions.assertThat(deletePet).describedAs("API response from system was not as expected").usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @DataProvider(name = "petStatus")
    public Object[][] localDataProvider() {
        return new Object[][]{
                {"available"},
                {"pending"},
                {"sold"}
        };
    }

    @Factory
    public static Object[] factoryMethod() {
        CreatePetTests createPetTests = new CreatePetTests(PetStatus.pending.toString(), "bruder");
        CreatePetTests createPetTests1 = new CreatePetTests(PetStatus.sold.toString(), "kora");
        CreatePetTests createPetTests2 = new CreatePetTests(PetStatus.available.toString(), "axa");
        return new Object[]{
                createPetTests,
                createPetTests1,
                createPetTests2
        };
    }
}