package DataReadIn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import HMMS.FirstTryHMM;

public class Main {
	public static String fileToWriteTo = "C:\\Users\\Hunter Johnson\\Documents\\CSCI598\\FinalProject\\moodAveragedData.txt";
	public static void main(String [] args) throws IOException{
		//MoodData moodData = new MoodData();
		//FirstTryHMM hmm = new FirstTryHMM(moodData.getSentences(),.90);
		double percentage = .05;
		MoodData moodData = new MoodData();
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWriteTo));
		for(int i = 6; i  < 19; ++i){
			FirstTryHMM hmm = new FirstTryHMM(moodData.getSentences(), i * .05);
			writer.write(" The % trained on is: " + i * percentage);
			double correctPer = hmm.findCorrectPercentage();
			writer.write(" The % correct is: " + correctPer);
			System.out.println("The ratio of correctly tagged words is: " + correctPer);
		}
		writer.close();
	}
}
