package tn.elearning.tests;

import tn.elearning.entities.User;
import java.util.Arrays;
import java.util.List;
import tn.elearning.services.ServiceUser;
import tn.elearning.tools.MyDataBase;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        ServiceUser su=new ServiceUser();
        List<String> roles = Arrays.asList("USER", "ADMIN");
        User user = new User(3, "hello","zizoo@gmail.com", "122", "aaa", "français", 2, "reason", "xork", "address", "pref", roles);

        try {
            su.ajouter(user);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
