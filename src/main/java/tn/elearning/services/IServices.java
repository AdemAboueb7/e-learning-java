package tn.elearning.services;

import java.util.List;

public interface IServices<T> {
    void ajouter(T t);
    void supprimerParId(int id);
    void supprimer(T t);
    void modifier(int id);
    List<T> recuperer();
    T recupererParId(int id);
}
