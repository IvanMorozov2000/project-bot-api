package me.ivanmorozov.telegramchatbot.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class ScrapperClient {
    private final WebClient webClient;


    public ScrapperClient() {
        String url = "http://localhost:9081";
        this.webClient = WebClient.create(url);
    }

    public boolean registerChat(long chatId) {
        try {
            webClient.post()
                    .uri("/tg-chat/{id}", chatId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (WebClientResponseException e) {
            log.error("ERROR / " + "Ошибка в методе registerChat " + e.getMessage());
            return false;
        }
    }

    public boolean isChatRegister(long chatId) {
        try {
            return   webClient.get()
                      .uri("/tg-chat/{id}/exists", chatId)
                      .retrieve()
                      .bodyToMono(Boolean.class)
                      .block();
        } catch (WebClientResponseException e) {
            log.error("Ошибка проверки регистрации чата {}: {}", chatId, e.getStatusCode());
            return false;
        }
    }
}
