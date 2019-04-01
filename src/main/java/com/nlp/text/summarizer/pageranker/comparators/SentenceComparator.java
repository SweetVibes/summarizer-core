package com.nlp.text.summarizer.pageranker.comparators;

import com.nlp.text.summarizer.pageranker.model.Sentence;

import java.util.Comparator;

public class SentenceComparator  implements Comparator<Sentence> {
	@Override
	public int compare(Sentence obj1, Sentence obj2) {
		if (obj1.getScore() > obj2.getScore()) {
			return -1;
		} else if (obj1.getScore() < obj2.getScore()) {
			return 1;
		} else {
			Integer keywordsForSen1 = obj1.getKeywordSentence().length();
			Integer keywordsForSen2 = obj2.getKeywordSentence().length();


			if (keywordsForSen1 < keywordsForSen2) {
				return 1;
			} else if (keywordsForSen1 > keywordsForSen2) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}