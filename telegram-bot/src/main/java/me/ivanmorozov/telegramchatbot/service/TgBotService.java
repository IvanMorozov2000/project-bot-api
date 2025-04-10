package me.ivanmorozov.telegramchatbot.service;


import lombok.extern.slf4j.Slf4j;
import me.ivanmorozov.telegramchatbot.config.TgBotConfig;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class TgBotService extends TelegramLongPollingBot {
    private final TgBotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getBOT_NAME();
    }
    @Override
    public String getBotToken() {
        return botConfig.getBOT_TOKEN();
    }


    public TgBotService(TgBotConfig botConfig){
        this.botConfig = botConfig;
        List<BotCommand> listCommand = new ArrayList<>();
        listCommand.add(new BotCommand("/start", "Начните работу с ботом"));
        listCommand.add(new BotCommand("/track", "Подписаться на новости по ссылке"));
        listCommand.add(new BotCommand("/untrack", "Отписаться от новостей"));
        listCommand.add(new BotCommand("/help", "Информация и помощь"));
        listCommand.add(new BotCommand("/list", "Показать все отслеживаемые ссылки"));
        listCommand.add(new BotCommand("/settings", "Настройки уведомлений"));
        try {
            this.execute(new SetMyCommands(listCommand, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e){
            log.error("Ошибка :/ " + e.getMessage());
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
             String msg = update.getMessage().getText();
             long chatId = update.getMessage().getChatId();
             String userName = update.getMessage().getChat().getUserName();

            try {

                switch (msg) {
                    case "/start" -> startCommand(chatId, userName);
                    case "/help" -> sendMsg(chatId, HELP_TEXT);

                    default -> sendMsg(chatId, "Команда не найдена :(");
                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void startCommand(long chatId, String userName) throws TelegramApiException {
        String answer = "Приветсвую, " + userName +", давайте я расскажу что умею...";
        sendMsg(chatId,answer);
        log.info("Пользователь сделал запрос " + userName);
    }

    private void trackCommand(long chatId, String link) throws TelegramApiException{
        String answer = "Вы подписались на получение новостей по ссылке: " + link;

        // логика подписки

        sendMsg(chatId, answer);
    }
    private void unTrackCommand(long chatId, String link) throws TelegramApiException{
        String answer = "Вы отписались от получение новостей по ссылке: " + link;

        // логика отпописки

        sendMsg(chatId, answer);
    }


    private void sendMsg(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textSend);

        try {
            execute(sendMessage);
        } catch (TelegramApiException te){
            log.error("ERROR / : "  + te.getMessage());
        }
    }

    final String HELP_TEXT = "\uD83D\uDCDA *Справка по боту*\n" +
            "\n" +
            "Основные команды:\n" +
            "`/start` - Начать работу с ботом\n" +
            "`/help` - Показать эту справку\n" +
            "`/track <ссылка>` - Добавить ссылку для отслеживания\n" +
            "`/untrack <ссылка>` - Удалить ссылку из отслеживания\n" +
            "`/list` - Показать все отслеживаемые ссылки\n" +
            "`/settings` - Настройки уведомлений\n" +
            "\n" +
            "\uD83D\uDCE2 *Как использовать?*\n" +
            "1. Отправьте `/track https://example.com`\n" +
            "2. Бот будет присылать уведомления при изменениях  \n" +
            "\n" +
            "\uD83D\uDEE0 *Поддержка*:\n" +
            "А кому сейчас легко?\n" +
            "Не работает? Ну и хуй бы с ним!";
}
