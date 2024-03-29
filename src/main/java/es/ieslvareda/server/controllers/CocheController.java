package es.ieslvareda.server.controllers;

import es.ieslvareda.model.Coche;
import es.ieslvareda.model.Result;
import es.ieslvareda.server.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.List;

public class CocheController {

    static Logger logger = LoggerFactory.getLogger(CocheController.class);

    private static CocheService service = new ImpCocheService();

    private static JsonTransformer<Coche> jsonTransformer = new JsonTransformer<>();

    public static List<Coche> getCoches(Request req, Response res){
        logger.info("Recibiendo solicitud de todos los coches...");
        return service.getAll();
    }

    public static Result<Coche> getCoche(Request req, Response res) {
        String matricula= req.queryParams("matricula");
        logger.info("Consiguiendo coche con matricula = " + matricula + "...");

        Result result = service.get(matricula);
        res.type("application/json");

        if(result instanceof Result.Success)
            res.status(200);
        else{
            Result.Error error= (Result.Error) result;
            res.status(error.getCode());
        }
        return result;
    }

    public static Result<Coche> addCoche(Request request, Response response) {
        logger.info("Añadiendo nuevo coche...");
        String body = request.body();
        Coche coche = jsonTransformer.getObjet(body, Coche.class);
        Result<Coche> result =  service.add(coche);
        response.type("application/json");

        if(result instanceof Result.Success)
            response.status(200);
        else{
            Result.Error error= (Result.Error) result;
            response.status(error.getCode());
        }

        return result;
    }

    public static Result<Coche> updateCoche(Request req, Response res) {
        logger.info("Actualizandoo coche...");
        String body = req.body();

        Coche coche = jsonTransformer.getObjet(body, Coche.class);
        Result<Coche> result = service.update(coche);
        res.type("application/json");

        if(result instanceof Result.Success)
            res.status(200);
        else{
            Result.Error error= (Result.Error) result;
            res.status(error.getCode());
        }
        return result;
    }

    public static Result<Coche> deleteCoche(Request req, Response res) {
        String matricula = req.queryParams("matricula");
        logger.info("Eliminando coche con matricula " + matricula + "...");

        Result<Coche> result = service.delete(matricula);
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
