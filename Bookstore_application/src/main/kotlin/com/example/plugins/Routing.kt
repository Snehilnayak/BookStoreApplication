package com.example.plugins

import data.Book
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import database.connection.BookRepository
import io.ktor.http.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable
import validations.bookValidation

@Serializable
data class BookRequest(val title: String,val author: String,val price: Int)

fun Application.configureRouting(db:BookRepository) {
    routing {
        get("/") {
            call.respondText("Hello world")
        }
        post("/books"){
            val parameter = call.receive<Parameters>()
            val title = parameter["title"] ?: return@post call.respondText(
                text = "MISSING FIELD",
                status = HttpStatusCode.Unauthorized
            )
            val author = parameter["author"] ?: return@post call.respondText(
                text = "MISSING FIELD",
                status = HttpStatusCode.Unauthorized
            )
            val price = parameter["price"] ?: return@post call.respondText(
                text = "MISSING FIELD",
                status = HttpStatusCode.Unauthorized
            )
            try {
                val user = BookRequest(title = title, author = author, price = price.toInt())
                val validationResult = bookValidation.validate(user)

                val book = db.insert(title, author, price.toInt())
                if(validationResult.errors.isEmpty() == null){
                    call.respond("Success")
                }
                else {
                    call.respond("Errors:")
                    call.respond(validationResult.errors.forEach { call.respond(it) })
                }

            } catch (e: Throwable) {
                call.respond("${e.message}")
            }
        }
        get("/books") {
            try {
                call.respond(db.getAllBooks())

            } catch (e: Throwable) {
                call.respond("${e.message}")
            }
        }
        delete("/books/{bookId}") {
            val bookId = call.parameters["bookId"] ?: return@delete call.respondText(
                "Missing bookId",
                status = HttpStatusCode.Unauthorized
            )
            val res = db.deleteById(bookId.toInt())

            try {
                if(res==1){
                    call.respondText("Deleted Successfully")
                }
                else{
                    call.respondText("bookId $bookId not found")
                }

            }catch (e: Throwable) {
                call.respond("${e.message}")
            }
        }
        get("/books/{bookId}"){
            val bookId = call.parameters["bookId"] ?: return@get call.respondText(
                "Missing bookId",
                status = HttpStatusCode.Unauthorized
            )
            val res = db.getBookById(bookId.toInt())
            try {
                if(res == null){
                    call.respondText("bookId $bookId not found")

                }
                else{
                    call.respond(db.getBookById(bookId.toInt())!!)
                }
            } catch (e: Throwable) {
                call.respond("${e.message}")
            }
        }
        put("/books"){
            val parameter = call.receive<Parameters>()
            val bookId = parameter["bookId"] ?: return@put call.respondText(
                text = "MISSING FIELD",
                status = HttpStatusCode.Unauthorized
            )
            val title = parameter["title"] ?: return@put call.respondText(
                text = "MISSING FIELD",
                status = HttpStatusCode.Unauthorized
            )
            val author = parameter["author"] ?: return@put call.respondText(
                text = "MISSING FIELD",
                status = HttpStatusCode.Unauthorized
            )
            val price = parameter["price"] ?: return@put call.respondText(
                text = "MISSING FIELD",
                status = HttpStatusCode.Unauthorized
            )
            try {
                val res = db.update(bookId.toInt(), title, author, price.toInt())
                if(res==1){
                    call.respond(db.getAllBooks())
                }else{
                    call.respondText("bookId not found")

                }
            } catch (e: Throwable) {
                call.respond("${e.message}")
            }

        }
    }
}
