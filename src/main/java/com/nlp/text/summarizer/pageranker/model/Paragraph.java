package com.nlp.text.summarizer.pageranker.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class Paragraph{
	int number;
	List<Sentence> sentences;

	public Paragraph(int number){
		this.number = number;
		sentences = new ArrayList<>();
	}
}