package com.example.examservice.command.api.service;

import com.example.examservice.command.api.commands.CreateExamCommand;
import com.example.examservice.command.api.controller.feign.OFQuestionRest;
import com.example.examservice.command.api.dto.request.CreateExamRequest;
import com.example.examservice.entity.Exam;
import com.example.examservice.repository.ExamRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class ExamCommandService {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private ExamRepository examRepo;

    @Autowired
    private OFQuestionRest ofQuestionRest;

    public CompletableFuture<String> createExam (CreateExamRequest request){
        return commandGateway.send(CreateExamCommand
                .builder()
                .id(UUID.randomUUID().toString())
                .collectionId(request.getCollectionId())
                .name(request.getName())
                .build());
    }

    public Object createOptionList(Map<String, Object> map) {
        try {
            String examId = map.get("examId").toString();
            Optional<Exam> examOptional = examRepo.findById(examId);
            if(examOptional.isEmpty()){
                return null;
            }

            //Save url audio
            Exam exam = examOptional.get();
            exam.setMediaLink(map.get("audioUrl").toString());
            examRepo.save(exam);

            Object response = ofQuestionRest.postExam(map);

            return response;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Object submitExam(Map<String, Object> map) {
        try {
            String examId = map.get("examId").toString();
            Optional<Exam> examOptional = examRepo.findById(examId);
            if(examOptional.isEmpty()){
                return null;
            }

            Object response = ofQuestionRest.submitAnswer(map);

            return response;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
