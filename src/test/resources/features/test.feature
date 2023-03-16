# language: en

@TEST

Feature: Тест ReqRes API и RickAnMortyAPI.

Scenario: Замена имени пользователя и добавление пункта job.
Given Запрос на смену имени и добавление работы

Scenario: Получение и персонажей,эпизодов где они встречаются, их местоположении и расса
When Добавление id "2" персонажа, его рассу "Human" и локацию "Citadel of Ricks"
When Добавление последнего эпизода "51"
When Добавление последнего персонажа "825" в последнем эпизоде
When Добавление локации "Earth (Unknown dimension)" и рассы "Human"  последнего персонажа
Then Проверка локации: Морти "Citadel of Ricks",Джерри "Earth (Unknown dimension)" и рассы Морти "Human",Джерри "Human"
