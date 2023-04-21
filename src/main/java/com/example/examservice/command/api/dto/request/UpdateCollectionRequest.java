package com.example.examservice.command.api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCollectionRequest {
    private String id;
    private String name;
}
