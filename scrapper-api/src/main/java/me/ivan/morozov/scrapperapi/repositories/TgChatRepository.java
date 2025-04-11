package me.ivan.morozov.scrapperapi.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class TgChatRepository {
   private final ConcurrentHashMap<Long, LocalDateTime> chats = new ConcurrentHashMap<>();

    public void add (Long id){
        chats.put(id, LocalDateTime.now());
        log.info("NEW ADD / "+"Добавление нового чата " + id);
    }

    public boolean exist(Long id){
       return chats.containsKey(id);
    }

    public void delete(Long id){
        chats.remove(id);
        log.info("DELETE / "+"Удаление нового чата " + id);
    }

    public LocalDateTime getRegisterTime(Long id){
        return chats.get(id);
    }

    @Override
    public String toString() {
        return "TgChatRepository{" +
                "chats=" + chats +
                '}';
    }
}
