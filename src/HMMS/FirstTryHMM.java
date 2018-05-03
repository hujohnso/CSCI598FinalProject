package HMMS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataReadIn.EmotionOfSentenceTag;
import DataReadIn.Sentence;
import DataReadIn.Word;

public class FirstTryHMM extends HMM {
	public FirstTryHMM(ArrayList<Sentence> sentences, double trainingDataPercentage){
		super(sentences,trainingDataPercentage);
	}
	@Override
	protected void makeTransitionProbabilitiesTable() {
		Map<EmotionOfSentenceTag,Integer> emmoodTagsCounter = new HashMap<>();
		emmoodTagsCounter = clean(emmoodTagsCounter);
		ArrayList<Word> trainingWords = makeTrainingWords();
		for(EmotionOfSentenceTag e: order){
			for(int i = 1; i < trainingWords.size(); ++i){
				if(trainingWords.get(i).getEmoodTag().equals(e)){
					int count = emmoodTagsCounter.get(trainingWords.get(i -1).getEmoodTag());
					emmoodTagsCounter.put(trainingWords.get(i - 1).getEmoodTag(), count + 1);
				}
			}
			transitionProbabilitiesTable.add(convertCountsToProbabilities(emmoodTagsCounter));
			emmoodTagsCounter = clean(emmoodTagsCounter);
		}
	}
	public ArrayList<Word> makeTrainingWords(){
		ArrayList<Word> trainingWords = new ArrayList<>();
		for(Sentence s: trainingDataSentences){
			for(Word w: s.getWords()){
				trainingWords.add(w);
			}
		}
		stats.setTrainingDataSizeInWords(trainingWords.size());
		return trainingWords;
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
	/*This table coresponds to each observations likelihood of being in a
	 * particular hidden state so like for every single word what is the probability
	 * that is the P(a given word|the emmood tag) */
	@Override
	protected void makeObservationLikelihoodTable() {
		int i = 0;
		for(Sentence s: trainingDataSentences){
			for(Word w: s.getWords()){
				i++;
			}
		}
		int trainingWordsSize = i;
		i = 0;
		for(Sentence s: trainingDataSentences){
			for(Word w: s.getWords()){
				if(i % 1000 == 0){
					System.out.println("We are " + ((double) i / (double) trainingWordsSize) * 100 + "% done");
				}
				i++;
				if(observationLikelihoodTable.contains(w.getWord())){
					incrementPresentObservationLikelihoodTable(w.getWord(),w.getEmoodTag());
				}
				else{
					ObservationLikelihoodTableEntry olt = new ObservationLikelihoodTableEntry(order,w.getWord());
					olt.incrementEmmoodCount(w.getEmoodTag());
					observationLikelihoodTable.add(olt);
				}
			}
		}
		for(ObservationLikelihoodTableEntry x: observationLikelihoodTable){
			x.makeEmmoodProbabilities();
		}
	}
	private void incrementPresentObservationLikelihoodTable(String word,EmotionOfSentenceTag emmoodTag){
		for(ObservationLikelihoodTableEntry x: observationLikelihoodTable){
			if(x.equals(word)){
				x.incrementEmmoodCount(emmoodTag);
			}
		}
	}
	@Override
	protected ArrayList<ArrayList<Word>> tagTestingSet() {
		viterbi = new Viterbi(testingDataSentences,observationLikelihoodTable,
				transitionProbabilitiesTable, initialStateProbabilities, order);
		ArrayList<ArrayList<Word>> hmmTaggedWords = viterbi.getTaggedSentences();
		return findDominateEmotion(hmmTaggedWords);
	}
	
	private ArrayList<ArrayList<Word>> findDominateEmotion(ArrayList<ArrayList<Word>> taggedTestingSet){
		Map<EmotionOfSentenceTag, Integer> emmoodCounts = new HashMap<>();
		for(int i = 0; i < taggedTestingSet.size(); ++i){
			for(EmotionOfSentenceTag e: order){
				emmoodCounts.put(e, 0);
			}
			for(Word words: taggedTestingSet.get(i)){
				int count = emmoodCounts.get(words.getEmoodTag());
				emmoodCounts.put(words.getEmoodTag(), count + 1);
			}
			EmotionOfSentenceTag domEmotion = null;
			//int currentHighestCount = -1;
			for(EmotionOfSentenceTag e: order){
				//////////////fidle
//				if(emmoodCounts.get(e) > currentHighestCount){
//					currentHighestCount = emmoodCounts.get(e);
//					domEmotion = e;
//				}
				/////fidle
				//OnlyEverTwoRightNow
				if(emmoodCounts.get(e) > 3 && !e.equals(EmotionOfSentenceTag.NEUTRAL)){
					if(domEmotion != null){
						domEmotion = emmoodCounts.get(e) > emmoodCounts.get(domEmotion) ? e:domEmotion;
					}
					else{
						domEmotion = e;
					}
				}
			}
			if(domEmotion == null){
				domEmotion = EmotionOfSentenceTag.NEUTRAL;
			}
			for(Word words: taggedTestingSet.get(i)){
				words.setEmmoodTag(domEmotion);
			}
			emmoodCounts.clear();
		}
		return taggedTestingSet;
	}
}
