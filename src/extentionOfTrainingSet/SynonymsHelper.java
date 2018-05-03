package extentionOfTrainingSet;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class SynonymsHelper {
	private final String pathToWordNetHome = "C:\\Program Files (x86)\\WordNet\\2.1\\dict";
	IDictionary dict;
	public void testDictionary() throws IOException{
		String WNHOME = "C:\\Program Files (x86)\\WordNet\\";
		String wnhome = System.getenv(WNHOME);
		String path = wnhome + File.separator + "dict";
		URL url = new URL("file", null, "C:\\Program Files (x86)\\WordNet\\2.1\\dict");
		IDictionary dict = new Dictionary(url);
		dict.open();
		IIndexWord idxWord = dict.getIndexWord("dog", POS.NOUN);
		IWordID wordId = idxWord.getWordIDs().get(0);
		IWord word = dict.getWord(wordId);
		System.out.println("Id = " + wordId);
		System.out.println("Lemma = " + word.getLemma());
		System.out.println("Gloss = " + word.getSynset().getGloss());
	}
	public SynonymsHelper() throws IOException{
		URL url = new URL("file", null, "C:\\Program Files (x86)\\WordNet\\2.1\\dict");
		dict = new Dictionary(url);
		dict.open();
	}
	public String getListOfSyns(String wordForSyn, POS pos){
		IIndexWord idxWord = dict.getIndexWord(wordForSyn,pos);
		IWordID wordID = idxWord.getWordIDs().get(0);
		IWord word = dict.getWord(wordID);
		ISynset synset = word.getSynset();
		String listOfWords = "";
		for(IWord w : synset.getWords()){
			listOfWords = listOfWords + w.getLemma() + ", ";
		}
		return listOfWords;
	}
	public ArrayList<String> getArrayListOfSyns(String wordForSyn, POS pos){
		IIndexWord idxWord = dict.getIndexWord(wordForSyn,pos);
		IWordID wordID = idxWord.getWordIDs().get(0);
		IWord word = dict.getWord(wordID);
		ISynset synset = word.getSynset();
		ArrayList<String> listOfWords = new ArrayList<>();
		for(IWord w : synset.getWords()){
			listOfWords.add(w.toString());
		}
		return listOfWords;
	}
	public void printPOS(){
		for(POS pos: POS.values()){
			System.out.println(pos);
		}
	}
}
