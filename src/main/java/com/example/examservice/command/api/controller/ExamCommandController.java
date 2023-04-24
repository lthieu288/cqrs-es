package com.example.examservice.command.api.controller;

import com.example.examservice.command.api.dto.request.CreateCollectionRequest;
import com.example.examservice.command.api.dto.request.CreateExamRequest;
import com.example.examservice.command.api.service.CollectionCommandService;
import com.example.examservice.command.api.service.ExamCommandService;
import com.example.examservice.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/exam")
public class ExamCommandController {

    @Autowired
    private ExamCommandService examCommandService;

    @PostMapping
    public ResponseEntity<?> createExam(@RequestBody CreateExamRequest request){
        try {
            CompletableFuture<String> response = examCommandService.createExam(request);

            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/questionCrt")
    public ResponseEntity<?> createQuestionInExam(@RequestBody Map<String, Object> map) {
        try {
            //Create option list
            long startTime = System.nanoTime(); // Lấy thời điểm bắt đầu thực thi hàm
            Object result = examCommandService.createOptionList(map);
            long endTime = System.nanoTime(); // Lấy thời điểm kết thúc thực thi hàm
            long duration = (endTime - startTime) / 1000000; // Tính toán thời gian thực thi hàm
            System.out.println("Thời gian thực thi của hàm là: " + duration + "ms");

            if(result != null){
                return ResponseUtils.success(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception");
        }
        return ResponseUtils.error(HttpStatus.NO_CONTENT, "Post exam fail");
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitExam(@RequestBody Map<String, Object> map) {
        try {
            Object result = examCommandService.submitExam(map);

            if(result != null){
                return ResponseUtils.success(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception");
        }
        return ResponseUtils.error(HttpStatus.NO_CONTENT, "Post exam fail");
    }
}
