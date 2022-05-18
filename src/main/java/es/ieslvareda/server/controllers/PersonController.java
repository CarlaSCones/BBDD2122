package es.ieslvareda.server.controllers;

import es.ieslvareda.model.MyDataSource;
import es.ieslvareda.model.Person;
import es.ieslvareda.model.Result;
import es.ieslvareda.server.model.ImpPersonService;
import es.ieslvareda.server.model.JsonTransformer;
import es.ieslvareda.server.model.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

// http://localhost:4567/person/dni=?1111

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PersonController {


    static Logger logger = LoggerFactory.getLogger(PersonController.class);

    private static PersonService service = new ImpPersonService();

    private static JsonTransformer<Person> jsonTransformer = new JsonTransformer<>();

    public static List<Person> getPersons(Request req, Response res){
        logger.info("Receiving request for all persons");
        return service.getAll();
    }

    public static Result<Person> getPerson(Request req, Response res) {
        String dni= req.queryParams("dni");
        logger.info("Get person with dni = " + dni);

        Result result = service.get(dni);
        res.type("application/json");

        if(result instanceof Result.Success)
            res.status(200);
        else{
            Result.Error error= (Result.Error) result;
            res.status(error.getCode());
        }
        return result;
    }

    public static Result<Person> addPerson(Request request, Response response) {
        logger.info("Add new person");
        String body = request.body();
        Person p = jsonTransformer.getObjet(body, Person.class);
        Result<Person> result =  service.add(p);
        response.type("application/json");

        if(result instanceof Result.Success)
            response.status(200);
        else{
            Result.Error error= (Result.Error) result;
            response.status(error.getCode());
        }

        return result;
    }

    public static Result<Person> updatePerson(Request req, Response res) {
        logger.info("Updating person ");
        Person p = (Person) jsonTransformer.getObjet(req.body(), Person.class);
        Result<Person> result = service.update(p);
        res.type("application/json");

        if(result instanceof Result.Success)
            res.status(200);
        else{
            Result.Error error= (Result.Error) result;
            res.status(error.getCode());
        }
        return result;
    }

    public static Result<Person> deletePerson(Request req, Response res) {
        logger.info("Request person by dni: " + req.queryParams("dni") );
        String dni = req.queryParams("dni");
        Result<Person> result = service.delete(dni);
        res.type("application/json");

        if(result instanceof Result.Success)
            res.status(200);
        else{
            Result.Error error= (Result.Error) result;
            res.status(error.getCode());
        }
        return result;
    }
}
