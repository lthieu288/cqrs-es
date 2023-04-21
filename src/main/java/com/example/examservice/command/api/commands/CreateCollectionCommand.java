package com.example.examservice.command.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateCollectionCommand {

    @TargetAggregateIdentifier
    String id;
    String name;
}
