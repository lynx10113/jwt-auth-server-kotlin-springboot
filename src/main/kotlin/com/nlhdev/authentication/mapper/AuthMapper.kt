package com.nlhdev.authentication.mapper

import com.nlhdev.authentication.dto.UserDetail
import com.nlhdev.authentication.entity.User
import org.springframework.stereotype.Component

@Component
class AuthMapper: Mapper<UserDetail, User> {
    override fun mapToDomain(entity: User): UserDetail {
        return UserDetail(
            id = entity.id,
            email = entity.email,
            username = entity.username
        )
    }

//    override fun mapToEntity(domain: UserDTO): User {
//        return User(
//            id = domain.id,
//            email = domain.email.toString(),
//            username = domain.username.toString(),
//            hashedPassword = domain.password.toString()
//        )
//    }
}