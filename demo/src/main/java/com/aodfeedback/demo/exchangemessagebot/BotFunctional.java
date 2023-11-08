package com.aodfeedback.demo.exchangemessagebot;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class BotFunctional extends TelegramLongPollingBot {

    private static final String SUPPORT_CHAT_ID = "-1001918439759L";

    private static final Logger LOG = LoggerFactory.getLogger(BotFunctional.class);

    private final Map<String, String> userChatIds = new HashMap<>();

    public BotFunctional(@Value("${bot.token}") String botToken) {
        super(botToken);

    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        LOG.info("message from user id {} && user name is {}", update.getMessage().getFrom().getId(), update.getMessage().getFrom().getUserName());
        LOG.info("user message.text: {}", update.getMessage().getText());
//        if (update.getMessage().getText().equals("/hello")) {
//            LOG.info("HYITA");
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setText("Hello " + update.getMessage().getFrom().getUserName());
//            sendMessage.setChatId(update.getMessage().getChatId().toString());
//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                LOG.error("Ваня долбаеб", e);
//            }
//        }
        if (update.hasMessage()) {
            forwardMessageToSupportChat(update.getMessage());
            sendResponseToUser(update.getMessage());
        }

        if (update.getMessage().getText().equals("/help")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Bot's help commands \n"
                    + "1. /start \n"
                    + "2. /hello \n"
                    + "3. /restart");
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                LOG.error("/help", e);
            }
        }

        if (update.getMessage().hasText()) {
            String userName = update.getMessage().getChat().getUserName();
            String userID = update.getMessage().getChat().getId().toString();
            writeUsersToCsvFile(userName, userID);
        }

        if (update.hasMessage()) {
            String userId = update.getMessage().getFrom().getId().toString();
            String chatId = update.getMessage().getChatId().toString();

            userChatIds.put(userId, chatId);

            if (update.getMessage().isReply() && update.getMessage().getReplyToMessage().hasText()) {
                String questionUserId = update.getMessage().getReplyToMessage().getFrom().getId().toString();
                respondToQuestion(update.getMessage(), questionUserId);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "This is AODFeedBack bot!";
    }

    public void writeUsersToCsvFile(String userName, String userID) throws IOException {

        FileWriter writeUser = new FileWriter("telegramBotUsers.csv", true);

        writeUser.append("userName");
        writeUser.append(",");
        writeUser.append("userID");
        writeUser.append("\n");

        writeUser.append(userName);
        writeUser.write(" , ");
        writeUser.append(userID);
        writeUser.append(" \n ");

        writeUser.close();

    }

    private void forwardMessageToSupportChat(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(SUPPORT_CHAT_ID);
        sendMessage.setText("Message from user " + message.getFrom().getUserName() + ": " + message.getText());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendResponseToUser(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Your message has been forwarded to our technical support team. They will respond as soon as possible.");

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void respondToQuestion(Message message, String questionUserId) {

        String questionUserChatId = userChatIds.get(questionUserId);

        if (questionUserChatId != null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(questionUserChatId);
            sendMessage.setText("You received a response to your question: " + message.getText());

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}

