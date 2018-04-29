package DataReadIn;

public enum EmotionOfSentenceTag {
	HAPPY("H",7),
	SUPPRIZED_PLUS("Su+",1),
	SUPRIZED_NEG("Su-",2),
	FEERFUL("F",3),
	SAD("S",4),
	ANGRY("A", 0),
	DISGUSTED("D",5),
	NEUTRAL("N",6),
	NEGATIVE("-", 8),
	POSITIVE("+",9);
	private final String tagValue;
	//Used as a convince for Viterbi algorithm
	public int tagNumber;
	EmotionOfSentenceTag(String tagValue, int tagNumber){
		this.tagValue = tagValue;
	}
	public String getTag(){
		return tagValue;
	}
	
}
