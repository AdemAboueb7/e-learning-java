package tn.elearning.entities;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Paiement.class)
public abstract class Paiement_ {

	public static volatile SingularAttribute<Paiement, LocalDateTime> date_paiement;
	public static volatile SingularAttribute<Paiement, Abonnement> abonnement;
	public static volatile SingularAttribute<Paiement, Double> montant;
	public static volatile SingularAttribute<Paiement, Integer> id;
	public static volatile SingularAttribute<Paiement, User> user;
	public static volatile SingularAttribute<Paiement, String> stripe_session_id;
	public static volatile SingularAttribute<Paiement, String> status;

	public static final String DATE_PAIEMENT = "date_paiement";
	public static final String ABONNEMENT = "abonnement";
	public static final String MONTANT = "montant";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String STRIPE_SESSION_ID = "stripe_session_id";
	public static final String STATUS = "status";

}

