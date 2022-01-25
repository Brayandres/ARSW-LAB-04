package edu.eci.arsw.blueprintsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.blueprintsapi"})
public class BlueprintsAPIApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(BlueprintsAPIApplication.class, args);
		} catch (Exception e) {
			System.out.println("\n##################################\n##################################");
			System.out.println(e.getMessage());
			System.out.println("##################################\n##################################\n");
		}
	}
}