package DataReadIn;
//Made from the Penn Treebank Project
public enum PartOfSpeech {
	CC("Coordinating conjunction", false, null),
	CD("Cardinal number", false, null),
	DT("Determiner", false, null),
	EX("Existential there", false, null),
	FW("Forign word", false, null),
	IN("Preposition or subordinating conjunction", false, null),
	JJ("Adjective", true, "adjective"),
	JJR("Adjective, comparative", true, "adjective"),
	JJS("Adjective, superlative", true, "adjective"),
	LS("List item maker", false, null),
	MD("Modal", true, null),
	NN("Noun, singular or mass", true, "noun"),
	NNS("Noun, plural", true, "noun"),
	NNP("Proper noun, singular", true, null),
	NNPS("Proper noun, plural", true, null),
	PDT("Predeterminer", true, null),
	POS("Possessive pronoun", true, "noun"),
	PRP("Personal pronoun", true, null),
	PRP$("Possessive pronoun", true, "noun"),
	RB("Adverb", true, "adverb"),
	RBR("Adverb, comparative", true, "adverb"),
	RBS("Adverb, superlative", true, "adverb"),
	RP("Particle", true, null),
	SYN("Symbol", false, null),
	TO("to", false, null),
	UH("Interjection", true, null),
	VB("Verb, base form", true, "verb"),
	VBD("Verb, past tense", true, "verb"),
	VBG("Verb, gerund or present participle", true, "verb"),
	VBN("Verb, past participle", true, "verb"),
	VBP("Verb, non-3rd person singular present", true, "verb"),
	VBZ("Verb, 3rd person singular present", true, "verb"),
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
