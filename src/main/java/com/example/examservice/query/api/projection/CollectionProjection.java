package com.example.examservice.query.api.projection;

import com.example.examservice.command.api.dto.response.GetCollectionResponse;
import com.example.examservice.entity.Collection;
import com.example.examservice.repository.CollectionRepository;
import com.example.examservice.query.api.queries.GetCollectionsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollectionProjection {

    @Autowired
    private CollectionRepository collectionRepository;

    @QueryHandler
    public List<GetCollectionResponse> handle(GetCollectionsQuery getCollectionsQuery){
        List<Collection> collections = collectionRepository.findAll();

        List<GetCollectionResponse> collectionRestModels =
                collections.stream()
                        .map(collection -> GetCollectionResponse
                                .builder()
                                .id(collection.getId())
                                .name(collection.getName())
                                        .build())
                        .collect(Collectors.toList());

        return collectionRestModels;
    }
}
