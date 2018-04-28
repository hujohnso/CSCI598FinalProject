package HMMS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataReadIn.EmotionOfSentenceTag;
import DataReadIn.Sentence;
import DataReadIn.Word;

public class FirstTryHMM extends HMM {
	/*This is my first try HMM it will be the easiest one.  First however I will go over my assumptions
	 * for this model.
	 * 
	 * For this model the hidden states will of course be the emotional tag for that word.
	 *
	 * 
	 * ASSUMPTIONS:
	 * 1. I will assume that the emotion of the sentence will tag the training set will be the tag to all
	 * of its parts.  This will not be the case in later models that I do.  I remember one of the papers
	 * talking about how only certian parts of speech will cause an effect.  This however will be my base
	 * model that I try to improve on.  
	 * 
	 * 2. I will also assume that the emotion of the word only depends on the last emotion tag. Later models
	 * will have different levels of going back.
	 * 
	 * 
	 * */
	FirstTryHMM(ArrayList<Sentence> sentences, double trainingDataPercentage){
		super(sentences,trainingDataPercentage);
	}
	/*This table represents the probabilities of transitions between all of the
	 * hidden states.  In this case the hidden states are the emmood tags.
	 * the rows and columns are both emmood tags.*/
	@Override
	protected void makeTransitionProbabilitiesTable() {
		ArrayList<EmotionOfSentenceTag> order = new ArrayList<>();
		Map<EmotionOfSentenceTag,Integer> emmoodTagsCounter = new HashMap<>();
		ArrayList<Integer> emmoodTagCounts = new ArrayList<>();
		emmoodTagsCounter = clean(emmoodTagsCounter);
		for(EmotionOfSentenceTag e: order){
			for(int i = 1; i < trainingWords.size(); ++i){
				if(trainingWords.get(i).getEmoodTag().equals(e)){
					int count = emmoodTagsCounter.get(trainingWords.get(i -1));
					emmoodTagsCounter.put(trainingWords.get(i - 1).getEmoodTag(), count + 1);
				}
			}
			transitionProbabilitiesTable.add(convertCountsToProbabilities(emmoodTagsCounter));
			emmoodTagsCounter = clean(emmoodTagsCounter);
		}
	}
	private ArrayList<Double> convertCountsToProbabilities(Map<EmotionOfSentenceTag,Integer> emmoodTagsCounter){
		ArrayList<Double> probs = new ArrayList<>();
		int totalSum = 0;
		for(EmotionOfSentenceTag e: order){
			totalSum = totalSum + emmoodTagsCounter.get(e);
		}
		for(EmotionOfSentenceTag e: order){
			probs.add((double) emmoodTagsCounter.get(e) / (double) totalSum);
		}
		return probs;
	}
	private Map<EmotionOfSentenceTag,Integer> clean(Map<EmotionOfSentenceTag,Integer> map){
		for(EmotionOfSentenceTag e: order){
			map.put(e, 0);
		}
		return map;
	}

	@Override
	protected void makeObservationLikelihoodTable() {

	}

}
