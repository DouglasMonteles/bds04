package com.devsuperior.bds04.components;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.devsuperior.bds04.repositories.UserRepository;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {

	@Autowired 
	private UserRepository userRepository;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, 
			OAuth2Authentication authentication) {
		var userEmail = authentication.getName();
		var user = this.userRepository.findByEmail(userEmail);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found. Username: " + userEmail);
		}
		
		var tokenAdditionalInformation = new HashMap<String, Object>();
		
		tokenAdditionalInformation.put("userId", user.getId());
		
		var token = (DefaultOAuth2AccessToken) accessToken;
		
		token.setAdditionalInformation(tokenAdditionalInformation);
		
		return accessToken;
	}

}
