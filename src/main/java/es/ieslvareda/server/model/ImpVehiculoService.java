package es.ieslvareda.server.model;

import es.ieslvareda.model.MyDataSource;
import es.ieslvareda.model.Person;
import es.ieslvareda.model.Result;
import es.ieslvareda.model.Vehiculo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImpVehiculoService implements VehiculoService{

    //Descargar oracle18 txd para conectarme desde casa
    @Override
    public List<Vehiculo> getAll() {

        List<Vehiculo> vehiculoList = new ArrayList<>();
        DataSource dataSource = MyDataSource.getMyOracleDataSource();

        try(Connection con = dataSource.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from vehiculo")){

            String matricula;
            int preciohora;
            String marca;
            String descripcion;
            String color;
            int bateria;
            java.sql.Date fechaadq;
            String estado;
            int idCarnet;
            Date changedTS;
            String changedBy;

            while(resultSet.next()){
                matricula = resultSet.getString("matricula");
                preciohora = resultSet.getInt("preciohora");
                marca = resultSet.getString("marca");
                descripcion = resultSet.getString("descripcion");
                color = resultSet.getString("color");
                bateria = resultSet.getInt("bateria");
                fechaadq = resultSet.getDate("fechaadq");
                estado = resultSet.getString("estado");
                idCarnet = resultSet.getInt("idCarnet");
                changedTS = resultSet.getDate("changedTS");
                changedBy = resultSet.getString("changedBy");

                vehiculoList.add(new Vehiculo(matricula,preciohora,marca,descripcion,color,bateria,fechaadq,estado,idCarnet,changedTS,changedBy));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vehiculoList;
    }

    @Override
    public Result<Vehiculo> get(String matricula) {
        DataSource dataSource = MyDataSource.getMyOracleDataSource();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from vehiculo where matricula='" + matricula + "'")) {

            int preciohora;
            String marca;
            String descripcion;
            String color;
            int bateria;
            java.sql.Date fechaadq;
            String estado;
            int idCarnet;
            Date changedTS;
            String changedBy;

            if (resultSet.next()) {

                preciohora = resultSet.getInt("preciohora");
                marca = resultSet.getString("marca");
                descripcion = resultSet.getString("descripcion");
                color = resultSet.getString("color");
                bateria = resultSet.getInt("bateria");
                fechaadq = resultSet.getDate("fechaadq");
                estado = resultSet.getString("estado");
                idCarnet = resultSet.getInt("idCarnet");
                changedTS = resultSet.getDate("changedTS");
                changedBy = resultSet.getString("changedBy");

                Vehiculo vehiculo = new Vehiculo(matricula,preciohora,marca,descripcion,color,bateria,fechaadq,estado,idCarnet,changedTS,changedBy);
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

        DataSource ds = MyDataSource.getMyOracleDataSource();

        String sql = "UPDATE vehiculo SET preciohora=?,marca=?,descripcion=?,color=?,bateria=?,fechaAdq=?,estado=?,idCarnet=?,changedTS=?,changedBy=? WHERE matricula LIKE ?";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setFloat(++pos, vehiculo.getPreciohora());
            pstmt.setString(++pos, vehiculo.getMarca());
            pstmt.setString(++pos, vehiculo.getDescripcion());
            pstmt.setString(++pos, vehiculo.getColor());
            pstmt.setInt(++pos, vehiculo.getBateria());
            pstmt.setDate(++pos, vehiculo.getFechaadq());
            pstmt.setString(++pos, vehiculo.getEstado());
            pstmt.setInt(++pos, vehiculo.getIdCarnet());
            pstmt.setDate(++pos, vehiculo.getChangedTS());
            pstmt.setString(++pos, vehiculo.getChangedBy());
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
        DataSource ds = MyDataSource.getMyOracleDataSource();

        try(Connection con = ds.getConnection();
            Statement statement = con.createStatement();){
            String sql = "INSERT INTO " + "vehiculo VALUES ('" +vehiculo.getMatricula()+ "','"
                    +vehiculo.getPreciohora()+ "','" +vehiculo.getMarca()+ "','" +vehiculo.getDescripcion()+ "','" +vehiculo.getColor()+
                    "','" +vehiculo.getBateria()+ "',to_date ('"+vehiculo.getFechaadq()+"','yyyy/mm/dd'),'" +vehiculo.getEstado()+
                    "','" +vehiculo.getIdCarnet()+  "',to_date ('" +vehiculo.getChangedTS()+
                    "','yyyy/mm/dd'),'" +vehiculo.getChangedBy()+ "')";

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
        DataSource ds = MyDataSource.getMyOracleDataSource();
        String sql = "DELETE FROM vehiculo WHERE matricula LIKE ? RETURNING preciohora,marca,color,bateria,fechaadq,estado,idCarnet,changedBy";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setString(++pos, matricula);
            int cant = pstmt.executeUpdate();

            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                Vehiculo vehiculo = new Vehiculo(matricula, rs.getInt("preciohora"), rs.getString("marca"),rs.getString("descripcion"),
                        rs.getString("color"), rs.getInt("bateria"), rs.getDate("fechaadq"), rs.getString("estado"),
                        rs.getInt("idCarnet"), rs.getDate("changedTS"), rs.getString("changedBy"));
                return new Result.Success<Vehiculo>(vehiculo);
            }
            return new Result.Error("Ningun vehiculo eliminado",404);
        } catch (Exception e) {
            return new Result.Error(e.getMessage(),404);
        }
    }
}
