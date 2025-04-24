package tn.elearning.entities;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Event.class)
public abstract class Event_ {

	public static volatile SingularAttribute<Event, String> image;
	public static volatile ListAttribute<Event, Participation> participations;
	public static volatile SingularAttribute<Event, LocalDateTime> dateDebut;
	public static volatile SingularAttribute<Event, Double> prix;
	public static volatile SingularAttribute<Event, String> titre;
	public static volatile SingularAttribute<Event, String> localisation;
	public static volatile SingularAttribute<Event, String> description;
	public static volatile SingularAttribute<Event, Integer> id;
	public static volatile SingularAttribute<Event, LocalDateTime> dateFin;
	public static volatile SingularAttribute<Event, String> type;

	public static final String IMAGE = "image";
	public static final String PARTICIPATIONS = "participations";
	public static final String DATE_DEBUT = "dateDebut";
	public static final String PRIX = "prix";
	public static final String TITRE = "titre";
	public static final String LOCALISATION = "localisation";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String DATE_FIN = "dateFin";
	public static final String TYPE = "type";

}

