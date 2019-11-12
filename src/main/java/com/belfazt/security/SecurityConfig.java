package com.belfazt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
			.withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN").authorities("ACCESS_API_DATA_1", "ACCESS_API_DATA_2")
			.and()
			.withUser("mani").password(passwordEncoder().encode("mani123")).roles("USER")
			.and()
			.withUser("maxi").password(passwordEncoder().encode("maxi123")).roles("MANAGER").authorities("ACCESS_API_DATA_1");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			/*.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.httpBasic(); //Basic html auth*/
			
			.authorizeRequests()
			.antMatchers("/index.html").permitAll()
			.antMatchers("/profile/**").authenticated()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/management/**").hasAnyRole("ADMIN", "MANAGER")
			//Protecting rest endpoints
			.antMatchers("/api/data1").hasAuthority("ACCESS_API_DATA_1")
			.antMatchers("/api/data2").hasAuthority("ACCESS_API_DATA_2")
			.and()
			.httpBasic();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
