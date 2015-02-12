package fr.univtours.polytech.di.multimedia.querymodels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univtours.polytech.di.multimedia.database.Database;
import fr.univtours.polytech.di.multimedia.database.DecreasingValuedObjectComparator;
import fr.univtours.polytech.di.multimedia.database.Document;
import fr.univtours.polytech.di.multimedia.database.InvertedIndex;
import fr.univtours.polytech.di.multimedia.database.ValuedObject;
import fr.univtours.polytech.di.multimedia.signextractors.SignExtractor;

/**
 * Classe implémentant le modèle d'interrogation vectoriel.
 * @author Sébastien Aupetit
 */
public class VectorQueryModel extends QueryModel {
  /** Indique s'il faut utiliser une ponderation TF-IDF. */
  private final boolean useTFIDF;

  /**
   * Le constructeur.
   * @param database la base de données
   * @param useTFIDF indique s'il faut utiliser une ponderation TF-IDF
   */
  public VectorQueryModel(final Database database, final boolean useTFIDF) {
    super(database);
    this.useTFIDF = useTFIDF;
  }

  /**
   * {@inheritDoc}
   * @see fr.univtours.polytech.di.multimedia.querymodels.QueryModel#getAnswers(java.lang.String)
   */
  @Override
  public List < ValuedObject > getAnswers(final String question) {
    final List < ValuedObject > results = new ArrayList < ValuedObject >();
    
    int nombreDocs = getDatabase().getDocuments().size();
    
    //Extraction des mots de la question
    ArrayList<String> words = new ArrayList<String>();
    SignExtractor signExtractor = getDatabase().getSignExtractor();
    signExtractor.setContent(question);
    String word;
    while ((word = signExtractor.nextToken()) != null) {
		//application des filtres sur chaque signe extrait 
		word = getDatabase().filterSign(word);
		words.add(word);
	}
    
    
    //Liste des documents indexé : documentsIndex
    InvertedIndex invertedIndex = getDatabase().getInvertedIndex();
	List<Document> documentsIndex = new ArrayList<Document>();
	
	for (String sign : words) {
		documentsIndex.addAll(invertedIndex.getAllDocuments(sign));
	}
    
	//Pacours de chaque document
	for(Document document : documentsIndex) {
		double somme = 0.0;
		double sommeCarre = 0.0;
		double sommeWord = 0.0;
		//Parcours de chaque sign de la question
		for(String sign : words) {
			double occurence = invertedIndex.getWordOccurrences(sign, document);
			sommeWord++;
			if(occurence != 0) {
				if(useTFIDF) {
					int nbDocs = invertedIndex.getAllDocuments(sign).size();
					occurence = occurence * (nombreDocs / nbDocs);
				}
				somme += occurence;
				sommeCarre += (occurence * occurence);
			}
		}
		if(somme != 0) {
			double normes = Math.sqrt(sommeCarre) * Math.sqrt(sommeWord);
			double cos = (normes == 0 ? 0 : somme / normes);
			results.add(new ValuedObject(document, cos));
		}
		
	}
	
	Collections.sort(results, new DecreasingValuedObjectComparator());

    // TODO : A COMPLETER ICI

    return results;
  }
}
