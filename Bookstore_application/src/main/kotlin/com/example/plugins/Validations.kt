package com.example.plugins

import validations.bookValidation
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
    install(RequestValidation) {
//        bookValidation()
    }

}