package tests.pet;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;

public class DeletePetTests extends SuiteTestBase {

    @Test
    @TmsLink("ID-4")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deleting pet with generated id number between 1000 and 10000. Verifying that HTTP status code is 404 - Not Found")
    public void givenNonExistingPetWhenDeletingPetThenPetNotFoundTest() {

        int randomGeneratedId = new Faker().number().numberBetween(1000, 10000);

        given().contentType(ContentType.JSON)
                .when().delete("/pet/{petId}", randomGeneratedId)
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
