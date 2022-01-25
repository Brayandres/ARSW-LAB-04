package edu.eci.arsw.blueprints.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Component("Subsampling")
public class SubsamplingFilter implements FilterBlueprint {

	@Override
	public Blueprint filter(Blueprint blueprint) {
		List<Point> points = blueprint.getPoints();
		blueprint.cleanPoints();
		int count = 0;
		for (Point p:points) {
			if (count%2 == 0) {
				blueprint.addPoint(p);
			}
			count ++;
		}
		return blueprint;
	}
}