package com.example.examservice.query.api.projection;

import com.example.examservice.command.api.dto.response.GetCollectionResponse;
import com.example.examservice.entity.Collection;
import com.example.examservice.entity.Exam;
import com.example.examservice.query.api.queries.GetCollectionsQuery;
import com.example.examservice.query.api.queries.GetExamsQuery;
import com.example.examservice.query.api.queries.GetExamsWithPagableQuery;
import com.example.examservice.repository.CollectionRepository;
import com.example.examservice.repository.ExamRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExamProjection {

    @Autowired
    private ExamRepository examRepository;

    @QueryHandler
    public List<Exam> handle(GetExamsQuery getExamsQuery){
        //Get exam
        Pageable sortByDate = PageRequest.of(0, 4, Sort.by("createdDate").descending());
        Page<Exam> examPage = examRepository.findAll(sortByDate);
        List<Exam> examResponseList = new ArrayList<>(examPage.getContent());

        return examResponseList;
    }

    @QueryHandler
    public List<Exam> handle(GetExamsWithPagableQuery getExamsQuery){
        //Get exam
        Pageable sortByDate = PageRequest.of(getExamsQuery.getPage(), getExamsQuery.getSize(), Sort.by("createdDate").descending());
        Page<Exam> examPage = examRepository.findAll(sortByDate);
        List<Exam> examResponseList = new ArrayList<>(examPage.getContent());

        return examResponseList;
    }
}
