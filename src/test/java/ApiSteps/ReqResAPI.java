package ApiSteps;

import io.cucumber.java.en.Given;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.*;

import static SettingsProperties.Settings.getProperty;
import static Specifications.SpecificationsAPI.requestSpec;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class ReqResAPI {

    public static String URL = getProperty("urlReqRes");
    public static JSONObject getJSONObject(String fileName) {
        try {
            return new JSONObject(new String(Files.readAllBytes(Paths.get("src/test/resources/" + fileName))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static JSONObject reName(){
        JSONObject user = getJSONObject("json/reqres.json");
        user.put("name", "Tomato");
        user.put("job", "Eat maket");
        return user;
    }
    @Step("Выполняем post запрос на изменение имени, добавление работы. Проверка статуса кода, валидности key и value")
    @Given("Запрос на замену имени {string},добавление работы {string}")
    public static void createUser(String name, String job) {
        Response changeUser = given()
                .spec(requestSpec(URL))
                .when()
                .body(reName().toString())
                .post("api/users"+name+job)
                .then()
                .statusCode(201)
                .body("name", equalTo(getProperty("userName")))
                .body("job",equalTo(getProperty("userJob")))
                .body("id",notNullValue())
                .body("createdAt",notNullValue())
                .extract()
                .response();
    }
}
