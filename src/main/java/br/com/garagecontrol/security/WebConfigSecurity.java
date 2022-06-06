package br.com.garagecontrol.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	@Override // Configura as solicitações de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()// Desativa as configurações padrão de memória.
		.authorizeRequests() // Permite restringir acessos
		.antMatchers("/estilo/**").permitAll()
		.antMatchers(HttpMethod.GET, "/meuprojeto").permitAll()// Qualquer usuário acessa a pagina inicial
		.antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN") // Somente admin cadastra pessoas
		.anyRequest().authenticated()
		.and().formLogin().permitAll()// permite qualquer usuário no login
        .loginPage("/login")
        .defaultSuccessUrl("/arearestrita")
        .failureUrl("/error")
        .and()
        .logout().logoutSuccessUrl("/login")
		// Mapeia URL de Logout e invalida usuário autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
	} 
	
	 
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/resources/**").addResourceLocations(
	                "/resources/");
	    }
	
	@Override // Cria autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implementacaoUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	
	}
	
	  public void defaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	         configurer.enable();
	    }
	
	@Override // Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/login/", "/estilo/**");
	}
	
	
	
}

//
//package br.com.garagecontrol.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//
//@SuppressWarnings("deprecation")
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
//	
//	@Autowired
//	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
//	
//	@Override // Configura as solicitações de acesso por Http
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf()
//		.disable()// Desativa as configurações padrão de memória.
//		.authorizeRequests() // Permite restringir acessos
//		.antMatchers("/estilo/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/meuprojeto").permitAll()// Qualquer usuário acessa a pagina inicial
//		.antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN") // Somente admin cadastra pessoas
//		.anyRequest().authenticated()
//		.and().formLogin().permitAll()// permite qualquer usuário no login
//        .loginPage("/login")
//        .defaultSuccessUrl("/arearestrita")
//        .failureUrl("/error")
//        .and()
//        .logout().logoutSuccessUrl("/login")
//		// Mapeia URL de Logout e invalida usuário autenticado
//		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
//		
//	} 
//	
//	@Override // Cria autenticação do usuário com banco de dados ou em memória
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth.userDetailsService(implementacaoUserDetailsService)
//		.passwordEncoder(new BCryptPasswordEncoder());
//	
//	}
//	
//	  public void defaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//	         configurer.enable();
//	    }
//	
//	@Override // Ignora URL especificas
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/login/", "/estilo/**");
//	}
//	
//	
//	
//}

