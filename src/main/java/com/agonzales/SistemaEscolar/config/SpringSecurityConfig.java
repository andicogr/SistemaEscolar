package com.agonzales.SistemaEscolar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	@Qualifier("authenticationProvider")
	private AuthenticationProvider authenticationProvider;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
    		.authorizeRequests()
		    	.antMatchers("/resources/**").permitAll()
		    	.antMatchers("/denied").permitAll()
		    	.antMatchers("/").hasAnyAuthority("PRIV_ADMIN", "PRIV_USER")
		        .anyRequest().authenticated()
		        .and()
            .formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/")
				.failureUrl("/login/failure")
				.and()
            .logout()
            	.logoutSuccessUrl("/login").permitAll()
				.and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        
        //http.sessionManagement().maximumSessions(1).expiredUrl("/expiredSession");

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	LimitLoginAuthenticationProvider provider = (LimitLoginAuthenticationProvider) authenticationProvider;
    	provider.setPasswordEncoder(passwordEncoder());
    	auth.authenticationProvider(authenticationProvider);
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
    	PasswordEncoder encoder = new BCryptPasswordEncoder();
    	return encoder;
    }

}
