package com.nlp.text.summarizer.pageranker.algo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Data
public class PageRank {
	public static final double d=0.85;
	double[][] intersectionMatrix;
	double pageRanks[];
	double prHolder[];
	public PageRank(double intersectionMatrix[][])
	{
		this.intersectionMatrix=intersectionMatrix;
		pageRanks=new double[intersectionMatrix[0].length];
		prHolder=new double[pageRanks.length];
		for(int i=0;i<pageRanks.length;i++)
		{
			pageRanks[i]=1.000;
			prHolder[i]=0.000;
		}
	}
	public double intermediate(int sentenceNumber)
	{
		double intermediate=0;
		for(int i : countIncomingLink(sentenceNumber))
		{
			if(i != sentenceNumber)
			intermediate+=pageRanks[i]/countIncomingLink(i).size();
		}
	//	System.out.println("Intermediate = "+intermediate);
		return intermediate;
	}
	public double[] calculatePageRank()
	{
		if(Arrays.equals(pageRanks, prHolder))
		{
			return pageRanks;
		}
		for(int i=0;i<pageRanks.length;i++)
		{
			prHolder[i]=pageRanks[i];
		}
		//System.out.println("\nprHolder = ");
		/*for(double i : prHolder)
		{
			System.out.print(i+" ");
		}*/
		for(int i=0;i<pageRanks.length;i++)
		{
			pageRanks[i]= (1-d) + d*intermediate(i);
		}
		//System.out.println("\nPageRanks = ");
		/*for(double i : pageRanks)
		{
			System.out.print(i+" ");
		}*/

		return calculatePageRank();
	}

	public List<Integer> countIncomingLink(int sentenceNumber)
	{
		List<Integer> count=new ArrayList<>();
		
			for(int j=0;j<intersectionMatrix[0].length;j++)
			{
				if(intersectionMatrix[j][sentenceNumber] > 0 && intersectionMatrix[j][sentenceNumber]< 1)
				{
					count.add(j);
				}
			}
		//	System.out.println("\nSentence number="+sentenceNumber+"\ncount = "+count);
		return count;	
	}
/*	public static void main(String[] args)
	{
		double intMat[][]={ {1.000,0.234,0.781,0.000} , 
							{0.234,1.000,0.655, 0.000},
							{0.781,0.655,1.000, 0.543},
							{0.000,0.000,0.543,1.000}
						};
		PageRank pr= new PageRank(intMat);
		double pageRank[]=pr.calculatePageRank();
		System.out.println("\n--------------------------------PAGE RANKS-----------------------------------------");
		for(double i : pageRank)
		{
			System.out.println(i);
		}
	}*/
}
