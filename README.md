# jwt-auth-server-kotlin-springboot

<div align="center">

<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/kotlin/kotlin-original.svg" width="56" />
  <img src="https://cdn.worldvectorlogo.com/logos/spring-boot-1.svg" width="56" />
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original.svg" width="56" />
  <img src="https://www.vectorlogo.zone/logos/supabase/supabase-icon.svg" width="56" />
  <img src="https://cdn.worldvectorlogo.com/logos/jwt-3.svg" width="56" />

# 🔐 Kotlin Spring Boot JWT Auth API

A stateless authentication REST API built with **Kotlin + Spring Boot**, using **JWT** for secure token-based authentication and **PostgreSQL** (via **Supabase**) as the database.

</div>

---

## 🚀 Features

- ✅ User Registration & Login
- 🪙 **JWT** Access + Refresh Tokens Auth
- ♻️ Refresh Token Rotation
- 🚪 Logout with Token Revocation
- 🔑 Change Password (Logged-In Users)
- 🛡️  BCrypt Password Hashing
- 🐘 **PostgreSQL** (via 🟢 **Supabase**)

---

## 🔐 API Endpoints

| Request Methods | Endpoints               | Descriptions                       |
|--------|------------------------|-----------------------------------|
| POST   |  /auth/register        | Register a new user               |
| POST   |  /auth/authenticate    | Authenticate user & get tokens    |
| POST   |  /auth/refresh-token   | Refresh access token              |
| GET    |  /auth/users/username  | Get current user profile          |
| POST   |  /auth/logout          | Logout and revoke refresh token   |
| POST   |  /auth/change-password | Change password                   |


---

## ⚙️ Tech Stacks

| Components | Tools                                                                                                     |
|-----------|----------------------------------------------------------------------------------------------------------|
| Language  | ![Kotlin](https://img.shields.io/badge/Kotlin-1.9.25-blue?logo=kotlin)                                     |
| Framework | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen?logo=springboot)               |
| Auth      | ![JWT](https://img.shields.io/static/v1?label=JWT&message=HS256&color=purple&logoColor=blue&logo=jsonwebtokens)                            |
| Database & BaaS | <img src="https://img.shields.io/badge/Database-PostgreSQL-blue?logo=postgresql&logoColor=blue" alt="PostgreSQL"/> <img src="https://img.shields.io/badge/Platform-Supabase-3ECF8E?logo=supabase&logoColor=3ECF8E" alt="Supabase" /> |
| ORM       | <img src="https://img.shields.io/badge/Persistence-Spring%20Data%20JPA-6DB33F?logo=spring&color=green&logoColor=green" alt="Spring Data JPA"/>
                                                                                          |
| Build Tool | Gradle (Kotlin DSL)                                                                                    |                                                |

