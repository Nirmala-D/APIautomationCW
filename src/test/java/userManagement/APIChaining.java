package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ExtentReport;
import utils.FailRetry;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class APIChaining extends BaseTest {

    @Test(retryAnalyzer = FailRetry.class)
    public void verifyBookstoreAddBooks() {
        ExtentReport.logInfo("Starting test: verifyBookstoreAddBooks");

        String authToken = generateAuthToken();
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body("{\"userId\":\"6fdcd89a-7efd-407e-b5ae-7d873cb9c16f\",\"collectionOfIsbns\":[{\"isbn\":\"9781593275846\"}]}")
                .when()
                .post("https://bookstore.toolsqa.com/BookStore/v1/Books");

        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);

       // ExtentReport.logPass("verifyBookstoreAddBooks executed successfully with Status Code: " + response.getStatusCode());
    }

    private String generateAuthToken() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"userName\":\"SidharthShuklaAPI\",\"password\":\"Test@123\"}")
                .when()
                .post("https://bookstore.toolsqa.com/Account/v1/GenerateToken");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("generateAuthToken executed successfully");
        String authToken = response.path("token");
        System.out.println(authToken);
        return authToken;
    }
}
