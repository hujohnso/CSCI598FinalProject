package HMMS;

import java.util.ArrayList;

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
	protected ArrayList<ArrayList<Double>> observationLikelihoodTable;
	protected HMM_Statistics stats;
	HMM(ArrayList<Sentence> sentences, double trainingDataPercentage){
		init();
		fillTestAndTrainingData(sentences,trainingDataPercentage);
		fillWordsArrays();
	}
	public void init(){
		trainingDataSentences = new ArrayList<>();
		testingDataSentences = new ArrayList<>();
		trainingWords = new ArrayList<>();
		testingWords = new ArrayList<>();
		stats = new HMM_Statistics();
		transitionProbabilitiesTable = new ArrayList<>();
		observationLikelihoodTable = new ArrayList<>();
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
	}
	protected abstract void makeTransitionProbabilitiesTable();
	protected abstract void makeObservationLikelihoodTable();
}
