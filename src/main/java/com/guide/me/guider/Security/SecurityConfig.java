package com.guide.me.guider.Security;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    
    private final JwtFilter jwt;
    
    public SecurityConfig(JwtFilter jwt) {
        this.jwt = jwt;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                // =====================================================
                // 1. ROUTES PUBLIQUES (SANS AUTHENTIFICATION)
                // =====================================================
                
                // Auth - Login & Register
                .requestMatchers("/api/auth/**").permitAll()
                
                // Pays & Villes
                .requestMatchers("/api/pays/**").permitAll()
                .requestMatchers("/api/villes/**").permitAll()
                
                // Guides - Routes publiques pour touristes
                .requestMatchers("/api/guides/touriste/**").permitAll()
                
                // Disponibilités - Routes publiques
                .requestMatchers("/api/disponibilites/*/calendar").permitAll()
                .requestMatchers("/api/disponibilites/guide/*").permitAll()
                
                // =====================================================
                // 2. ROUTES TOURISTE (ROLE_TOURIST)
                // =====================================================
                
                // Réservations touriste
                .requestMatchers("/api/reservations/tourist/**").hasAuthority("TOURIST")
                
                // Avis
                .requestMatchers("/api/avis/**").hasAuthority("TOURIST")
                
                // =====================================================
                // 3. ROUTES GUIDE (ROLE_GUIDER)
                // =====================================================
                
                // Disponibilités guide
                .requestMatchers("/api/disponibilites").hasAuthority("GUIDER")
                .requestMatchers("/api/disponibilites/me/**").hasAuthority("GUIDER")
                .requestMatchers("/api/disponibilites/*").hasAuthority("GUIDER")
                .requestMatchers("/api/disponibilites/multi-dispo").hasAuthority("GUIDER")
                
                // Réservations guide
                .requestMatchers("/api/reservations/guide/**").hasAuthority("GUIDER")
                
                // Profil guide
                .requestMatchers("/api/guides/me/**").hasAuthority("GUIDER")
                
                // =====================================================
                // 4. ROUTES AUTHENTIFIÉES (TOURIST ou GUIDER)
                // =====================================================
                
                // Notifications (tout utilisateur connecté)
                .requestMatchers("/api/notifications/**").authenticated()
                
                // =====================================================
                // 5. RESTE = AUTHENTIFIÉ
                // =====================================================
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) 
            throws Exception {
        return config.getAuthenticationManager();
    }
    
}