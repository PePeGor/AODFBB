package com.aodfeedback.demo.configuration;

import com.aodfeedback.demo.exchangemessagebot.BotFunctional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@Data
@PropertySource("config.properties")
public class BotConfig {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;
    @Value("${bot.chatID}")
    String chatId;

    public String getBotName() {
        return null;
    }
}

//@Slf4j
//@Component
//public class Initializer {
//    @Autowired
//    CounterTelegramBot bot;
//
//    @EventListener({ContextRefreshedEvent.class})
//    public void initBot(){
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot((LongPollingBot) bot);
//        } catch (TelegramApiException e){
//            log.error(e.getMessage());
//        }
//    }
//}

@Component
class BotInitializer  {
    private final BotFunctional telegramBot;

    @Autowired
    public BotInitializer(BotFunctional telegramBot){
        this.telegramBot = telegramBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException{
        TelegramBotsApi telegramBotsApi  = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
        }catch (TelegramApiException ignored){

        }
    }
}