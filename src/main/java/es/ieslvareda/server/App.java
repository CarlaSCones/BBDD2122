package es.ieslvareda.server;

import es.ieslvareda.server.controllers.CocheController;
import es.ieslvareda.server.controllers.EmpleadoController;
import es.ieslvareda.server.controllers.VehiculoController;
import es.ieslvareda.server.model.JsonTransformer;


import static spark.Spark.*;
public class App {
    public static void main(String[] args) {

        // Oracle
        post(API.Routes.AUTHENTICATE, EmpleadoController::authenticate, new JsonTransformer<>());
        //Autenticar persona
        //localhost:4567/authenticate
        //body/raw:
        //{
        //    email:"pepa@mordor.es",
        //    passwd:"1111"
        //}

        //Tabla coche
        get(API.Routes.COCHES, CocheController::getCoches, new JsonTransformer<>());
        //localhost:4567/coches

        get(API.Routes.COCHE, CocheController::getCoche,new JsonTransformer<>());
        //localhost:4567/coche?matricula=0000AAA

        post(API.Routes.COCHE, CocheController::addCoche, new JsonTransformer<>());
        //localhost:4567/coche
        //body/raw:{
        //    matricula: '0000ABA',
        //    numPlazas: 5,
        //    numPuertas: 4
        //}
        //

        put(API.Routes.COCHE, CocheController::updateCoche,new JsonTransformer<>());
        //localhost:4567/coche
        //body/raw:{
        //    matricula: '0000ABA',
        //    numPlazas: 5,
        //    numPuertas: 4
        //}
        //

        delete(API.Routes.COCHE, CocheController::deleteCoche, new JsonTransformer<>());
        //localhost:4567/coche?matricula=0000AAA


        //Tabla Vehiculo
        get(API.Routes.VEHICULOS, VehiculoController::getVehiculos, new JsonTransformer<>());
        //localhost:4567/vehiculos

        get(API.Routes.VEHICULO, VehiculoController::getVehiculo,new JsonTransformer<>());
        //localhost:4567/vehiculo?matricula=0000AAA

        post(API.Routes.VEHICULO, VehiculoController::addVehiculo, new JsonTransformer<>());
        //localhost:4567/vehiculo
        //body/raw:
        //    matricula: '0123plj',
        //    preciohora: 5,
        //    marca: 'Ford',
        //    color: 'rojo',
        //    bateria: 45,
        //    fechaadq: '07/01/21',
        //    estado: 'preparado',
        //    idCarnet: 2,
        //    changedBy: 'initial'
        //}
        //

        put(API.Routes.VEHICULO, VehiculoController::updateVehiculo,new JsonTransformer<>());
        //localhost:4567/vehiculo
        //body/raw:
        //    matricula: '0123plj',
        //    preciohora: 5,
        //    marca: 'Ford',
        //    color: 'rojo',
        //    bateria: 45,
        //    fechaadq: '07/01/21',
        //    estado: 'preparado',
        //    idCarnet: 2,
        //    changedBy: 'initial'
        //}
        //

        delete(API.Routes.VEHICULO, VehiculoController::deleteVehiculo, new JsonTransformer<>());
        //localhost:4567/vehiculo?matricula=0000AAA

    }


}
