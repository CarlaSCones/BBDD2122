package es.ieslvareda.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {

    public List<Person> getPersons(){

        List<Person> personList = new ArrayList<>();
        DataSource dataSource = MyDataSource.getMyMariaDBDataSource();

        try(Connection con = dataSource.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from person")){

            String dni;
            String nombre;
            String apellidos;
            int edad;

            while(resultSet.next()){
                dni = resultSet.getString("dni");
                nombre = resultSet.getString("nombre");
                apellidos = resultSet.getString("apellidos");
                edad = resultSet.getInt("edad");

                personList.add(new Person(dni,nombre,apellidos,edad));

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return personList;

    }

    public List<Empleado> getEmpleados(){

        List<Empleado> empleados = new ArrayList<>();
        try(Connection con = MyDataSource.getMyOracleDataSource().getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT NOMBRE FROM EMPLEADO")) {

            int pos;
            while (rs.next()){
                empleados.add(new Empleado(rs.getString(1)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return empleados;
    }

    public Person addPerson(Person person){
        DataSource ds = MyDataSource.getMyMariaDBDataSource();

        try(Connection con = ds.getConnection();
            Statement statement = con.createStatement();){
            String sql = "INSERT INTO " + "person VALUES ('" +person.getDni()+ "','" +  person.getNombre()+ "','"
                    + person.getApellidos()+ "','" +person.getEdad() + "');";
            int count = statement.executeUpdate(sql);
            System.out.println(count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

     public int updatePerson(Person person) {
        DataSource ds = MyDataSource.getMyMariaDBDataSource();
        int count = 0;
         String sql = "UPDATE person SET " +
                 "dni='" + person.getDni() + "', nombre='" + person.getNombre() + "', apellidos='"
                 + person.getApellidos() + "', edad='" + person.getEdad();
        try (Connection con = ds.getConnection();
             Statement statement = con.createStatement();) {

            count = statement.executeUpdate(sql);
            System.out.println(count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int deldetePerson(String dni){
        DataSource ds = MyDataSource.getMyMariaDBDataSource();
        int count = 0;
        String sql = "DELETE FROM person WHERE dni LIKE ? RETURNING nombre,apellidos,edad";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);) {

            int pos = 0;
            pstmt.setString(++pos,dni);
            System.out.println("Eliminado");
            return count = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    //Oracle

    public boolean authenticate(String email, String password){
        boolean auth = false;
        DataSource ds = MyDataSource.getMyMariaDBDataSource();
        String sql = "SELECT COUNT(*) FROM EMPLEADO WHERE" + "EMAIL='" + email + "'AND " + "PASSWORD=ENCRYPT_PASWD.encrypt_val('"
                +password+"')";
        try(Connection con = ds.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql)){

                rs.next();
                int count = rs.getInt(1);
                auth= (count==0)?false:true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return auth;
    }
}
