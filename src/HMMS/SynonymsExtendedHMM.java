package HMMS;

import java.util.ArrayList;

import DataReadIn.EmotionOfSentenceTag;
import DataReadIn.Sentence;
import DataReadIn.Word;
import edu.mit.jwi.item.POS;
import extentionOfTrainingSet.SynonymsHelper;

public class SynonymsExtendedHMM extends HMM {
	SynonymsHelper synHelper;
	public SynonymsExtendedHMM(ArrayList<Sentence> sentences, double trainingDataPercentage) {
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
		synHelper = new SynonymsHelper();
		System.out.println("The size of the observation likelihoodTable is: " + observationLikelihoodTable.size());
		ArrayList<ObservationLikelihoodTableEntry> additionalEntries = new ArrayList<>();
		for(ObservationLikelihoodTableEntry o: observationLikelihoodTable){
			try{
				ArrayList<String> syns = synHelper.getArrayListOfSyns(o.getWord(),POS.valueOf(o.getExtendedWord().getPos().getWordNetMappedPOS()));
				for(String s: syns){
					if(!s.equals(o.getWord())){
						ObservationLikelihoodTableEntry newOLTE = new ObservationLikelihoodTableEntry(o.getEmmoodProbabilities(),o.getExtendedWord());
						additionalEntries.add(newOLTE);
					}
				}
			}catch(Exception e){
			}
		}
		observationLikelihoodTable.addAll(additionalEntries);
	}

	@Override
	protected ArrayList<ArrayList<Word>> tagTestingSet() {
		addSynonymsToObservationLikelihoodTable();
		System.out.println("The size of the observation likelihood table is now: " + observationLikelihoodTable.size());
		viterbi = new Viterbi(testingDataSentences,observationLikelihoodTable,
				transitionProbabilitiesTable, initialStateProbabilities, order);
		ArrayList<ArrayList<Word>> hmmTaggedWords = viterbi.getTaggedSentences();
		return findDominateEmotion(hmmTaggedWords);
	}

}
