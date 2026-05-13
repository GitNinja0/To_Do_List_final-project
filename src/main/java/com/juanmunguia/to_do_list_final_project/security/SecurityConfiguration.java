package com.juanmunguia.to_do_list_final_project.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.juanmunguia.to_do_list_final_project.auth.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${api-endpoint}")
    String endpoint;

    @Autowired
    private AuthenticationEntryPoint CustomAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfiguration(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .logout(out -> out
                        .logoutUrl(endpoint + "/logout")
                        .deleteCookies("JSESSIONID"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(endpoint + "/auth/register").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, endpoint + "/auth/login").hasAnyRole("USER", "ADMIN", "GESTOR")
                        .requestMatchers(HttpMethod.GET, endpoint + "/users/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, endpoint + "/users/changePassword/{id}")
                        .hasAnyRole("USER", "ADMIN", "GESTOR")
                        .requestMatchers(HttpMethod.PUT, endpoint + "/users/changeFullName/{id}")
                        .hasAnyRole("USER", "ADMIN", "GESTOR")
                        .requestMatchers(HttpMethod.PUT, endpoint + "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, endpoint + "/users/*/promote").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, endpoint + "/users/*/demote").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, endpoint + "/roles/changeRole/{id}").hasRole("ADMIN")
                        .requestMatchers(endpoint + "/manager/categories/**").hasAnyRole("ADMIN", "GESTOR")
                        .requestMatchers(endpoint + "/categories/**").hasAnyRole("USER", "ADMIN", "GESTOR")
                        .requestMatchers(endpoint + "/task/**").hasAnyRole("USER", "ADMIN", "GESTOR")
                        .requestMatchers(endpoint + "/tag/**").hasAnyRole("USER", "ADMIN", "GESTOR")
                        .requestMatchers(endpoint + "/dashboard/**").hasAnyRole("USER", "ADMIN", "GESTOR")
                        .requestMatchers(endpoint + "/user/profile").hasAnyRole("USER", "ADMIN", "GESTOR")
                        .requestMatchers(endpoint + "/task/**").hasAnyRole("USER", "ADMIN", "GESTOR")
                        .anyRequest().authenticated())
                .userDetailsService(jpaUserDetailsService)
                .httpBasic(basic -> basic.authenticationEntryPoint(CustomAuthenticationEntryPoint))
                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandler))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.headers(header -> header.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration
                .setAllowedOrigins(Arrays.asList("http://localhost:5173", "https://printgo.factoriaf5asturias.org/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}