package es.ieslvareda.server.model;

import es.ieslvareda.model.Coche;
import es.ieslvareda.model.Result;
import es.ieslvareda.model.Vehiculo;

import java.util.List;

public interface CocheService {
    List<Coche> getAll();
    Result<Coche> get(String matricula);
    Result<Coche> update(Coche coche);
    Result<Coche> add(Coche coche);
    Result<Coche> delete(String matricula);
}
