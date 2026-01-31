package com.ainzson.usermanagementservice.config;

import com.ainzson.usermanagementservice.exception.CustomAccessDeniedHandler;
import com.ainzson.usermanagementservice.exception.CustomBasicAuthenticationEntryPoint;
import com.ainzson.usermanagementservice.filter.AuthoritiesLoggingAtFilter;
import com.ainzson.usermanagementservice.filter.CsrfCookieFilter;
import com.ainzson.usermanagementservice.filter.RequestValidationBeforeFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProdSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        httpSecurity.securityContext( context -> context.requireExplicitSave(false))
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration corsConfiguration = new CorsConfiguration();
                        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                        corsConfiguration.setAllowCredentials(true);
                        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                        corsConfiguration.setMaxAge(3600L);
                        return corsConfiguration;
                    }
                }))
                .csrf(csrf ->
                        csrf.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                                .ignoringRequestMatchers("/create")
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .addFilterBefore(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                .requiresChannel(rcc -> rcc.anyRequest().requiresSecure())
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").authenticated() // POST without auth
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").authenticated() // GET requires auth

                        // General patterns after specific ones
                        .requestMatchers("/api/v1/users/**").authenticated() // All other user endpoints
                        .requestMatchers("/api/v1/address/**").authenticated()
                        .requestMatchers("/api/v1/roles/create").permitAll()
                        .requestMatchers("/api/v1/roles/**").authenticated()

                        // Catch-all for any other requests
                        .anyRequest().authenticated())
                .formLogin(withDefaults())
                .httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()))
                .exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

                return httpSecurity.build();
    }


//    @Bean
//    public SecurityFilterChain defaultSecurityFilterChaisn(HttpSecurity httpSecurity) throws Exception {
//
//        return httpSecurity.authorizeHttpRequests((request) ->
//                        request
//                                // Specific rules first - in order of specificity
//                                .requestMatchers(HttpMethod.POST, "/api/v1/users").authenticated() // POST without auth
//                                .requestMatchers(HttpMethod.GET, "/api/v1/users").authenticated() // GET requires auth
//
//                                // General patterns after specific ones
//                                .requestMatchers("/api/v1/users/**").authenticated() // All other user endpoints
//                                .requestMatchers("/api/v1/address/**").authenticated()
//                                .requestMatchers("/api/v1/roles/create").permitAll()
//                                .requestMatchers("/api/v1/roles/**").authenticated()
//
//                                // Catch-all for any other requests
//                                .anyRequest().authenticated()
//                )
//                .csrf(csr -> csr.disable())
//                .formLogin(flc -> flc.disable())
//                .httpBasic(withDefaults())
//                .build();
//    }
//

//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker() {
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                .password("{noop}12345")
//                .authorities("read")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password("12345")
//                .authorities("admin")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public PasswordEncoder  passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
