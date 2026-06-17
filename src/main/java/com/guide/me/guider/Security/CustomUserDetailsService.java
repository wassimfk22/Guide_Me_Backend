package com.guide.me.guider.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.guide.me.guider.Repositories.UtilisateurRepository;
import com.guide.me.guider.entities.Utilisateur;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UtilisateurRepository utilRep;
	
	public CustomUserDetailsService ( UtilisateurRepository utilRep ) {
		this.utilRep = utilRep;
	}
	
	@Override
	public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
		Utilisateur util = this.utilRep.findByUsername(username)
				.orElseThrow( () -> new UsernameNotFoundException("Utilisateur introuvable !") );
		GrantedAuthority authority = new SimpleGrantedAuthority( util.getRole().name() );
		List <GrantedAuthority> authorities = List.of(authority);
		return new User ( util.getUsername(), util.getPassword(), authorities );
	}

}
