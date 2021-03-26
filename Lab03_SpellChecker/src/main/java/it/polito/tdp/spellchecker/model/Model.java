package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
					/*word= word.replace("-", "");
					word=word.replace("'", "");
					word=word.replace(" ", "");*/
					paroleDizionario.add(word.toLowerCase());
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
					
					
					paroleDizionario.add(word.toLowerCase());
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
			r.setCorrect(false);
			if(this.paroleDizionario.contains(s)) {
				r.setCorrect(true);
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
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		long tempoIniziale=System.nanoTime();
		for(String s: inputTextList) {
			RichWord r= new RichWord(s);
			r.setCorrect(false);
			for(String t: this.paroleDizionario) {
				if(s.equals(t)) {
					r.setCorrect(true);
					break;
				}
			
			}
			this.listaRichWord.add(r);
		}
		
		long tempoFinale=System.nanoTime();
		this.tempo=tempoFinale-tempoIniziale;
		
		return this.listaRichWord;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList){
		
		
		long tempoIniziale=System.nanoTime();
		int lunghezza=this.paroleDizionario.size();
		int tentativi= (int)((Math.log(lunghezza) / Math.log(2))+1);
		String[] paroleDiz=new String[lunghezza];
		paroleDiz=this.paroleDizionario.toArray(paroleDiz);
		
		for(String s: inputTextList) {
			RichWord r= new RichWord(s);
			r.setCorrect(false);
			int min=0;
			int max=lunghezza;
			
					
			for(int i=0; i<tentativi; i++) {
				int media=(int)(min+max)/2;
				String temp= paroleDiz[media];
				if(s.compareTo(temp)==0) {
					r.setCorrect(true);
					break;
				}
				if(s.compareTo(temp)>0) {
					min=media; 
					
				}
				if(s.compareTo(temp)<0) {
					max=media;
					
				}	
			}
			this.listaRichWord.add(r);	
		}
		long tempoFinale=System.nanoTime();
		this.tempo=tempoFinale-tempoIniziale;
		
		return this.listaRichWord;
		
		
	}
	
	/**
	 * Provando i tre metodi, si nota che la ricerca dicotomica è la più efficiente,
	 * il List.contain() ha delle prestazioni simili alla ricerca lineare (un po' migliori, ma dello stesso
	 * ordine di grandezza; la dicotomica ha un tempo minore di un ordine di grandezza almeno).
	 * La LinkedList con tutte e tre le tipologie di ricerca è risultata meno efficiente rispetto alla
	 * ArrayList.
	 */
	
	
	
	
	
}
