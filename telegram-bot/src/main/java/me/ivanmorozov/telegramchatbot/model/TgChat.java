package me.ivanmorozov.telegramchatbot.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TgChat {
    @Id
    private Long chatId;
    private LocalDateTime createdAt;
    private String status = "ACTIVE";
}
