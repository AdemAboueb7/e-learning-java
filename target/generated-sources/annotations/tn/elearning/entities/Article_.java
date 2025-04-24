package tn.elearning.entities;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Article.class)
public abstract class Article_ {

	public static volatile SingularAttribute<Article, LocalDateTime> createdAt;
	public static volatile SingularAttribute<Article, String> image;
	public static volatile ListAttribute<Article, Comment> comments;
	public static volatile SingularAttribute<Article, String> titre;
	public static volatile SingularAttribute<Article, Integer> id;
	public static volatile SingularAttribute<Article, String> contenu;
	public static volatile SingularAttribute<Article, Integer> userId;
	public static volatile SingularAttribute<Article, LocalDateTime> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String IMAGE = "image";
	public static final String COMMENTS = "comments";
	public static final String TITRE = "titre";
	public static final String ID = "id";
	public static final String CONTENU = "contenu";
	public static final String USER_ID = "userId";
	public static final String UPDATED_AT = "updatedAt";

}

