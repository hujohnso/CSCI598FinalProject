package DataReadIn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import HMMS.FirstTryHMM;
import HMMS.SynonymsExtendedHMM;
import edu.mit.jwi.item.POS;
import extentionOfTrainingSet.SynonymsHelper;

public class Main {
	public static String fileToWriteTo = "C:\\Users\\Hunter Johnson\\Documents\\CSCI598\\FinalProject\\ContingencyTables\\";
	public static String dataFileToReadObservationLikelihoodFrom = "observationLikelihoodTable.txt";
	public static String dataFileToReadTransitionProbabilitiesFrom = "transitionProbabilities.txt";
//	public static void main(String [] args) throws IOException{
//		double percentage = .05;
//		MoodData moodData = new MoodData();
//		BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWriteTo + "plainSentences.txt"));
//		FirstTryHMM hmm = new FirstTryHMM(moodData.getSentences(), .70);
//		writer.write(hmm.stats.makeHeaderForSummarayStatistics() + "\n");
//		writer.write(hmm.stats.makeSummaryStatisticsStringForCSV() + "\n");
//		writer.write(hmm.stats.contingencyTableString());
//		hmm.stats.printToConsole();
//		writer.close();
//	}
	//
		public static void main(String [] args) throws IOException{
			MoodData moodData = new MoodData();
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWriteTo + "deleteMe.txt"));
			SynonymsExtendedHMM hmm = new SynonymsExtendedHMM(moodData.getFilteredSentences(),1.0);
			writer.write(hmm.stats.makeHeaderForSummarayStatistics() + "\n");
			writer.write(hmm.stats.makeSummaryStatisticsStringForCSV() + "\n");
			writer.write(hmm.stats.contingencyTableString());
			hmm.stats.printToConsole();
			writer.close();
		}
//		public static void main(String [] args) throws IOException{
//			SynonymsExtendedHMM hmm = new SynonymsExtendedHMM();
//			System.out.println("Sentence: I ");
//			System.out.println("The mood of that sentence is: " + hmm.getMoodFromString("kismet's venomous smile makes me terrified and uneasy"));
//		}

}
