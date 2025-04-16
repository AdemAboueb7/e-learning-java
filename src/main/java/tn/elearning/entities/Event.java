package entities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class event {
    private int id,prix;
    private String titre,description,type,image;
    private Date date_debut,date_fin;

    public event() {
    }

    public event(int id, int prix, String titre, String description, String type, Date date_debut, Date date_fin, String image) {
        this.id = id;
        this.prix = prix;
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.image = image;
    }

    public event(int prix, String titre, String description, String type, Date date_debut, Date date_fin, String image) {
        this.prix = prix;
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_debut() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.of(
                date_debut.getYear() + 1900,
                date_debut.getMonth() + 1,
                date_debut.getDate()
        );
        return localDate.format(formatter);
    }

    public Date getDate_debut1() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }


    public String getDate_fin() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.of(
                date_fin.getYear() + 1900,
                date_fin.getMonth() + 1,
                date_fin.getDate()
        );
        return localDate.format(formatter);
    }

    public Date getDate_fin1() {
        return date_fin;
    }
    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", prix=" + prix +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", image='" + image + '\'' +
                '}';
    }


}
