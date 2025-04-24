package tn.elearning.entities;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> reason;
	public static volatile ListAttribute<User, Participation> participations;
	public static volatile SingularAttribute<User, String> address;
	public static volatile ListAttribute<User, Comment> comments;
	public static volatile SingularAttribute<User, String> work;
	public static volatile ListAttribute<User, String> roles;
	public static volatile SingularAttribute<User, Integer> experience;
	public static volatile SingularAttribute<User, Boolean> isActive;
	public static volatile SingularAttribute<User, String> nom;
	public static volatile SingularAttribute<User, String> matiere;
	public static volatile ListAttribute<User, Paiement> paiements;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> phoneNumber;
	public static volatile SingularAttribute<User, Module> idMatiere;
	public static volatile SingularAttribute<User, String> pref;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile ListAttribute<User, Article> articles;
	public static volatile SingularAttribute<User, String> email;

	public static final String REASON = "reason";
	public static final String PARTICIPATIONS = "participations";
	public static final String ADDRESS = "address";
	public static final String COMMENTS = "comments";
	public static final String WORK = "work";
	public static final String ROLES = "roles";
	public static final String EXPERIENCE = "experience";
	public static final String IS_ACTIVE = "isActive";
	public static final String NOM = "nom";
	public static final String MATIERE = "matiere";
	public static final String PAIEMENTS = "paiements";
	public static final String PASSWORD = "password";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String ID_MATIERE = "idMatiere";
	public static final String PREF = "pref";
	public static final String ID = "id";
	public static final String ARTICLES = "articles";
	public static final String EMAIL = "email";

}

