package tn.elearning.entities;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Chapitre.class)
public abstract class Chapitre_ {

	public static volatile SingularAttribute<Chapitre, Module> module;
	public static volatile SingularAttribute<Chapitre, String> description;
	public static volatile SingularAttribute<Chapitre, Integer> id;
	public static volatile SingularAttribute<Chapitre, String> nom;
	public static volatile ListAttribute<Chapitre, Cours> cours;

	public static final String MODULE = "module";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String COURS = "cours";

}

