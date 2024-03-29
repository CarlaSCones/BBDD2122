package es.ieslvareda.server.model;

import es.ieslvareda.model.Coche;
import es.ieslvareda.model.MyDataSource;
import es.ieslvareda.model.Result;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImpCocheService implements CocheService {
    @Override
    public List<Coche> getAll() {

        List<Coche> cocheList = new ArrayList<>();
        DataSource dataSource = MyDataSource.getMyOracleDataSource();

        try(Connection con = dataSource.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from coche")){

            String matricula;
            int numPlazas;
            int numPuertas;

            while(resultSet.next()){
                matricula = resultSet.getString("matricula");
                numPlazas = resultSet.getInt("numPlazas");
                numPuertas = resultSet.getInt("numPuertas");
                cocheList.add(new Coche(matricula,numPlazas,numPuertas));

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cocheList;
    }

    @Override
    public Result<Coche> get(String matricula) {

        DataSource dataSource = MyDataSource.getMyOracleDataSource();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from coche where matricula='" + matricula + "'")) {

            int numPlazas;
            int numPuertas;

            if (resultSet.next()) {

                numPlazas = resultSet.getInt("numPlazas");
                numPuertas = resultSet.getInt("numPuertas");

                Coche coche = new Coche(matricula, numPlazas, numPuertas);
                return new Result.Success<>(coche);

            } else {
                return new Result.Error("No se ha encontrado la matricula " + matricula, 404);
            }


        }catch (SQLSyntaxErrorException sqlee){
            return new Result.Error("Error de acceso a la BD", 404);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result.Error(throwables.getMessage(),404);
        }

    }

    @Override
    public Result<Coche> update(Coche coche) {
        DataSource ds = MyDataSource.getMyOracleDataSource();
        String sql = "UPDATE coche SET numPlazas=?,numPuertas=? WHERE matricula LIKE ?";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setInt(++pos, coche.getNumPlazas());
            pstmt.setInt(++pos, coche.getNumPuertas());
            pstmt.setString(++pos, coche.getMatricula());
            int cant = pstmt.executeUpdate();
            if (cant == 1)
                return new Result.Success<>(coche);
            else
                return new Result.Error("Ningun coche actualizado", 404);
        } catch (Exception e) {
            return new Result.Error(e.getMessage(),404);
        }
    }

    @Override
    public Result<Coche> add(Coche coche) {
        DataSource ds = MyDataSource.getMyOracleDataSource();

        try(Connection con = ds.getConnection();
            Statement statement = con.createStatement();){
            String sql = "INSERT INTO " + "coche VALUES ('" +coche.getMatricula()+ "','"
                    +coche.getNumPlazas()+ "','" +coche.getNumPuertas()+ "')";

            int count = statement.executeUpdate(sql);
            if(count==1)
                return new Result.Success<>(coche);
            else
                return new Result.Error("Error al añadir el coche",404);

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result.Error(throwables.getMessage(),404);
        }
    }

    @Override
    public Result<Coche> delete(String matricula) {
        DataSource ds = MyDataSource.getMyOracleDataSource();
        String sql = "DELETE FROM coche WHERE matricula = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setString(++pos, matricula);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                Coche coche = new Coche(matricula, rs.getInt("numPlazas"), rs.getInt("numPuertas"));
                return new Result.Success<>(coche);
            }else
                return new Result.Error("Ningun coche eliminado",404);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(e.getMessage(),404);
        }
    }
}
