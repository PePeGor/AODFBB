package com.aodfeedback.demo.exchangemessagebot;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class BotFunctional extends TelegramLongPollingBot {


    private static final Logger LOG = LoggerFactory.getLogger(BotFunctional.class);

    public BotFunctional(@Value("${bot.token}") String botToken) {
        super(botToken);

    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        if ("/start".equals(message)) {
            String userName = update.getMessage().getChat().getUserName();
            startCommand(chatId, userName);
        } else {
            LOG.info("Unexpected message");
        }
    }

    private void startCommand(Long chatId, String userName) {
        var text = " Привет, это телеграм-бот AOD который отвечает на все вашы вопросы";

        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);

    }

    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error("Ошибка отправки сообщения", e);
        }
    }

    @Override
    public String getBotUsername() {
        return "This is AODFeedBack bot!";
    }


}
