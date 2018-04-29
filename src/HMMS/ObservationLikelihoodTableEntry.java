package HMMS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//This could have been a map to a map of probabilities but I think this is a logical convince.
import DataReadIn.EmotionOfSentenceTag;
public class ObservationLikelihoodTableEntry {
	String word;
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

	Map<EmotionOfSentenceTag,Integer> emmoodCounts;
	Map<EmotionOfSentenceTag,Double> emmoodProbabilities;
	
	public void incrementEmmoodCount(EmotionOfSentenceTag emmoodTag){
		int count = emmoodCounts.get(emmoodTag);
		emmoodCounts.put(emmoodTag, count + 1);
	}
	ObservationLikelihoodTableEntry(ArrayList<EmotionOfSentenceTag> order, String word){
		emmoodCounts = new HashMap<>();
		emmoodProbabilities = new HashMap<>();
		init(order);
	}
	public void init(ArrayList<EmotionOfSentenceTag> order){
		for(EmotionOfSentenceTag e: order){
			emmoodCounts.put(e, 0);
			emmoodProbabilities.put(e, 0.0);
		}
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
