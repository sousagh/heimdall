package com.storylogs.heimdall.security

import com.storylogs.heimdall.model.Role
import com.storylogs.heimdall.model.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*


@Service
class UserService {

    //username:passwowrd -> user:user
    private val userUsername = "user"// password: user
    private val user = User(userUsername, "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Arrays.asList(Role.ROLE_USER))

    //username:passwowrd -> admin:admin
    private val adminUsername = "admin"// password: admin
    private val admin = User(adminUsername, "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true, Arrays.asList(Role.ROLE_ADMIN))

    fun findByUsername(username: String): Mono<User> {
        return if (username == userUsername) {
            Mono.just<User>(user)
        } else if (username == adminUsername) {
            Mono.just<User>(admin)
        } else {
            Mono.empty<User>()
        }
    }

}