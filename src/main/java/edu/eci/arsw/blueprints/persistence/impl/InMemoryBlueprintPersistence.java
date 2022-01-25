/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */

@Component("InMemory")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();
    Blueprint bp1 = new Blueprint("VanGogh","StarryNight", new Point[] {new Point(0, 0), new Point(100, 100)});
    Blueprint bp2 = new Blueprint("DaVinci","Gioconda", new Point[] {new Point(0, 0), new Point(150, 150)});
    Blueprint bp3 = new Blueprint("DaVinci","VitrubianMan", new Point[] {new Point(0, 0), new Point(155, 155)});

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
    	if (blueprints.get(new Tuple<>(author, bprintname)) == null) {
    		throw new BlueprintNotFoundException("The blueprint "+bprintname+" with author "+author+" does not exist.");
    	}
    	return blueprints.get(new Tuple<>(author, bprintname));
    }

	@Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException{
    	Set<Blueprint> bluePrintsAuthor = new HashSet();
    	Iterator it = blueprints.entrySet().iterator();
    	while (it.hasNext()) {
    		Map.Entry<Tuple, Blueprint> entry = (Map.Entry)it.next();
    		if (entry.getKey().getElem1() == author) {
    			bluePrintsAuthor.add(entry.getValue());
    		}
    	}
    	if (bluePrintsAuthor.isEmpty()) {
    		throw new BlueprintNotFoundException("No Blueprints were found with the author: "+author);
    	}
		return bluePrintsAuthor;
    }
}