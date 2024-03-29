package paster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import paster.prop.Prop;

@SpringBootApplication
public class ImageBedApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageBedApplication.class, args);
        Prop.init();
    }

}
