package DataReadIn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import HMMS.FirstTryHMM;
import edu.mit.jwi.item.POS;
import extentionOfTrainingSet.SynonymsHelper;

public class Main {
	public static String fileToWriteTo = "C:\\Users\\Hunter Johnson\\Documents\\CSCI598\\FinalProject\\ContingencyTables\\";
	//	public static void main(String [] args) throws IOException{
	//		double percentage = .05;
	//		MoodData moodData = new MoodData();
	//		BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWriteTo + "secondTierSentencesWithFilter.txt"));
	//		FirstTryHMM hmm = new FirstTryHMM(moodData.getFilteredSecondTierSentences(), .70);
	//		writer.write(hmm.stats.makeHeaderForSummarayStatistics() + "\n");
	//		writer.write(hmm.stats.makeSummaryStatisticsStringForCSV() + "\n");
	//		writer.write(hmm.stats.contingencyTableString());
	//		hmm.stats.printToConsole();
	//		writer.close();
	//	}

	public static void main(String [] args) throws IOException{
		SynonymsHelper synonymsHelper = new SynonymsHelper();
		synonymsHelper.printPOS();
	}
}
