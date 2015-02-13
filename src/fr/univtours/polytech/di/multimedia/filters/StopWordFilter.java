package fr.univtours.polytech.di.multimedia.filters;


/**
 * Classe de filtrage impl�mentant un filtre de mots vides.
 * @author S�bastien Aupetit
 */
public class StopWordFilter implements Filter {

	private String[] stopWord = {
		    "le",
		    "de",
		    "un",
		    "�tre",
		    "et",
		    "�",
		    "il",
		    "avoir",
		    "ne",
		    "je",
		    "son",
		    "que",
		    "se",
		    "qui",
		    "ce",
		    "dans",
		    "en",
		    "du",
		    "elle",
		    "au",
		    "de",
		    "ce",
		    "le",
		    "pour",
		    "pas",
		    "que",
		    "vous",
		    "par",
		    "sur",
		    "faire",
		    "plus",
		    "dire",
		    "me",
		    "on",
		    "mon",
		    "lui",
		    "nous",
		    "comme",
		    "mais",
		    "pouvoir",
		    "avec",
		    "tout",
		    "y",
		    "aller",
		    "voir",
		    "en",
		    "bien",
		    "o�",
		    "sans",
		    "tu",
		    "ou",
		    "leur",
		    "homme",
		    "si",
		    "deux",
		    "mari",
		    "moi",
		    "vouloir",
		    "te",
		    "femme",
		    "venir",
		    "quand",
		    "grand",
		    "celui",
		    "si",
		    "notre",
		    "devoir",
		    "l�",
		    "jour",
		    "prendre",
		    "m�me",
		    "votre",
		    "tout",
		    "rien",
		    "petit",
		    "encore",
		    "aussi",
		    "quelque",
		    "dont",
		    "tout",
		    "mer",
		    "trouver",
		    "donner",
		    "temps",
		    "�a",
		    "peu",
		    "m�me",
		    "falloir",
		    "sous",
		    "parler",
		    "alors",
		    "main",
		    "chose",
		    "ton",
		    "mettre",
		    "vie",
		    "savoir",
		    "yeux",
		    "passer",
		    "autre"
	    };
	
  /**
   * Le constructeur.
   * @param caseFilterApplied indique si les signes ont �t� filtr�s en minuscule
   * @param accentFilterApplied indique si les signes ont �t� filtr�s sans
   *          accent et sans caract�res sp�ciaux
   */
  public StopWordFilter(final boolean caseFilterApplied,
      final boolean accentFilterApplied) {
	  
	  //recup�ration de la liste stopWord avec l'onglet stats
	  //getOverallWordOccurrences
	  
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
