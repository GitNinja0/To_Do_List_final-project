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
                        .requestMatchers(HttpMethod.GET, endpoint + "/auth/login")
                        .hasAnyAuthority("USER", "ADMIN", "GESTOR", "ROLE_USER", "ROLE_ADMIN", "ROLE_GESTOR")
                        .requestMatchers(HttpMethod.GET, endpoint + "/users/")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN", "GESTOR", "ROLE_GESTOR")
                        .requestMatchers(HttpMethod.PUT, endpoint + "/users/changePassword/{id}")
                        .hasAnyAuthority("USER", "ADMIN", "GESTOR", "ROLE_USER", "ROLE_ADMIN", "ROLE_GESTOR")
                        .requestMatchers(HttpMethod.PUT, endpoint + "/users/changeFullName/{id}")
                        .hasAnyAuthority("USER", "ADMIN", "GESTOR", "ROLE_USER", "ROLE_ADMIN", "ROLE_GESTOR")
                        .requestMatchers(HttpMethod.PUT, endpoint + "/users/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, endpoint + "/users/*/promote")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, endpoint + "/users/*/demote")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, endpoint + "/roles/changeRole/{id}")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(endpoint + "/manager/categories/**")
                        .hasAnyAuthority("ADMIN", "GESTOR", "ROLE_ADMIN", "ROLE_GESTOR")
                        .requestMatchers(endpoint + "/categories/**")
                        .hasAnyAuthority("USER", "ADMIN", "GESTOR", "ROLE_USER", "ROLE_ADMIN", "ROLE_GESTOR")
                        .requestMatchers(endpoint + "/task/**")
                        .hasAnyAuthority("USER", "ADMIN", "GESTOR", "ROLE_USER", "ROLE_ADMIN", "ROLE_GESTOR")
                        .requestMatchers(endpoint + "/tag/**")
                        .hasAnyAuthority("USER", "ADMIN", "GESTOR", "ROLE_USER", "ROLE_ADMIN", "ROLE_GESTOR")
                        .requestMatchers(endpoint + "/dashboard/**")
                        .hasAnyAuthority("USER", "ADMIN", "GESTOR", "ROLE_USER", "ROLE_ADMIN", "ROLE_GESTOR")
                        .requestMatchers(endpoint + "/user/profile")
                        .hasAnyAuthority("USER", "ADMIN", "GESTOR", "ROLE_USER", "ROLE_ADMIN", "ROLE_GESTOR")
                        .requestMatchers(endpoint + "/task/**")
                        .hasAnyAuthority("USER", "ADMIN", "GESTOR", "ROLE_USER", "ROLE_ADMIN", "ROLE_GESTOR")
                        .requestMatchers("/error").permitAll()
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
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:5173",
                "https://*.vercel.app",
                "https://to-do-list-final-project-front.vercel.app"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
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