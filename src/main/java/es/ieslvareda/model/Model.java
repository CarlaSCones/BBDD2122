package es.ieslvareda.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {

    //Exponer dia 10
    //Oracle

    public boolean authenticate(String email, String password){
        boolean auth = false;
        DataSource ds = MyDataSource.getMyMariaDBDataSource();
        String sql = "SELECT COUNT(*) FROM EMPLEADO WHERE" + "EMAIL='" + email
                + "'AND " + "PASSWORD=ENCRYPT_PASWD.encrypt_val('"
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

    //Tabla Vehiculo
    public List<Vehiculo> getVehiculos(){

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

    public Vehiculo addVehiculo(Vehiculo vehiculo){

        DataSource ds = MyDataSource.getMyOracleDataSource();

        try(Connection con = ds.getConnection();
            Statement statement = con.createStatement();){
            String sql = "INSERT INTO " + "vehiculo VALUES ('" +vehiculo.getMatricula()+ "','" +  vehiculo.getPreciohora() + "','"
                    + vehiculo.getMarca()+ "','" +vehiculo.getDescripcion() +"','" +vehiculo.getColor() +  "','" + vehiculo.getBateria() + "','" + vehiculo.getFechaadq()
                    + "','" +vehiculo.getEstado() + "','" + vehiculo.getIdCarnet() +  "','" + vehiculo.getChangedTS() +
                    "','" + vehiculo.getChangedBy() + "');";
            int count = statement.executeUpdate(sql);
            System.out.println(count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculo;
    }

    public int updateVehiculo(Vehiculo vehiculo) {

        DataSource ds = MyDataSource.getMyOracleDataSource();
        int count = 0;

        String sql = "UPDATE vehiculo SET " +
                "matricula='" + vehiculo.getMatricula() + "', preciohora='" + vehiculo.getPreciohora() + "', marca='"
                + vehiculo.getMarca() + "', descripcion='" + vehiculo.getDescripcion() + "', color='" + vehiculo.getColor() + "', bateria='" + vehiculo.getBateria() + "', fechaAdq='"
                + vehiculo.getFechaadq() + "', estado='" + vehiculo.getEstado() + "', idCarnet='" + vehiculo.getIdCarnet() + "', changedTS='" + vehiculo.getChangedTS() + "', changedBy='" + vehiculo.getChangedBy();
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
        DataSource ds = MyDataSource.getMyOracleDataSource();
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

    //Tabla Coche

    public List<Coche> getCoches(){

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

    public Coche addCoche(Coche coche){
        DataSource ds = MyDataSource.getMyOracleDataSource();

        try(Connection con = ds.getConnection();
            Statement statement = con.createStatement();){
            String sql = "INSERT INTO " + "coche VALUES ('" +coche.getMatricula()+ "','" +  coche.getNumPlazas()+ "','"
                    + coche.getNumPuertas()+ "');";
            int count = statement.executeUpdate(sql);
            System.out.println(count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coche;
    }

    public int updateCoche(Coche coche) {
        DataSource ds = MyDataSource.getMyOracleDataSource();
        int count = 0;
        String sql = "UPDATE coche SET " +
                "matricula='" + coche.getMatricula() + "', numPlazas='" + coche.getNumPlazas() + "', numPuertas='" + coche.getNumPuertas();
        try (Connection con = ds.getConnection();
             Statement statement = con.createStatement();) {

            count = statement.executeUpdate(sql);
            System.out.println(count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int deldeteCoche(String matricula){
        DataSource ds = MyDataSource.getMyOracleDataSource();
        int count = 0;
        String sql = "DELETE FROM coche WHERE matricula LIKE ? RETURNING numPlazas,numPuertas";
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

}
