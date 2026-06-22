package io.homeinventory;

import io.homeinventory.menu.MainMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeInventoryApp implements CommandLineRunner {

    @Autowired
    private MainMenu mainMenu;

    public static void main(String[] args) {
        SpringApplication.run(HomeInventoryApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mainMenu.show();
    }

}
