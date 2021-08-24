/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thomas
 */
public class DataMapper {

    public static void createPerson(Person p) {

        try {
            Connection con = DBconnector.connection();
            String SQL = "INSERT INTO Person (name, age) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getAge());
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            p.setId(id);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    public static List<Person> getAllPersons() {
        List<Person> persons = new ArrayList();
        try {
            Connection con = DBconnector.connection();
            String SQL = "SELECT * FROM Person";
            PreparedStatement ps = con.prepareStatement(SQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                persons.add(new Person(id, name, age));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return persons;
    }

    public static void main(String[] args) {
        createPerson(new Person("Hansen", 22));
        List<Person> persons = getAllPersons();
        for (Person person : persons) {
            System.out.println(person);
        }
    }
}
