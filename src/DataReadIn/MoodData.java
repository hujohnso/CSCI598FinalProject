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
		filterSentences();
		convertToSecondTier();
	}
	private void filterSentences(){
		ArrayList<Sentence> sentencesToRemove = new ArrayList<>();
		for(int i = 0; i < sentences.size(); ++i){
			if(sentences.get(i).getWords().size() == 0){
				sentencesToRemove.add(sentences.get(i));
			}
		}
		for(Sentence s: sentencesToRemove){
			sentences.remove(s);
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
	//So the theory here is that only certian parts of speech matter
	public void filterOutIrrelvantPartsOfSpeech(){
		ArrayList<Word> filterOut = new ArrayList<>();
		ArrayList<Sentence> filterOutSentences = new ArrayList<>();
		for(Sentence x: sentences){
			if(x.getWords().size() == 0){
				filterOutSentences.add(x);
			}
			for(Word w: x.getWords()){
				if(!w.getPos().getCarriesEmotionalForce()){
					filterOut.add(w);
				}
			}
			for(Word w: filterOut){
				x.getWords().remove(w);
			}
			if(x.getWords().size() == 0){
				filterOutSentences.add(x);
			}
		}
		sentences.removeAll(filterOutSentences);
	}
	
	public void convertToSecondTier(){
		for(Sentence x: sentences){
			for(Word w: x.getWords()){
				if(w.getEmoodTag().getSecondTier() != null){
					w.setEmmoodTag(w.getEmoodTag().getSecondTier());
				}
			}
		}
	}
}