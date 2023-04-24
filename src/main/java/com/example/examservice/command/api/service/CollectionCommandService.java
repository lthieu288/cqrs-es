package com.example.examservice.command.api.service;

import com.example.examservice.command.api.commands.CreateCollectionCommand;
import com.example.examservice.command.api.commands.DeleteCollectionCommand;
import com.example.examservice.command.api.commands.UpdateCollectionCommand;
import com.example.examservice.command.api.dto.request.CreateCollectionRequest;
import com.example.examservice.command.api.dto.request.DeleteCollectionRequest;
import com.example.examservice.command.api.dto.request.UpdateCollectionRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CollectionCommandService {

    @Autowired
    private CommandGateway commandGateway;

    public CompletableFuture<String> createCollection (CreateCollectionRequest collectionRequest){
        return commandGateway.send(CreateCollectionCommand
                .builder()
                .id(UUID.randomUUID().toString())
                .name(collectionRequest.getName())
                .build());
    }

    public CompletableFuture<String> updateCollection (UpdateCollectionRequest collectionRequest){
        return commandGateway.send(UpdateCollectionCommand
                .builder()
                .id(collectionRequest.getId())
                .name(collectionRequest.getName())
                .build());
    }

    public CompletableFuture<String> deleteCollection (DeleteCollectionRequest collectionRequest){
        return commandGateway.send(DeleteCollectionCommand
                .builder()
                .id(collectionRequest.getId())
                .build());
    }
}
