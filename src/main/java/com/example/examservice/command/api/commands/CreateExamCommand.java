package com.example.examservice.command.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateExamCommand {

    @TargetAggregateIdentifier
    String id;
    String collectionId;
    String name;
}
