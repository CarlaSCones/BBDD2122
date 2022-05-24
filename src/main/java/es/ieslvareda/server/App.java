package es.ieslvareda.server;

import es.ieslvareda.server.controllers.EmpleadoController;
import es.ieslvareda.server.controllers.PersonController;
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

        get(API.Routes.VEHICULOS, VehiculoController::getVehiculos, new JsonTransformer<>());
        //localhost:4567/vehiculos

        get(API.Routes.VEHICULO, VehiculoController::getVehiculo,new JsonTransformer<>());
        //localhost:4567/vehiculo?matricula=0000AAA

        post(API.Routes.VEHICULO, VehiculoController::addVehiculo, new JsonTransformer<>());
        //localhost:4567/vehiculo
        //body/raw:{
        //        //    matricula: '1111ABD',
        //        //    preciohora: 15,
        //        //    marca: 'KIA',
        //        //    color: 'negro'
        //        //}
        //

        put(API.Routes.VEHICULO, VehiculoController::updateVehiculo,new JsonTransformer<>());
        //localhost:4567/vehiculo
        //body/raw:{
        //        //    matricula: '1111ABC',
        //        //    preciohora: 15,
        //        //    marca: 'KIA',
        //        //    color: 'negro'
        //        //}
        //

        delete(API.Routes.VEHICULO, VehiculoController::deleteVehiculo, new JsonTransformer<>());
        //localhost:4567/vehiculo?matricula=0000AAA


        //MariaDB
        get(API.Routes.ALL_PERSON, PersonController::getPersons, new JsonTransformer<>());
        //Mostrar todas las personas:
        //localhost:4567/persons

        get(API.Routes.PERSON, PersonController::getPerson,new JsonTransformer<>());
        //Mostrar persona con DNI:
        //localhost:4567/person?dni=1234

        post(API.Routes.PERSON, PersonController::addPerson, new JsonTransformer<>());
        //Añadir persona:
        //localhost:4567/person
        //body/raw:{
        //        //    dni: '1111',
        //        //    nombre: 'Paula',
        //        //    apellidos: 'Santamaria',
        //        //    edad: 15
        //        //}
        //

        put(API.Routes.PERSON, PersonController::updatePerson,new JsonTransformer<>());
        //Cambiar datos de una persona:
        //localhost:4567/person
        //body/raw:{
        //        //    dni: '1111',
        //        //    nombre: 'Gema',
        //        //    apellidos: 'Santamaria',
        //        //    edad: 15
        //        //}
        //

        delete(API.Routes.PERSON, PersonController::deletePerson, new JsonTransformer<>());
        //Eliminar persona con DNI:
        //localhost:4567/person?dni=1234
    }


}
