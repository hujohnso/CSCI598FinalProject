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
	protected ArrayList<Word> trainingWords;
	protected ArrayList<Sentence> testingDataSentences;
	protected ArrayList<Word> testingWords;
	protected ArrayList<EmotionOfSentenceTag> order;
	protected ArrayList<ArrayList<Double>> transitionProbabilitiesTable;
	protected ArrayList<ObservationLikelihoodTableEntry> observationLikelihoodTable;
	protected Map<EmotionOfSentenceTag,Integer> initCounts;
	protected Map<EmotionOfSentenceTag,Double> initialStateProbabilities;
	
	protected HMM_Statistics stats;
	protected Viterbi viterbi;
	HMM(ArrayList<Sentence> sentences, double trainingDataPercentage){
		init();
		System.out.println("Starting to fill test and training data...");
		fillTestAndTrainingData(sentences,trainingDataPercentage);
		System.out.println("Starting fillWordsArrays...");
		fillWordsArrays();
		System.out.println("Making initial probabilities map...");
		makeInitialProbabilitiesMap();
		System.out.println("Making transition probabilities map...");
		makeTransitionProbabilitiesTable();
		System.out.println("Making observation likelihood table...");
		makeObservationLikelihoodTable();
		System.out.println("Finished making Observation likelihood table");
	}
	public void init(){
		trainingDataSentences = new ArrayList<>();
		testingDataSentences = new ArrayList<>();
		trainingWords = new ArrayList<>();
		testingWords = new ArrayList<>();
		stats = new HMM_Statistics();
		initCounts = new HashMap<>();
		transitionProbabilitiesTable = new ArrayList<>();
		observationLikelihoodTable = new ArrayList<>();
		initialStateProbabilities = new HashMap<>();
		order = new ArrayList<>();
		initOrderOfSentenceArray();
		
	}
	public void fillTestAndTrainingData(ArrayList<Sentence> sentences, double trainingDataPercentage){
		int numberOfTrainingSentences = (int) (sentences.size() * trainingDataPercentage);
		stats.setTrainingDataSize(numberOfTrainingSentences);
		stats.setTestingDataSize(sentences.size() - numberOfTrainingSentences);
		for(int i = 0; i < sentences.size(); i++){
			if( i <= numberOfTrainingSentences){
				trainingDataSentences.add(sentences.get(i));
			}
			else{
				testingDataSentences.add(sentences.get(i));
			}
		}
	}
	public void fillWordsArrays(){
		for(Sentence s: trainingDataSentences){
			for(Word w: s.getWords()){
				trainingWords.add(w);
			}
		}
		for(Sentence s: testingDataSentences){
			for(Word w: s.getWords()){
				testingWords.add(w);
			}
		}

	}
	//This is purely to preserve order.  Because I don't know what kind of 
	//Map iterator vudo maps do.
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
		for(Word w: trainingWords){
			int count = initCounts.get(w.getEmoodTag());
			initCounts.put(w.getEmoodTag(), count + 1);
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
	protected abstract void makeTransitionProbabilitiesTable();
	protected abstract void makeObservationLikelihoodTable();
	protected abstract ArrayList<Word> tagTestingSet();
	public double findCorrectPercentage(){
		ArrayList<Word> hmmTaggedWords = tagTestingSet();
		int totalCorrectWords = 0;
		for(int i = 0; i < testingWords.size();++i){
			if(testingWords.get(i).getEmoodTag().equals(hmmTaggedWords.get(i).getEmoodTag())){
				totalCorrectWords++;
			}
		}
		return (double) totalCorrectWords / (double) testingWords.size();
	}
}
