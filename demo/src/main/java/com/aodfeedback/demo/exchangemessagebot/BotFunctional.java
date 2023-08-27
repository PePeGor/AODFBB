package com.aodfeedback.demo.exchangemessagebot;

import com.aodfeedback.demo.configuration.BotConfig;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.jcl.Log4jLog;
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
        //String memberName = update.getMessage().getFrom().getFirstName();

        switch (messageText){
            case "/start":
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                //startBot(chatId, memberName);
                break;
            default:
                Log4jLog log = null;
                log.info("Unexpected message");
        }
    }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

//    private void startBot(long chatId, String userName){
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId);
//        message.setText("Hello, " + userName + "! I'm AoD FeedBack Bot.");
//
//        Log4jLog log = null;
//        try {
//            execute(message);
//            log.info("Replay sent");
//        } catch (TelegramApiException e){
//            log.error(e.getMessage());
//        }
//    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Hi, " + name + ", nice to meet you!" + "\n" +
                "Enter the currency whose official exchange rate" + "\n" +
                "you want to know in relation to BYN." + "\n" +
                "For example: USD";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }
}
