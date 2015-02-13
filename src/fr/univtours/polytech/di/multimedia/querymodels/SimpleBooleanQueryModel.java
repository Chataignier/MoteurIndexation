package fr.univtours.polytech.di.multimedia.querymodels;

import java.util.ArrayList;
import java.util.List;

import fr.univtours.polytech.di.multimedia.database.Database;
import fr.univtours.polytech.di.multimedia.database.Document;
import fr.univtours.polytech.di.multimedia.database.InvertedIndex;
import fr.univtours.polytech.di.multimedia.database.ValuedObject;
import fr.univtours.polytech.di.multimedia.signextractors.SignExtractor;

/**
 * Classe implémentant le modèle d'interrogation booléen simple.
 * @author Sébastien Aupetit
 */
public class SimpleBooleanQueryModel extends QueryModel {

  /**
   * Le constructeur.
   * @param database la base de données
   */
  public SimpleBooleanQueryModel(final Database database) {
    super(database);
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
    
    //ensemble des docs
	for (Document document : documents) {
		
		double contient = 0;

		for (int i = 0; i < words.size(); ++i) {
			
			//On verifie si le signe est présent dans le document
			if ((this.getDatabase().getInvertedIndex().getWordOccurrences(words.get(i), document)) >= 1) {
				//on l'ajoute au compteur
				contient++;
			} else {
				continue;
			}
		}
		
		if (contient == words.size()) {
			results.add(new ValuedObject(document, 1.0));
		}
	}
    
    return results;
  }

}
