package com.storylogs.heimdall.security

import com.storylogs.heimdall.model.Role
import reactor.core.publisher.Mono
import java.util.stream.Collectors
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.util.ArrayList
import io.jsonwebtoken.Claims
import org.springframework.security.core.Authentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.stereotype.Component


@Component
class AuthenticationManager : ReactiveAuthenticationManager {

    @Autowired
    private val jwtUtil: JWTUtil? = null

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials.toString()

        var username: String?
        try {
            username = jwtUtil!!.getUsernameFromToken(authToken)
        } catch (e: Exception) {
            username =
                    null
        }

        if (username != null && jwtUtil!!.validateToken(authToken)!!) {
            val claims = jwtUtil.getAllClaimsFromToken(authToken)
            val rolesMap = claims["role"] as List<*>
            val roles = ArrayList<Role>()
            for (rolemap in rolesMap) {
                roles.add(Role.valueOf(rolemap.toString()))
            }
            val auth = UsernamePasswordAuthenticationToken(
                    username, null,
                    roles.stream()
                        .map { authority -> SimpleGrantedAuthority(authority.name) }
                        .collect(Collectors.toList())
            )
            return Mono.just(auth)
        } else {
            return Mono.empty()
        }
    }
}