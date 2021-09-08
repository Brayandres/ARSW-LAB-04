/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
        
    }
    
    @Test
    public void shouldSearchABlueprint() {
    	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    	BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
    	
    	Point[] pts = new Point[]{new Point(10, 10), new Point(100, 100)};
        Blueprint bp= new Blueprint("Brayan", "Canvas", pts);
        
    	Point[] pts1 = new Point[]{new Point(20, 20), new Point(200, 200)};
        Blueprint bp1 = new Blueprint("Kevin", "Picture", pts1);
        
        Blueprint b1, b2;
        
        try {
        	bps.addNewBlueprint(bp);
        	bps.addNewBlueprint(bp1);
        	b1 =  bps.getBlueprint("Brayan", "Canvas");
        	b2 =  bps.getBlueprint("Kevin", "Picture");
            assertNotNull(b1);
            assertNotNull(b2);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        } catch (BlueprintNotFoundException e) {
        	fail("Blueprint persistence failed searching bp by Author and Name.");
        }
        
        try {
        	bps.getBlueprint("Otro", "Obra");
        } catch (BlueprintNotFoundException e) {
        	assertEquals("The blueprint Obra with author Otro does not exist.", e.getMessage());
        }
    }
    
    @Test
    public void shouldSearchBlueprintsByAnAuthor() {
    	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    	BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
    	
    	Point[] pts = new Point[]{new Point(10, 10), new Point(100, 100)};
        Blueprint bp= new Blueprint("Brayan", "Canvas", pts);
        
    	Point[] pts1 = new Point[]{new Point(20, 20), new Point(200, 200)};
        Blueprint bp1 = new Blueprint("Kevin", "Picture", pts1);
        
        Set<Blueprint> b1, b2;
        
        try {
        	bps.addNewBlueprint(bp);
        	bps.addNewBlueprint(bp1);
        	b1 =  bps.getBlueprintsByAuthor("Brayan");
        	b2 =  bps.getBlueprintsByAuthor("Kevin");
            assertFalse(b1.isEmpty());
            assertFalse(b2.isEmpty());
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        } catch (BlueprintNotFoundException e) {
        	fail("Blueprint persistence failed searching bp by Author and Name.");
        }
        
        try {
        	bps.getBlueprintsByAuthor("Otro");
        } catch (BlueprintNotFoundException e) {
        	assertEquals("No Blueprints were found with the author: Otro", e.getMessage());
        }
    }

    @Test
    public void shouldApplyRedundantFilter() {
    	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    	BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
    	
    	Point[] pts = new Point[]{new Point(10, 10), new Point(100, 100), new Point(10, 10), new Point(100, 100), 
				  				  new Point(20, 20), new Point(200, 200), new Point(20, 20), new Point(200, 200)};
		Blueprint bp= new Blueprint("Brayan", "Canvas", pts);

		//Before being filtered
		assertTrue(bp.getPoints().size() == 8);
		assertEquals("{(10, 10) (100, 100) (10, 10) (100, 100) (20, 20) (200, 200) (20, 20) (200, 200) }\n", bp.printPoints());
		
		//After being filtered
		assertTrue(bps.applyFilter(bp).getPoints().size() == 4);
		assertEquals("{(10, 10) (100, 100) (20, 20) (200, 200) }\n", bp.printPoints());
    }
    
    //@Test
    public void shouldApplySubsamplingFilter() {
    	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    	BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
    	
    	Point[] pts1 = new Point[]{new Point(20, 20), new Point(200, 200), new Point(30, 30), new Point(300, 300), 
		   		   				   new Point(40, 40), new Point(400, 400), new Point(50, 50), new Point(500, 500)};
    	Blueprint bp1 = new Blueprint("Kevin", "Picture", pts1);
    	
    	//Before being filtered
    	assertTrue(bp1.getPoints().size() == 8);
    	assertEquals("{(20, 20) (200, 200) (30, 30) (300, 300) (40, 40) (400, 400) (50, 50) (500, 500) }\n", bp1.printPoints());
    	
    	//After being filtered
    	assertTrue(bps.applyFilter(bp1).getPoints().size() == 4);
    	assertEquals("{(20, 20) (30, 30) (40, 40) (50, 50) }\n", bp1.printPoints());
    }
}