package fr.univtours.polytech.di.multimedia.querymodels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univtours.polytech.di.multimedia.database.Database;
import fr.univtours.polytech.di.multimedia.database.DecreasingValuedObjectComparator;
import fr.univtours.polytech.di.multimedia.database.Document;
import fr.univtours.polytech.di.multimedia.database.InvertedIndex;
import fr.univtours.polytech.di.multimedia.database.ValuedObject;
import fr.univtours.polytech.di.multimedia.parser.ExpressionParser;
import fr.univtours.polytech.di.multimedia.signextractors.SignExtractor;

import org.antlr.runtime.tree.Tree;

/**
 * Classe impl�mentant le mod�le d'interrogation vectoriel.
 * @author S�bastien Aupetit
 */
public class VectorQueryModel extends QueryModel {
  /** Indique s'il faut utiliser une ponderation TF-IDF. */
  private final boolean useTFIDF;

  /**
   * Le constructeur.
   * @param database la base de donn�es
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
    
    
    int nombreDocs = getDatabase().getDocuments().size(); //Nombre de documents
    String sign; //sign temporaire
    Double docScore; //score temporaire
    ValuedObject valuedObject;
    ArrayList<String> words = new ArrayList<String>(); //Liste de mots de la question
    InvertedIndex invertedIndex = getDatabase().getInvertedIndex(); // index invers�
    
    //Extraction des mots
    SignExtractor signExtractor = getDatabase().getSignExtractor();
    signExtractor.setContent(question);
    while ((sign = signExtractor.nextToken()) != null) 
    {
    	//Ajout avec application du filtre
    	words.add(getDatabase().filterSign(sign));
	}
    
    //Liste des documents index� : documentsIndex
	List<Document> documentsIndex = new ArrayList<Document>();
	for (String word : words) {
		documentsIndex.addAll(invertedIndex.getAllDocuments(word));
	}
	
	//Attention au negation supprimer les mot avec -
	//Pacours de chaque document
	for(Document document : documentsIndex) {
		
		if(this.useTFIDF) {
			docScore =  getScoreTDIDF(document, words); //calcul score avec TDIDF
		} else {
			docScore = getScoreNotTDIDF(document, words); //Calcul score sans TDIDF
		}
		
		//Ajout du score
		if(docScore != 0.0) {
			valuedObject = new ValuedObject(document, docScore);
			results.add(valuedObject);
		}
	}
	
	//Tri des r�ponses
	Collections.sort(results, new DecreasingValuedObjectComparator());

    return results;
  
	}
  
  /**
   * 
   * @param document
   * @param words
   * @return
   */
  private double getScoreTDIDF(Document document, ArrayList<String> words) {
	  
	  	double internalWeight = 0;
	  	double externalWeight = 0; 
	  	double result = 0;
		double nbTotalDocs = this.getDatabase().getDocuments().size();
		
		//Parcours des signes
		for (int i = 0; i < words.size(); ++i) {

			//Si le document n'apparait pas : log 0 impossible)
			if (!((internalWeight = this.getDatabase().getInvertedIndex().getWordOccurrences(words.get(i), document)) == 0)) {
				
				// Calcule du poids interne
				internalWeight = 1 + Math.log(internalWeight);
			}

			//Calcule du poids externe
			externalWeight = Math.log(nbTotalDocs / this.getDatabase().getInvertedIndex().getOverallWordOccurrences(words.get(i)));

			// On somme le r�sultat du tf-idf
			result += (internalWeight * externalWeight);
		}
		
		// On retourne le r�sultat que s'il est positif sinon 0
		if(Double.isNaN(result) || result <= 0) {
			return 0.;
		}
		
		return result;  
  }
  
  /**
   * 
   * @param document
   * @param words
   * @return
   */
  private double getScoreNotTDIDF(Document document, ArrayList<String> words) {
	  	
	  	double cosinus = 0;
		double sommeOccurence = 0;
		double norme;
		double squareSomme = 0;
		
	  	//Parcours des signes
		for (int i = 0; i < words.size(); ++i) {
			 //Somme des occurrences des signes dans le document
			sommeOccurence += this.getDatabase().getInvertedIndex().getWordOccurrences(words.get(i), document);
			
			// Num�rateur au carr�, utile pour le d�nominateur dans la formule du cosinus
			squareSomme += Math.pow(sommeOccurence, 2);
					
		}
		
		//Cosinus
		norme = (Math.sqrt(words.size()) * Math.sqrt(squareSomme));
		if(norme == 0) {
			return 0.0;
		}
			
		return sommeOccurence / norme;
  }
}
