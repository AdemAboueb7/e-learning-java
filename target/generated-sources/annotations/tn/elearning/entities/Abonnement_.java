package tn.elearning.entities;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Abonnement.class)
public abstract class Abonnement_ {

	public static volatile ListAttribute<Abonnement, Paiement> paiements;
	public static volatile SingularAttribute<Abonnement, Double> prix;
	public static volatile SingularAttribute<Abonnement, String> description;
	public static volatile SingularAttribute<Abonnement, Integer> duree;
	public static volatile SingularAttribute<Abonnement, Integer> id;
	public static volatile SingularAttribute<Abonnement, String> type;

	public static final String PAIEMENTS = "paiements";
	public static final String PRIX = "prix";
	public static final String DESCRIPTION = "description";
	public static final String DUREE = "duree";
	public static final String ID = "id";
	public static final String TYPE = "type";

}

