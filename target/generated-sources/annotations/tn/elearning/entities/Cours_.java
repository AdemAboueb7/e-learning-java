package tn.elearning.entities;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cours.class)
public abstract class Cours_ {

	public static volatile SingularAttribute<Cours, String> titre;
	public static volatile SingularAttribute<Cours, String> description;
	public static volatile SingularAttribute<Cours, Integer> id;
	public static volatile SingularAttribute<Cours, Chapitre> chapitre;
	public static volatile SingularAttribute<Cours, String> contenuFichier;
	public static volatile SingularAttribute<Cours, LocalDateTime> updatedAt;

	public static final String TITRE = "titre";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String CHAPITRE = "chapitre";
	public static final String CONTENU_FICHIER = "contenuFichier";
	public static final String UPDATED_AT = "updatedAt";

}

