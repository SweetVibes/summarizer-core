package com.nlp.text.summarizer.pageranker.helpers;

import com.nlp.text.summarizer.pageranker.algo.PageRank;
import com.nlp.text.summarizer.pageranker.comparators.SentenceComparator;
import com.nlp.text.summarizer.pageranker.comparators.SentenceComparatorForSummary;
import com.nlp.text.summarizer.pageranker.model.Paragraph;
import com.nlp.text.summarizer.pageranker.model.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class SummaryTool{

	Logger logger = LoggerFactory.getLogger(SummaryTool.class);
	private String data;

	private List<Sentence> getSentences() {
		return sentences;
	}

	private List<Sentence> getContentSummary() {
		return contentSummary;
	}

	public List<Paragraph> getParagraphs() {
		return paragraphs;
	}

	public Map<Sentence, Double> getDictionary() {
		return dictionary;
	}

	private List<Sentence> sentences, contentSummary;
	private List<Paragraph> paragraphs;
	private double[][] intersectionMatrix;
	private Map<Sentence,Double> dictionary;
	 public final static MaxentTagger tagger ;
	 static {
			tagger= new MaxentTagger("taggers/english-left3words-distsim.tagger");
	 }

	public SummaryTool(){

	}

	public void init(String data){
		sentences = new ArrayList<>();
		paragraphs = new ArrayList<>();
		contentSummary = new ArrayList<>();
		dictionary = new LinkedHashMap<>();

		this.data = data;



	}

	/*Gets the sentences from the entire passage*/


	public void groupSentencesIntoParagraphs(){
		int paraNum = 0;
		Paragraph paragraph = new Paragraph(0);

		for(int i=0;i<sentences.size();i++){
			if(!(sentences.get(i).getParagraphNumber() == paraNum)){
                paragraphs.add(paragraph);
                paraNum++;
                paragraph = new Paragraph(paraNum);
			}
			paragraph.getSentences().add(sentences.get(i));
		}

		paragraphs.add(paragraph);
	}

	double noOfCommonWords(String str1, String str2){
		double commonCount = 0;
        String[] strWordList1 = str1.split("\\s+");
        String[] strWordList2= str2.split("\\s+");

        if(Arrays.equals(strWordList1, strWordList2)){
            return strWordList1.length;
        }


        Set<Integer> visitedInStr1 = new HashSet<>();
        Set<Integer> visitedInStr2= new HashSet<>();



		for(int i = 0; i<strWordList1.length; i++){
			for(int j = 0; j < strWordList2.length; j++){
				if(!visitedInStr1.contains(i) && !visitedInStr2.contains(j) && strWordList1[i].compareToIgnoreCase(strWordList2[j]) == 0){
					commonCount++;
					visitedInStr1.add(i);
					visitedInStr2.add(j);
				}
			}
		}

		return commonCount;
	}

	public void createIntersectionMatrix() throws IOException{
		intersectionMatrix = new double[sentences.size()][sentences.size()];
		for(int i=0;i<sentences.size();i++){
			for(int j=0;j<sentences.size();j++){

				if(i<=j){
					String str1 = KeywordsExtractor.getKeywordsList(sentences.get(i).getKeywordSentence());
					String str2 = KeywordsExtractor.getKeywordsList(sentences.get(j).getKeywordSentence());
					System.out.println("Sentence "+i+"----> "+str1+" Length --->"+str1.length());
					System.out.println("Sentence "+j+"----> "+str2+" Length --->"+str2.length());
					System.out.println("No of Common Words ------>"+ noOfCommonWords(str1,str2));
					intersectionMatrix[i][j] = noOfCommonWords(str1,str2) / ((double)(str1.split("\\s").length+ str2.split("\\s").length)/2);
					System.out.println("Score  ------> "+intersectionMatrix[i][j]);

				}else{
					intersectionMatrix[i][j] = intersectionMatrix[j][i];
				}
				
			}
		}
	}



	public void createDictionary(){
		
		PageRank pr=new PageRank(intersectionMatrix);
		double score[]= pr.calculatePageRank();
		for(int i=0;i<sentences.size();i++){
			dictionary.put(sentences.get(i), score[i]);
			((Sentence)sentences.get(i)).setScore(score[i]);
			}
			
			
		}
	

	public void createSummary(){
		System.out.println("Paragraph Size --------->"+paragraphs.size());
	      for(int j=0;j<paragraphs.size();j++){
	      		int primary_set = paragraphs.get(j).getSentences().size();

	      		//Sort based on score (importance)
	      		Collections.sort(paragraphs.get(j).getSentences(),new SentenceComparator());
		      	for(int i=0;i<primary_set;i++){
		      		contentSummary.add(paragraphs.get(j).getSentences().get(i));
		      	}
	      }

	      //To ensure proper ordering
	      Collections.sort(contentSummary,new SentenceComparatorForSummary());
		
	}


	void printSentences(){
		System.out.println("---------------------------------------------------All Sentences-----------------------------------------------");
		for(Sentence sentence : sentences){
			System.out.println(sentence.getNumber() + " => " + sentence.getKeywordSentence() + " => " + sentence.getStringLength()  + " => " + sentence.getNoOfWords() + " => " + sentence.getParagraphNumber());
		}

		System.out.println("---------------------------------------------------------------------------------------------------------------");
	}

	void printIntersectionMatrix(){

		System.out.println("------------------------------------------------Intersection Matrix---------------------------------------------");
		for(int i=0;i<sentences.size();i++){
			for(int j=0;j<sentences.size();j++){
				System.out.printf("%.5f ",intersectionMatrix[i][j]);
			}
			System.out.print("\n");
		}

		System.out.println("-----------------------------------------------------------------------------------------------------------------");
	}

	public void printDictionary(){
		System.out.println("-----------------------------Dictionary---------------------------------");
		  // Get a set of the entries
	      Set set = dictionary.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
	      // Display elements
	      while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	         System.out.print(((Sentence)me.getKey()).getValue() + ": ");
	         System.out.println(me.getValue());
	      }

		System.out.println("--------------------------------------------------------------------------");
	}

	public void printSummary(){

		System.out.println("------------------------------------------------------Summary------------------------------------------");
		System.out.println("no of paragraphs = "+ paragraphs.size());
		for(Sentence sentence : contentSummary){
			System.out.println(sentence.getValue());
		}
		System.out.println("--------------------------------------------------------------------------------------------");
	}
	
	public void printTop10()
	{

		System.out.println("---------------------------TOP 10------------------------------------");
		Collections.sort(sentences,new SentenceComparator());
		int k=0;
		for(Sentence i : sentences)
		{
			k++;
			System.out.println(i.getValue());
			if(k==10)
			{
				break;
			}
		}

		System.out.println("------------------------------------------------------------------------");
	}

	
	
	double getWordCount(List<Sentence> sentenceList){
		double wordCount = 0.0;
		for(Sentence sentence:sentenceList){
			wordCount +=(sentence.getValue().split("\\s+")).length;
		}
		return wordCount;
	}

	public void printStats(){
		System.out.println("----------------------------------------------Stats---------------------------------------------");
		System.out.println("number of words in Context : " + getWordCount(sentences));
		System.out.println("number of words in Summary : " + getWordCount(contentSummary));
		System.out.println("Compression : " +(double)((getWordCount(sentences)-getWordCount(contentSummary))/ getWordCount(sentences)) * 100 );
		System.out.println("-------------------------------------------------------------------------------------------------");

	}

	public List<Sentence> startSummarization(String data) {

		init(data);
		extractSentencesFromContext();
		groupSentencesIntoParagraphs();
		//summary.printSentences();
		try {
			createIntersectionMatrix();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println("INTERSECTION MATRIX");
		//printIntersectionMatrix();

		createDictionary();
		//	summary.printDictionary();
		createSummary();
		printSentences();
		printDictionary();
		printSummary();
		printTop10();
		printStats();
		sentences.sort(new SentenceComparator());
		return sentences;
	}


	public void extractSentencesFromContext() {

		sentences = Pattern.compile("(?<=\\.\\s)|(?<=[?!]\\s)", Pattern.MULTILINE | Pattern.COMMENTS)
				.splitAsStream(data)
				.map(Sentence::new)
				.collect(Collectors.toList());


		sentences.stream()
				.limit(4)
				.forEach(System.out::println);

				/*.filter(s -> s.trim().length() > 1)
		      .flatMap( s -> Arrays.stream(s.split("\\.")))
			  .map( s -> s.trim())
		      .forEach(System.out::println);*/
	}
}