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
public class PrincipleButton extends TelegramLongPollingBot {
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
        if (callbackData.equals("Принцип роботи")) {
            sendPrinciple(chatId);
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

    public void sendPrinciple(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(
                "_В даному розділі, Вам буде розібрано по пунктах як працює автомобіль._\n\n" +
                        "Перед тим, як наважитися сісти за кермо, Ви повинні пройти декілька підготовчих етапів. " +
                        "Зрозуміло, що для початку Ви маєте вивчити, знати і дотримуватись ПДР, а також розуміти " +
                        "де Ви знаходитесь і як треба поводитися на дорозі. Все це можна почитати в інтернеті, " +
                        "знайти відповідну літературу, запитати поради в досвідченого водія і так далі. " +
                        "Поза цим всім, головне, що треба взяти до уваги - це хоч трохи " +
                        "розібратися і зрозуміти, як взагалі влаштоване авто та як воно те все працює.\n\n" +
                        "Зрозуміла річ, що дуже багато деталей, механізмів, і важко відразу все сприйняти " +
                        "та зрозуміти. Але на те є я, `AutoBot`, щоб Вам розкласти все по полицях, " +
                        "в свою чергу, зробити так, щоб Ви послідовно та легко підходили до цієї " +
                        "теми та керували авто із задоволенням. А це головне, бо якщо їхати і від " +
                        "цього не отримувати користі, то навіщо воно взагалі треба? " +
                        "Отож, уважно читайте що написано, і станьте справжнім профі 😉\n\n" +
                        "Як Ви вже знаєте, серцем автомобіля є двигун. Саме від нього " +
                        "залежить доля Вашої машини, і за ним як за дитиною - треба ретельний догляд, " +
                        "щоб все працювало чітко і без нарікань. Але зараз акцентую увагу на те, що " +
                        "треба розуміти стосовно того, як запускається двигун, що і за чим починає " +
                        "працювати, і як далі все передається по механізмам, щоб авто почало рух? " +
                        "Вся машина це послідовність приводів, відтворена у різних системах і механізмах. " +
                        "Одне приводить в дію інше. Давайте по алгоритму все розберемо.\n\n" +
                        "👉 `Першим ділом треба завести двигун`\nЗапуск двигуна бере свій початок від стартера. " +
                        "Це що не на є механізм, призначений для обертання колінчастого вала. " +
                        "Він кріпиться на картері двигуна таким чином, " +
                        "що якір з шестірнею виступають і прилягають до маховика. " +
                        "Після того, як ми робимо запуск ключем, якір починає крутитися і " +
                        "шестерня прилягаючи до маховика, запускає колінвал. " +
                        "Він у свою чергу переміщує поршні у циліндрах. Паралельно з колінвалом " +
                        "запускається система запалювання, яка подає іскру, і система живлення, яка подає " +
                        "повітряно-паливну суміш. Далі вже працює газорозподільний механізм, де розподільні вали " +
                        "повертаючись на відповідні кути відкривають і закривають клапани " +
                        "для впуску і випуску суміші відповідно. В загальному, якщо скоротити весь цей процес, то " +
                        "всі системи та механізми беруть початок від запуску маховика на одному кінці, " +
                        "а на другому вже запускається все інше.\n\n" +
                        "👉 `З'єднуємо двигун із платформою ТЗ`\nДалі, все просто та послідовно. " +
                        "Транспорт рухається внаслідок передачі крутного моменту від самого двигуна " +
                        "до ведучих коліс. Щоб його передати, треба з'єднати ДВЗ з трансмісією. Це робиться " +
                        "за рахунок повного вижатого зчеплення та увімкненням першої передачі. " +
                        "Від колінчастого валу момент іде на коробку передач, на якій є розподільні вали з " +
                        "шестернями, які з'єднуються між собою і утворюючи зусилля, передають " +
                        "обертальний момент далі на колеса (карданна та головна передача, " +
                        "диференціал, ШРКШ, півосі).\n\n" +
                        "👉 `Процес передавання та приведення коліс у дію`\nНу і заключна частина в цій історії. " +
                        "Момент передається по карданному валу на головну передачу (зменшує частоту обертання та " +
                        "збільшує оберти), і під прямим кутом передає це на диференціал. Диференціал розподіляє " +
                        "цей момент між вихідними валами, тим самим передає вже його на ведучі колеса та відповідно " +
                        "рухає автомобіль.\n\n" +
                        "\uD83D\uDE0F Як бачите, все доволі ясно та в принципі не складно. " +
                        "Основна суть - передавання моменту обертання за допомогою різних " +
                        "систем та механізмів, робота яких має бути узгодженою.\n\n" +
                        "_P. S. Рекомендую перед початком поїздки вивчити ці речі, щоб навчитися " +
                        "потім відчувати машину і керувати в задоволення. Як, що і до чого, " +
                        "перемикання передач, технологію управління та інші відомості " +
                        "будуть описані та доступні у розділі_ `Будова`\n\n" +
                        "_Бажаю удачі на дорозі_ \uD83D\uDE0E");
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