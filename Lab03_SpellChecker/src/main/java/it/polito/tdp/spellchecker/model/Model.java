package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {
	List<String> paroleDizionario= new ArrayList<>();
	List<RichWord> listaRichWord= new ArrayList<>();
	List<RichWord> paroleSbagliate= new ArrayList<>();
	long tempo=0;
	
	
	public void loadDictionary(String language) {
		if(language.equals("English")){
		    try {
			
			
				FileReader fr= new FileReader("src/main/resources/English.txt");
				BufferedReader br= new BufferedReader(fr);
				String word;
				while((word= br.readLine())!= null) {
					
					paroleDizionario.add(word);
				}
				br.close();
				fr.close();
				
			  }catch(IOException e) {
					System.out.println("Errore nella lettura del file");
				}
		    
		}
	    if(language.equals("Italiano")) {
	    	try {
	    		
			    FileReader fr= new FileReader("src/main/resources/Italian.txt");
			    BufferedReader br= new BufferedReader(fr);
				String word;
				while((word= br.readLine())!= null) {
					
					paroleDizionario.add(word);
				}
				br.close();
				fr.close();
				
			} catch(IOException e) {
			System.out.println("Errore nella lettura del file");
		   }
	    }
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		long tempoIniziale=System.nanoTime();
		for(String s: inputTextList) {
			RichWord r= new RichWord(s);
			if(this.paroleDizionario.contains(s)) {
				r.setCorrect(true);
			}
			else {
				r.setCorrect(false);
			}
			this.listaRichWord.add(r);
		}
		long tempoFinale=System.nanoTime();
		this.tempo=tempoFinale-tempoIniziale;
		
		return this.listaRichWord;
	}
	
	public String paroleSbagliate() {
		
		String s="";
		
		for(RichWord r: this.listaRichWord) {
			if(!r.isCorrect())
			     this.paroleSbagliate.add(r);
		}
		
		for(RichWord t: this.paroleSbagliate) {
			
			s+=t.toString()+"\n";
		}
		
		return s;
		
	}
	
	public int numeroErrori() {
		return this.paroleSbagliate.size();
	}

	public long getTempo() {
		return tempo;
	}
	
	public void reset() {
		this.listaRichWord.clear();
		this.paroleSbagliate.clear();
		this.paroleDizionario.clear();
		this.tempo=0;
	}
	
	
	
	
}
