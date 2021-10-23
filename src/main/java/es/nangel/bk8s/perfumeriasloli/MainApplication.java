package es.nangel.bk8s.perfumeriasloli;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {
    private static Class<MainApplication> mainApplicationClass = MainApplication.class;

    public static void main(String[] args) {
        SpringApplication.run(mainApplicationClass, args);
    }
}
