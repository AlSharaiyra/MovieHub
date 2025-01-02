package com.AAAW.MovieAPP;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieApp {

    public static void main(String[] args) {
        // Load environment variables
        Dotenv dotenv = Dotenv.configure().load();

        // Access the MONGO_URI variable
        System.setProperty("MONGO_URI", dotenv.get("MONGO_URI"));
        SpringApplication.run(MovieApp.class, args);


    }
}
