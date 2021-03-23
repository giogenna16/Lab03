package it.polito.tdp.spellchecker.model;

public class RichWord {
	
	String word;
	boolean correct;

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public RichWord(String word) {
		
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	public String toString() {
		return this.word;
	}
	
	

}
