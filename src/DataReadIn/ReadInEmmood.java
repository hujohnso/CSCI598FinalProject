package DataReadIn;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadInEmmood extends ReadData {
	File folder;
	ReadInEmmood(String pathToDataSetFolder){
		super(pathToDataSetFolder, "emmood");
	}
	
	public ArrayList<Sentence> readInAllEmmoodFiles() throws IOException{
		ArrayList<Sentence> sentences = new ArrayList<>();
		for(File x: allFiles){
			FileReader fileReader = null;
			try {
				fileReader = new FileReader(x);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null){
				sentences.add(new Sentence(line, x.getPath()));
			}
		}
		return sentences;
	}
}
