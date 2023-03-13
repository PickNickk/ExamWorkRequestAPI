package ApiSteps;

import io.cucumber.java.en.*;
import io.qameta.allure.Step;
import io.restassured.response.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import static SettingsProperties.Settings.getProperty;
import static io.restassured.RestAssured.*;

public class RickAndMortyAPI {
    public static String charterId;
    public static int lastEpisode;
    public static int lastCharterId;
    public static String locationMorty;
    public static String speciesMorty;
    public static String locationJerry;
    public static String speciesJerry;
    public static String url = getProperty("urlRickAndMorty");

    public static Response getResponse(String targetURL){
        return given()
                .baseUri(url)
                .when()
                .get(targetURL)
                .then()
                .extract()
                .response();
    }
    public static String getJsonObj(Response response,String value){
        return new JSONObject(response.getBody().asString()).get(value).toString();
    }
    public static int getJsonObjLength(Response response, String value){
        return (new JSONObject(response.getBody().asString()).getJSONArray(value).length()-1);
    }
    public static Integer getParseString(Response response,String value,int value2){
        return Integer.parseInt(new JSONObject(response.getBody().asString())
                .getJSONArray(value).get(value2).toString().replaceAll("[^0-9]",""));
    }
    @Step("Выполняем get запрос на получение id персонажа, его локацию  и рассу")
    @When("Добавление id {string} персонажа, его рассу {string} и локацию {string}")
    public static void getCharter(String id,String species,String location){
        Response gettingChar = getResponse("/character/"+id);

        charterId = getJsonObj(gettingChar,getProperty("charOneId"));
        locationMorty = gettingChar.jsonPath().get(getProperty("charOneLoc")).toString();
        speciesMorty = getJsonObj(gettingChar,getProperty("charOneSpec"));
    }
    @Step("Выполняем get запрос на получение последнего эпизода персонажа")
    @When("Добавление последнего эпизода {string}")
    public static void getEpisode(String episode){
        Response gettingLastEpisode = getResponse("/character/"+charterId);

        int episodes = getJsonObjLength(gettingLastEpisode,getProperty("charOneEp"));
        lastEpisode = getParseString(gettingLastEpisode,getProperty("charOneEp"),episodes);
    }
    @Step("Выполняем get запрос на получение id последнего персонажа в последнем эпизоде")
    @When("Добавление последнего персонажа {string} в последнем эпизоде")
    public static void lastCharterIdInLastEpisode(String characters){
        Response gettingLastCharterInLastEpisode = getResponse("/episode/"+lastEpisode);

        int charterLastId = getJsonObjLength(gettingLastCharterInLastEpisode,getProperty("charLastId"));
        lastCharterId = getParseString(gettingLastCharterInLastEpisode,getProperty("charLastId"),charterLastId);
    }
    @Step("Выполняем get запрос на получение локации и рассы последнего персонажа")
    @When("Добавление локации {string} и рассы {string}  последнего персонажа")
    public static void locAndRaceLastChar(String location,String species){
        Response gettingLastCharterInLastEpisode = getResponse("/character/"+lastCharterId);

        locationJerry = gettingLastCharterInLastEpisode.jsonPath().get(getProperty("charLastLoc")).toString();
        speciesJerry = getJsonObj(gettingLastCharterInLastEpisode,getProperty("charLastSpec"));
    }
    @Step("Проверка локаций и рассы персонажей Морти(первый персонаж), Джерри(последний персонаж)")
    @Then("Проверка локации: Морти {string},Джерри {string} и рассы Морти {string},Джерри {string}")
    public static void checkCharactersAndLocation(String locMorty,String locJerry,String specMorty,String specJerry){
        Assertions.assertNotEquals(locationMorty,locationJerry);
        Assertions.assertEquals(speciesMorty,speciesJerry);
    }
}
