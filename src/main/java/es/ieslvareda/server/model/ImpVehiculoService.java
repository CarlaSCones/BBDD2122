package es.ieslvareda.server.model;

import es.ieslvareda.model.MyDataSource;
import es.ieslvareda.model.Person;
import es.ieslvareda.model.Result;
import es.ieslvareda.model.Vehiculo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpVehiculoService implements VehiculoService{
    @Override
    public List<Vehiculo> getAll() {

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

    @Override
    public Result<Vehiculo> get(String matricula) {
        DataSource dataSource = MyDataSource.getMyMariaDBDataSource();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from person where matricula='" + matricula + "'")) {

            float preciohora;
            String modelo;
            String color;

            if (resultSet.next()) {

                preciohora = resultSet.getFloat("preciohora");
                modelo = resultSet.getString("modelo");
                color = resultSet.getString("color");

                Vehiculo vehiculo = new Vehiculo(matricula, preciohora, modelo, color);
                return new Result.Success<>(vehiculo);

            } else {
                return new Result.Error("No se ha encontrado el matricula " + matricula, 404);
            }


        }catch (SQLSyntaxErrorException sqlee){
            return new Result.Error("Error de acceso a la BD", 404);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result.Error(throwables.getMessage(),404);
        }
    }

    @Override
    public Result<Vehiculo> update(Vehiculo vehiculo) {

        DataSource ds = MyDataSource.getMyMariaDBDataSource();

        int bateria = 0;
        Date fechaAdq = new Date(22/02/2022);
        String estado = "a";
        int idCarnet = 0;
        Timestamp changedTS = new Timestamp(fechaAdq.getTime());
        String changedBy = "a";

        String sql = "UPDATE vehiculo SET preciohora=?,marca=?,color=?,bateria=?,fechaAdq=?,estado=?,idCarnet=?,changedTS=?,changedBy=? WHERE matricula LIKE ?";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setFloat(++pos, vehiculo.getPreciohora());
            pstmt.setString(++pos, vehiculo.getMarca());
            pstmt.setString(++pos, vehiculo.getColor());
            pstmt.setInt(++pos, bateria);
            pstmt.setDate(++pos, (java.sql.Date) fechaAdq);
            pstmt.setString(++pos, estado);
            pstmt.setInt(++pos, idCarnet);
            pstmt.setTimestamp(++pos, changedTS);
            pstmt.setString(++pos, changedBy);
            pstmt.setString(++pos, vehiculo.getMatricula());
            int cant = pstmt.executeUpdate();
            if (cant == 1)
                return new Result.Success<Vehiculo>(vehiculo);
            else
                return new Result.Error("Ningun vehiculo actualizado", 404);
        } catch (Exception e) {
            return new Result.Error(e.getMessage(),404);
        }
    }

    @Override
    public Result<Vehiculo> add(Vehiculo vehiculo) {
        DataSource ds = MyDataSource.getMyMariaDBDataSource();

        int bateria = 0;
        Date fechaAdq = new Date(22/02/2022);
        String estado = "a";
        int idCarnet = 0;
        Timestamp changedTS = new Timestamp(fechaAdq.getTime());
        String changedBy = "a";

        try(Connection con = ds.getConnection();
            Statement statement = con.createStatement();){
            String sql = "INSERT INTO " + "vehiculo VALUES ('" +vehiculo.getMatricula()+ "','"
                    +vehiculo.getPreciohora()+ "','" +vehiculo.getMarca()+ "'," +vehiculo.getColor()+
                    "'," +bateria+ "'," +fechaAdq+ "'," +estado+ "'," +idCarnet+  "'," +changedTS+
                    "'," +changedBy+ ")";

            int count = statement.executeUpdate(sql);
            if(count==1)
                return new Result.Success<>(vehiculo);
            else
                return new Result.Error("Error al añadir el vehiculo",404);

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result.Error(throwables.getMessage(),404);
        }
    }

    @Override
    public Result<Vehiculo> delete(String matricula) {
        DataSource ds = MyDataSource.getMyMariaDBDataSource();
        String sql = "DELETE FROM vehiculo WHERE matricula LIKE ? RETURNING preciohora,marca,color";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setString(++pos, matricula);
            int cant = pstmt.executeUpdate();

            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                Vehiculo vehiculo = new Vehiculo(matricula, rs.getFloat("preciohora"), rs.getString("marca"), rs.getString("color"));
                return new Result.Success<Vehiculo>(vehiculo);
            }
            return new Result.Error("Ningun vehiculo eliminado",404);
        } catch (Exception e) {
            return new Result.Error(e.getMessage(),404);
        }
    }
}
