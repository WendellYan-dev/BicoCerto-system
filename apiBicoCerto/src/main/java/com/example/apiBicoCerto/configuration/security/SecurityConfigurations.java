package com.example.apiBicoCerto.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/user/register").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/test/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/user/updateProfile").authenticated()
                        .requestMatchers(HttpMethod.PATCH,"/user/updateAddress/{idAddress}").authenticated()
                        .requestMatchers(HttpMethod.POST,"/informalWorker/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/work/register").authenticated()
                        .requestMatchers(HttpMethod.PATCH,"/work/edit/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET,"/work/search/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/work/delete/{id}").authenticated()
                        .requestMatchers(HttpMethod.PATCH,"/informalWorker/updateProfile").authenticated()
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/availability/register").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/availability/delete").authenticated()
                        .requestMatchers(HttpMethod.GET,"/availability/list").authenticated()
                        .requestMatchers(HttpMethod.POST,"/booking/register").authenticated()
                        .requestMatchers(HttpMethod.PATCH,"/booking/confirm").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
