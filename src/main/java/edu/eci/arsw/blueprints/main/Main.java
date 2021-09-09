package edu.eci.arsw.blueprints.main;

import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

public class Main {

	public static void maina(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    	BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
    	
    	Point[] pts = new Point[]{new Point(10, 10), new Point(100, 100), new Point(10, 10), new Point(100, 100), 
    							  new Point(20, 20), new Point(200, 200), new Point(20, 20), new Point(200, 200)};
        Blueprint bp= new Blueprint("Brayan", "Canvas", pts);
        
    	Point[] pts1 = new Point[]{new Point(20, 20), new Point(200, 200), new Point(30, 30), new Point(300, 300), 
    							   new Point(40, 40), new Point(400, 400), new Point(50, 50), new Point(500, 500)};
        Blueprint bp1 = new Blueprint("Kevin", "Picture", pts1);
        
    	Point[] pts2 = new Point[]{new Point(30, 30), new Point(300, 300)};
        Blueprint bp2 = new Blueprint("Juan", "Obra", pts2);
        
    	Point[] pts3 = new Point[]{new Point(40, 40), new Point(400, 400)};
        Blueprint bp3 = new Blueprint("Carlos", "Cuadro", pts3);
        
    	Point[] pts4 = new Point[]{new Point(30, 30), new Point(300, 300)};
        Blueprint bp4 = new Blueprint("Brayan", "Picture", pts4);
        
    	Point[] pts5 = new Point[]{new Point(40, 40), new Point(400, 400)};
        Blueprint bp5 = new Blueprint("Kevin", "Pintura", pts5);
        
    	Point[] pts6 = new Point[]{new Point(50, 50), new Point(500, 500)};
        Blueprint bp6 = new Blueprint("Carlos", "Cuadro", pts6);
        
        Blueprint b1, b2, b3, b6;
        Set<Blueprint> result1, result2, result3;
        
        try {
        	bps.addNewBlueprint(bp);
        	bps.addNewBlueprint(bp1);
        	bps.addNewBlueprint(bp2);
        	bps.addNewBlueprint(bp3);
        	bps.addNewBlueprint(bp4);
        	bps.addNewBlueprint(bp5);
        	bps.addNewBlueprint(bp6);
        } catch (BlueprintPersistenceException e) {
        	e.printStackTrace();
        	//System.out.println(e.getMessage());
        } 
        System.out.println("Terminó Primer try.\n");
        
        try {
        	b1 = bps.getBlueprint("Brayan", "Canvas");
        	b2 = bps.getBlueprint("Kevin", "Picture");
        	b3 = bps.getBlueprint("Juan", "Obra");
        	b6 = bps.getBlueprint("Carlos", "Otro");
        } catch (BlueprintNotFoundException e) {
        	e.printStackTrace();
        	//System.out.println(e.getMessage());
        }
        System.out.println("Terminó Segundo try.\n");
        
        try {
        	result1 = bps.getBlueprintsByAuthor("Brayan");
        	result2 = bps.getBlueprintsByAuthor("Kevin");
        	result3 = bps.getBlueprintsByAuthor("Otro");
        } catch (BlueprintNotFoundException e) {
        	e.printStackTrace();
        	//System.out.println(e.getMessage());
        }
        System.out.println("Terminó Tercer try.\n\n");
        
        
        //PRUEBAS PUNTO 5
        System.out.println("PRUEBAS PUNTO 5.");
        
        //For redundant
        System.out.println(bp.printPoints());
        Blueprint filtered = bps.applyFilter(bp);
        System.out.println(filtered.printPoints());
        
        //For Sub-sampling
        System.out.println(bp1.printPoints());
        Blueprint filtered1 = bps.applyFilter(bp1);
        System.out.println(filtered1.printPoints());
	}
}