package com.belfazt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.belfazt.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	CustomUserDetailService useDetailService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//DB AUTH
		auth.authenticationProvider(authenticationProvider());
		
		/*//IN MEMORY AUTH
		auth
			.inMemoryAuthentication()
			.withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN").authorities("ACCESS_API_DATA_1", "ACCESS_API_DATA_2")
			.and()
			.withUser("mani").password(passwordEncoder().encode("mani123")).roles("USER")
			.and()
			.withUser("maxi").password(passwordEncoder().encode("maxi123")).roles("MANAGER").authorities("ACCESS_API_DATA_1");
		*/
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
			.antMatchers("/api/admin/users").hasRole("ADMIN")
			.antMatchers("/api/management/report").hasRole("MANAGER")
			.and()
			.formLogin()	//--> in-built login form
			.loginPage("/login").permitAll()	//Set the custom login page
			.and()
			 //Logout code
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
			.and()
			 //Rememberme  //Remembers only for 30 days
			.rememberMe().tokenValiditySeconds(2592000);
	}
	
	@Bean 
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(useDetailService);
		
		return authenticationProvider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
