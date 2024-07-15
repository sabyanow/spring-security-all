package com.sabya.ms;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sabya.ms.service.UserDetailsServiceImpl;


@Deprecated
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    DataSource dataSource;
    
    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
     /* auth.jdbcAuthentication()
      .dataSource(dataSource);*/
    	
    	auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());

    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }

     @Override
    protected void configure(HttpSecurity http) throws Exception{
      http.authorizeRequests()
      //.antMatchers("/admin").hasRole("ROLE_ADMIN")
      //.antMatchers("/user").hasAnyRole("ROLE_ADMIN","ROLE_USER")
      .antMatchers("/api/auth/**").permitAll()
      .antMatchers("/h2-console/**").permitAll()
      .anyRequest().authenticated()
      .and().formLogin();
      
      http.csrf().disable();
      http.headers().frameOptions().disable();
   

    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        //return NoOpPasswordEncoder.getInstance();
    	 return new BCryptPasswordEncoder();
    }
}
