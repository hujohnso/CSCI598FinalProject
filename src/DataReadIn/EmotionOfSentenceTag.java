package DataReadIn;

import java.io.Serializable;

public enum EmotionOfSentenceTag implements Serializable {
	NEUTRAL("N",6,null),
	NEGATIVE("-", 8,null),
	POSITIVE("+",9,null),
	HAPPY("H",7,EmotionOfSentenceTag.POSITIVE),
	SUPPRIZED_PLUS("Su+",1,EmotionOfSentenceTag.POSITIVE),
	SUPRIZED_NEG("Su-",2,EmotionOfSentenceTag.NEGATIVE),
	FEERFUL("F",3,EmotionOfSentenceTag.NEGATIVE),
	SAD("S",4,EmotionOfSentenceTag.NEGATIVE),
	ANGRY("A", 0,EmotionOfSentenceTag.NEGATIVE),
	DISGUSTED("D",5,EmotionOfSentenceTag.NEGATIVE);
	private final String tagValue;
	//Used as a convince for Viterbi algorithm
	public int tagNumber;
	private EmotionOfSentenceTag secondTier;
	EmotionOfSentenceTag(String tagValue, int tagNumber, EmotionOfSentenceTag secondTier){
		this.tagValue = tagValue;
		this.tagNumber = tagNumber;
		this.secondTier = secondTier;
	}
	public String getTag(){
		return tagValue;
	}
	public int getTagNumber(){
		return tagNumber;
	}
	public EmotionOfSentenceTag getSecondTier(){
		return secondTier;
	}
	
	
}
