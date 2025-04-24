package tn.elearning.entities;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Participation.class)
public abstract class Participation_ {

	public static volatile SingularAttribute<Participation, LocalDateTime> created_at;
	public static volatile SingularAttribute<Participation, Integer> id;
	public static volatile SingularAttribute<Participation, Event> event;
	public static volatile SingularAttribute<Participation, User> user;

	public static final String CREATED_AT = "created_at";
	public static final String ID = "id";
	public static final String EVENT = "event";
	public static final String USER = "user";

}

