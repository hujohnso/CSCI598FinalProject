package DataReadIn;
//Made from the Penn Treebank Project
public enum PartOfSpeech {
	CC("Coordinating conjunction"),
	CD("Cardinal number"),
	DT("Determiner"),
	EX("Existential there"),
	FW("Forign word"),
	IN("Preposition or subordinating conjunction"),
	JJ("Adjective"),
	JJR("Adjective, comparative"),
	JJS("Adjective, superlative"),
	LS("List item maker"),
	MD("Modal"),
	NN("Noun, singular or mass"),
	NNS("Noun, plural"),
	NNP("Proper noun, singular"),
	NNPS("Proper noun, plural"),
	PDT("Predeterminer"),
	POS("Possessive pronoun"),
	PRP("Personal pronoun"),
	PRP$("Possessive pronoun"),
	RB("Adverb"),
	RBR("Adverb, comparative"),
	RBS("Adverb, superlative"),
	RP("Particle"),
	SYN("Symbol"),
	TO("to"),
	UH("Interjection"),
	VB("Verb, base form"),
	VBD("Verb, past tense"),
	VBG("Verb, gerund or present participle"),
	VBN("Verb, past participle"),
	VBP("Verb, non-3rd person singular present"),
	VBZ("Verb, 3rd person singular present"),
	WDT("Wh-determiner"),
	WP("Wh-pronoun"),
	WP$("Possessive wh-pronoun"),
	AUX("auxiliary"),
	AUXG("Root auxiliary?"),
	SYM("symbol"),
	UNREC("this is for random punctuation mishaps"),
	WRB("Wh-adverb");
	private final String description;
	PartOfSpeech(String description){
		this.description = description;
	}
	public String getDescription(){
		return description;
	}
}
