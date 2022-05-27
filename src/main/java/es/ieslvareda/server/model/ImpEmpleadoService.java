package es.ieslvareda.server.model;

import es.ieslvareda.model.AuthenticateData;
import es.ieslvareda.model.Empleado;
import es.ieslvareda.model.MyDataSource;
import es.ieslvareda.model.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImpEmpleadoService implements EmpleadoService {
    @Override
    public Result<Empleado> authenticate(AuthenticateData authenticateData) {

        String email = authenticateData.getEmail();
        String passwd = authenticateData.getPasswd();
        String sql = "SELECT * FROM EMPLEADO WHERE " +
                "EMAIL=? AND PASSWORD=ENCRYPT_PASWD.encrypt_val(?)";

        try(Connection con = MyDataSource.getMyOracleDataSource().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            int pos = 0;
            pstmt.setString(++pos,email);
            pstmt.setString(++pos,passwd);

            ResultSet rs = pstmt.executeQuery();

            String nombre;
            String apellidos;
            String dni;

            if(rs.next()){
                nombre = rs.getString("NOMBRE");
                apellidos = rs.getString("APELLIDOS");
                dni = rs.getString("DNI");

                Empleado e = new Empleado(nombre, apellidos, dni, email);
                return new Result.Success<>(e);

            } else
                return new Result.Error("Email y/o password incorrectos.", 404);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Result.Error(throwables.getMessage(), 404);
        }


    }
}
