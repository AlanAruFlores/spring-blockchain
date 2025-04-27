package com.ar.blockchain.spring_block;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ar.blockchain.spring_block.services.BlockService;

@SpringBootApplication
public class SpringBlockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBlockApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(BlockService blockService){
		return args->{
			//Ejemplo simple
			//blockService.runBlockExample();
			

			blockService.runFilesWithBlockChain();
			
		};
	}

}
