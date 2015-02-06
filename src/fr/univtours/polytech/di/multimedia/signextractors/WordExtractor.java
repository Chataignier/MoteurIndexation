package fr.univtours.polytech.di.multimedia.signextractors;

import java.util.StringTokenizer;


/**
 * Classe permettant d'extraite des mots � partir d'une cha�ne de caract�res.
 * @author S�bastien Aupetit
 */
public class WordExtractor implements SignExtractor {

	private StringTokenizer phrase;
	
  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.signextractors.SignExtractor#nextToken()
   */
  @Override
  public String nextToken() {	
	  String test = "lol";
	return phrase.nextToken();
    
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.signextractors.SignExtractor#setContent(java.lang.String)
   */
  @Override
  public void setContent(final String content) {
    this.phrase = new StringTokenizer(content, " \t\n\r\f,.:;?![]'");;
    }
}
