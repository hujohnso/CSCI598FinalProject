package DataReadIn;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadInPartOfSpeech extends ReadData {
	int lineProcessing = 0;
	ArrayList<Sentence> sentences;
	ReadInPartOfSpeech(String pathToDataSetFolder, ArrayList<Sentence> sentences){
		super(pathToDataSetFolder, "pos");
		this.sentences = sentences;
	}
	public ArrayList<Word> readInAllPartOfSpeechFiles() throws IOException{
		ArrayList<Word> words = new ArrayList<>();
		for(File x: allFiles){
			FileReader fileReader = null;
			try {
				fileReader = new FileReader(x);
			} catch(FileNotFoundException e){
				e.printStackTrace();
			}
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null){
				ArrayList<Word> wordsInLine = processLine(line);
				words.addAll(wordsInLine);
				attemptToMatchPOS(wordsInLine);
			}
		}
		return words;
	}

	public ArrayList<Word> processLine(String line){
		ArrayList<Word> words = new ArrayList<>();
		String [] wordEntry = line.split("\\):\\(");
		for(String x: wordEntry){
			String temp = x;
			temp = temp.replace("(","");
			temp = temp.replace(")", "");
			String [] tagAndWord = temp.split(" ");
			if(!isPunctuation(tagAndWord[1])){
				String filteredWord = filterPunctutaionOutOfWords(tagAndWord[1]);
				if(filteredWord.length() > 0){
					try{
						words.add(new Word(filteredWord,PartOfSpeech.valueOf(tagAndWord[0])));
					}
					catch (Exception e){
						words.add(new Word(filteredWord,PartOfSpeech.UNREC));
					}
				}
			}
		}
		return words;
	}
	public void attemptToMatchPOS(ArrayList<Word> words){
		for(Word x: sentences.get(lineProcessing).getWords()){
			for(Word w: words){
				if(w.getWord().equals(x.getWord())){
					x.setPos(w.getPos());
					continue;
				}
			}
			for(Word w: words){
				if(w.getWord().contains(x.getWord()) && x.getPos() == null){
					x.setPos(w.getPos());
					continue;
				}
				if(x.getWord().contains(w.getWord()) && x.getPos() == null){
					x.setPos(w.getPos());
					continue;
				}
			}

		}
		lineProcessing++;
	}
	public boolean isPunctuation(String word){
		if(word.equals(".") ||
				word.equals(":") ||
				word.equals(",") ||
				word.equals(";") ||
				word.equals("!") ||
				word.equals("?") ||
				word.equals("``")||
				word.equals("\"")||
				word.equals("'") ||
				word.contains("*") ||
				word.equals("''") ||
				word.equals("-LRB-") ||
				word.equals("-RRB-") ||
				word.equals("-LSB-") ||
				word.equals("-RSB-")
				){
			return true;
		}
		return false;
	}
	private String filterPunctutaionOutOfWords(String word){
		word = word.replace(".","");
		word = word.replace(":","");
		word = word.replace(",","");
		word = word.replace(";","");
		word = word.replace("!","");
		word = word.replace("?","");
		word = word.replace("\"","");
		word = word.replace("'","");
		word = word.replace("*", "");
		word = word.replace("``", "");
		word = word.replace("[a-z]", "");
		word = word.replace("[0-9]", "");
		return word;
	}
	public ArrayList<Sentence> getSentences(){
		return this.sentences;
	}
}
