package fr.univtours.polytech.di.multimedia.filters;


/**
 * Classe de filtrage impl�mentant un filtre de mots vides.
 * @author S�bastien Aupetit
 */
public class StopWordFilter implements Filter {

	private String[] stopWord = {" "};
	
  /**
   * Le constructeur.
   * @param caseFilterApplied indique si les signes ont �t� filtr�s en minuscule
   * @param accentFilterApplied indique si les signes ont �t� filtr�s sans
   *          accent et sans caract�res sp�ciaux
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
