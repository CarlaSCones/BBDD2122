package es.ieslvareda.server.model;

import es.ieslvareda.model.MyDataSource;
import es.ieslvareda.model.Person;
import es.ieslvareda.model.Result;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImpPersonService implements PersonService {

    @Override
    public List<Person> getAll() {

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

    @Override
    public Result<Person> get(String dni) {

        DataSource dataSource = MyDataSource.getMyMariaDBDataSource();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from person where dni='" + dni + "'")) {

            String nombre;
            String apellidos;
            int edad;

            if (resultSet.next()) {

                nombre = resultSet.getString("nombre");
                apellidos = resultSet.getString("apellidos");
                edad = resultSet.getInt("edad");

                Person person = new Person(dni, nombre, apellidos, edad);
                return new Result.Success<>(person);

            } else {
                return new Result.Error("No se ha encontrado el dni " + dni, 404);
            }


        }catch (SQLSyntaxErrorException sqlee){
            return new Result.Error("Error de acceso a la BD", 404);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result.Error(throwables.getMessage(),404);
        }

    }

    @Override
    public Result<Person> update(Person person) {
        DataSource ds = MyDataSource.getMyMariaDBDataSource();
        String sql = "UPDATE person SET nombre=?,apellidos=?,edad=? WHERE dni LIKE ?";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setString(++pos, person.getNombre());
            pstmt.setString(++pos, person.getApellidos());
            pstmt.setInt(++pos, person.getEdad());
            pstmt.setString(++pos, person.getDni());
            int cant = pstmt.executeUpdate();
            if (cant == 1)
                return new Result.Success<Person>(person);
            else
                return new Result.Error("Ninguna persona actualizada", 404);
        } catch (Exception e) {
            return new Result.Error(e.getMessage(),404);
        }
    }

    @Override
    public Result<Person> add(Person person) {

        DataSource ds = MyDataSource.getMyMariaDBDataSource();

        try(Connection con = ds.getConnection();
            Statement statement = con.createStatement();){
            String sql = "INSERT INTO " + "person VALUES ('" +person.getDni()+ "','"
                    +person.getNombre()+ "','" +person.getApellidos()+ "'," +person.getEdad()+ ")";

            int count = statement.executeUpdate(sql);
            if(count==1)
                return new Result.Success<>(person);
            else
                return new Result.Error("Error al añadir la persona",404);

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result.Error(throwables.getMessage(),404);
        }
    }



    @Override
    public Result<Person> delete(String dni) {
        DataSource ds = MyDataSource.getMyMariaDBDataSource();
        String sql = "DELETE FROM person WHERE dni LIKE ? RETURNING nombre,apellidos,edad";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setString(++pos, dni);
            int cant = pstmt.executeUpdate();

            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                Person person = new Person(dni, rs.getString("nombre"), rs.getString("apellidos"), rs.getInt("edad"));
                return new Result.Success<Person>(person);
            }
            return new Result.Error("Ninguna persona eliminada",404);
        } catch (Exception e) {
            return new Result.Error(e.getMessage(),404);
        }
    }

}
