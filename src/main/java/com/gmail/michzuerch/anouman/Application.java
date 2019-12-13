package com.gmail.michzuerch.anouman;

import com.gmail.michzuerch.anouman.app.security.SecurityConfiguration;
import com.gmail.michzuerch.anouman.backend.data.entity.User;
import com.gmail.michzuerch.anouman.backend.repositories.UserRepository;
import com.gmail.michzuerch.anouman.backend.service.UserService;
import com.gmail.michzuerch.anouman.ui.MainView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses = {SecurityConfiguration.class, MainView.class, Application.class,
        UserService.class}, exclude = ErrorMvcAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
@EntityScan(basePackageClasses = {User.class})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
