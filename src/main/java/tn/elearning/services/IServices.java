package tn.elearning.services;

import java.sql.SQLException;
import java.util.List;

public interface IServices<T> {
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(T t) throws SQLException;

    void modifier(int id, String nom) throws SQLException;

    List<T> recuperer() throws SQLException;
    T recupererParId(int id) throws SQLException;
}
