package com.storylogs.heimdall.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors


class User(private val username: String,
           private val password: String,
           private val enabled: Boolean,
           val roles: List<Role>) : UserDetails {

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return this.enabled
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return this.roles.stream()
                .map { authority -> SimpleGrantedAuthority(authority.name) }
                .collect(Collectors.toList())
    }

    @JsonIgnore
    override fun getPassword(): String? {
        return password
    }
}