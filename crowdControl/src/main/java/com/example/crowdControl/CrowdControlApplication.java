package com.example.crowdControl;

import com.example.crowdControl.models.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableAsync
public class CrowdControlApplication {

	public static void main(String[] args) {

		SpringApplication.run(CrowdControlApplication.class, args);
	}

}
