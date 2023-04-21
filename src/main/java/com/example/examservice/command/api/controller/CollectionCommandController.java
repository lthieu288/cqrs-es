package com.example.examservice.command.api.controller;

import com.example.examservice.command.api.dto.request.CreateCollectionRequest;
import com.example.examservice.command.api.dto.request.DeleteCollectionRequest;
import com.example.examservice.command.api.dto.request.UpdateCollectionRequest;
import com.example.examservice.command.api.service.CollectionCommandService;
import com.example.examservice.entity.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/collection")
public class CollectionCommandController {

    @Autowired
    private CollectionCommandService collectionCommandService;

    @PostMapping
    public ResponseEntity<?> addCollection(@RequestBody CreateCollectionRequest collection){
        try {
            CompletableFuture<String> response = collectionCommandService.createCollection(collection);

            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<?> editCollection(@RequestBody UpdateCollectionRequest updateCollectionRequest){
        try {
            CompletableFuture<String> response = collectionCommandService.updateCollection(updateCollectionRequest);
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCollection(@RequestBody DeleteCollectionRequest deleteCollectionRequest){
        try {
            CompletableFuture<String> response = collectionCommandService.deleteCollection(deleteCollectionRequest);

            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
