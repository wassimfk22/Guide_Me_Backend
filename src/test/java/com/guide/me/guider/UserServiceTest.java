//package com.guide.me.guider;
//
//import com.guide.me.guider.DTOs.LoginRequest;
//import com.guide.me.guider.DTOs.RegisterGuideRequest;
//import com.guide.me.guider.DTOs.RegisterTouristeRequest;
//import com.guide.me.guider.Repositories.*;
//import com.guide.me.guider.Security.JwtUtil;
//import com.guide.me.guider.Services.AuthService;
//import com.guide.me.guider.entities.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AuthServiceTest {
//
//    @Mock private UtilisateurRepository utilisateurRepo;
//    @Mock private GuideRepository guideRepo;
//    @Mock private VisiteurRepository visiteurRepo;
//    @Mock private VilleRepository villeRepo;
//    @Mock private PaysRepository paysRepo;
//    @Mock private BCryptPasswordEncoder passwordEncoder;
//    @Mock private AuthenticationManager authManager;
//    @Mock private JwtUtil jwtUtil;
//
//    @InjectMocks
//    private AuthService authService;
//
//    private LoginRequest loginRequest;
//    private Utilisateur utilisateurExemple;
//
//    @BeforeEach
//    void setUp() {
//        loginRequest = new LoginRequest("wassimfk22", "password123");
//        
//        // Simuler un utilisateur concret car Utilisateur est une classe abstraite
//        utilisateurExemple = new Utilisateur() {}; 
//        utilisateurExemple.setId(1L);
//        utilisateurExemple.setUsername("wassimfk22");
//        utilisateurExemple.setEmail("wassim@example.com");
//        utilisateurExemple.setRole(RoleUser.TOURIST);
//    }
//
//    // ==========================================
//    // TESTS POUR LA METHODE LOGIN()
//    // ==========================================
//
//    @Test
//    void login_Succes() {
//        // GIVEN
//        Authentication authentication = mock(Authentication.class);
//        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
//        when(utilisateurRepo.findByUsername("wassimfk22")).thenReturn(Optional.of(utilisateurExemple));
//        when(jwtUtil.generateToken("wassimfk22")).thenReturn("mocked-jwt-token");
//
//        // WHEN
//        ResponseEntity<?> response = authService.login(loginRequest);
//
//        // THEN
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
//    }
//
//    @Test
//    void login_Echec_BadCredentials() {
//        // GIVEN
//        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenThrow(new BadCredentialsException("Mauvais identifiants"));
//
//        // WHEN
//        ResponseEntity<?> response = authService.login(loginRequest);
//
//        // THEN
//        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//        assertEquals("Username ou mot de passe incorrect", response.getBody());
//    }
//
//    // ==========================================
//    // TESTS POUR REGISTER GUIDE()
//    // ==========================================
//
//    @Test
//    void registerGuide_Succes() {
//        // GIVEN
//        RegisterGuideRequest request = new RegisterGuideRequest();
//        request.setUsername("guide1");
//        request.setEmail("guide1@test.com");
//        request.setVilleId(1L);
//        request.setPassword("pass");
//
//        Ville mockVille = new Ville();
//        Guide mockGuideSaved = new Guide();
//        mockGuideSaved.setId(2L);
//        mockGuideSaved.setUsername("guide1");
//        mockGuideSaved.setRole(RoleUser.GUIDER);
//
//        when(utilisateurRepo.findByUsername("guide1")).thenReturn(Optional.empty());
//        when(utilisateurRepo.findByEmail("guide1@test.com")).thenReturn(Optional.empty());
//        when(villeRepo.findById(1L)).thenReturn(Optional.of(mockVille));
//        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");
//        when(guideRepo.save(any(Guide.class))).thenReturn(mockGuideSaved);
//        when(jwtUtil.generateToken("guide1")).thenReturn("guide-token");
//
//        // WHEN
//        ResponseEntity<?> response = authService.registerGuide(request);
//
//        // THEN
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(guideRepo, times(1)).save(any(Guide.class));
//    }
//
//    @Test
//    void registerGuide_Echec_UsernameExiste() {
//        // GIVEN
//        RegisterGuideRequest request = new RegisterGuideRequest();
//        request.setUsername("guide1");
//
//        when(utilisateurRepo.findByUsername("guide1")).thenReturn(Optional.of(utilisateurExemple));
//
//        // WHEN
//        ResponseEntity<?> response = authService.registerGuide(request);
//
//        // THEN
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("Ce username est déjà utilisé", response.getBody());
//        verify(guideRepo, never()).save(any(Guide.class));
//    }
//
//    // ==========================================
//    // TESTS POUR REGISTER TOURISTE()
//    // ===========================================
//
//    @Test
//    void registerTouriste_Succes() {
//        // GIVEN
//        RegisterTouristeRequest request = new RegisterTouristeRequest();
//        request.setUsername("tourist1");
//        request.setEmail("tourist1@test.com");
//        request.setPaysOrigineId(1L);
//        request.setPassword("pass");
//
//        Pays mockPays = new Pays();
//        Visiteur mockVisiteurSaved = new Visiteur();
//        mockVisiteurSaved.setId(3L);
//        mockVisiteurSaved.setUsername("tourist1");
//        mockVisiteurSaved.setRole(RoleUser.TOURIST);
//
//        when(utilisateurRepo.findByUsername("tourist1")).thenReturn(Optional.empty());
//        when(utilisateurRepo.findByEmail("tourist1@test.com")).thenReturn(Optional.empty());
//        when(paysRepo.findById(1L)).thenReturn(Optional.of(mockPays));
//        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");
//        when(visiteurRepo.save(any(Visiteur.class))).thenReturn(mockVisiteurSaved);
//        when(jwtUtil.generateToken("tourist1")).thenReturn("tourist-token");
//
//        // WHEN
//        ResponseEntity<?> response = authService.registerTouriste(request);
//
//        // THEN
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(visiteurRepo, times(1)).save(any(Visiteur.class));
//    }
//
//    @Test
//    void registerTouriste_Echec_EmailExiste() {
//        // GIVEN
//        RegisterTouristeRequest request = new RegisterTouristeRequest();
//        request.setUsername("tourist1");
//        request.setEmail("tourist1@test.com");
//
//        when(utilisateurRepo.findByUsername("tourist1")).thenReturn(Optional.empty());
//        when(utilisateurRepo.findByEmail("tourist1@test.com")).thenReturn(Optional.of(utilisateurExemple));
//
//        // WHEN
//        ResponseEntity<?> response = authService.registerTouriste(request);
//
//        // THEN
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//        assertEquals("Cet email est déjà utilisé", response.getBody());
//        verify(visiteurRepo, never()).save(any(Visiteur.class));
//    }
//}