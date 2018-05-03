package DataReadIn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import HMMS.FirstTryHMM;

public class Main {
	public static String fileToWriteTo = "C:\\Users\\Hunter Johnson\\Documents\\CSCI598\\FinalProject\\testingNewStatistics.txt";
	public static void main(String [] args) throws IOException{
		double percentage = .05;
		MoodData moodData = new MoodData();
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWriteTo));
		for(int i = 16; i  < 17; ++i){
			FirstTryHMM hmm = new FirstTryHMM(moodData.getSentences(), i * .05);
			writer.write(hmm.stats.makeHeaderForSummarayStatistics() + "\n");
			writer.write(hmm.stats.makeSummaryStatisticsStringForCSV() + "\n");
			writer.write(" The % trained on is: " + i * percentage);
			hmm.stats.printToConsole();
		}
		writer.close();
	}
}
