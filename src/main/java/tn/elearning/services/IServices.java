package tn.elearning.services;

import java.util.List;
import java.sql.SQLException;

public interface IServices<T> {
    void ajouter(T t) throws SQLException;
    void supprimer(T t) throws SQLException;
    void modifier(int id, String nom) throws SQLException;
    List<T> recuperer() throws SQLException;
}












