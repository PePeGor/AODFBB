package com.aodfeedback.demo.exchangemessagebot;

import ch.qos.logback.core.util.DelayStrategy;
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

//    @Override
//    public void onUpdateReceived(@NotNull Update update) {
//        if (!update.hasMessage() || !update.getMessage().hasText()) {
//            return;
//        }
//        String message = update.getMessage().getText();
//        Long chatId = update.getMessage().getChatId();
//
//        if ("/start".equals(message)) {
//            String userName = update.getMessage().getChat().getUserName();
//            startCommand(chatId, userName);
//        } else {
//            LOG.info("Unexpected message");
//        }

//
//    }

    @Override
    public void onUpdateReceived(Update update) {

            LOG.info("message from user id {} && user name is {}",update.getMessage().getFrom().getId(),update.getMessage().getFrom().getUserName());
            LOG.info("user message.text: {}", update.getMessage().getText());
            if (update.getMessage().getText().equals("/hello")){
                LOG.info("HYITA");
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Hello " + update.getMessage().getFrom().getUserName());
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                }catch (TelegramApiException e){
                    LOG.error("Ваня долбаеб" , e);
                }
            }
            if (update.getMessage().getText().equals("Ваня долбаеб")){
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Не очень умный ");
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                }catch (TelegramApiException e){
                    LOG.error("Ваня долбаеб" , e);
                }
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


//    public void forwardMessage(String chat_id,  int message_id) {
//        TelegramLongPollingBot bot = new TelegramLongPollingBot("${bot.token}");
//        try {
//            SendMessage request = new SendMessage();
//            request.setChatId(chat_id);
//            request.setText("Forwarding message from chat " + chat_id);
//            bot.execute(request);
//            ForwardMessage forwardMessage = new ForwardMessage();
//            forwardMessage.setChatId(dest_chat_id);
//            forwardMessage.setFromChatId(chat_id);
//            forwardMessage.setMessageId(message_id);
//            bot.execute(forwardMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
}
