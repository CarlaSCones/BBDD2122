package es.ieslvareda.properties;

import es.ieslvareda.model.Model;

public class Test {
    public static void main(String[] args) {

        Model model = new Model();
        //System.out.println(model.getPersons());
        // System.out.println(model.addPerson(new Person("4444", "Manolo", "Hernandez", 58)));
        //System.out.println(model.deldetePerson("4444"));

      System.out.println(model.updatePerson(new Person("4444", "Juan", "Hernandez", 58)));
       // System.out.println(model.deldetePerson("1 OR 1=1"));
      //  System.out.println(model.authenticate("1' OR email='vanesa@mordor.es' or password!='1", "234"));

        //System.out.println(model.getEmpleados());

        //System.out.println(model.authenticate("1' or password!='1","234"));
    }
}
