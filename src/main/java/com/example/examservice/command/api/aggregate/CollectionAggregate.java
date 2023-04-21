package com.example.examservice.command.api.aggregate;

import com.example.examservice.command.api.commands.CreateCollectionCommand;
import com.example.examservice.command.api.commands.DeleteCollectionCommand;
import com.example.examservice.command.api.commands.UpdateCollectionCommand;
import com.example.examservice.command.api.events.CollectionCreateEvent;
import com.example.examservice.command.api.events.CollectionDeleteEvent;
import com.example.examservice.command.api.events.CollectionUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CommandHandlerInterceptor;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class CollectionAggregate {

    @AggregateIdentifier
    String id;
    String name;

    public CollectionAggregate() {
    }

    @CommandHandler //create
    public CollectionAggregate(CreateCollectionCommand createCollectionCommand){
        log.info("CreateCollectionCommand :: {}", createCollectionCommand);

        AggregateLifecycle.apply(CollectionCreateEvent
                .builder()
                .id(createCollectionCommand.getId())
                .name(createCollectionCommand.getName())
                .build());
    }

    @EventSourcingHandler //create
    public void on(CollectionCreateEvent collectionCreateEvent){
        log.info("CreateCollectionCommand occurred");
        this.id = collectionCreateEvent.getId();
        this.name = collectionCreateEvent.getName();
    }

    @CommandHandler //update
    public void on(UpdateCollectionCommand updateCollectionCommand){
        log.info("UpdateCollectionCommand :: {}", updateCollectionCommand);

        AggregateLifecycle.apply(CollectionUpdateEvent
                .builder()
                .id(updateCollectionCommand.getId())
                .name(updateCollectionCommand.getName())
                .build());
    }

    @EventSourcingHandler //update
    public void on(CollectionUpdateEvent collectionUpdateEvent){
        log.info("UpdateCollectionCommand occurred");
        this.name = collectionUpdateEvent.getName();
    }

    @CommandHandler //delete
    public void on(DeleteCollectionCommand deleteCollectionCommand){
        log.info("DeleteCollectionCommand :: {}", deleteCollectionCommand);

        AggregateLifecycle.apply(CollectionDeleteEvent
                .builder()
                .id(deleteCollectionCommand.getId())
                .build());
    }

//    @EventSourcingHandler //update
//    public void on(CollectionUpdateEvent collectionUpdateEvent){
//        log.info("UpdateCollectionCommand occurred");
//    }
}
