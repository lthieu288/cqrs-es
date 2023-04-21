package com.example.examservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "exams")
public class Exam {
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_takers")
    private Long numTakers;

    @Column(name = "number_of_comments")
    private Long numComments;

    @Column(name = "collection_id")
    private Long collectionId;

    @Column(name = "media_link")
    private String mediaLink;

    @Column(name = "created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy")
    private Date createdDate;
}
