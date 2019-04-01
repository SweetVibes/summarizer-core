package com.nlp.text.summarizer.pageranker.model;

import com.nlp.text.summarizer.pageranker.helpers.SummaryTool;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Data
public class Sentence{

	private static int sentenceTracker = 1;
	int paragraphNumber;
	int number;
	int stringLength; //Dont need this 
	double score;
	int noOfWords;
	String value;
	String keywordSentence;
	public Sentence(int number, String value, int stringLength, int paragraphNumber){
		this.number = number;
		this.value = new String(value);
		this.keywordSentence=extractKeywords(SummaryTool.tagger.tagString(cleanup(value)));
		this.stringLength = stringLength;
		noOfWords = value.split("\\s+").length;
		score = 0.0;
		this.paragraphNumber=paragraphNumber;
	}
	public Sentence(String s) {
		this.number = sentenceTracker++;
		this.value = new String(s);
		this.paragraphNumber = 0;
		this.keywordSentence=extractKeywords(SummaryTool.tagger.tagString(cleanup(this.value)));
		this.stringLength = s.length();
		this.noOfWords = this.value.split(("\\s")).length;
		this.score = 0.0;
	}
	private String cleanup(String Text)
	{
		String fullText=new String(Text);
		// replace any punctuation char but apostrophes and dashes with a space
        fullText = fullText.replaceAll("[\\p{Punct}&&[^'-]]+", " ");
        
        // replace most common English contractions
        fullText = fullText.replaceAll("(?:'(?:[tdsm]|[vr]e|ll))+\\b", "");
        
        return fullText;
	}
	
	private String extractKeywords(String fullText)
	{
		 String Nadj="";
     	Pattern p = Pattern.compile("(?:(\\w+)NN[A-Z]?)|(?:(\\w+)JJ[A-Z]?)");
     	Matcher m=p.matcher(fullText);
     	while(m.find())
     	{
     		Nadj+=m.group()+" ";
     	}
     	Nadj=Nadj.replaceAll("(?:_NN[A-Z]?)|(?:_JJ[A-Z]?)","");
		System.out.println("Keywords : "+Nadj);
     	return Nadj;
	}
	
}