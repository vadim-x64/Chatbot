package com.example.demo.components;

import com.example.demo.entities.History;
import com.example.demo.interfaces.HistoryRepos;
import com.example.demo.properties.Keyboard;
import com.example.demo.properties.Properties;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final Properties c;
    private final MarksButton marksButton;
    private final HistoryRepos historyRepos;
    private final CategoryButton categoryButton;
    private final StructureButton structureButton;
    private final PrincipleButton principleButton;

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
        if (update.hasMessage()) {
            String username = update.getMessage().getChat().getFirstName();
            long chatId = update.getMessage().getChatId();
            if (update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                if (messageText.startsWith("/")) {
                    handleCommand(messageText, chatId, username, update);
                } else if (messageText.equals("Категорії")) {
                    categoryButton.sendInlineCategoriesKeyboard(chatId);
                } else if (messageText.equals("Будова")) {
                    structureButton.handleTextMessage(chatId);
                } else if (messageText.equals("Принцип роботи")) {
                    principleButton.sendPrinciple(chatId);
                } else if (messageText.equals("Марки")) {
                    marksButton.sendMarks(chatId);
                } else {
                    sendMessage(chatId);
                }
            } else {
                sendMessage(chatId);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();

            if ("close".equals(callbackData)) {
                try {
                    int messageId = update.getCallbackQuery().getMessage().getMessageId();
                    String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
                    execute(new DeleteMessage(chatId, messageId));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                structureButton.onUpdateReceived(update);
            }
        }
    }

    private void handleCommand(String command, long chatId, String username, Update update) {
        switch (command) {
            case "/start":
                sendStartMessage(chatId, username);
                return;
            case "/restart":
                sendRestartMessage(chatId);
                return;
            case "/history":
                sendHistoryMessage(chatId);
                break;
            case "/info":
                sendForumMessage(chatId);
                break;
            case "/references":
                sendReferencesMessage(chatId);
                break;
            default:
                sendMessage(chatId);
        }
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            execute(new DeleteMessage(String.valueOf(chatId), update.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendStartMessage(long chatId, String username) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(
                "Привіт, " + username + " \uD83D\uDC4B\n\n" +
                        "Мене звати `AutoBot`. Я Ваш ментор у світі автомобілів. Тут Ви знайдете інформаційно-навчальний " +
                        "та якісний контент про авто. Моя мета - не лише допомогти Вам розібратися " +
                        "в основах автомобільної техніки, але й закріпити знання та відкрити для Вас величезний світ " +
                        "автомобільної індустрії.\n\n" +
                        "Надаю інформацію по будові авто, і, звичайно, пояснюю " +
                        "загальний принцип роботи. Більше того, Ви побачите справжні шедеври автопрому - потужні та " +
                        "ексклюзивні автомобілі від відомих світових виробників.\n\n" +
                        "Нижче розташована панель з кількома опціями. Ви можете ними користуватися, " +
                        "наприклад, натиснувши на одну з них, і Вам відкриється відповідне меню. " +
                        "Вони будуть доступні постійно, та навіть можна натиснути з правого боку іконку, щоб їх скрити.\n\n" +
                        "При кожному введені /start, вони будуть нагадувати про своє існування. " +
                        "Таким чином Ви маєте можливість без зайвих кроків отримувати доступ до них \uD83D\uDC47");
        Keyboard keyboardBuilder = new Keyboard();
        ReplyKeyboardMarkup keyboardMarkup = keyboardBuilder.buildKeyboard();
        keyboardMarkup.setOneTimeKeyboard(false);
        message.setReplyMarkup(keyboardMarkup);
        message.enableMarkdown(true);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRestartMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(
                "`AutoBot` до Ваших послуг. Я знову готовий до роботи!\n\nБудь ласка, оберіть опцію \uD83D\uDC47");
        Keyboard keyboardBuilder = new Keyboard();
        ReplyKeyboardMarkup keyboardMarkup = keyboardBuilder.buildKeyboard();
        keyboardMarkup.setOneTimeKeyboard(false);
        message.setReplyMarkup(keyboardMarkup);
        message.enableMarkdown(true);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendForumMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(
                """
                *Керівництво користувача* \uD83D\uDCCB
    
                Як правило, до будь-якої системи або програми існує певний, \
                скажімо так, перелік або набір правил та інструкцій щодо \
                експлуатації програмного продукту. У випадку з ботами це не є \
                виключенням, тому в даному розділі Ви, як користувач \
                бота і `Telegram` в цілому, маєте змогу вивчити його головне призначення \
                і, безпосередньо, навчитися взаємодіяти з ним, щоб користуватися програмою \
                було ефективно та зручно.
    
                Перегляньте наступні пункти \uD83D\uDC47
    
                /start → базова команда `Telegram`, яку може викликати \
                користувач для початку взаємодії з ботом. Вона запрограмована таким чином, \
                що коли Ви відправляєте її боту, то Вам стане доступна клавіатура, з основними кнопками програми. \
                Якщо випадковим чином видалити повідомлення - кнопки скриються. Активувати їх можна \
                таким же способом, ввівши наново цей запит.
    
                /restart → призначена перезапускати бота. Тобто, якщо Ви почистили історію бота, \
                то просто зайдіть у меню, і натисніть на цю опцію. Вам знову стане доступна клавіатура.
    
                /references → надаються корисні посилання на інтернет-джерела та літературу. \
                Ви можете переглядати відео в інтернеті у вигляді 3D-анімацій та наочно бачити \
                принцип роботи кожної деталі. А також знаходити опис того чи іншого механізму.
    
                /history → виступає у ролі запиту, що надає дані про початок розвитку першого авто і \
                завершуючи сучасними тенденціями. Описано все доволі коротко, \
                а більш детальні відомості можна знайти за посиланням, яке також там вказано.
    
                Стосовно клавіатури, то це головний інструмент з вивчення автомобіля. В ньому \
                нічого складного немає, тобто Ви просто тиснете по кнопках, читаєте опис відкритого \
                меню і спокійно вивчаєте матеріал. Він доступний, без обмежень та написаний в оригіналі \
                (тобто не просто `COPY + PASTE` з інтернету). \
                Вся інформація перевірена та носить достовірний характер.\
    
                
                Ознайомтеся з клавішами нижче \uD83D\uDC47
    
                \uD83D\uDDC2 `КАТЕГОРІЇ` → зібрані відомі світові моделі машин, що розбиті по категоріях. \
                Можуть містити опис, фото та відео.
    
                ⚙️ `ПРИНЦИП РОБОТИ` → частина, де чітко та обгрунтовано надано \
                загальний принцип роботи автомобіля.
    
                🔤️ `МАРКИ` → висунуто найпопулярніші та найкрутіші марки транспорту, \
                роки та країни їх заснування. Також додано короткий опис до них.
    
                \uD83D\uDD29 `БУДОВА` → значуща категорія, адже містить досить багато різних \
                характеристик та медіа матеріалів стосовно принципу роботи та архітектури авто. \
                Все поділено по розділах. Підготовлено зрозумілий та чітко обгрунтований \
                вміст.
    
                На завершення треба додати, що в кожному такому меню є кнопка `Закрити`, \
                при натисненні на яку, Ви закриєте відповідно відкритий розділ. Це зроблено для зручності \
                навігації та щоб не залишати повідомлень у чаті \uD83D\uDE09""");
        message.enableMarkdown(true);
        message.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendReferencesMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(
                "_Шановні автолюбителі_ \uD83E\uDD1F\n\n" +
                        "Сподіваюсь, Вам подобаються публікації, " +
                        "які Ви отримуєте в цій програмі. І вони приносять користь, а саме дають зрозуміти, " +
                        "які бувають машини та як вони працюють. Розумію що, можливо, не відразу все можна сприйняти " +
                        "та зрозуміти як саме все відбувається. Тому, саме для Вас маю невеличкий посібник з посиланнями " +
                        "на інтернет-ресурси, де Ви матимете змогу наочно бачити як працює кожна деталь певного механізму " +
                        "автомобіля.\n\n" +
                        "Інформаційні дані подано у вигляді 3D-анімацій. А також є 2 вебсайти, де все чітко " +
                        "та структуровано поділено по розділах.\n\n" +
                        "\uD83D\uDC49\u2063 https://green-way.com.ua/uk\n" +
                        "_вебсайт_ `GREEN-WAY`_ надає довідники, тести ПДР, новини і т. д._\n\n" +
                        "\uD83D\uDC49 https://automotive-heritage.com/\n" +
                        "_вебсайт_ `Автомобільна Спадщина` _пропонує типи та види авто, марки, будову і т. п._\n\n" +
                        "_Матеріали, що містять відео, подано англійською мовою. " +
                        "Відео, що вказані за цими посиланнями розміщенні на платформі_ `YouTube`\n\n" +
                        "\uD83D\uDC49 https://www.youtube.com/watch?v=nC6fsNXdcMQ\n" +
                        "_анімація, як працює диференціал_\n\n" +
                        "\uD83D\uDC49 https://www.youtube.com/watch?v=vRZu3-64yo0\n" +
                        "_анімація, як працює система охолодження_\n\n" +
                        "\uD83D\uDC49 https://www.youtube.com/watch?v=PmQnV1oxfe8\n" +
                        "_анімація, як працює зчеплення_\n\n" +
                        "\uD83D\uDC49 https://www.youtube.com/watch?v=wCu9W9xNwtI\n" +
                        "_механічна коробка передач_\n\n" +
                        "\uD83D\uDC49 https://www.youtube.com/watch?v=ZQvfHyfgBtA\n" +
                        "_ДВЗ та його робота (частина 1)_\n\n" +
                        "\uD83D\uDC49 https://www.youtube.com/watch?v=ASSsg8hcQjM\n" +
                        "_ДВЗ та його робота (частина 2)_");
        message.enableMarkdown(true);
        message.disableWebPagePreview();
        message.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendHistoryMessage(long chatId) {
        List<History> histories = historyRepos.findAll();
        for (History history : histories) {
            String photo = history.getPhoto();
            String text = history.getText();
            String symbol = "<i>\uD83D\uDCDC На фото 1885 рік. Берта з синами на автомобілі Карла Бенца.\n\n</i>";
            String detailsUrl = "https://surl.li/thawo";
            String caption = symbol + text + " \uD83D\uDC49 <a href='" + detailsUrl + "'>Читати</a> \uD83D\uDC48";
            sendPhotoWithCaption(chatId, photo, caption);
        }
    }

    private void sendPhotoWithCaption(long chatId, String photoPath, String caption) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(String.valueOf(chatId));
        try {
            File file = new ClassPathResource(photoPath).getFile();
            photo.setPhoto(new InputFile(file));
            photo.setCaption(caption);
            photo.setParseMode("HTML");
            photo.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
            execute(photo);
        } catch (TelegramApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(long chatId) {
        SendMessage message1 = new SendMessage();
        message1.setChatId(String.valueOf(chatId));
        message1.setText("\uD83E\uDD14");
        SendMessage message2 = new SendMessage();
        message2.setChatId(String.valueOf(chatId));
        message2.setText("Упс! Ви ввели невірну команду! Будь ласка, спробуйте ще раз.");
        try {
            execute(message1);
            execute(message2);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}