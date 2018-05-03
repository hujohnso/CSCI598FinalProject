package HMMS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import DataReadIn.EmotionOfSentenceTag;
import DataReadIn.MoodData;
import DataReadIn.Sentence;
import DataReadIn.Word;

public abstract class HMM {
	protected ArrayList<Sentence> trainingDataSentences;
	protected ArrayList<Sentence> testingDataSentences;
	protected ArrayList<EmotionOfSentenceTag> order;
	protected ArrayList<ArrayList<Double>> transitionProbabilitiesTable;
	protected ArrayList<ObservationLikelihoodTableEntry> observationLikelihoodTable;
	protected Map<EmotionOfSentenceTag,Integer> initCounts;
	protected Map<EmotionOfSentenceTag,Double> initialStateProbabilities;
	protected int totalCorrectSentences;
	protected int totalSentences;
	protected int totalNeutralSentences;
	public HMM_Statistics stats;
	protected Viterbi viterbi;
	HMM(ArrayList<Sentence> sentences, double trainingDataPercentage){
		init();
		System.out.println("Starting to fill test and training data...");
		fillTestAndTrainingData(sentences,trainingDataPercentage);
		System.out.println("Starting fillWordsArrays...");
		System.out.println("Making initial probabilities map...");
		makeInitialProbabilitiesMap();
		System.out.println("Making transition probabilities map...");
		makeTransitionProbabilitiesTable();
		System.out.println("Making observation likelihood table...");
		makeObservationLikelihoodTable();
		System.out.println("Finished making Observation likelihood table");
		findCorrectPercentageSentences();
	}
	protected abstract void makeObservationLikelihoodTable();
	protected abstract ArrayList<ArrayList<Word>> tagTestingSet();
	public void init(){
		trainingDataSentences = new ArrayList<>();
		testingDataSentences = new ArrayList<>();
		stats = new HMM_Statistics();
		initCounts = new HashMap<>();
		transitionProbabilitiesTable = new ArrayList<>();
		observationLikelihoodTable = new ArrayList<>();
		initialStateProbabilities = new HashMap<>();
		totalCorrectSentences = 0;
		totalSentences = 0;
		totalNeutralSentences = 0;
		order = new ArrayList<>();
		initOrderOfSentenceArray();

	}
	public void fillTestAndTrainingData(ArrayList<Sentence> sentences, double trainingDataPercentage){
		int numberOfTrainingSentences = (int) (sentences.size() * trainingDataPercentage);
		stats.setTrainingDataSizeInSentences(numberOfTrainingSentences);
		stats.setTestingDataSizeInSentences(sentences.size() - numberOfTrainingSentences);
		stats.setPercentageTrainedOn(trainingDataPercentage);
		for(int i = 0; i < sentences.size(); i++){
			if( i <= numberOfTrainingSentences){
				trainingDataSentences.add(sentences.get(i));
			}
			else{
				testingDataSentences.add(sentences.get(i));
			}
		}
	}
	public void initOrderOfSentenceArray(){
		order.add(EmotionOfSentenceTag.ANGRY);
		order.add(EmotionOfSentenceTag.SUPPRIZED_PLUS);
		order.add(EmotionOfSentenceTag.SUPRIZED_NEG);
		order.add(EmotionOfSentenceTag.FEERFUL);
		order.add(EmotionOfSentenceTag.SAD);
		order.add(EmotionOfSentenceTag.DISGUSTED);
		order.add(EmotionOfSentenceTag.NEUTRAL);
		order.add(EmotionOfSentenceTag.HAPPY);
		order.add(EmotionOfSentenceTag.NEGATIVE);
		order.add(EmotionOfSentenceTag.POSITIVE);
	}
	//Initial counts for the transition from the start hidden state
	public void initialCounts(){
		initInitCounts();
		for(Sentence s: trainingDataSentences){
			for(Word w: s.getWords()){
				int count = initCounts.get(w.getEmoodTag());
				initCounts.put(w.getEmoodTag(), count + 1);
			}
		}
	}
	public void makeInitialProbabilitiesMap(){
		initialCounts();
		int totalCount = 0;
		for(EmotionOfSentenceTag x: initCounts.keySet()){
			totalCount = initCounts.get(x) + totalCount;
		}
		for(EmotionOfSentenceTag x: initCounts.keySet()){
			initialStateProbabilities.put(x,(double)initCounts.get(x) / (double)totalCount);
		}
	}

	public void initInitCounts(){
		for(EmotionOfSentenceTag e: order){
			initCounts.put(e, 0);
		}
	}
	public double findCorrectPercentage(){
		ArrayList<ArrayList<Word>> hmmTaggedWords = tagTestingSet();
		int totalCorrectWords = 0;
		int totalWords = 0;
		for(int i = 0; i < testingDataSentences.size(); i++){
			ArrayList<Word> taggedWords = hmmTaggedWords.get(i);
			for(int j = 0; j < testingDataSentences.get(i).getWords().size(); ++j){
				if(taggedWords.get(j).getEmoodTag().equals(testingDataSentences.get(i).getWords().get(j).getEmoodTag())){
					totalCorrectWords++;
				}
				totalWords++;
			}
		}
		stats.setTestingDataSizeInWords(totalWords);
		stats.setTotalCorrectWords(totalCorrectWords);
		return (double) totalCorrectWords / (double) totalWords;
	}
	public double findCorrectPercentageSentences(){
		ArrayList<ArrayList<Word>> hmmTaggedWords = tagTestingSet();
		totalCorrectSentences = 0;
		totalSentences = 0;
		int numberOfTaggedSentencesNotNeutral = 0;
		for(int i = 0; i < testingDataSentences.size(); i++){
			totalSentences++;
			if(testingDataSentences.get(i).getWords().get(0).getEmoodTag().equals(EmotionOfSentenceTag.NEUTRAL)){
				totalNeutralSentences++;
			}
			if(hmmTaggedWords.get(i).get(0).getEmoodTag().equals(testingDataSentences.get(i).getWords().get(0).getEmoodTag())){
				totalCorrectSentences++;
				if(!hmmTaggedWords.get(i).get(0).getEmoodTag().equals(EmotionOfSentenceTag.NEUTRAL)){
					numberOfTaggedSentencesNotNeutral++;
				}
			}
			else{
				System.out.println("Tagged: " + hmmTaggedWords.get(i));
				System.out.println("Testing data: " + testingDataSentences.get(i));
			}			
		}
		stats.setPercentageOfUnknownWords(viterbi.getNumberOfUnseenWords() / (double) viterbi.getNumberOfWords());
		stats.setTotalNeutralSentences(totalNeutralSentences);
		stats.setTotalCorrectSentences(totalCorrectSentences);
		stats.setNumberOfTaggedSentencesNotNeutral(numberOfTaggedSentencesNotNeutral);
		stats.setTotalCorrectPercentage((double) totalCorrectSentences / (double) totalSentences);
		return (double) totalCorrectSentences / (double) totalSentences;
	}
	public int getTotalCorrectSentences(){
		return totalCorrectSentences;
	}
	public int getTotalNeutralSentences(){
		return totalNeutralSentences;
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
	protected ArrayList<ArrayList<Word>> findDominateEmotion(ArrayList<ArrayList<Word>> taggedTestingSet){
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
	protected Map<EmotionOfSentenceTag,Integer> clean(Map<EmotionOfSentenceTag,Integer> map){
		for(EmotionOfSentenceTag e: order){
			map.put(e, 0);
		}
		return map;
	}
	public ArrayList<Double> convertCountsToProbabilities(Map<EmotionOfSentenceTag,Integer> emmoodTagsCounter){
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
	
}
