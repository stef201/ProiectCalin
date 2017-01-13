package ro.sci.studentad.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import ro.sci.studentad.service.SiteUserService;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	SiteUserService siteUserService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//@formatter:off
		http
			.authorizeRequests()
				.antMatchers("/","/about","/console","/signup","/signupok.jsp","/newmessage")
					.permitAll()
				.antMatchers("/js/*","/css/*","/img/*")
					.permitAll()
				.antMatchers("/projects")
						.hasRole("ADMIN")
					.and()
				.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/")
					.permitAll()
					.and()
				.logout()
					.permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(siteUserService).passwordEncoder(passwordEncoder);
	}
	
	
	//@formatter:on
	
}
