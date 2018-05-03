package DataReadIn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sentence {
	private String story;
	private String  fullSentenceEntry;
	private int sentenceNumber;
	//Note four people read each story and annotated them.	
	private ArrayList<EmotionOfSentenceTag> emotionTags;
	private EmotionOfSentenceTag dominateEmotion;
	private String sentence;
	private ArrayList<Word> words;

	Sentence(String fullSentenceEntry, String story){
		this.fullSentenceEntry = fullSentenceEntry;
		this.story = story;
		processFullSentenceEntry(preprocessing(this.fullSentenceEntry));
		findAndSetDominateEmotion();
	}
	//Example Sentence entry: 22:22	N:N	F:F	She shut the door violently, and ran away.
	private void processFullSentenceEntry(String fullSentenceEntry){
		try{
			String [] fullSentenceArray = fullSentenceEntry.split("\\s+");
			this.sentenceNumber = extractSentenceNumber(fullSentenceArray);
			this.emotionTags = extractEmotionTags(fullSentenceArray);
			this.sentence = extractFullSentence(fullSentenceArray);
			this.words = extractWords();
		}
		catch(Exception e){
			System.out.println("Reading in data failed on the full sentence entry: " + fullSentenceEntry);
		}
	}
	private int extractSentenceNumber(String [] fullSentenceArray){
		int number = 0;
		String [] numArray = fullSentenceArray[0].split(":");
		return Integer.parseInt(numArray[0]);
	}
	private ArrayList<EmotionOfSentenceTag> extractEmotionTags(String [] fullSentenceArray){
		ArrayList<EmotionOfSentenceTag> emotionOfSentenceTags = new ArrayList<>();
		String [] emotionTagsAsString1 = fullSentenceArray[1].split(":");
		String [] emotionTagsAsString2 = fullSentenceArray[2].split(":");
		ArrayList<String> emotionalTags = new ArrayList<>();
		emotionalTags.add(emotionTagsAsString1[0]);
		emotionalTags.add(emotionTagsAsString1[1]);
		emotionalTags.add(emotionTagsAsString2[0]);
		emotionalTags.add(emotionTagsAsString2[1]);
		for(String x: emotionalTags){
			for(EmotionOfSentenceTag e: EmotionOfSentenceTag.values()){
				if(x.equals(e.getTag())){
					emotionOfSentenceTags.add(e);
				}
			}
		}
		return emotionOfSentenceTags;
	}

	private String extractFullSentence(String [] fullSentenceArray){
		String sentence = "";
		for(int i = 3; i < fullSentenceArray.length; ++i){
			sentence = sentence + fullSentenceArray[i] + " ";
		}
		return sentence;
	}

	private ArrayList<Word> extractWords(){
		ArrayList<Word> words = new ArrayList<>();
		for(String x: sentence.split("\\s+")){
			String filteredWord = filterPunctutaionOutOfWords(x);
			if(filteredWord.length() > 0){
				words.add(new Word(filteredWord));
			}
		}
		return words;
	}

	public ArrayList<EmotionOfSentenceTag> getEmotionTags() {
		return emotionTags;
	}
	public void setEmotionTags(ArrayList<EmotionOfSentenceTag> emotionTags) {
		this.emotionTags = emotionTags;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
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
		word = word.replace("[","");
		word = word.replace("]", "");
		word = word.replace("(", "");
		word = word.replace(")", "");
		word = word.replace("[0-9]", "");
		return word;
	}
	private String preprocessing(String word){
		word = word.replace("--", " ");
		if(word.contains("- ") && word.endsWith(".")){
			word = word.replace("- ", "-");
		}
		if(word.contains("- ")){
			word = word.replace("- ", " ");
		}
		if(word.contains("'s ")){
			word = word.replace("'s "," s");
		}
		if(word.contains(":- ")){
			word = word.replace(":-", " ");
		}
		return word;
	}
	public ArrayList<Word> getWords(){
		return words;
	}
	public void setWords(ArrayList<Word> words){
		this.words = words;
	}
	public void findAndSetDominateEmotion(){
		EmotionOfSentenceTag winningEffect = findWinner(makeCountForEmotionOfSentenceTags());
		for(Word w: words){
			w.setEmmoodTag(winningEffect);
		}
	}
	public Map<EmotionOfSentenceTag, Integer> makeCountForEmotionOfSentenceTags(){
		Map<EmotionOfSentenceTag, Integer> emotionMap = new HashMap<>();
		for(EmotionOfSentenceTag e: emotionTags){
			if(emotionMap.containsKey(e)){
				int numberOfOccurances = emotionMap.get(e);
				numberOfOccurances++;
				emotionMap.remove(e);
				emotionMap.put(e, numberOfOccurances);
			}
			else{
				emotionMap.put(e,1);
			}
		}
		return emotionMap;
	}
	//Put the findWinner after we average the emmood data
	private EmotionOfSentenceTag findWinner(Map<EmotionOfSentenceTag, Integer> emotionMap){
		EmotionOfSentenceTag winningEOST = null;
		int greatestSimular = 0;
		for(EmotionOfSentenceTag x: emotionMap.keySet()){
			if(winningEOST == null){
				winningEOST = x;
				greatestSimular = emotionMap.get(x);
			}
			if(emotionMap.get(x) > greatestSimular || (emotionMap.get(x) == greatestSimular && winningEOST == EmotionOfSentenceTag.NEUTRAL)){
				winningEOST = x;
				greatestSimular = emotionMap.get(x);
			}
		}
		return winningEOST;
	}
	@Override
	public String toString(){
		String toString = "";
		for(Word w: words){
			toString = toString + w.toString();
		}
		return toString;
	}
}
