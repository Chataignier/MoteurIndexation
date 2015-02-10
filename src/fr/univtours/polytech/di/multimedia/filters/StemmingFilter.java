package fr.univtours.polytech.di.multimedia.filters;

/**
 * Classe de filtre réalisant un stemming.
 * @author Sébastien Aupetit
 */
public class StemmingFilter implements Filter {

	private String[] stem = {};
	
  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.filters.Filter#filter(java.lang.String)
   */
  @Override
  public String filter(final String sign) {
	  //reduction du pluriel, reduction des adverbes.
	  for(int i=0 ; i < stem.length ; i++) {
		  if(sign.endsWith(stem[i])) {
			  String tmpSign = (String) sign.subSequence(0, sign.length() - stem[i].length());
			  return tmpSign;
		  }
	  }
	  return sign;
  }

}
