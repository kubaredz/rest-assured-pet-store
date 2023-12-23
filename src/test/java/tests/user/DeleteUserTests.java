package tests.user;

import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;

public class DeleteUserTests extends SuiteTestBase {

    @Test
    @TmsLink("ID-6")
    @Issue("DEFECT-1")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deleting pet with generated id number between 1000 and 10000. Verifying that HTTP status code is 404 - Not Found")
    public void givenUserThatNotExistWhenDeleteUserThenUserNotFoundTest() {

        String generatedFakeUsername = new Faker().name().username();

        given().contentType(ContentType.JSON)
                .when().delete("user/{username}", generatedFakeUsername)
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
