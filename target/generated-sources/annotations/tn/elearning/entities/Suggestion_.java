package tn.elearning.entities;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Suggestion.class)
public abstract class Suggestion_ {

	public static volatile SingularAttribute<Suggestion, question> question;
	public static volatile SingularAttribute<Suggestion, Boolean> estCorrecte;
	public static volatile SingularAttribute<Suggestion, Integer> id;
	public static volatile SingularAttribute<Suggestion, String> contenu;

	public static final String QUESTION = "question";
	public static final String EST_CORRECTE = "estCorrecte";
	public static final String ID = "id";
	public static final String CONTENU = "contenu";

}

