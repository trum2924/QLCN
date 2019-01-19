package wee.budget.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import wee.budget.service.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
//	@Autowired
//	private App_logDAO app_logDAO;
	
	@Autowired
	private UserDetailsServiceImp userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	public AuthenticationSuccessHandler insertLoginLog() {
		return new AuthenticationSuccessHandler() {
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
//				app_logDAO.insertLoginLog(authentication.getName());
				final Logger LOGGER = LoggerFactory.getLogger("LOGIN");
				LOGGER.warn("UserID=" + authentication.getName());
				response.sendRedirect("/loan/loanList/0");
			}
		};
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		
		http.authorizeRequests().antMatchers("/","/login").permitAll();
		
		http.authorizeRequests().antMatchers("/loan/*"
											,"/borrower/*"
											).access("hasAuthority('user')");
		
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/");
		
		http.authorizeRequests().and().formLogin()
			.loginProcessingUrl("/security_check")
			.loginPage("/login")
			.successHandler(insertLoginLog())
			.failureUrl("/login?error=true")
			.usernameParameter("username")
			.passwordParameter("password")
			
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
	}
}
