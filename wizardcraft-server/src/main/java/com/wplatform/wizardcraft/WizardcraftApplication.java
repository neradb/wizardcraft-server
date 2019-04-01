package com.wplatform.wizardcraft;

import com.wplatform.wizardcraft.network.NetworkServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.wplatform.wizardcraft.domain")
@EnableJpaRepositories("com.wplatform.wizardcraft.repostory")
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
