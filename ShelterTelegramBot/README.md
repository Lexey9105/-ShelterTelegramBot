# ShelterTelegramBot

Программа-менеджер приюта для животных для предоставления информации потенциальным усыновителям и сопровождения на всех этапах

## Technology stack

Java 17, Maven, Spring 3 (Boot, MVC, Data, Security), Hibernate, Postgresql, Freemarker, HTML, Telegram Bots

## Над проектом работали
Алексей Павликов
Ян Сударкин

## Сруктура проекта
**TelegramBotConfiguration**

**TelegramBotUpdatesListener**

**Model:**

Client 

Report  - поля : общее количество пропусков для фото и опросника; int123; int totalOwed; String days. int 1;int2int3(заносим id=day)- для фиксирования отсутствующего пропуска фото несколько дней подряд. Если пользователь не прислал отчет по фото – заполняется int1 и totalOwed++, при повторном случае пропуска отчета – if (int2-1==int1){ totalOwed++} else{обнуляем все int}.  Если (int3-1==int2) -> переносим пользователя в таблицу с испытательным сроком. Эта логика будет реализована в ControlService. Условия для попадания в TrialPeriodRepository для каждой категории отчета будут разные

Button

Quest

Photo

**Service:**

ClientService

NotificationService – отправление уведомлений

ControlService  – Содержит CacheControlRepository. Вызывает NotificationService.  Взаимодействоует с  MessageUtils и ControlUtils для проверки сообщения и базы данных  клиента во время парсинга сервисом  TelegramBotUpdatesListener, редактирует и выполняет проверку  таблицы -  CacheNormalControlRepository,  ControlUtils. Заполняет OwedReportIntRepository. По имеющимся данным в ORR Распределяет между TrialPeriodRepository,  и SuccessPassedRepository. Удаляет клиентов из CacheControlRepository если прислали данные.

CommandHandler:

MainMenuCommand Обработка  InlineKeyboardMarkup для Главное меню

ShelterInformationCommand  Обработка  InlineKeyboardMarkup для Узнать информацию о приюте

AnimalInformationCommand Обработка  InlineKeyboardMarkup для Как взять животное из приюта (этап 2)

ControlCommand Обработка  InlineKeyboardMarkup для Прислать отчет о питомце (этап 3)

QuestCommand Обработка  InlineKeyboardMarkup и ReplyKeyboardMarkup для опросника

**Exception**


**Repositories**

 TotalClientRepository

CacheControlRepository  Проходят опросник Repository (id=day) Hash – (Ключ Client.id, Объект Report)

OwedReportIntRepository  - хранит целочисленные данные по задолженностям(Дни когда клиент не присылал отчет по текстовой и фото информации можно исправить если опросник будет отправлен на следующий день и фото если будет вчерашняя дата)

TrialPeriodRepository  Таблица для каждого клиента  попавшего на испытательный срок ( заносим если -Три дня подряд или 10 дней отсутствовала фотография или 10 дней вовремя не присылали опросник)

FailedRepository - клиенты не прошедшие испыательный срок

 SuccessPassedRepository клиенты успешно прошедшие сопровождение


**Utils**

MessageUtils Проверка сообщения (паттерны отправки Контактных данных, паттерны отправки отчета по фото и опроснику)

ControlUtils Проверка отчета и хеш таблиц (При отправке сообщения сервису проверка хэш таблицы связанной с OwedReportIntRepository,  на наличие задолженностей у сервиса – возврат количества пропущенных дней в общем и есть ли пропуски отчета несколько дней подряд)
