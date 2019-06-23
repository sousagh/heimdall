package com.storylogs.heimdall.config

import com.storylogs.heimdall.security.AuthenticationManager
import com.storylogs.heimdall.security.SecurityContextRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfig {

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Autowired
    private val securityContextRepository: SecurityContextRepository? = null

    @Bean
    fun securitygWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
                .exceptionHandling()
                .authenticationEntryPoint { swe, e -> Mono.fromRunnable { swe.response.statusCode = HttpStatus.UNAUTHORIZED } }
                .accessDeniedHandler { swe, e -> Mono.fromRunnable { swe.response.statusCode = HttpStatus.FORBIDDEN } }.and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository!!)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/login").permitAll()
                .pathMatchers("/api/public/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .build()
    }
}