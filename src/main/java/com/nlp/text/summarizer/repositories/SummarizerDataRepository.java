package com.nlp.text.summarizer.repositories;

import com.nlp.text.summarizer.com.nlp.text.summarizer.entity.SummarizerData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "summarize", path = "summarize")
public interface SummarizerDataRepository extends PagingAndSortingRepository<SummarizerData, Integer> {





}
