package com.rk.statemachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.config.EnableStateMachine;

@SpringBootApplication
public class StateMachineDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StateMachineDemoApplication.class, args);
	}

}
