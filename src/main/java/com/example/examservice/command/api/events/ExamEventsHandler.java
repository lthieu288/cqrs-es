package com.example.examservice.command.api.events;

import com.example.examservice.entity.Collection;
import com.example.examservice.entity.Exam;
import com.example.examservice.repository.CollectionRepository;
import com.example.examservice.repository.ExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ExamEventsHandler {

    @Autowired
    private ExamRepository examRepo;

    @Autowired
    private CollectionRepository collectionRepo;

    @EventHandler
    public void on (ExamCreateEvent event){

        Collection collection = collectionRepo.findById(event.getCollectionId()).orElse(null);
        if(collection == null){
            return;
        }

        Exam exam = new Exam();
        BeanUtils.copyProperties(event, exam);
        exam.setNumTakers(0L);
        exam.setCreatedDate(new Date());
        exam.setNumComments(0L);
        examRepo.save(exam);
    }
}
