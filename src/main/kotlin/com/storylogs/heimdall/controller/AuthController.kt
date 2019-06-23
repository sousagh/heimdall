package com.storylogs.heimdall.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import com.storylogs.heimdall.model.AuthResponse
import com.storylogs.heimdall.model.AuthRequest
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Mono
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestMapping
import com.storylogs.heimdall.security.UserService
import org.springframework.beans.factory.annotation.Autowired
import com.storylogs.heimdall.security.PBKDF2Encoder
import com.storylogs.heimdall.security.JWTUtil
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController {

    @Autowired
    private val jwtUtil: JWTUtil? = null

    @Autowired
    private val passwordEncoder: PBKDF2Encoder? = null

    @Autowired
    private val userRepository: UserService? = null

    @RequestMapping(value = "/login", method = [RequestMethod.POST])
    fun login(@RequestBody ar: AuthRequest): Mono<ResponseEntity<AuthResponse>> {
        return userRepository!!.findByUsername(ar.username)
                .map {
                    if (passwordEncoder!!.encode(ar.password) == it.password) {
                        ResponseEntity.ok(AuthResponse(jwtUtil!!.generateToken(it)))
                    } else {
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
                    }
                }.defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build())
    }

}