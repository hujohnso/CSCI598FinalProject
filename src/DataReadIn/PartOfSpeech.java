package DataReadIn;
//Made from the Penn Treebank Project
public enum PartOfSpeech {
	CC("Coordinating conjunction", false),
	CD("Cardinal number", false),
	DT("Determiner", false),
	EX("Existential there", false),
	FW("Forign word", false),
	IN("Preposition or subordinating conjunction", false),
	JJ("Adjective", true),
	JJR("Adjective, comparative", true),
	JJS("Adjective, superlative", true),
	LS("List item maker", false),
	MD("Modal", true),
	NN("Noun, singular or mass", true),
	NNS("Noun, plural", true),
	NNP("Proper noun, singular", true),
	NNPS("Proper noun, plural", true),
	PDT("Predeterminer", true),
	POS("Possessive pronoun", true),
	PRP("Personal pronoun", true),
	PRP$("Possessive pronoun", true),
	RB("Adverb", true),
	RBR("Adverb, comparative", true),
	RBS("Adverb, superlative", true),
	RP("Particle", true),
	SYN("Symbol", false),
	TO("to", false),
	UH("Interjection", true),
	VB("Verb, base form", true),
	VBD("Verb, past tense", true),
	VBG("Verb, gerund or present participle", true),
	VBN("Verb, past participle", true),
	VBP("Verb, non-3rd person singular present", true),
	VBZ("Verb, 3rd person singular present", true),
	WDT("Wh-determiner", false),
	WP("Wh-pronoun", false),
	WP$("Possessive wh-pronoun", false),
	AUX("auxiliary", false),
	AUXG("Root auxiliary?",false),
	SYM("symbol", false),
	UNREC("this is for random punctuation mishaps", false),
	WRB("Wh-adverb", false);
	public final String description;
	public final Boolean carriesEmotionalForce;
	PartOfSpeech(String description, Boolean carriesEmotionalForce){
		this.description = description;
		this.carriesEmotionalForce = carriesEmotionalForce;
	}
	public String getDescription(){
		return description;
	}
	public Boolean getCarriesEmotionalForce(){
		return carriesEmotionalForce;
	}
}
