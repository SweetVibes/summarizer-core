package com.nlp.text.summarizer.pageranker;

import com.nlp.text.summarizer.pageranker.helpers.SummaryTool;

import java.io.*;
import java.util.stream.Collectors;

class improved_summary{
	public static void main(String[] args) throws IOException{
		//long startTime = System.nanoTime();
		/*SummaryTool summary = new SummaryTool();



		summary.init();
		summary.extractSentenceFromContext();
		summary.groupSentencesIntoParagraphs();
		//summary.printSentences();
		summary.createIntersectionMatrix();

		//System.out.println("INTERSECTION MATRIX");
		//summary.printIntersectionMatrix();

		summary.createDictionary();
	//	summary.printDictionary();
		summary.createSummary();
		//summary.printTop10();
		//summary.printSummary();
		//summary.printStats();
		//long endTime=System.nanoTime();
		//System.out.println("Time Taken : "+(double)(endTime - startTime)/1000000000+" Seconds ");

		*/
		SummaryTool tool = new SummaryTool();

	FileInputStream inputStream;

    {
        try {
                inputStream = new FileInputStream("samples/amazon/apple_watch");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String data = reader.lines().collect(Collectors.joining());


            System.out.println(tool.startSummarization(data));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
	}






}
