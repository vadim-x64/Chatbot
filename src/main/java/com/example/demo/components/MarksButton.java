package com.example.demo.components;

import com.example.demo.properties.Keyboard;
import com.example.demo.properties.Properties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@AllArgsConstructor
@Component
public class MarksButton extends TelegramLongPollingBot {
    private final Properties c;

    @Override
    public String getBotUsername() {
        return c.getBotName();
    }

    @Override
    public String getBotToken() {
        return c.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        if (callbackData.equals("Марки")) {
            sendMarks(chatId);
        } else if (callbackData.equals("close")) {
            deleteMessage(chatId, callbackQuery.getMessage().getMessageId());
        }
    }

    public void deleteMessage(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMarks(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(
                "\uD83C\uDDEF\uD83C\uDDF5 `Acura`\n_Соітіро Хонда - 1986 рік_\n\n" +
                        "\uD83C\uDDEE\uD83C\uDDF9 `Alfa Romeo`\n_Нікола Ромео - 1910 рік_\n\n" +
                        "\uD83C\uDDEC\uD83C\uDDE7 `Aston Martin`\n_Лайонел Мартін - 1913 рік_\n\n" +
                        "\uD83C\uDDE9\uD83C\uDDEA `Audi`\n_Август Хорьх - 1909 рік_\n\n" +
                        "\uD83C\uDDEC\uD83C\uDDE7 `Bentley`\n_Волтер Бентлі - 1919 рік_\n\n" +
                        "\uD83C\uDDE9\uD83C\uDDEA `BMW`\n_Фріц Фідлер - 1916 рік_\n\n" +
                        "\uD83C\uDDEB\uD83C\uDDF7 `Bugatti`\n_Етторе Бугатті - 1909 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Cadillac`\n_Генрі Леланд - 1902 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Chevrolet`\n_Луї Шевроле - 1911 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Chrysler`\n_Волтер Крайслер - 1925 рік_\n\n" +
                        "\uD83C\uDDEB\uD83C\uDDF7 `Citroen`\n_Андре-Гюстав Сітроєн - 1919 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Dodge`\n_Джон Френсіс і Горас Елджин Додж - 1900 рік_\n\n" +
                        "\uD83C\uDDEE\uD83C\uDDF9 `Ferrari`\n_Енцо Феррарі - 1939 рік_\n\n" +
                        "\uD83C\uDDEE\uD83C\uDDF9 `Fiat`\n_Джованні Аньєллі - 1899 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Ford`\n_Генрі Форд - 1903 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `GM`\n_Вільям Дюрант - 1908 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `GMC`\n_Вільям Дюрант - 1911 рік_\n\n" +
                        "\uD83C\uDDEF\uD83C\uDDF5 `Honda`\n_Соітіро Хонда - 1949 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Hummer`\n_Вільям Дюрант - 1992 рік_\n\n" +
                        "\uD83C\uDDEF\uD83C\uDDF5 `Infiniti`\n_Айкава Йошісуке - 1989 рік_\n\n" +
                        "\uD83C\uDDEC\uD83C\uDDE7 `Jaguar`\n_Вільям Лайонс - 1922 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Jeep`\n_Волтер Крайслер - 1941 рік_\n\n" +
                        "\uD83C\uDDF8\uD83C\uDDEA `Koenigsegg`\n_Крістіан фон Кенігсегг - 1994 рік_\n\n" +
                        "\uD83C\uDDEE\uD83C\uDDF9 `Lamborghini`\n_Ферруччо Ламборгіні - 1963 рік_\n\n" +
                        "\uD83C\uDDEC\uD83C\uDDE7 `Land Rover`\n_Дональд Стокс - 1978 рік_\n\n" +
                        "\uD83C\uDDEF\uD83C\uDDF5 `Lexus`\n_Киічиро Тойода - 1989 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Lincoln`\n_Генрі Леланд - 1917 рік_\n\n" +
                        "\uD83C\uDDEE\uD83C\uDDF9 `Maserati`\n_Ернесто Мазераті - 1914 рік_\n\n" +
                        "\uD83C\uDDE9\uD83C\uDDEA `Maybach`\n_Вільгельм Майбах - 1912 рік_\n\n" +
                        "\uD83C\uDDEF\uD83C\uDDF5 `Mazda`\n_Дюдзіро Мацуда - 1920 рік_\n\n" +
                        "\uD83C\uDDEC\uD83C\uDDE7 `McLaren`\n_Брюс Макларен - 2010 рік_\n\n" +
                        "\uD83C\uDDE9\uD83C\uDDEA `Mercedes-Benz`\n_Карл Бенц - 1926 рік_\n\n" +
                        "\uD83C\uDDEF\uD83C\uDDF5 `Mitsubishi`\n_Ятаро Івасакі - 1970 рік_\n\n" +
                        "\uD83C\uDDEF\uD83C\uDDF5 `Nissan`\n_Ютака Катаяма - 1933 рік_\n\n" +
                        "\uD83C\uDDE9\uD83C\uDDEA `Opel`\n_Адам Опель - 1899 рік_\n\n" +
                        "\uD83C\uDDEE\uD83C\uDDF9 `Pagani`\n_Гораціо Пагані - 1992 рік_\n\n" +
                        "\uD83C\uDDEB\uD83C\uDDF7 `Peugeot`\n_Арман Пежо - 1896 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Plymouth`\n_Волтер Крайслер - 1928 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Pontiac`\n_Елліот Естес - 1907 рік_\n\n" +
                        "\uD83C\uDDE9\uD83C\uDDEA `Porsche`\n_Фердинанд Порше - 1931 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `RAM`\n_Брати Додж - 2010 рік_\n\n" +
                        "\uD83C\uDDEB\uD83C\uDDF7 `Renault`\n_Луї Рено - 1899 рік_\n\n" +
                        "\uD83C\uDDEC\uD83C\uDDE7 `Rolls-Royce`\n_Фредерік Ройс - 1906 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Shelby`\n_Керол Шелбі - 1962 рік_\n\n" +
                        "\uD83C\uDDE8\uD83C\uDDFF `Skoda`\n_Вацлав Клемент - 1905 рік_\n\n" +
                        "\uD83C\uDDEF\uD83C\uDDF5 `Subaru`\n_Кендзі Кіта - 1953 рік_\n\n" +
                        "\uD83C\uDDEF\uD83C\uDDF5 `Suzuki`\n_Мітіо Судзукі - 1954 рік_\n\n" +
                        "\uD83C\uDDFA\uD83C\uDDF8 `Tesla`\n_Ілон Маск - 2003 рік_\n\n" +
                        "\uD83C\uDDE9\uD83C\uDDEA `Volkswagen`\n_Генріх Нордхоф - 1938 рік_\n\n" +
                        "\uD83C\uDDF8\uD83C\uDDEA `Volvo`\n_Ассар Габріельссон - 1927 рік_\n\n");
        sendMessage.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
        sendMessage.enableMarkdown(true);
        try {
            Message sentMessage = execute(sendMessage);
            int sentMessageId = sentMessage.getMessageId();
            deleteMessage(chatId, sentMessageId - 1);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

/*
    trabant
    tatra
    smart
    seat
    scion
    saab
    rover
    packard
    oldsmobile
    mini
    mercury
    lotus
    lancia
    jawa
    isuzu
    hudson
    delorean
    datsun
    daihatsu
    daf
*/