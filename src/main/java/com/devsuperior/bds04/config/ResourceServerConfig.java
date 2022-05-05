package com.devsuperior.bds04.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private Environment env;
	
	private static final String[] PUBLIC = {
			"/oauth/token",
			"/h2-console/**",
	};
	
	private static final String[] CLIENT_OR_ADMIN = {
			"/cities/**",
			"/events/**",
	};
	
	private static final String[] CLIENT = {
			"/events/**",
	};
	
	private static final String[] ADMIN = {
			"/cities/**",
			"/events/**",
	};
	
	@Autowired
	private JwtTokenStore jwtTokenStore;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(this.jwtTokenStore);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// H2
		if (List.of(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.authorizeRequests()
			.antMatchers(PUBLIC).permitAll()
			.antMatchers(HttpMethod.GET, CLIENT_OR_ADMIN).permitAll()
			.antMatchers(HttpMethod.POST, CLIENT).hasAnyRole("CLIENT", "ADMIN")
			.antMatchers(ADMIN).hasRole("ADMIN")
			.anyRequest().authenticated();
	}
	
}
