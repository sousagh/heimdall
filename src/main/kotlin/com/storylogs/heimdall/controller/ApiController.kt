package com.storylogs.heimdall.controller

import com.storylogs.heimdall.model.Message
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ApiController {

    @RequestMapping(value = "/api/user", method = [RequestMethod.GET])
    @PreAuthorize("hasRole('USER')")
    fun user(): Mono<ResponseEntity<*>> {
        return Mono.just(ResponseEntity.ok<Any>(Message("Content for user")))
    }

    @RequestMapping(value = "/api/admin", method = [RequestMethod.GET])
    @PreAuthorize("hasRole('ADMIN')")
    fun admin(): Mono<ResponseEntity<*>> {
        return Mono.just(ResponseEntity.ok<Any>(Message("Content for admin")))
    }

    @RequestMapping(value = "/api/user-or-admin", method = [RequestMethod.GET])
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun userOrAdmin(): Mono<ResponseEntity<*>> {
        return Mono.just(ResponseEntity.ok<Any>(Message("Content for user or admin")))
    }

    @RequestMapping(value = "/api/public", method = [RequestMethod.GET])
    fun public(): Mono<ResponseEntity<*>> {
        return Mono.just(ResponseEntity.ok<Any>(Message("Public content!")))
    }
}