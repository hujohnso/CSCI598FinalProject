package TrainedModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataReadIn.EmotionOfSentenceTag;
import HMMS.ObservationLikelihoodTableEntry;
//If I had more time I would have done this with generics but sadily I did not.
public class WriteOutAndReadIn {
	public static final String root = "C:\\Users\\Hunter Johnson\\Documents\\CSCI598\\FinalProject\\trainedModel\\";
	public void serializable(ArrayList<ObservationLikelihoodTableEntry> observationLikelihoodTable, String fileName){
		try{
			OutputStream file = new FileOutputStream(root + fileName);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			output.writeObject(observationLikelihoodTable);
			output.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.getCause();
		}
	}
	public ArrayList<ObservationLikelihoodTableEntry> deserialize(String fileName){
		ArrayList<ObservationLikelihoodTableEntry> observationLikelihoodTableEntry = new ArrayList<>();
		try{
			InputStream file = new FileInputStream(root + fileName);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			observationLikelihoodTableEntry = (ArrayList<ObservationLikelihoodTableEntry>) input.readObject();
			input.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return observationLikelihoodTableEntry;
	}
	public void serializeTransition(ArrayList<ArrayList<Double>> transitionProbabilitiesTable, String fileName){
		try{
			OutputStream file = new FileOutputStream(root + fileName);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			output.writeObject(transitionProbabilitiesTable);
			output.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.getCause();
		}
	}
	public ArrayList<ArrayList<Double>> deserializeTransition(String fileName){
		ArrayList<ArrayList<Double>> transitionProbabilitiesTable = new ArrayList<>();
		try{
			InputStream file = new FileInputStream(root + fileName);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			transitionProbabilitiesTable = (ArrayList<ArrayList<Double>>) input.readObject();
			input.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return transitionProbabilitiesTable;
	}
	public void serializeInitial(Map<EmotionOfSentenceTag,Double> initialStateProbabilities, String fileName){
		try{
			OutputStream file = new FileOutputStream(root + fileName);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			output.writeObject(initialStateProbabilities);
			output.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.getCause();
		}
	}
	public Map<EmotionOfSentenceTag,Double> deserializeInitial(String fileName){
		Map<EmotionOfSentenceTag,Double> initialStateProbabilities = new HashMap<>();
		try{
			InputStream file = new FileInputStream(root + fileName);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			initialStateProbabilities = (Map<EmotionOfSentenceTag,Double>) input.readObject();
			input.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return initialStateProbabilities;
	}
}
