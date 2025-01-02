package com.AAAW.MovieAPP;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieApp {

    public static void main(String[] args) {
        // Load environment variables
//        Dotenv dotenv;
//        if (isDevelopmentEnvironment()) {
//            dotenv = Dotenv.load();
//            System.setProperty("MONGO_URI", dotenv.get("MONGO_URI"));
//        }

        SpringApplication.run(MovieApp.class, args);


    }

//    private static boolean isDevelopmentEnvironment() {
//        return "dev".equalsIgnoreCase(System.getProperty("spring.profiles.active"));
//    }

}
