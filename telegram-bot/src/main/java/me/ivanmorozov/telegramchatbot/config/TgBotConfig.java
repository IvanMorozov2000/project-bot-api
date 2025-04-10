package me.ivanmorozov.telegramchatbot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("/application.properties")
@Getter
public class TgBotConfig {
    @Value("${tg.bot.api.key}")
    private String BOT_TOKEN;

    @Value("${tg.bot.name}")
    private String BOT_NAME;

}
