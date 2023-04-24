package com.example.examservice.query.api.controller;

import com.example.examservice.command.api.dto.response.GetCollectionResponse;
import com.example.examservice.command.api.dto.response.Response;
import com.example.examservice.query.api.queries.GetCollectionsQuery;
import com.example.examservice.utils.ResponseUtils;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/collection")
public class CollectionQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public ResponseEntity<?> getAllCollections (){
        GetCollectionsQuery getCollectionsQuery = new GetCollectionsQuery();

        List<GetCollectionResponse> collectionRestModels =
                queryGateway.query(getCollectionsQuery, ResponseTypes.multipleInstancesOf(GetCollectionResponse.class))
                        .join();
        return ResponseUtils.success(collectionRestModels);
    }
}
