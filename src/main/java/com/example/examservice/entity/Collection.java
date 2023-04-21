package com.example.examservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
public class Collection {

    @Id
    private String id;
    private String name;
}
