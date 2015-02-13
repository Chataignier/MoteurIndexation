package fr.univtours.polytech.di.multimedia.querymodels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.runtime.tree.Tree;

import fr.univtours.polytech.di.multimedia.database.Database;
import fr.univtours.polytech.di.multimedia.database.DecreasingValuedObjectComparator;
import fr.univtours.polytech.di.multimedia.database.Document;
import fr.univtours.polytech.di.multimedia.database.InvertedIndex;
import fr.univtours.polytech.di.multimedia.database.ValuedObject;
import fr.univtours.polytech.di.multimedia.parser.BooleanExpressionParser;
import fr.univtours.polytech.di.multimedia.parser.ExpressionParser;

/**
 * Classe implémentant le modèle d'interrogation booléen pondéré.
 * @author Sébastien Aupetit
 */
public class WeightedBooleanQueryModel extends QueryModel {

  /** Indique s'il faut utiliser une pondération TF-IDF. */
  private final boolean useTFIDF;

  /**
   * Le constructeur.
   * @param database la base de données
   * @param useTFIDF indique s'il faut utiliser une pondération TF-IDF
   */
  public WeightedBooleanQueryModel(final Database database,
      final boolean useTFIDF) {
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

    //Declaration de l'arbre
    final Tree tree = ExpressionParser.parseQuery(question);
    
    //Declaration de la liste des documents et parcours des documents
    List<Document> docs = getDatabase().getDocuments();
	for (int i = 0; i < this.getDatabase().getDocuments().size(); i++) {
		//Calcul du resultat
		double result = parcoursArbre(docs.get(i), tree);
		if (result != 0) {
			results.add(new ValuedObject(docs.get(i), result));
		}
	}
	
	Collections.sort(results, new DecreasingValuedObjectComparator());
    return results;
  }
  
  /**
   * Parcours de l'arbre en recurcif et calcul du result
   * @param document
   * @param arbre
   * @return
   */
  private double parcoursArbre(Document document, Tree arbre) {
	  double result = 0;
	  switch (arbre.getType()) 
	  {
	  		case BooleanExpressionParser.OR:
	  			result = parcoursArbre(document, arbre.getChild(0))
					+ parcoursArbre(document, arbre.getChild(1));
				break;
				
			case BooleanExpressionParser.AND:
				result = parcoursArbre(document, arbre.getChild(0))
						* parcoursArbre(document, arbre.getChild(1));
				break;
				
			case BooleanExpressionParser.NOT:
				result = -parcoursArbre(document, arbre.getChild(0));
				break;
				
			case BooleanExpressionParser.SIGN:
				String sign = arbre.getText();
				InvertedIndex invertedIndex = getDatabase().getInvertedIndex();
				result = invertedIndex.getWordOccurrences(sign, document);
				if (useTFIDF) {
					int nb = invertedIndex.getAllDocuments(sign).size();
					result = result * (this.getDatabase().getDocuments().size() / nb);
				}
				break;
			
			default:
				break;
		}
	  	return result;
  }
}
