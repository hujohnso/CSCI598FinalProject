package DataReadIn;

import java.util.ArrayList;

public class Word {
	private String word;
	private PartOfSpeech pos;
	private EmotionOfSentenceTag emmoodTag;
	Word(String word,PartOfSpeech pos){
		this.word = word;
		this.pos = pos;
	}
	public EmotionOfSentenceTag getEmoodTag() {
		return emmoodTag;
	}
	public void setEmmoodTag(EmotionOfSentenceTag tag) {
		this.emmoodTag = tag;
	}
	Word(String word){
		this.word = word;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public PartOfSpeech getPos() {
		return pos;
	}
	public void setPos(PartOfSpeech pos) {
		this.pos = pos;
	}
	
}
