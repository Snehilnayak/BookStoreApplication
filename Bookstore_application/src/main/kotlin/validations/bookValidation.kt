package validations

import com.example.plugins.BookRequest
import data.Book
import io.konform.validation.*
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.ktor.server.application.*
import io.ktor.server.response.*

val bookValidation = Validation<BookRequest> {

    BookRequest::title {
            // Ensure id is greater than 0
        }
    BookRequest::author {
            minLength(3) // Ensure username has at least 3 characters
            maxLength(20) // Ensure username has at most 20 characters
        }
    BookRequest::price {
            addConstraint("ID must be greater than 0") { it > 0 }
            // Ensure the email matches a basic regex pattern for emails
        }
    }