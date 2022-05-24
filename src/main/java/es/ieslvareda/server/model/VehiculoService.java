package es.ieslvareda.server.model;

import es.ieslvareda.model.Person;
import es.ieslvareda.model.Result;
import es.ieslvareda.model.Vehiculo;

import java.util.List;

public interface VehiculoService {
    List<Vehiculo> getAll();
    Result<Vehiculo> get(String matricula);
    Result<Vehiculo> update(Vehiculo vehiculo);
    Result<Vehiculo> add(Vehiculo vehiculo);
    Result<Vehiculo> delete(String matricula);
}
