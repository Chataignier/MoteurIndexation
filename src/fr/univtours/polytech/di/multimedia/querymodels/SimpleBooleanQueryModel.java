package fr.univtours.polytech.di.multimedia.querymodels;

import java.util.ArrayList;
import java.util.List;

import fr.univtours.polytech.di.multimedia.database.Database;
import fr.univtours.polytech.di.multimedia.database.Document;
import fr.univtours.polytech.di.multimedia.database.InvertedIndex;
import fr.univtours.polytech.di.multimedia.database.ValuedObject;
import fr.univtours.polytech.di.multimedia.signextractors.SignExtractor;

/**
 * Classe impl�mentant le mod�le d'interrogation bool�en simple.
 * @author S�bastien Aupetit
 */
public class SimpleBooleanQueryModel extends QueryModel {

  /**
   * Le constructeur.
   * @param database la base de donn�es
   */
  public SimpleBooleanQueryModel(final Database database) {
    super(database);
    // TOTO : A COMPLETER ICI
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.querymodels.QueryModel#getAnswers(java.lang.String)
   */
  @Override
  public List < ValuedObject > getAnswers(final String question) {
    final List < ValuedObject > results = new ArrayList < ValuedObject >();

    String sign; //sign temporaire
    ArrayList<String> words = new ArrayList<String>(); //Liste de mots de la question
    List<Document> documents = this.getDatabase().getDocuments(); //Liste des documents
    
    //Extraction des mots de la question
    SignExtractor signExtractor = getDatabase().getSignExtractor();
    signExtractor.setContent(question);
    while ((sign = signExtractor.nextToken()) != null) 
    {
    	//Ajout avec application du filtre
    	words.add(getDatabase().filterSign(sign));
	}
    
    /*
	 * On parcours l'ensemble des documents
	 */
	for (Document document : documents) {
		
		double matchedSign = 0;
		/*
		 * Pour chaque signe de la requete
		 */
		for (int i = 0; i < words.size(); ++i) {
			
			//On verifie si le signe est pr�sent dans le document
			if ((this.getDatabase().getInvertedIndex().getWordOccurrences(words.get(i), document)) >= 1) {
				/*
				 * Le signe est contenu dans le document. On indique que le
				 * signe 'match' le document
				 */
				matchedSign++;
			} else {
				continue;
			}
		}
		
		/*
		 * Le document est dit "pertinent" s'il contient tous les signes de
		 * la requ�te. On affecte 1 (Vrai) si tel est le cas.
		 */
		if (matchedSign == words.size()) {
			results.add(new ValuedObject(document, 1.0));
		}
	}
    
    return results;
  }

}
