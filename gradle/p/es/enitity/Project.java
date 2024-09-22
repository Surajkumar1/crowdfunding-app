package com.example.demo.es.enitity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "projects")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    private Long id;
    private String title;
    private String description;

    // Getters and Setters
}