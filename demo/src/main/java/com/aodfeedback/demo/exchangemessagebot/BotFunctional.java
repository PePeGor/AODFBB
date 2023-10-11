package com.aodfeedback.demo.exchangemessagebot;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class BotFunctional extends TelegramLongPollingBot {


    private static final Logger LOG = LoggerFactory.getLogger(BotFunctional.class);

    public BotFunctional(@Value("${bot.token}") String botToken) {
        super(botToken);

    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        LOG.info("message from user id {} && user name is {}", update.getMessage().getFrom().getId(), update.getMessage().getFrom().getUserName());
        LOG.info("user message.text: {}", update.getMessage().getText());
        if (update.getMessage().getText().equals("/hello")) {
            LOG.info("HYITA");
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Hello " + update.getMessage().getFrom().getUserName());
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                LOG.error("Ваня долбаеб", e);
            }
        }
        if (update.getMessage().getText().equals("Ваня долбаеб")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Не очень умный ");
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                LOG.error("Ваня долбаеб", e);
            }
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

        SendMessage message = new SendMessage();
        message.setText("Hello World");
        message.setChatId(395137825L);
        execute(message);

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

        writeUser.close();

    }



}
