package com.blogapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blogapi.security.CustomUserDetailsService;
import com.blogapi.security.JwtAuthenticationEntryPoint;
import com.blogapi.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity 
public class SecurityConfig   {
	
	 public static final String[] PUBLIC_URLS = {"/auth/**", "/","/api/user" };
	
	@Autowired
    private CustomUserDetailsService customUserDetailService;

	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		 http.csrf()
         .disable()
         .authorizeHttpRequests()
         .requestMatchers(PUBLIC_URLS)
         .permitAll()     
         .anyRequest()
         .authenticated()
         .and().exceptionHandling()
         .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

 http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

 http.authenticationProvider(daoAuthenticationProvider());
 DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

 return defaultSecurityFilterChain;


    }
	
	
	
	
	
	    @Bean
	    public DaoAuthenticationProvider daoAuthenticationProvider() {

	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setUserDetailsService(this.customUserDetailService);
	        provider.setPasswordEncoder(this.passwordEncoder());
	        return provider;

	    }
	 
	 
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
//	    @Bean
//	    public BCryptPasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder(10);
//	    }
	 

	    @Bean
	    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }
	
	
	
	
}
