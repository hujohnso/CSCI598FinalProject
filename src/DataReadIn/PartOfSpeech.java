package DataReadIn;

import java.io.Serializable;

//Made from the Penn Treebank Project
public enum PartOfSpeech implements Serializable{
	CC("Coordinating conjunction", false, null),
	CD("Cardinal number", false, null),
	DT("Determiner", false, null),
	EX("Existential there", false, null),
	FW("Forign word", false, null),
	IN("Preposition or subordinating conjunction", false, null),
	JJ("Adjective", true, "ADJECTIVE"),
	JJR("Adjective, comparative", true, "ADJECTIVE"),
	JJS("Adjective, superlative", true, "ADJECTIVE"),
	LS("List item maker", false, null),
	MD("Modal", true, null),
	NN("Noun, singular or mass", true, "NOUN"),
	NNS("Noun, plural", true, "NOUN"),
	NNP("Proper noun, singular", true, null),
	NNPS("Proper noun, plural", true, null),
	PDT("Predeterminer", true, null),
	POS("Possessive pronoun", true, "NOUN"),
	PRP("Personal pronoun", true, null),
	PRP$("Possessive pronoun", true, "NOUN"),
	RB("Adverb", true, "ADVERB"),
	RBR("Adverb, comparative", true, "ADVERB"),
	RBS("Adverb, superlative", true, "ADVERB"),
	RP("Particle", true, null),
	SYN("Symbol", false, null),
	TO("to", false, null),
	UH("Interjection", true, null),
	VB("Verb, base form", true, "VERB"),
	VBD("Verb, past tense", true, "VERB"),
	VBG("Verb, gerund or present participle", true, "VERB"),
	VBN("Verb, past participle", true, "VERB"),
	VBP("Verb, non-3rd person singular present", true, "VERB"),
	VBZ("Verb, 3rd person singular present", true, "VERB"),
	WDT("Wh-determiner", false, null),
	WP("Wh-pronoun", false, null),
	WP$("Possessive wh-pronoun", false, null),
	AUX("auxiliary", false, null),
	AUXG("Root auxiliary?",false, null),
	SYM("symbol", false, null),
	UNREC("this is for random punctuation mishaps", false, null),
	WRB("Wh-adverb", false, null);
	public final String description;
	public final Boolean carriesEmotionalForce;
	public final String wordNetMappedValue;
	PartOfSpeech(String description, Boolean carriesEmotionalForce, String wordNetMappedValue){
		this.description = description;
		this.carriesEmotionalForce = carriesEmotionalForce;
		this.wordNetMappedValue = wordNetMappedValue;
	}
	public String getDescription(){
		return description;
	}
	public Boolean getCarriesEmotionalForce(){
		return carriesEmotionalForce;
	}
	public String getWordNetMappedPOS(){
		return wordNetMappedValue;
	}
}
