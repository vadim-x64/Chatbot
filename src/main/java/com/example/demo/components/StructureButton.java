package com.example.demo.components;

import com.example.demo.entities.Types;
import com.example.demo.entities.TypesImages;
import com.example.demo.entities.VideoPaths;
import com.example.demo.interfaces.TypesRepos;
import com.example.demo.properties.Keyboard;
import com.example.demo.properties.Properties;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class StructureButton extends TelegramLongPollingBot {
    private final Properties c;
    private final TypesRepos typesRepos;
    private final CategoryButton categoryButton;
    private final Map<Long, List<Integer>> sentMessages = new HashMap<>();

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
        if (callbackData.equals("Категорії")) {
            categoryButton.sendInlineCategoriesKeyboard(chatId);
        } else if (!callbackData.equals("Будова")) {
            categoryButton.sendCarsByCategory(chatId, callbackData);
        }
        if (callbackData.startsWith("close")) {
            List<Integer> messageIds = sentMessages.get(chatId);
            if (messageIds != null) {
                for (int messageId : messageIds) {
                    deleteMessage(chatId, messageId);
                }
                sentMessages.remove(chatId);
            }
        } else {
            switch (callbackData) {
                case "engine":
                case "chassis":
                case "body":
                case "species":
                    sendStructureTypes(chatId, callbackData);
                    break;
                case "2_mechanisms":
                    sendMechanismsDescription(chatId);
                    break;
                case "5_systems":
                    sendSystemsDescription(chatId);
                    break;
                case "transmission":
                    sendTransmissionDescription(chatId);
                    break;
                case "bridges":
                    sendBridgesDescription(chatId);
                    break;
                case "control":
                    sendControlDescription(chatId);
                    break;
                case "basics":
                    sendBasicsManagement(chatId);
                    break;
                case "tech":
                    sendTechManagement(chatId);
                    break;
                default:
                    break;
            }
        }
    }

    private void sendTechManagement(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(
                "Сіли Ви в автомобіль. З попереднього розділу знаєте, що і як треба робити. " +
                        "Тобто відрегулювати сидіння, потім дзеркала, пристебнутися, виставити нейтраль, " +
                        "витиснути зчеплення та завести двигун. А як діяти далі?\n\n" +
                        "А далі все буде розписано поетапно, щоб керувати авто було легко та без " +
                        "питань. Як раніше зазначалось, рух починається з першої передачі. Тут важливо пояснити те, " +
                        "що не треба важіль коробки передач сильно тиснути або дьоргати. У нього чіткі та зрозумілі кроки. Тому " +
                        "перемикати треба спокійно і плавно.\n\n" +
                        "☝️ А тепер важливий момент! \n\n" +
                        "_Взаємодія з коробкою доступні тільки при повному витиснутому зчепленні._\n\n" +
                        "Тож тепер, алгоритм розвитку подій.\n\n" +
                        "1) Знімаємо з ручніка. Тиснемо кнопку, підтягуємо ручку трохи до верху та плавно опускаємо. " +
                        "Відразу ставимо ногу на педаль гальма (по центру).\n\n" +
                        "2) Повністю до упора давимо педаль зчеплення і вмикаємо першу передачу. " +
                        "За нею поворот і якщо немає перешкоди, розпочинаємо рух.\n\n" +
                        "3) З'єднуємо двигун із трансмісією. Плавно попускаючи зчеплення до " +
                        "легенької вібрації, додаємо газу. І, як вже машина починає їхати, впевнено тиснемо газ, відпустивши плавно зчеплення.\n\n" +
                        "4) Тепер що робити далі? Звісно, що на одній передачі ми не виїдемо, тому треба змінювати тягові зусилля і, відповідно " +
                        "збільшувати кількість руху. Так як на МКП зазвичай 5 передач, то ми маємо підлаштувати швидкість так, щоб машині було " +
                        "легше їхати, а Вам вільно керувати цим всім та відчувати, що потребує Ваш ТЗ. Чи меншої передачі чи більшої швидкості і так далі. " +
                        "Розігнавшись до 20 км/год, вже розуміємо що час перемикатися на більшу передачу.\n\n" +
                        "Як це робиться під час ходу?\n\n" +
                        "1) Знімаємо ногу з газу, а лише потім вижимаємо зчеплення. Коли так робимо, ми від'єднуємо двигун від трансмісії. " +
                        "Тобто, якщо вижати зчеплення і паралельно тиснути на газ, то будемо чути характерний звук " +
                        "збільшення оборотів. Щоб такого не допускати, просто скиньте газ та вижміть зчеплення.\n\n" +
                        "2) Після цього перемикаємо на другу передачу, що розташована під першою. Далі так само плавно попускаємо зчеплення і " +
                        "даємо газу. І ось так всі послідуючі передачі. Дивимось на спідометр (вимірювач швидкості) та кожні 20-30 км/год перемикаємо " +
                        "передачі.\n\n" +
                        "Тобто в принципі, залишилось трохи попрактикуватись. Воно все втягується з часом. Головна задача - не кидати та не " +
                        "перетримувати зчеплення. Це найпоширеніша проблема механіки. Із-за цього машина починає глохнути. А якщо перетримувати його, " +
                        "то це також не дуже добре. Тримати можна не більше 5 секунд.\n\n" +
                        "Декілька порад починаючому автомобілісту\n\n" +
                        "\uD83D\uDC49 _Уважність перш за все._\nКрутіть головою, дивіться по сторонах, поглядайте в дзеркала " +
                        "та слідкуйте за дорожньою обстановкою. Це головне що треба робити, аби забезпечити " +
                        "безпеку руху.\n\n" +
                        "\uD83D\uDC49 _Не спішіть._\nПомилка, що спіткає багатьох водіїв. І задля легкої та впевненої їзди " +
                        "краще спокійно зосередитися на маневрі та плавно проїхати.\n\n" +
                        "\uD83D\uDC49 _Впевнені на 101%? Проїжджайте._\nДуже специфічна задача, коли їдете по населеному пункту, " +
                        "а перед вами заморгав світлофор. Якщо він моргає перед Вами - проїжджайте. Бувають випадки, " +
                        "щоб уникнути ситуації на дорозі, і на жовте проїжджають. Тому тут максимальна увага.\n\n" +
                        "\uD83D\uDC49 _Пам'ятайте, що Ви в житловій зоні._\nДіти, велосипеди, м'ячі - все це треба прийняти до уваги та їхати " +
                        "по пішохідній та житловій зоні не більше 20 км/год. На переходах - пропускайте пішоходів, дітей, " +
                        "людей похилого віку тощо.\n\n" +
                        "\uD83D\uDC49 _Хочете здійснити маневр? Повідомте про це іншого водія._\nПеред поворотом, перестроюванням, початком руху, " +
                        "парковкою, об'їздом перешкоди - вмикайте повороти. Щоб інші водії розуміли хід Ваших дій. " +
                        "Особливо, це стосується тих ситуацій, коли Ви знаходитеся у місті.\n\n" +
                        "\uD83D\uDC49 _Будьте ввічливими._\nЯкщо Вам уступили дорогу, поморгайте аварійкою або скажіть _Дякую,_ виставивши руку.\n\n" +
                        "Не забудьте поділитися з друзями і удача буде завжди з Вами \uD83D\uDE09");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendBasicsManagement(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(
                "☝️ Запам'ятайте! Для того, щоб поїздка для Вас була комфортною " +
                        "та безпечною, найважливішим елементом є робоче місце водія. " +
                        "Залежно від того, як Ви сидите за кермом - " +
                        "все буде відображатися на дорозі. Якщо ви " +
                        "відчуваєте дискомфорт та втому, значить в " +
                        "цьому і вся справа. Це найпоширеніша помилка " +
                        "багатьох початківців. Тож, думаю саме час " +
                        "надати Вам ази керування ТЗ, після чого " +
                        "Ви будете водити авто із задоволенням.\n\n" +
                        "Робоче місце водія - простір, у якому " +
                        "перебуває водій та розташовані органи керування " +
                        "автомобілем. Перед початком руху, шофер має " +
                        "забезпечити такі умови на робочому місці, щоб " +
                        "надалі перемикати передачі, тиснути педалі, дивитися по дзеркалах " +
                        "і так далі не було складною задачею.\n\n" +
                        "\uD83D\uDCCC Примітка. Ось цей порядок, який зараз буде написано, " +
                        "в такому порядку і треба робити. Нічого не треба додумувати " +
                        "і перекручувати. Саме в такій послідовності усе треба виконувати!\n\n" +
                        "\uD83D\uDC49 _З самого початку потрібно сісти в авто і відрегулювати сидіння._ " +
                        "Робиться це наступним чином\n\n" +
                        "\t\t\t→ вижати педаль зчеплення до упора так, щоб не тягнутися носком до нього " +
                        "і не сильно підтягуватися до руля\n\n" +
                        "\t\t\t→ покласти руки на руль так, щоб вони згиналися у ліктях (приблизно " +
                        "градусів 120)\n\n" +
                        "\t\t\t→ спина має повністю прилягати до спинки сидіння\n\n" +
                        "❗️ Тобто основна суть, щоб водій діставав до всіх органів керування, " +
                        "не відриваючи спини від сидіння.\n\n" +
                        "\uD83D\uDC49 _Регулюємо бокові дзеркала та дзеркало заднього огляду._ Тут нічого " +
                        "складного немає абсолютно\n\n" +
                        "\t\t\t→ дзеркало заднього виду регулюємо так, " +
                        "щоб заднє скло авто збігалося з рамкою цього дзеркала. Тобто, " +
                        "щоб воно повністю вписувалось в трюмо\n\n" +
                        "\t\t\t→ бокові ж треба розмістити так, " +
                        "щоб в нижньому правому куті лівого дзеркала та в лівому нижньому куті правого " +
                        "дзеркала були видні ручки передніх дверей\n\n" +
                        "❗️ Завдяки таким правилам розташування дзеркал під відповідними кутами, " +
                        "є можливість чітко бачити дорожню обстановку без зайвих поворотів головою.\n\n" +
                        "\uD83D\uDC49 _Після даних процедур, залишилось пристебнути ремінь безпеки " +
                        "та підлаштувати підголівник._ В принципі, на цьому якби і все. Плавно переходимо до " +
                        "запуску двигуна.\n\n" +
                        "Що і як би там не було, першим ділом треба впевнитися, що у машині не увімкнена передача " +
                        "та вона стоїть на стоянковому гальмі. Ручне гальмо має бути піднятий догори, а коробка передач " +
                        "має бути у нейтральному положенні. Якщо це не так, то натисніть педаль зчеплення та розмістіть " +
                        "важіль по центру коробки і плавно поводіть його у сторони. " +
                        "Це і буде нейтраль. А тепер треба все виконати по кроках\n\n" +
                        "\t\t\t→ вставляємо ключ і повертаємо його один раз (вмикається система електрообладнання). " +
                        "Потім другий раз, вмикаємо систему запалювання ДВЗ, починають працювати контрольні лампочки. " +
                        "Ну і на третій раз запускаємо двигун. Варто зазначити, що не треба тримати ключ при запуску " +
                        "довше ніж 2 секунди. Це може вплинути на подальшу експлуатацію стартера.\n\n" +
                        "_Пам'ятайте, запуск двигуна відбувається на вижатому до упора зчепленні._\n\n" +
                        "\t\t\t→ знімаємо машину зі стоянкового гальма. Натискаємо кнопку на важелі, трохи " +
                        "відтягуємо його до верху і плавно опускаємо донизу. " +
                        "Якщо машина знаходиться на хоча б маленькому уклоні, доцільним рішенням буде подбати про те, " +
                        "щоб встановити праву ногу " +
                        "на педаль гальма, оскільки авто може в'їхати в яку-небудь перешкоду\n\n" +
                        "\t\t\t→ ну і головне - рух автомобіля. Все робиться по ось такому алгоритму\n\n" +
                        "\t\t\t1. Тиснемо педаль зчеплення до упора\n\n" +
                        "\t\t\t2. Плавно вмикаємо першу передачу\n_(рух на легкових механічних авто " +
                        "починається тільки з першої передачі)_\n\n" +
                        "\t\t\t3. Вмикаємо поворот, дивимося по сторонах, і якщо немає перешкод - плавно опускаючи зчеплення " +
                        "до легенької вібрації, паралельно починаємо додавати газу. І так підвищуючи подачу повітряно-паливної " +
                        "суміші у камери згоряння, тим самим збільшуючи крутний момент колінвала і приводячи авто у рух, " +
                        "плавно опускаємо зчеплення і їдемо у визначеному напрямку.\n\n" +
                        "Отже, в ході цього уроку, сподіваюсь, Ви зрозуміли, що як і до чого. " +
                        "Як бачите, все чітко та обгрунтовано описано. Як перемикати передачі та взагалі керувати автомобілем - " +
                        "читайте розділ `Техніка керування`\n\nУспіхів \uD83D\uDE09");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteMessage(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendError(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Наразі ця категорія порожня!");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMechanismsDescription(long chatId) {
        sendData(chatId, "2_mechanisms");
    }

    private void sendSystemsDescription(long chatId) {
        sendData(chatId, "5_systems");
    }

    private void sendTransmissionDescription(long chatId) {
        sendData(chatId, "transmission");
    }

    private void sendBridgesDescription(long chatId) {
        sendData(chatId, "bridges");
    }

    private void sendControlDescription(long chatId) {
        sendData(chatId, "control");
    }

    private void sendData(long chatId, String callback) {
        List<Types> types = typesRepos.findByName(callback);
        if (types.isEmpty()) {
            sendError(chatId);
            return;
        }
        List<Integer> messageIds = sentMessages.computeIfAbsent(chatId, k -> new ArrayList<>());
        for (Types type : types) {
            List<String> photoPaths = new ArrayList<>();
            List<String> videoPaths = new ArrayList<>();
            for (TypesImages image : type.getImages()) {
                photoPaths.add(image.getPhotoPath());
            }
            for (VideoPaths video : type.getVideos()) {
                videoPaths.add(video.getVideoPath());
            }
            String description = type.getDescription();
            sendStructureInfo(chatId, photoPaths, videoPaths, description, callback.equals("engine"), callback.equals("chassis"), messageIds);
        }
        sendCloseButton(chatId, messageIds);
    }

    public void handleTextMessage(long chatId) {
        Keyboard keyboardBuilder = new Keyboard();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardBuilder.getStructure();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(
                """
                Автомобіль вже давно став невід'ємною частиною нашого життя. На сьогодні у світі є автомобілі \
                різного призначення, і їхня кількість збільшується. Відповідно, збільшується і кількість водіїв. \
                Саме по собі авто це складний механічний пристрій, що включає в себе безліч різних компонентів, \
                кожен з яких виконує свою роль. Він є результатом багатьох років інновацій та вдосконалень, що \
                постійно розвиваються. Отже, поїхали!
    
                Автомобіль складається з 3 основних систем
    
                \t\t\t ⚙️ \t\t ДВЗ
    
                \t\t\t \uD83D\uDD27 \t\t Шасі або платформа
    
                \t\t\t \uD83D\uDEFB \t\t Кузов автомобіля
    
                Всі вони забезпечують його коректну та ефективну роботу. Кожна з цих систем має свою власну структуру \
                та функції, і всі вони взаємодіють між собою. І як результат, машина має змогу рухатися. Цей розділ \
                присвячений детальному ознайомленню з архітектурою автомобіля, де Ви зможете дізнатися більше про ці \
                системи завдяки детальному опису, малюнкам, кресленням та поясненнями \uD83D\uDC47
    
                Отже, перед вами є ці складові, що включають цей опис до кожної системи. Ви можете тиснути по кнопках \
                і ретельно вивчати матеріал, який закладено в кожну з них. Звичайно, що більш детальну інформацію можна \
                знайти і в інтернеті, і в книжках, і т. д., але тут викладено все чітко та стисло, щоб зрозуміти основну \
                суть та загальний принцип роботи. Оскільки авто складний механізм, вивчити все і відразу неможливо. Кожен \
                ТЗ по-своєму унікальний, кожен потребує правильного догляду та відношення. Тому усе поступово. Спочатку \
                ази, а надалі можна вивчати більше. Успіхів \uD83D\uDCAA""");
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            Message sentMessage = execute(message);
            int sentMessageId = sentMessage.getMessageId();
            deleteMessage(chatId, sentMessageId - 1);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendStructureTypes(long chatId, String callback) {
        List<Types> types;
        switch (callback) {
            case "engine" -> types = typesRepos.findByName("ДВЗ");
            case "chassis" -> types = typesRepos.findByName("Шасі");
            case "body" -> types = typesRepos.findByName("Кузов");
            case "species" -> types = typesRepos.findByName("Види");
            default -> {
                return;
            }
        }
        if (types.isEmpty()) {
            sendError(chatId);
            return;
        }
        List<Integer> messageIds = sentMessages.computeIfAbsent(chatId, k -> new ArrayList<>());
        for (Types type : types) {
            List<String> photoPaths = new ArrayList<>();
            List<String> videoPaths = new ArrayList<>();
            for (TypesImages image : type.getImages()) {
                photoPaths.add(image.getPhotoPath());
            }
            for (VideoPaths video : type.getVideos()) {
                videoPaths.add(video.getVideoPath());
            }
            String description = type.getDescription();
            sendStructureInfo(chatId, photoPaths, videoPaths, description, callback.equals("engine"), callback.equals("chassis"), messageIds);
        }
        sendCloseButton(chatId, messageIds);
    }

    public void sendStructureInfo(long chatId, List<String> photos, List<String> videos, String caption, boolean isEngine, boolean isChassis, List<Integer> messageIds) {
        for (String photoPath : photos) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(String.valueOf(chatId));
            try {
                File photoFile = new ClassPathResource(photoPath).getFile();
                InputFile inputFile = new InputFile(photoFile);
                sendPhoto.setPhoto(inputFile);
                Message sentPhotoMessage = execute(sendPhoto);
                messageIds.add(sentPhotoMessage.getMessageId());
            } catch (IOException | TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        for (String videoPath : videos) {
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(String.valueOf(chatId));
            try {
                File videoFile = new ClassPathResource(videoPath).getFile();
                InputFile inputFile = new InputFile(videoFile);
                sendVideo.setVideo(inputFile);
                Message sentVideoMessage = execute(sendVideo);
                messageIds.add(sentVideoMessage.getMessageId());
            } catch (IOException | TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(caption);
        message.setParseMode("HTML");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        if (isEngine) {
            Keyboard keyboardBuilder = new Keyboard();
            InlineKeyboardMarkup engineButtons = keyboardBuilder.buildEngineButtons();
            keyboard.addAll(engineButtons.getKeyboard());
        } else if (isChassis) {
            Keyboard keyboardBuilder = new Keyboard();
            InlineKeyboardMarkup chassisButtons = keyboardBuilder.buildChassisButtons();
            keyboard.addAll(chassisButtons.getKeyboard());
        }
        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            Message sentMessage = execute(message);
            messageIds.add(sentMessage.getMessageId());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCloseButton(long chatId, List<Integer> messageIds) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Бажаєте повернутися до головного меню? Натисніть, щоб закрити розділ \uD83D\uDC47");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton closeButton = new InlineKeyboardButton();
        closeButton.setText("Закрити");
        closeButton.setCallbackData("close_body");
        List<InlineKeyboardButton> closeButtonRow = new ArrayList<>();
        closeButtonRow.add(closeButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(closeButtonRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            Message sentMessage = execute(message);
            messageIds.add(sentMessage.getMessageId());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}