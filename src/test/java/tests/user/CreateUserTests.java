package tests.user;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pojo.ApiResponse;
import pojo.User;
import test.data.UserTestDataGenerator;
import tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;

public class CreateUserTests extends SuiteTestBase {
    private User user;

    @Test
    @TmsLink("ID-5")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Creating user with generated data. Verify code, type and message")
    public void givenUserWhenPostUserThenUserIsCreatedTest() {
        UserTestDataGenerator userTestDataGenerator = new UserTestDataGenerator();
        user = userTestDataGenerator.generateUser();

        ApiResponse apiResponse = given().contentType(ContentType.JSON)
                .body(user)
                .when().post("user")
                .then().statusCode(HttpStatus.SC_OK).extract().as(ApiResponse.class);

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(user.getId().toString());

        Assertions.assertThat(apiResponse).describedAs("Api response should be same as expected api response").usingRecursiveComparison().isEqualTo(expectedApiResponse);
    }

    @AfterMethod
    @Description("After method clean up after User tests")
    public void cleanUpAfterTest() {
        ApiResponse apiResponse = given().contentType(ContentType.JSON)
                .when().delete("user/{username}", user.getUsername())
                .then().statusCode(HttpStatus.SC_OK).extract().as(ApiResponse.class);

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(user.getUsername());

        Assertions.assertThat(apiResponse).describedAs("Api response should be same when deleting like expected api response").usingRecursiveComparison().isEqualTo(expectedApiResponse);
    }
}