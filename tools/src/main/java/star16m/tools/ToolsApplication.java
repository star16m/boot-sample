package star16m.tools;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToolsApplication {
    public static void main(String[] args) {
        SpringApplication toolsApplication = new SpringApplication(ToolsApplication.class);
        toolsApplication.setWebApplicationType(WebApplicationType.NONE);
        toolsApplication.setBannerMode(Banner.Mode.OFF);
        toolsApplication.setLogStartupInfo(false);
        toolsApplication.run(args);
    }
}
