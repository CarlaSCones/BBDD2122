package es.ieslvareda.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Model {

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

    public List<Vehiculo> getVehiculos(){

        List<Vehiculo> vehiculoList = new ArrayList<>();
        DataSource dataSource = MyDataSource.getMyMariaDBDataSource();

        try(Connection con = dataSource.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from vehiculo")){

            String matricula;
            float preciohora;
            String modelo;
            String color;

            while(resultSet.next()){
                matricula = resultSet.getString("matricula");
                preciohora = resultSet.getFloat("preciohora");
                modelo = resultSet.getString("modelo");
                color = resultSet.getString("color");

                vehiculoList.add(new Vehiculo(matricula,preciohora,modelo,color));

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return vehiculoList;

    }

    public Vehiculo addVehiculo(Vehiculo vehiculo){

        DataSource ds = MyDataSource.getMyMariaDBDataSource();

        int bateria = 0;
        Date fechaAdq = new Date(22/02/2022);
        String estado = "a";
        int idCarnet = 0;
        Timestamp changedTS = new Timestamp(fechaAdq.getTime());
        String changedBy = "a";

        try(Connection con = ds.getConnection();
            Statement statement = con.createStatement();){
            String sql = "INSERT INTO " + "vehiculo VALUES ('" +vehiculo.getMatricula()+ "','" +  vehiculo.getPreciohora() + "','"
                    + vehiculo.getMarca()+ "','" +vehiculo.getColor() +  "','" + bateria +  "','" + fechaAdq + "','" + estado + "','"
                    + idCarnet + "','" + changedTS + "','" + changedBy + "');";
            int count = statement.executeUpdate(sql);
            System.out.println(count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculo;
    }

    public int updateVehiculo(Vehiculo vehiculo) {

        DataSource ds = MyDataSource.getMyMariaDBDataSource();
        int count = 0;

        int bateria = 0;
        Date fechaAdq = new Date(22/02/2022);
        String estado = "a";
        int idCarnet = 0;
        Timestamp changedTS = new Timestamp(fechaAdq.getTime());
        String changedBy = "a";

        String sql = "UPDATE vehiculo SET " +
                "matricula='" + vehiculo.getMatricula() + "', preciohora='" + vehiculo.getPreciohora() + "', marca='"
                + vehiculo.getMarca() + "', color='" + vehiculo.getColor() + "', bateria='" + bateria + "', fechaAdq='"
                + fechaAdq + "', estado='" + estado + "', idCarnet='" + idCarnet + "', changedTS='" + changedTS + "', changedBy='" + changedBy;
        try (Connection con = ds.getConnection();
             Statement statement = con.createStatement();) {

            count = statement.executeUpdate(sql);
            System.out.println(count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int deldeteVehiculo(String matricula){
        DataSource ds = MyDataSource.getMyMariaDBDataSource();
        int count = 0;
        String sql = "DELETE FROM vehiculo WHERE matricula LIKE ? RETURNING preciohora,marca,color";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);) {

            int pos = 0;
            pstmt.setString(++pos,matricula);
            System.out.println("Eliminado");
            return count = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    //Mariadb

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

}
