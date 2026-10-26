
package com.jobconnect.config;

import java.util.List;

import com.jobconnect.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .cors(cors ->
                cors.configurationSource(corsConfigurationSource())
            )

            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/api/auth/**")
                .permitAll()

                .requestMatchers(HttpMethod.POST, "/api/users")
                .permitAll()

                // Recruiters must authenticate to view their own jobs.
                .requestMatchers(HttpMethod.GET, "/api/jobs/my-jobs")
                .hasRole("RECRUITER")

                // Public job browsing.
                .requestMatchers(HttpMethod.GET, "/api/jobs/**")
                .permitAll()

                .requestMatchers(HttpMethod.POST, "/api/jobs/**")
                .hasRole("RECRUITER")

                .requestMatchers(HttpMethod.PUT, "/api/jobs/**")
                .hasRole("RECRUITER")

                .requestMatchers(HttpMethod.DELETE, "/api/jobs/**")
                .hasRole("RECRUITER")

                .requestMatchers(HttpMethod.POST, "/api/applications")
                .hasRole("CANDIDATE")
                
                .requestMatchers(HttpMethod.GET, "/api/applications/my-applications")
                .hasRole("CANDIDATE")

                .requestMatchers(HttpMethod.PUT, "/api/applications/**")
                .hasRole("RECRUITER")

                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                .anyRequest()
                .authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
            List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

