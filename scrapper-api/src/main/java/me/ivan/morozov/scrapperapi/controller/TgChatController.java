package me.ivan.morozov.scrapperapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ivan.morozov.scrapperapi.repositories.TgChatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
@Slf4j
public class TgChatController {
    private final TgChatRepository repository;

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> registerChat(@PathVariable("id") long id){
        try {
            if (repository.exist(id)){
                return ResponseEntity.badRequest().build();
            }
            repository.add(id);
            System.out.println(repository.toString()); // потом удалить
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Ошибка при добавлении чата", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsChat(@PathVariable("id") long id) {
        return ResponseEntity.ok(repository.exist(id));
    }

}
