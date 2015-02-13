package fr.univtours.polytech.di.multimedia.filters;


/**
 * Classe de filtrage implémentant un filtre de mots vides.
 * @author Sébastien Aupetit
 */
public class StopWordFilter implements Filter {

	private String[] stopWord = {
		    "le",
		    "de",
		    "un",
		    "être",
		    "et",
		    "à",
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
		    "où",
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
		    "là",
		    "jour",
		    "prendre",
		    "même",
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
		    "ça",
		    "peu",
		    "même",
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
   * @param caseFilterApplied indique si les signes ont été filtrés en minuscule
   * @param accentFilterApplied indique si les signes ont été filtrés sans
   *          accent et sans caractères spéciaux
   */
  public StopWordFilter(final boolean caseFilterApplied,
      final boolean accentFilterApplied) {
	  
	  //recupération de la liste stopWord avec l'onglet stats
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
