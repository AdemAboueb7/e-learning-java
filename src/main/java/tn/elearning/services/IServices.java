package tn.elearning.services;

import java.util.List;
import java.sql.SQLException;

public interface IServices<T> {
    void ajouter(T t) throws SQLException ;
    void supprimerParId(int id);
    void supprimer(T t) throws SQLException ;
    void modifier(int id) throws SQLException ;
    List<T> recuperer();
    T recupererParId(int id);  
    List<T> getAll() throws SQLException;

}


