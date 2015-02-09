package fr.univtours.polytech.di.multimedia.filters;


/**
 * Classe de filtrage implémentant un filtre de mots vides.
 * @author Sébastien Aupetit
 */
public class StopWordFilter implements Filter {

	private String[] stopWord = {" "};
	
  /**
   * Le constructeur.
   * @param caseFilterApplied indique si les signes ont été filtrés en minuscule
   * @param accentFilterApplied indique si les signes ont été filtrés sans
   *          accent et sans caractères spéciaux
   */
  public StopWordFilter(final boolean caseFilterApplied,
      final boolean accentFilterApplied) {
	  
	  CaseFilter cf = new CaseFilter();
	  AccentFilter af = new AccentFilter();
	  
	  for (int i=0 ; i < stopWord.length ; i++) {
		  if(!caseFilterApplied) {
			  //Faire le filtre case
			  stopWord[i] = cf.filter(stopWord[i]);
		  }
		  if(!accentFilterApplied) {
			  //Faire le filtre accent
			  stopWord[i] = af.filter(stopWord[i]);
		  }
	  }
	  
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.filters.Filter#filter(java.lang.String)
   */
  @Override
  public String filter(final String sign) {
	  
	  for(int i=0 ; i < stopWord.length ; i++ ) {
		  if(stopWord[i].equals(sign)) {
			  return "";
		  }
	  }
	  return sign;
  }

}
