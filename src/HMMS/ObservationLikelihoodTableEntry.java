package HMMS;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//This could have been a map to a map of probabilities but I think this is a logical convince.
import DataReadIn.EmotionOfSentenceTag;
import DataReadIn.Word;
public class ObservationLikelihoodTableEntry implements Serializable {
	private static final long serialVersionUID = 1L;
	String word;
	Word extendedWord;
	Map<EmotionOfSentenceTag,Integer> emmoodCounts;
	Map<EmotionOfSentenceTag,Double> emmoodProbabilities;
	ObservationLikelihoodTableEntry(String word, Word extendedWord, Map<EmotionOfSentenceTag,Integer> emmoodCounts,Map<EmotionOfSentenceTag,Double> emmoodProbabilities){
		this.word = word;
		this.extendedWord = extendedWord;
		this.emmoodCounts = emmoodCounts;
		this.emmoodProbabilities = emmoodProbabilities;
	}
	public Map<EmotionOfSentenceTag, Double> getEmmoodProbabilities() {
		return emmoodProbabilities;
	}
	public void setEmmoodProbabilities(Map<EmotionOfSentenceTag, Double> emmoodProbabilities) {
		this.emmoodProbabilities = emmoodProbabilities;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public void incrementEmmoodCount(EmotionOfSentenceTag emmoodTag){
		int count = emmoodCounts.get(emmoodTag);
		emmoodCounts.put(emmoodTag, count + 1);
	}
	ObservationLikelihoodTableEntry(ArrayList<EmotionOfSentenceTag> order, String word){
		emmoodCounts = new HashMap<>();
		emmoodProbabilities = new HashMap<>();
		this.word = word;
		init(order);
	}
	ObservationLikelihoodTableEntry(ArrayList<EmotionOfSentenceTag> order, Word extendedWord){
		emmoodCounts = new HashMap<>();
		emmoodProbabilities = new HashMap<>();
		this.word = extendedWord.getWord();
		this.extendedWord = extendedWord;
		init(order);
	}
	ObservationLikelihoodTableEntry(Map<EmotionOfSentenceTag,Double> emmoodProbabilities, Word extendedWord){
		this.word = extendedWord.getWord();
		this.extendedWord = extendedWord;
		this.emmoodProbabilities = emmoodProbabilities;
	}
	public void init(ArrayList<EmotionOfSentenceTag> order){
		for(EmotionOfSentenceTag e: order){
			emmoodCounts.put(e, 0);
			emmoodProbabilities.put(e, 0.0);
		}
	}
	public Word getExtendedWord() {
		return extendedWord;
	}
	public void setExtendedWord(Word extendedWord) {
		this.extendedWord = extendedWord;
	}
	@Override
	public boolean equals(Object v){
		if(v.equals(this.word)){
			return true;
		}
		return false;
	}
	public void makeEmmoodProbabilities(){
		int totalCount = 0;
		for(EmotionOfSentenceTag e: emmoodCounts.keySet()){
			totalCount = totalCount + emmoodCounts.get(e);
		}
		for(EmotionOfSentenceTag e: emmoodCounts.keySet()){
			emmoodProbabilities.put(e, (double) (emmoodCounts.get(e) / (double) totalCount));
		}
	}
}
