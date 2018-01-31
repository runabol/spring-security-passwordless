package com.creactiviti.spring.security.passwordless;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity aHttpSecurity) throws Exception {
    aHttpSecurity.httpBasic()
                   .disable()
                 .authorizeRequests()
                    .antMatchers("/css/**","/signin/**","/signup/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                 .logout()
                    .permitAll();
  }
}
