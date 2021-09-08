package edu.eci.arsw.blueprints.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Component("Redundant")
public class RedundantFilter implements FilterBlueprint {

	@Override
	public Blueprint filter(Blueprint blueprint) {
		Map<String, Character> newPoints = new HashMap<String, Character>();
		List<Point> points = blueprint.getPoints();
		blueprint.cleanPoints();
		for (Point p:points) {
			String key = p.getX()+", "+p.getY();
			if (!newPoints.containsKey(key)) {
				newPoints.put(key, null);
				blueprint.addPoint(p);
			}
		}
		return blueprint;
	}
}