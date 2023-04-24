package com.example.examservice.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetExamsWithPagableQuery {
    int page;
    int size;
}
