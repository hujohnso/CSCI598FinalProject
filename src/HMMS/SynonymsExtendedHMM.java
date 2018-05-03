package HMMS;

import java.util.ArrayList;

import DataReadIn.EmotionOfSentenceTag;
import DataReadIn.Sentence;
import DataReadIn.Word;
import edu.mit.jwi.item.POS;
import extentionOfTrainingSet.SynonymsHelper;

public class SynonymsExtendedHMM extends HMM {
	SynonymsHelper synHelper;
	SynonymsExtendedHMM(ArrayList<Sentence> sentences, double trainingDataPercentage) {
		super(sentences, trainingDataPercentage);
	}
	

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
					ObservationLikelihoodTableEntry olt = new ObservationLikelihoodTableEntry(order,w);
					olt.incrementEmmoodCount(w.getEmoodTag());
					observationLikelihoodTable.add(olt);
				}
			}
		}
		for(ObservationLikelihoodTableEntry x: observationLikelihoodTable){
			x.makeEmmoodProbabilities();
		}
	}
	
	protected void addSynonymsToObservationLikelihoodTable(){
		for(ObservationLikelihoodTableEntry o: observationLikelihoodTable){
			for(String s: synHelper.getArrayListOfSyns(o.getWord(),POS.valueOf(o.getExtendedWord().getPos().getWordNetMappedPOS()))){
				ObservationLikelihoodTableEntry newOLTE = new ObservationLikelihoodTableEntry(o.getEmmoodProbabilities(),o.getExtendedWord());
				observationLikelihoodTable.add(newOLTE);
			}
		}
	}

	@Override
	protected ArrayList<ArrayList<Word>> tagTestingSet() {
		return null;
	}
	
}
