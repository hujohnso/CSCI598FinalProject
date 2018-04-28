package DataReadIn;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MoodData {
	private ArrayList<Sentence> sentences;
	private ArrayList<Word> words;
	private ReadInEmmood readInEmmood;
	private ReadInPartOfSpeech readInPartOfSpeech;
	private final static String dataPathDefault = "C:\\Users\\Hunter Johnson\\Documents\\CSCI598\\FinalProject\\data_set\\";
	MoodData(String dataPath){
		init(dataPath);
	}
	MoodData(){
		init(dataPathDefault);
	}
	public ArrayList<Sentence> getSentences() {
		return sentences;
	}
	public void setSentences(ArrayList<Sentence> sentences) {
		this.sentences = sentences;
	}
	public ArrayList<Word> getWords() {
		return words;
	}
	public void setWords(ArrayList<Word> words) {
		this.words = words;
	}
	private void init(String dataPath){
		readInEmmood = new ReadInEmmood(dataPath);
		try{
		sentences = readInEmmood.readInAllEmmoodFiles();
		readInPartOfSpeech = new ReadInPartOfSpeech(dataPath, sentences);
		words = readInPartOfSpeech.readInAllPartOfSpeechFiles();
		sentences = readInPartOfSpeech.getSentences();
		System.out.println("The sentence size is: " + sentences.size());
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	public void printNumMissmatchedPartsOfSpeech(){
		int i = 0;
		for(Sentence x: sentences){
			for(Word w: x.getWords()){
				if(w.getPos() == null){
					i++;
					System.out.println("The word is: " + w.getWord());
				}
			}
		}	
		System.out.println("The number of words that do not have a match is: " + i);
		System.out.println("The number persentage of words that do not have a match is: " + (double) i /(double)countMoodDataWords());
	}
	
	public ArrayList<Sentence> pairPOSWordsWithSentenceWords(){
		int i = 0;
		boolean wordPaired = false;
		for(Sentence x: sentences){
			ArrayList<Word> newWordsWithPair = new ArrayList<>();
			for(Word xWords: x.getWords()){
				wordPaired = false;
				while(!wordPaired){
					if(xWords.getWord().equals(words.get(i).getWord())){
						newWordsWithPair.add(words.get(i));
						wordPaired = true;
					}
					i++;					
				}
				x.setWords(newWordsWithPair);
			}
		}
		return null;
	}
	public int countMoodDataWords(){
		int i = 0;
		for(Sentence x: sentences){
			for(Word w: x.getWords()){
				i++;
			}
		}
		return i;
	}
	
	public void findWordsWithoutMatchesSetStyle(){
		Set<String> sentenceWordSet = new HashSet<>();
		Set<String> wordsSet = new HashSet<>();
		for(Sentence x: sentences){
			for(Word w: x.getWords()){
				sentenceWordSet.add(w.getWord());
			}
		}
		for(Word w: words){
			wordsSet.add(w.getWord());
		}
		wordsSet.removeAll(sentenceWordSet);
		System.out.println("The size of wordsSet is now: " + wordsSet.size());
		for(String x: wordsSet){
			System.out.println(x);
		}
	}
}