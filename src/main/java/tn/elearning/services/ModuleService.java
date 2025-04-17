package tn.elearning.services;

import tn.elearning.entities.Chapitre;
import tn.elearning.entities.Module;
import tn.elearning.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleService implements IServices<Module> {
    private Connection cnx = MyDataBase.getInstance().getCnx();
    @Override
    public void ajouter(Module module) throws SQLException {
        String sql = "INSERT INTO module(nom, description) VALUES (?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, module.getNom());
        ps.setString(2, module.getDescription());
        ps.executeUpdate();
    }

    @Override
    public void modifier(Module module) throws SQLException {
        String sql = "UPDATE module SET nom = ?, description = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, module.getNom());
        ps.setString(2, module.getDescription());
        ps.setInt(3, module.getId());
        ps.executeUpdate();
    }


    public void supprimer(Module module) throws SQLException {
        String sql = "DELETE FROM module WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, module.getId());
        ps.executeUpdate();
    }

    @Override
    public void modifier(int id, String nom) throws SQLException {

    }

    @Override
    public List<Module> recuperer() throws SQLException {
        String sql = "SELECT * FROM module";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Module> modules = new ArrayList<>();
        while (rs.next()) {
            Module m = new Module();
            m.setId(rs.getInt("id"));
            m.setNom(rs.getString("nom"));
            m.setDescription(rs.getString("description"));
            modules.add(m);
        }
        return modules;
    }

    @Override
    public Module recupererParId(int id) throws SQLException {
        String sql = "SELECT * FROM module WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Module m = new Module();
            m.setId(rs.getInt("id"));
            m.setNom(rs.getString("nom"));
            m.setDescription(rs.getString("description"));
            return m;
        }
        return null;
    }
    public void ajouterModule(String nom, String description) {
        // Logique d'ajout du module
        // Par exemple, ajouter le module dans une base de données
        System.out.println("Module ajouté : " + nom + ", Description : " + description);

        // Si tu utilises une base de données, tu peux faire une requête SQL ici
        // Exemple :
        // String query = "INSERT INTO modules (nom, description) VALUES (?, ?)";
        // Exécuter cette requête via JDBC
    }
}
