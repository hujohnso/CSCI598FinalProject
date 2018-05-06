package HMMS;

import java.util.ArrayList;

import DataReadIn.EmotionOfSentenceTag;

public class HMM_Statistics {
	/*This class is for keeping track of statistics and will only handle keeping track of stats.*/
	int trainingDataSizeInWords;
	int testingDataSizeInWords;
	int trainingDataSizeInSentences;
	int testingDataSizeInSentences;
	int numberOfTaggedSentencesNotNeutral;
	int totalCorrectSentences;
	int totalNeutralSentences;
	int totalCorrectWords;
	double percentageOfUnknownWords;
	double percentageTrainedOn;
	double totalCorrectPercentage;
	int sizeOfObservationLikelihoodTable;
	int sizeOfObservationLikelihoodTableExtended;
	ArrayList<ArrayList<Integer>> contingencyTable;
	ArrayList<EmotionOfSentenceTag> order;

	public HMM_Statistics(){
		trainingDataSizeInWords = 0;
		testingDataSizeInWords= 0;
		trainingDataSizeInSentences= 0;
		testingDataSizeInSentences= 0;
		numberOfTaggedSentencesNotNeutral= 0;
		totalCorrectSentences= 0;
		totalNeutralSentences= 0;
		totalCorrectWords= 0;
		percentageOfUnknownWords= 0;
		percentageTrainedOn= 0;
		totalCorrectPercentage = 0;
		sizeOfObservationLikelihoodTable = 0;
		sizeOfObservationLikelihoodTableExtended = 0;
		contingencyTable = new ArrayList<>();
	}
	public String makeSummaryStatisticsStringForCSV(){
		String returnString = trainingDataSizeInWords + ", " + 
		testingDataSizeInWords + ", " + 
		trainingDataSizeInSentences + ", " + 
		testingDataSizeInSentences  + ", " + 
		numberOfTaggedSentencesNotNeutral + ", " + 
		totalCorrectSentences + ", " + 
		totalNeutralSentences + ", " + 
		totalCorrectWords + ", " + 
		percentageOfUnknownWords + ", " + 
		percentageTrainedOn + ", " +
		totalCorrectPercentage + ", " +
		sizeOfObservationLikelihoodTable + ", " +
		sizeOfObservationLikelihoodTableExtended;
		return returnString;
	}
	public String makeHeaderForSummarayStatistics(){
		return "trainingDataSizeInWords, testingDataSizeInWords, trainingDataSizeInSentences, testingDataSizeInSentences, numberOfTaggedSentencesNotNeutral, totalCorrectSentences, totalNeutralSentences, totalCorrectWords, percentageOfUnknownWords, percentageTrainedOn, totalCorrectPercentage, sizeOfObservationLikelihoodTable, sizeOfObservationLikelihoodTableExtended";
	}
	public void printToConsole(){
		System.out.println("trainingDataSizeInWords: " + trainingDataSizeInWords);
		System.out.println("testingDataSizeInWords: " + testingDataSizeInWords);
		System.out.println("trainingDataSizeInSentences: " + trainingDataSizeInSentences);
		System.out.println("testingDataSizeInSentences" + testingDataSizeInSentences);
		System.out.println("numberOfTaggedSentencesNotNeutral: " + numberOfTaggedSentencesNotNeutral);
		System.out.println("totalCorrectSentences: " + totalCorrectSentences);
		System.out.println("totalNeutralSentences: " + totalNeutralSentences);
		System.out.println("totalCorrectWords: " + totalCorrectWords);
		System.out.println("percentageOfUnknownWords: " + percentageOfUnknownWords);
		System.out.println("percentageTrainedOn: " + percentageTrainedOn);
		System.out.println("totalCorrectPercentage: " + totalCorrectPercentage);
		System.out.println("sizeOfObservationLikelihoodTable: " + sizeOfObservationLikelihoodTable);
		System.out.println("sizeOfObservationLikelihoodTableExtended: " + sizeOfObservationLikelihoodTableExtended);
	}
	
	public String contingencyTableString(){
		String contingencyTableString = " ,";
		for(EmotionOfSentenceTag e: order){
			contingencyTableString = contingencyTableString + e + " ,";
		}
		contingencyTableString = contingencyTableString + "\n";
		for(int i = 0; i < order.size(); ++i){
			contingencyTableString = contingencyTableString + order.get(i);
			for(int j = 0; j < order.size(); ++j){
				contingencyTableString = contingencyTableString + ", " + contingencyTable.get(i).get(j);
			}
			contingencyTableString = contingencyTableString + "\n";
		}
		return contingencyTableString;
	}
	public void setContingencyTable(ArrayList<ArrayList<Integer>> contingencyTable){
		this.contingencyTable = contingencyTable;
	}
	public int getTrainingDataSizeInWords() {
		return trainingDataSizeInWords;
	}
	public void setTrainingDataSizeInWords(int trainingDataSizeInWords) {
		this.trainingDataSizeInWords = trainingDataSizeInWords;
	}
	public int getTestingDataSizeInWords() {
		return testingDataSizeInWords;
	}
	public void setTestingDataSizeInWords(int testingDataSizeInWords) {
		this.testingDataSizeInWords = testingDataSizeInWords;
	}
	public int getTrainingDataSizeInSentences() {
		return trainingDataSizeInSentences;
	}
	public void setTrainingDataSizeInSentences(int trainingDataSizeInSentences) {
		this.trainingDataSizeInSentences = trainingDataSizeInSentences;
	}
	public int getTestingDataSizeInSentences() {
		return testingDataSizeInSentences;
	}
	public void setTestingDataSizeInSentences(int testingDataSizeInSentences) {
		this.testingDataSizeInSentences = testingDataSizeInSentences;
	}
	public int getNumberOfTaggedSentencesNotNeutral() {
		return numberOfTaggedSentencesNotNeutral;
	}
	public void setNumberOfTaggedSentencesNotNeutral(int numberOfTaggedSentencesNotNeutral) {
		this.numberOfTaggedSentencesNotNeutral = numberOfTaggedSentencesNotNeutral;
	}
	public int getTotalCorrectSentences() {
		return totalCorrectSentences;
	}
	public void setTotalCorrectSentences(int totalCorrectSentences) {
		this.totalCorrectSentences = totalCorrectSentences;
	}
	public int getTotalNeutralSentences() {
		return totalNeutralSentences;
	}
	public void setTotalNeutralSentences(int totalNeutralSentences) {
		this.totalNeutralSentences = totalNeutralSentences;
	}
	public int getTotalCorrectWords() {
		return totalCorrectWords;
	}
	public void setTotalCorrectWords(int totalCorrectWords) {
		this.totalCorrectWords = totalCorrectWords;
	}
	public double getPercentageOfUnknownWords() {
		return percentageOfUnknownWords;
	}
	public void setPercentageOfUnknownWords(double percentageOfUnknownWords) {
		this.percentageOfUnknownWords = percentageOfUnknownWords;
	}
	public double getPercentageTrainedOn() {
		return percentageTrainedOn;
	}
	public void setPercentageTrainedOn(double percentageTrainedOn) {
		this.percentageTrainedOn = percentageTrainedOn;
	}
	public double getTotalCorrectPercentage() {
		return totalCorrectPercentage;
	}
	public void setTotalCorrectPercentage(double totalCorrectPercentage) {
		this.totalCorrectPercentage = totalCorrectPercentage;
	}
	public ArrayList<EmotionOfSentenceTag> getOrder() {
		return order;
	}
	public void setOrder(ArrayList<EmotionOfSentenceTag> order) {
		this.order = order;
	}
	public int getSizeOfObservationLikelihoodTable() {
		return sizeOfObservationLikelihoodTable;
	}
	public void setSizeOfObservationLikelihoodTable(int sizeOfObservationLikelihoodTable) {
		this.sizeOfObservationLikelihoodTable = sizeOfObservationLikelihoodTable;
	}
	public int getSizeOfObservationLikelihoodTableExtended() {
		return sizeOfObservationLikelihoodTableExtended;
	}
	public void setSizeOfObservationLikelihoodTableExtended(int sizeOfObservationLikelihoodTableExtended) {
		this.sizeOfObservationLikelihoodTableExtended = sizeOfObservationLikelihoodTableExtended;
	}
}
