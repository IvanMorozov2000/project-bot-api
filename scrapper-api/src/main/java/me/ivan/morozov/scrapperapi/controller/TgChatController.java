package me.ivan.morozov.scrapperapi.controller;

import lombok.RequiredArgsConstructor;
import me.ivan.morozov.scrapperapi.dao.TgChatRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class TgChatController {
    private final TgChatRepository repository;

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> registerChat(@PathVariable long id){
        if (repository.exist(id)){
            return ResponseEntity.badRequest().build();
        }
        repository.add(id);
        return ResponseEntity.ok().build();
    }

}
