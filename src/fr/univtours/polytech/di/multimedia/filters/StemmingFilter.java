package fr.univtours.polytech.di.multimedia.filters;


/**
 * Classe de filtre réalisant un stemming.
 * @author Sébastien Aupetit
 */
public class StemmingFilter implements Filter {

	// on regroupe les voyelles ensembles
	public static final String voyelles = "aeiouyâàëéêèïîôûù";

	  /**
	   * {@inheritDoc}
	   * @see fr.univtours.polytech.di.multimedia.filters.Filter#filter(java.lang.String)
	   */
	@Override
	public String filter(final String sign) {
		// Si le signe est nul, on n'a rien a faire
		if (sign.equals("") || sign == null )
			return null;

		//On stocke le mot et on le met en miniscule
		String mot = sign;
		mot = mot.toLowerCase();

		// Comme On met une partie des lettres en majuscule
		mot = mettreEnMinUnePartie(mot);

		// On enlève les suffixes
		mot = supprimerSuffixes(mot);

		
		// On remet tout en minuscule
		mot = mot.toLowerCase();

		return mot;
	}


	/**
	 * Si le caractère est dans la liste des voyelles alors c'est une voyelle
	 * @param c
	 * @return true si voyelle false sinon
	 */
	public static final boolean isVoyelle(char c) {

		return voyelles.indexOf(c) == -1 ? false : true;
	}


	public static final boolean isConsonne(char c) {
		return !isVoyelle(c);
	}

	/**
	 * Partie 0 d'initialisation du stemming
	 * 
	 * @param sign
	 *            Le signe à parser
	 * @return Le signe parsé pour accentuer certains caractères
	 */
	private static final String mettreEnMinUnePartie(final String sign) {

		// On passe notre String en tableau de caratère pour travailler plus simplement
		char[] mot = sign.toCharArray();

		for (int i = 0; i < mot.length; i++) {
			if (isVoyelle(mot[i])) {

				switch (mot[i]) {

				case 'u':
					//Si il y a un q avant
					if (i > 1 && mot[i - 1] == 'q') {
						mot[i] = Character.toUpperCase(mot[i]);
					}
				case 'i':
					// Une voyelle de chaque coté
					if ((i > 1 && isVoyelle(mot[i - 1]))
							&& (i < mot.length - 1 && isVoyelle(mot[i + 1]))) {
						mot[i] = Character.toUpperCase(mot[i]);
					}
					break;
				case 'y':
					// Une voyelle d'un coté ou de l'autre
					if ((i > 1 && isVoyelle(mot[i - 1]))
							|| (i < mot.length - 1 && isVoyelle(mot[i + 1]))) {
						mot[i] = Character.toUpperCase(mot[i]);
					}
					break;
				}
			}
		}

		if(mot.length > 0){
			return new String(mot);
		}else{
			return null;
		}
	}

	/**
	 * Modification du suffixe
	 * @param sign
	 * @return
	 */
	private static final String supprimerSuffixes(final String sign) {

		String mot = sign;

		String[] liste1 = {"ables", "istes" ,"iqUe", "isme", "able", "iste", "eux", "ance", 
				"ances", "iqUes" ,"ismes","ateur", "atrices",  "atrice","ateurs", "ations" , "ation",
				"ivement", "ivements" , "ativement", "ativements",
				 "ablement", "ablements", "iqUement", "iqUements",
				 "ité", "ités",
				 "ivité", "ivités",
				 "if", "ive", "ifs", "ives",
				 "atif", "ative", "atifs", "atives"};
		for (String suffixe : liste1) {
			if (R2(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "");
			}
		}


		String[] liste2 = { "icatrice", "icateur", "ication", "icatrices",
				"icateurs", "ications" };
		for (String suffixe : liste2) {
			if (R2(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "");
			} else {
				mot = mot.replace(suffixe, "iqU");
			}
		}

		String[] liste3 = { "logie", "logies" };
		for (String suffixe : liste3) {
			if (R2(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "log");
			}
		}

		String[] liste4 = { "usion", "ution", "usions", "utions" };
		for (String suffixe : liste4) {
			if (R2(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "u");
			}
		}

		String[] liste5 = { "ence", "ences" };
		for (String suffixe : liste5) {
			if (R2(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "ent");
			}
		}

		String[] liste6 = { "ement", "ements" };
		for (String suffixe : liste6) {
			if (RV(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "");
			}
		}



		String[] liste7 = { "eusement", "eusements" };
		for (String suffixe : liste7) {
			if (R2(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "");
			} else if (R1(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "eux");
			}
		}



		String[] liste8 = { "ièrement", "ièrements", "Ièrement",
				"Ièrements" };
		for (String suffixe : liste8) {
			if (RV(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "i");
			}
		}


		String[] liste9 = { "ablité", "ablités" };
		for (String suffixe : liste9) {
			if (R2(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "");
			} else {
				mot = mot.replace(suffixe, "abl");
			}
		}

		String[] liste10 = { "icité", "icités" };
		for (String suffixe : liste10) {
			if (R2(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "");
			} else {
				mot = mot.replace(suffixe, "iqU");
			}
		}

		

		String[] liste11 = { "icatif", "icative", "icatifs", "icatives" };
		for (String suffixe : liste11) {
			if (R2(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "");
			} else {
				mot = mot.replace(suffixe, "iqU");
			}
		}

		String[] liste12 = { "eaux" };
		for (String suffixe : liste12) {
			mot = mot.replace(suffixe, "eau");
		}

		String[] liste13 = { "aux" };
		for (String suffixe : liste13) {
			if (R1(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "al");
			}
		}

		String[] liste14 = { "euse", "euses" };
		for (String suffixe : liste14) {
			if (R2(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "");
			} else if (R1(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "eux");
			}
		}

		String[] liste15 = { "issement", "issements" };
		for (String suffixe : liste15) {
			if (R1(mot).contains(suffixe)) {
				if (mot.indexOf(suffixe) >= 1
						&& isConsonne(mot.charAt(mot.indexOf(suffixe) - 1))) {
					mot = mot.replace(suffixe, "");
				}
			}
		}

		String[] liste16 = { "amment" };
		for (String suffixe : liste16) {
			if (RV(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "ant");
			}
		}

		String[] liste17 = { "emment" };
		for (String suffixe : liste17) {
			if (RV(mot).contains(suffixe)) {
				mot = mot.replace(suffixe, "ent");
			}
		}

		String[] liste18 = { "ment", "ments" };
		for (String suffixe : liste18) {
			if (RV(mot).contains(suffixe)) {
				if (mot.indexOf(suffixe) >= 1
						&& isVoyelle(mot.charAt(mot.indexOf(suffixe) - 1))) {
					mot = mot.replace(suffixe, "");
				}
			}
		}

		if(mot.equals("")){
			return null;
		}else{
			return mot;
		}
	}


	/**
	 * Récupère le radical RV du mot
	 * @param sign
	 * @return
	 */
	private static final String RV(final String sign) {

		char[] mot = sign.toCharArray();

		if (isVoyelle(mot[0])) {
			if(mot.length >= 1)
				return sign.substring(1);
			else if (mot.length >= 3)
				return sign.substring(3);
		}
		

		if ( sign.indexOf("par") == 0 || sign.indexOf("col") == 0 ||  sign.indexOf("tap") == 0) {
			return sign.substring(3);
		}

		for (int iLettre = 1; iLettre < mot.length; iLettre++) {
			if (isVoyelle(mot[iLettre]) && mot.length >= iLettre + 1) {
				return sign.substring(iLettre + 1);
			}
		}

		return sign;
	}


	/**
	 * récupère le radical R1 du mot
	 * @param sign
	 * @return
	 */
	private static final String R1(final String sign) {

		char[] mot = sign.toCharArray();

		int placeVoyelle = -1;

		for (int i = 0; i < mot.length; i++) {
			if (isVoyelle(mot[i])) {
				if (placeVoyelle == -1) {
					placeVoyelle = i;
				}
			} else if (placeVoyelle != -1) {
				return sign.substring(i + 1);
			}
		}

		if (placeVoyelle != -1) {
			return sign.substring(placeVoyelle + 1);
		} else {
			return sign;
		}
	}


	/**
	 * récupère le radical R2 du mot
	 * @param sign
	 * @return
	 */
	private static final String R2(final String sign) {
		return R1(R1(sign));
	}

}