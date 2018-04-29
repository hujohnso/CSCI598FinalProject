package DataReadIn;

import HMMS.FirstTryHMM;

public class Main {
	public static void main(String [] args){
		MoodData moodData = new MoodData();
		FirstTryHMM hmm = new FirstTryHMM(moodData.getSentences(),.90);
		System.out.println("The ratio of correctly tagged words is: " + hmm.findCorrectPercentage());
	}
}
