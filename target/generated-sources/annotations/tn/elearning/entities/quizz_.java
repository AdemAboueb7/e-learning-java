package tn.elearning.entities;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(quizz.class)
public abstract class quizz_ {

	public static volatile SingularAttribute<quizz, String> bestg;
	public static volatile ListAttribute<quizz, question> questions;
	public static volatile SingularAttribute<quizz, String> best;
	public static volatile SingularAttribute<quizz, Integer> id;
	public static volatile SingularAttribute<quizz, Integer> chapitre;
	public static volatile SingularAttribute<quizz, Integer> difficulte;
	public static volatile SingularAttribute<quizz, String> etat;
	public static volatile SingularAttribute<quizz, String> matiere;
	public static volatile SingularAttribute<quizz, String> gain;

	public static final String BESTG = "bestg";
	public static final String QUESTIONS = "questions";
	public static final String BEST = "best";
	public static final String ID = "id";
	public static final String CHAPITRE = "chapitre";
	public static final String DIFFICULTE = "difficulte";
	public static final String ETAT = "etat";
	public static final String MATIERE = "matiere";
	public static final String GAIN = "gain";

}

