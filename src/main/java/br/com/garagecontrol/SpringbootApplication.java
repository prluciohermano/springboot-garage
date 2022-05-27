package br.com.garagecontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = "br.com.garagecontrol.entity")
@ComponentScan(basePackages = { "br.com.garagecontrol.*" })
@EnableJpaRepositories(basePackages = { "br.com.garagecontrol.repository" })
@EnableTransactionManagement
@EnableWebMvc
public class SpringbootApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
	}
}




/*


@SpringBootApplication
@EntityScan(basePackages="br.com.garagecontrol.entity")
@ComponentScan(basePackages= {"br.com.garagecontrol.*"})
@EnableJpaRepositories(basePackages= {"br.com.garagecontrol.repository"})
@EnableTransactionManagement
@EnableWebMvc
public class SpringbootApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
		
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode("2201");
		System.out.println(result);
		
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/login").setViewName("/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
		
	}

}
*/