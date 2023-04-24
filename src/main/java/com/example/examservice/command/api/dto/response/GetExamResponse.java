package com.example.examservice.command.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetExamResponse {
    private String id;
    private String name;
    private String collectionId;
    private Date createdDate;
}
