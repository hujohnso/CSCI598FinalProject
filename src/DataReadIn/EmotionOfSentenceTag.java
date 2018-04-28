package DataReadIn;

public enum EmotionOfSentenceTag {
	HAPPY("H"),
	SUPPRIZED_PLUS("Su+"),
	SUPRIZED_NEG("Su-"),
	FEERFUL("F"),
	SAD("S"),
	ANGRY("A"),
	DISGUSTED("D"),
	NEUTRAL("N"),
	NEGATIVE("-"),
	POSITIVE("+");
	private final String tagValue;
	EmotionOfSentenceTag(String tagValue){
		this.tagValue = tagValue;
	}
	public String getTag(){
		return tagValue;
	}
	
}
