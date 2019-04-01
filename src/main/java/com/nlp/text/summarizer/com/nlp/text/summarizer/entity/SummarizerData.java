package com.nlp.text.summarizer.com.nlp.text.summarizer.entity;


import com.nlp.text.summarizer.pageranker.model.Sentence;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "user_text_data")
@Getter
@Setter
@ToString
public class SummarizerData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String paragraph;


    @Column
    private LocalDateTime creationDate;

    @Transient
    private List<Sentence> summarizedSentences;


    public SummarizerData(){

    }

    public SummarizerData(String paragraph){
        this.paragraph = paragraph;
        this.creationDate = LocalDateTime.now();
    }


}
