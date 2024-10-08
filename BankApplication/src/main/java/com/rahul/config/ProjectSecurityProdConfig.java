package com.rahul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.rahul.exceptionHandling.CustomeAccessDeniedHandler;
import com.rahul.exceptionHandling.CustomeBasicAuthenticationEntryPoint;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(smc -> smc
                        .invalidSessionUrl("/invalidSession")
                        .maximumSessions(5)
                        .maxSessionsPreventsLogin(true))
                .requiresChannel(rcc -> rcc
                        .anyRequest().requiresSecure())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans").authenticated()
                        .requestMatchers("/contacts", "/notices", "/error", "/register", "/invalidSession").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(hbc -> hbc
                        .authenticationEntryPoint(new CustomeBasicAuthenticationEntryPoint()))
                .exceptionHandling(ehc -> ehc
                        .accessDeniedHandler(new CustomeAccessDeniedHandler()));

		return http.build();

	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}

}
