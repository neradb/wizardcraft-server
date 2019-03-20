package com.wplatform.muoline;

import com.wplatform.muoline.network.NetworkServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WizardcraftApplication implements CommandLineRunner {

    @Autowired
    private NetworkServer onlineServer;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WizardcraftApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> onlineServer.shutdown()));
        onlineServer.run();
    }
}
