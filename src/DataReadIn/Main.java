package DataReadIn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import HMMS.FirstTryHMM;

public class Main {
	public static String fileToWriteTo = "C:\\Users\\Hunter Johnson\\Documents\\CSCI598\\FinalProject\\data_set\\";
	public static void main(String [] args){
		MoodData moodData = new MoodData();
		FirstTryHMM hmm = new FirstTryHMM(moodData.getSentences(),.90);
		System.out.println("The ratio of correctly tagged words is: " + hmm.findCorrectPercentage());
	}
	public void runAndWriteToADataFile() throws IOException{
		double percentage = .05;
		MoodData moodData = new MoodData();
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWriteTo));
		for(int i = 1; i  < 20; ++i){
			FirstTryHMM hmm = new FirstTryHMM(moodData.getSentences(), i * .05);
			writer.write("The % trained on is: " + i * percentage);
			writer.write("The % correct is: " + hmm.findCorrectPercentage());
		}
	}
}
