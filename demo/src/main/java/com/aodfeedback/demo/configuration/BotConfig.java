package com.aodfeedback.demo.configuration;

import com.aodfeedback.demo.exchangemessagebot.BotFunctional;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig{

    public TelegramBotsApi telegramBotsApi(BotFunctional botFunctional) throws TelegramApiException {
    var apiBot = new TelegramBotsApi(DefaultBotSession.class);
    apiBot.registerBot(botFunctional);
    return apiBot;
    }

}

