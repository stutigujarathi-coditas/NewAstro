package NewAstroConnectApp.NewAstroConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "NewAstroConnectApp.NewAstroConnect.Config")
//@ComponentScan(basePackages = "NewAstroConnectApp.NewAstroConnect.Security")
//@ComponentScan(basePackages = "NewAstroConnectApp.NewAstroConnect.Service")
public class NewAstroConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewAstroConnectApplication.class, args);
	}

}
