package HMMS;
/*So the idea of the viterbi algorithm is that it takes a sequence of observations which are states
 * in the world and outputs the most probable sequence of hidden states that correspond to that sequence
 * of observations.*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataReadIn.EmotionOfSentenceTag;
import DataReadIn.Word;

public class Viterbi {
	ArrayList<Word> observations;
	ArrayList<ObservationLikelihoodTableEntry> observationLikelihoodTable;
	ArrayList<ArrayList<Double>> transitionProbabilitiesTable;
	Map<EmotionOfSentenceTag,Double> initialStateProbabilities;
	ArrayList<EmotionOfSentenceTag> order;
	ArrayList<ViterbiEntry>  viterbi;
	ArrayList<Word> taggedObservations;
	Viterbi(ArrayList<Word> observations, ArrayList<ObservationLikelihoodTableEntry> observationLikelihoodTable,
			ArrayList<ArrayList<Double>> transitionProbabilitiesTable, Map<EmotionOfSentenceTag,Double> initialStateProbabilities,
			ArrayList<EmotionOfSentenceTag> order){
		this.observations = observations;
		this.observationLikelihoodTable = observationLikelihoodTable;
		this.transitionProbabilitiesTable = transitionProbabilitiesTable;
		this.initialStateProbabilities = initialStateProbabilities;
		this.order = order;
		viterbi = new ArrayList<>();
		performViterbi();
		taggedObservations = traceBackStep();
	}
	public ArrayList<Word> getTaggedObservations(){
		return taggedObservations;
	}
	public void performViterbi(){
		initializationStep();
		for(Word x: observations){
			performViterbiStep(x);
		}
	}
	
	public ArrayList<Word> traceBackStep(){
		ArrayList<Word> hmmTaggedDataSet = new ArrayList<>();
		for(int i = 0; i < observations.size() - 1; ++i){
			Word word = new Word(observations.get(i).getWord());
			word.setEmmoodTag(viterbi.get(i + 1).lastChoice);
			hmmTaggedDataSet.add(word);
		}
		Word lastWord = new Word(observations.get(observations.size() - 1).getWord());
		lastWord.setEmmoodTag(order.get(getMostProbableHiddenState(viterbi.get(viterbi.size() - 1).viterbiValues)));
		return hmmTaggedDataSet;
	}
	
	public void performViterbiStep(Word word){
		int indexOfLastHighestVertbi = getMostProbableHiddenState(viterbi.get(viterbi.size() - 1).viterbiValues);
		ViterbiEntry newViterbiEntry = new ViterbiEntry(word);
		ArrayList<Double> viterbiValues = new ArrayList<Double>();
		Map<EmotionOfSentenceTag, Double> likelihoods = findObservationLikelihoodMap(word);
		newViterbiEntry.lastChoice = order.get(indexOfLastHighestVertbi);
		System.out.println("The current word is: " + word.getWord());
		for(EmotionOfSentenceTag e: order){
			System.out.println("The size is: " + viterbi.size());
			System.out.println("The last highest virtbi value is: " + viterbi.get(viterbi.size() - 1).viterbiValues.get(indexOfLastHighestVertbi) );
			System.out.println("The likelihoods are " + likelihoods.get(e));
			System.out.println("The transition probability is " + transitionProbabilitiesTable.get(indexOfLastHighestVertbi).get(e.tagNumber));
			viterbiValues.add(viterbi.get(viterbi.size() - 1).viterbiValues.get(indexOfLastHighestVertbi) * likelihoods.get(e) * transitionProbabilitiesTable.get(indexOfLastHighestVertbi).get(e.tagNumber));
		}
		newViterbiEntry.viterbiValues = viterbiValues;
		viterbi.add(newViterbiEntry);
	}
	
	
	public void initializationStep(){
		ViterbiEntry viterbiEntry = new ViterbiEntry(observations.get(0));
		ArrayList<Double> viterbiValues = new ArrayList<>();
		Map<EmotionOfSentenceTag, Double> likelihoods = findObservationLikelihoodMap(observations.get(0));
		for(EmotionOfSentenceTag e: order){
			Double viterbiValue = initialStateProbabilities.get(e) * likelihoods.get(e);
			viterbiValues.add(viterbiValue);
		}
		viterbiEntry.viterbiValues = viterbiValues;
		viterbi.add(viterbiEntry);
	}
	
	public Map<EmotionOfSentenceTag, Double> findObservationLikelihoodMap(Word w){
		Map<EmotionOfSentenceTag,Double> likelihoods = new HashMap<>();
		for(ObservationLikelihoodTableEntry o: observationLikelihoodTable){
			if(o.getWord().equals(w.getWord())){
				likelihoods = o.getEmmoodProbabilities();
			}
		}
		if(likelihoods.size() == 0){
			System.out.println("We found a word we don't know :(");
			return getEqualLikelihoodTable();
		}
		System.out.println("We found a word we know!");
		return likelihoods;
	}
	public Map<EmotionOfSentenceTag, Double> getEqualLikelihoodTable(){
		Map<EmotionOfSentenceTag, Double> equal = new HashMap<>();
		for(EmotionOfSentenceTag e: order){
			equal.put(e, 1.0 / (double)order.size());
		}
		return equal;
	}
	public int getMostProbableHiddenState(ArrayList<Double> numbs){
		double highestDouble = 0;
		int indexOfHighestDouble = -1;
		for(int i = 0; i < numbs.size(); ++i){
			if(numbs.get(i) > highestDouble){
				indexOfHighestDouble = i;
				highestDouble = numbs.get(i);
			}
		}
		if(indexOfHighestDouble == -1){
			System.out.println("This one is WRONG!");
		}
		return indexOfHighestDouble;
	}
	
	public class ViterbiEntry{
		public Word observationWord;
		public ArrayList<Double> viterbiValues;
		//This functions as the pointer to the last value;
		public EmotionOfSentenceTag lastChoice;
		ViterbiEntry(Word observationWord){
			this.observationWord = observationWord;
		}

		
	}
}
