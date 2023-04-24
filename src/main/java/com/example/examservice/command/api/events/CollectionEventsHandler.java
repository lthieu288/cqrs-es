package com.example.examservice.command.api.events;

import com.example.examservice.entity.Collection;
import com.example.examservice.repository.CollectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CollectionEventsHandler {

    @Autowired
    private CollectionRepository collectionRepo;

    @EventHandler
    public void on (CollectionCreateEvent event){
        Collection collection = new Collection();
        BeanUtils.copyProperties(event, collection);
        collectionRepo.save(collection);
    }

    @EventHandler
    public String on (CollectionUpdateEvent event){
        log.info("Handling CollectionUpdateEvent :: {}", event);
        Collection collection = collectionRepo.findById(event.getId()).orElse(null);

        if(collection != null){
            collection.setName(event.getName());
            collectionRepo.save(collection);
            return "ok";
        }
        return "fail";
    }

    @EventHandler
    public void on (CollectionDeleteEvent event){
        log.info("Handling CollectionDeleteEvent :: {}", event);
        Collection collection = collectionRepo.findById(event.getId()).orElse(null);

        if(collection != null){
            collectionRepo.delete(collection);
        }
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
