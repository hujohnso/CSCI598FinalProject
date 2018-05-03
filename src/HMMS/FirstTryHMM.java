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
}
