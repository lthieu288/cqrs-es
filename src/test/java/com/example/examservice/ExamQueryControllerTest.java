package com.example.examservice;

import com.example.examservice.command.api.controller.feign.OFQuestionRest;
import com.example.examservice.command.api.dto.response.Response;
import com.example.examservice.entity.Exam;
import com.example.examservice.query.api.controller.ExamQueryController;
import com.example.examservice.query.api.queries.GetExamsWithPagableQuery;
import com.example.examservice.repository.ExamRepository;
import com.example.examservice.utils.ResponseUtils;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.queryhandling.QueryGateway;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class ExamQueryControllerTest {

    private  ExamRepository examRepo;

    private OFQuestionRest ofQuestionRest;
    private QueryGateway queryGateway;

    private ExamQueryController examQueryController;


    private final Exam exam = new Exam();

    @BeforeEach
    void init(){
        examRepo = mock(ExamRepository.class);
        ofQuestionRest = mock(OFQuestionRest.class);
        queryGateway = mock(QueryGateway.class);
        examQueryController = new ExamQueryController(queryGateway,ofQuestionRest,examRepo);
        exam.setId("1");
        exam.setName("TOEIC");
        exam.setCreatedDate(null);
        exam.setMediaLink("http://abc");
        exam.setNumTakers(1L);
        exam.setNumComments(2L);
        exam.setCollectionId("1");
    }

    @Test
    void getTopExams_ReturnList_Success() {
        Exam exam2 = new Exam();
        exam2.setId("2");
        exam2.setName("TOEIC2");
        exam2.setCreatedDate(null);
        exam2.setMediaLink("http://abc2");
        exam2.setNumTakers(1L);
        exam2.setNumComments(2L);
        exam2.setCollectionId("1");
        List<Exam> exams= new ArrayList<>(Arrays.asList(exam,exam2));
        CompletableFuture<List<Exam>> future = CompletableFuture.completedFuture(exams);
        when(queryGateway.query(any(), any(ResponseType.class))).thenReturn(future);
        ResponseEntity<?> responseEntity = examQueryController.getTopExams();
        assertThat(responseEntity.getStatusCode(),is(HttpStatus.OK));
        assertThat(responseEntity.getBody(),is(Response.success(exams)));
    }
//    @Test
//    void getTopExams_ReturnListWithPage_Success() {
//        int page = 0;
//        int limit = 10;
//        Exam exam2 = new Exam();
//        exam2.setId("2");
//        exam2.setName("TOEIC2");
//        exam2.setCreatedDate(null);
//        exam2.setMediaLink("http://abc2");
//        exam2.setNumTakers(1L);
//        exam2.setNumComments(2L);
//        exam2.setCollectionId("1");
//        List<Exam> exams= new ArrayList<>(Arrays.asList(exam,exam2));
//        CompletableFuture<List<Exam>> future = CompletableFuture.completedFuture(exams);
//        GetExamsWithPagableQuery query = new GetExamsWithPagableQuery(page, limit);
//
//        when(queryGateway.query(eq(query), any(ResponseType.class))).thenReturn(future);
//
//        ResponseEntity<?> responseEntity = examQueryController.getTopExams(limit, page);
//
//        assertThat(responseEntity.getStatusCode(),is(HttpStatus.OK));
//        assertThat(responseEntity.getBody(),is(Response.success(exams)));
//    }

    @Test
    void getDetailExam_FindIdExam_ResponseUtilsError() {
        when(examRepo.findById("1")).thenReturn(Optional.empty());
        assertThat(ResponseUtils.error(HttpStatus.NOT_FOUND, "Not found exam"),is(examQueryController.getDetailExam("1")));
    }
    @Test
    void getDetailExam_FindIdExam_Success() {
        when(examRepo.findById("1")).thenReturn(Optional.of(exam));
        Map<String, Object> detail = new HashMap<>();
        detail.put("detail1", "value1");
        detail.put("detail2", "value2");
        when(ofQuestionRest.getDetailExam("1")).thenReturn(detail);
        ResponseEntity<?> response = examQueryController.getDetailExam("1");
        assertThat(HttpStatus.OK,is(response.getStatusCode()));
        assertThat(response.getBody(),is(Response.success(detail)));
    }
}

