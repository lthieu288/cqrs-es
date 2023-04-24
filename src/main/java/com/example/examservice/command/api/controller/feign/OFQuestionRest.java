package com.example.examservice.command.api.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(value = "test-consumer",
//        url = "http://localhost:8080"
        url = "http://18.139.83.149:8080"
)
public interface OFQuestionRest {

    @PostMapping(value = "/exam/questionCrt", produces = MediaType.APPLICATION_JSON_VALUE)
    Object postExam(Map<String, Object> map);

    @PostMapping(value = "/exam/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    Object getDetailExam(String id);

    @PostMapping(value = "/exam/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    Object submitAnswer(Map<String, Object> map);
}