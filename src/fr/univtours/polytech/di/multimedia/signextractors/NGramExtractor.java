package fr.univtours.polytech.di.multimedia.signextractors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe permettant d'extraite des N-grams à partir d'une chaîne de caractères.
 * @author Sébastien Aupetit
 */
public class NGramExtractor implements SignExtractor {

	private Pattern nGram;
	private Matcher matcher;
	private WordExtractor WE;
	private String word;
	
	
  /**
   * Le constructeur.
   * @param size la taille des N-grams
   */
  public NGramExtractor(final int size) {
		WE = new WordExtractor();
		word = "";
		nGram = Pattern.compile("[^ .,;:!?]{"+size+"}");
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.signextractors.SignExtractor#nextToken()
   */
  @Override
  public String nextToken() {
	  // On parcours tous les nGram
	  while(true){
		  // On récupère le mot renvoyé par WE.nextToken
		  matcher = nGram.matcher(word);

		  // Si on peut récupérer un nGram dans matcher
		  if(matcher.find()){
			  // On décale d'une lettre pour passer au nGram suivant
			  word = word.substring(1);
			  return matcher.group();
		  }else{
			  // Si on ne trouve pas de nGram on passe au mot suivant dans la chaine de base
			  word = WE.nextToken();

			  if(word==null){
				  return null;
			  }
		  } 
	  }
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.signextractors.SignExtractor#setContent(java.lang.String)
   */
  @Override
  public void setContent(final String content) {
	  WE.setContent(content);
	  matcher = null;
	  word  = "";
	  
  }
}
