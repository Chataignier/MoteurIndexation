package fr.univtours.polytech.di.multimedia.signextractors;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;


/**
 * Classe permettant d'extraite des mots à partir d'une chaîne de caractères.
 * @author Sébastien Aupetit
 */
public class WordExtractor implements SignExtractor {

	private StringTokenizer doc;
	
  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.signextractors.SignExtractor#nextToken()
   */
  @Override
  public String nextToken() {	
	try{
		return doc.nextToken();
	}catch (NoSuchElementException nsee){
		return null;
	}
    
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.signextractors.SignExtractor#setContent(java.lang.String)
   */
  @Override
  public void setContent(final String content) {
    this.doc = new StringTokenizer(content, " ");;
    }
}
