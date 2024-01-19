package com.todo.todoapplication.global.config

import com.todo.todoapplication.global.auth.jwt.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class ConfigurationPropertiesConfig