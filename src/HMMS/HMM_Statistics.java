package HMMS;

public class HMM_Statistics {
/*This class is for keeping track of statistics and will only handle keeping track of stats.*/
	int trainingDataSize;
	int testingDataSize;
	public int getTrainingDataSize() {
		return trainingDataSize;
	}
	public void setTrainingDataSize(int trainingDataSize) {
		this.trainingDataSize = trainingDataSize;
	}
	public int getTestingDataSize() {
		return testingDataSize;
	}
	public void setTestingDataSize(int testingDataSize) {
		this.testingDataSize = testingDataSize;
	}
	
}
