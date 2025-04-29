package tn.elearning.services;

import tn.elearning.entities.matiereprof;
import tn.elearning.entities.matiereprof;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMatiereProf {

    private final Connection cnx;

    public ServiceMatiereProf() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public List<matiereprof> getAll() {
        List<matiereprof> matieres = new ArrayList<>();
        String sql = "SELECT * FROM matiereprof";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                matiereprof matiere = new matiereprof();
                matiere.setId(rs.getInt("idmatiereprof"));
                matiere.setNom(rs.getString("nommatiereprof"));
                matieres.add(matiere);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des matières : " + e.getMessage());
        }

        return matieres;
    }
    public List<String> getAllMatieres() throws SQLException {
        List<String> matieres = new ArrayList<>();
        String sql = "SELECT nom FROM matiereprof";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            matieres.add(rs.getString("nom"));
        }

        return matieres;
    }
}
