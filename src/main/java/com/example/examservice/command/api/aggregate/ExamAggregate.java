package com.example.examservice.command.api.aggregate;

import com.example.examservice.command.api.commands.CreateCollectionCommand;
import com.example.examservice.command.api.commands.CreateExamCommand;
import com.example.examservice.command.api.commands.DeleteCollectionCommand;
import com.example.examservice.command.api.commands.UpdateCollectionCommand;
import com.example.examservice.command.api.events.CollectionCreateEvent;
import com.example.examservice.command.api.events.CollectionDeleteEvent;
import com.example.examservice.command.api.events.CollectionUpdateEvent;
import com.example.examservice.command.api.events.ExamCreateEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Column;
import java.util.Date;

@Aggregate
@Slf4j
public class ExamAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private Long numTakers;
    private Long numComments;
    private String collectionId;
    private String mediaLink;
    private Date createdDate;

    public ExamAggregate() {
    }

    @CommandHandler //create
    public ExamAggregate(CreateExamCommand command){
        log.info("CreateExamCommand :: {}", command);

        AggregateLifecycle.apply(ExamCreateEvent
                .builder()
                .id(command.getId())
                .name(command.getName())
                .collectionId(command.getCollectionId())
                .build());
    }

    @EventSourcingHandler //create
    public void on(ExamCreateEvent event){
        log.info("ExamCreateEvent occurred");
        this.id = event.getId();
        this.name = event.getName();
        this.collectionId = event.getCollectionId();
    }
}
