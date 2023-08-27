package com.aodfeedback.demo.exchangemessagebot;

import com.aodfeedback.demo.configuration.BotConfig;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class BotFunctional extends TelegramLongPollingBot {

        final BotConfig config;

    public BotFunctional(@Value("${bot.token}") BotConfig config) {
        super(String.valueOf(config));
        this.config = config;
    }


    @Override
    public void onUpdateReceived(@NotNull Update update) {
    if(update.hasMessage() && update.getMessage().hasText()){
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        String memberName = update.getMessage().getFrom().getFirstName();

        switch (messageText){
            case "/start":
                startBot(chatId, memberName);
                break;
            default: log.info("Unexpected message");
        }
    }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    private void startBot(long chatId, String userName){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hello, " + userName + "! I'm AoD FeedBack Bot.");

        try {
            execute(message);
            log.info("Replay sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
}
