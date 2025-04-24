package tn.elearning.entities;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Module.class)
public abstract class Module_ {

	public static volatile SingularAttribute<Module, String> description;
	public static volatile SingularAttribute<Module, Integer> id;
	public static volatile ListAttribute<Module, Chapitre> chapitres;
	public static volatile SingularAttribute<Module, String> nom;
	public static volatile ListAttribute<Module, User> users;

	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String CHAPITRES = "chapitres";
	public static final String NOM = "nom";
	public static final String USERS = "users";

}

