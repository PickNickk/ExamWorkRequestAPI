package Tests;

import Hooks.ApiHooks;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ApiSteps.ReqResAPI.*;
import static ApiSteps.RickAndMortyAPI.*;

public class ApiTest extends ApiHooks {
    @Test
    @DisplayName("API запросы \"Рик и Морти\"")
    @Description("Получение и проверка данных о персонажах,их эпизодов,местоположении и рассе")
    public void rickAndMortyTest() {
        getCharter("2","Human","Citadel of Ricks");
        getEpisode("51");
        lastCharterIdInLastEpisode("825");
        locAndRaceLastChar("Earth (Unknown dimension)","Human");
        checkCharactersAndLocation("Citadel of Ricks","Earth (Unknown dimension)"
                ,"Human","Human");
    }

    @Test
    @DisplayName("API запросы \"ReqRes\"")
    @Description("Изменение имени пользователя.Добавление работы.Проверка волидности данных по значениям key и value.")
    public void reqResTest(){
        createUser("Tomato","Eat maket");
    }
}
