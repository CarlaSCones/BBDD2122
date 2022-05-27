package es.ieslvareda.server.model;

import es.ieslvareda.model.AuthenticateData;
import es.ieslvareda.model.Empleado;
import es.ieslvareda.model.Result;

public interface EmpleadoService {

    Result<Empleado> authenticate(AuthenticateData authenticateData);

}