package tn.elearning.entities;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(question.class)
public abstract class question_ {

	public static volatile SingularAttribute<question, String> question;
	public static volatile SingularAttribute<question, quizz> quizz;
	public static volatile SingularAttribute<question, Integer> id;

	public static final String QUESTION = "question";
	public static final String QUIZZ = "quizz";
	public static final String ID = "id";

}

