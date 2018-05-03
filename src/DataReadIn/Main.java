package DataReadIn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import HMMS.FirstTryHMM;

public class Main {
	public static String fileToWriteTo = "C:\\Users\\Hunter Johnson\\Documents\\CSCI598\\FinalProject\\moodAveragedWithSecondTierNoDataRemoval.txt";
	public static void main(String [] args) throws IOException{
		//MoodData moodData = new MoodData();
		//FirstTryHMM hmm = new FirstTryHMM(moodData.getSentences(),.90);
		double percentage = .05;
		MoodData moodData = new MoodData();
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWriteTo));
		for(int i = 16; i  < 17; ++i){
			FirstTryHMM hmm = new FirstTryHMM(moodData.getSentences(), i * .05);
			writer.write(" The % trained on is: " + i * percentage);
			double correctPer = hmm.findCorrectPercentageSentences();
			writer.write(" The % correct is: " + correctPer);
			System.out.println("The ratio of correctly tagged sentences is: " + correctPer);
			System.out.println("The ratio of unseen words is: " + hmm.perOfUnknownWords());
			System.out.println("The total correct sentences is: " + hmm.getTotalCorrectSentences());
			System.out.println("the total neutral sentences is: " + hmm.getTotalNeutralSentences());
			System.out.println("The number of correct sentences not neutral is : " + hmm.getNumberOfTaggedSentencesNotNeutral());
		}
		writer.close();
	}
}
