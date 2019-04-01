package com.nlp.text.summarizer.rest;


import com.nlp.text.summarizer.com.nlp.text.summarizer.entity.SummarizerData;
import com.nlp.text.summarizer.pageranker.helpers.SummaryTool;
import com.nlp.text.summarizer.pageranker.model.Sentence;
import com.nlp.text.summarizer.repositories.SummarizerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RepositoryRestController
@RequestMapping("/api")
public class TextSummarizerController{

    @Autowired
    SummarizerDataRepository repository;

    Logger logger = Logger.getLogger(TextSummarizerController.class.getName());


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/summarize")
    public @ResponseBody SummarizerData getSummarizerData(@RequestBody SummarizerData data ){
        System.out.println("Returning Summarized Data");
        SummaryTool summaryTool = new SummaryTool();

        logger.info(data.toString());
        repository.save(data);
        data.setSummarizedSentences(summaryTool.startSummarization(data.getParagraph()));
        return data;
    }



}
