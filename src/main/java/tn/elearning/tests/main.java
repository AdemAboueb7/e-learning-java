package tn.elearning.tests;

import tn.elearning.entities.Abonnement;
import tn.elearning.entities.Paiement;
import tn.elearning.entities.User;
import tn.elearning.services.ServiceAbonnement;
import tn.elearning.services.ServicePaiement;
import tn.elearning.tools.MyDataBase;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class main {
    public static void main(String[] args) {
        ServicePaiement sp = new ServicePaiement();
        LocalDateTime ldt = LocalDateTime.now();
        Abonnement a1=new Abonnement();
        a1.setId(1);
        User user1 = new User();
        user1.setId(24);
       // Paiement p1=new Paiement(1,100.0,a1,ldt,user1);
        //try {
          //  sp.ajouter(p1);
       // } catch (SQLException e) {
          //  System.out.println(e.getMessage());
       // }

       // try {
         //   sp.modifier(1,250.0);
      //  } catch (SQLException e) {
         //   System.out.println(e.getMessage());
       // }
        try {
            System.out.println(sp.recuperer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(sp.recupererParId(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ServiceAbonnement sa=new ServiceAbonnement();
        Abonnement abonnement=new Abonnement("ramadan",200.5,"abonnement ramadan","1 mois");
        try {
            sa.ajouter(abonnement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }




}
