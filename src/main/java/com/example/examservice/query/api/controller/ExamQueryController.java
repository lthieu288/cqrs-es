package com.example.examservice.query.api.controller;

import com.example.examservice.command.api.controller.feign.OFQuestionRest;
import com.example.examservice.command.api.dto.response.GetCollectionResponse;
import com.example.examservice.entity.Exam;
import com.example.examservice.query.api.queries.GetCollectionsQuery;
import com.example.examservice.query.api.queries.GetExamsQuery;
import com.example.examservice.query.api.queries.GetExamsWithPagableQuery;
import com.example.examservice.repository.ExamRepository;
import com.example.examservice.utils.ResponseUtils;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private OFQuestionRest ofQuestionRest;

    @Autowired
    private ExamRepository examRepo;

    @GetMapping("/top")
    public ResponseEntity<?> getTopExams (){
        GetExamsQuery getExamsQuery = new GetExamsQuery();

        List<Exam> result = queryGateway.query(getExamsQuery, ResponseTypes.multipleInstancesOf(Exam.class)).join();
        return ResponseUtils.success(result);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getTopExams (@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                   @RequestParam(value = "page", defaultValue = "0") Integer page){
        GetExamsWithPagableQuery getExamsQuery = new GetExamsWithPagableQuery(page, limit);

        List<Exam> result = queryGateway.query(getExamsQuery, ResponseTypes.multipleInstancesOf(Exam.class)).join();
        return ResponseUtils.success(result);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetailExam (@PathVariable String id){
        Exam exam = examRepo.findById(id).orElse(null);
        if(exam == null){
            return ResponseUtils.error(HttpStatus.NOT_FOUND, "Not found exam");
        }

        Object result = ofQuestionRest.getDetailExam(id);
        return ResponseUtils.success(result);
    }
}
