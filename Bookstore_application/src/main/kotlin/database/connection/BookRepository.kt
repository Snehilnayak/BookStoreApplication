package database.connection

import com.fasterxml.jackson.databind.ObjectMapper
import data.Book
import data.BookTable
import data.dao.BookDao
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

fun rowToBook(row: ResultRow): Book {
    return Book(
        bookId = row[BookTable.bookId],
        title = row[BookTable.title],
        author = row[BookTable.author],
        price = row[BookTable.price]

    )

}

class BookRepository: BookDao {
    override suspend fun insert(title: String, author: String, price: Int): Book? {
        var statement: InsertStatement<Number>?=null
        DataSource.dbQuery {
            statement = BookTable.insert{ book ->
                book[BookTable.title] = title
                book[BookTable.author] = author
                book[BookTable.price] = price
            }

        }
        return statement?.resultedValues?.let { rowToBook(it.get(0)) }

    }

    override suspend fun getAllBooks(): List<Book> =
        DataSource.dbQuery {
            BookTable.selectAll().mapNotNull {
                rowToBook(it)
            }
        }

    override suspend fun getBookById(bookId: Int): Book? =
        DataSource.dbQuery {
            BookTable.select{BookTable.bookId.eq(bookId)}.map {
                rowToBook(it)

            }.singleOrNull()
        }

    override suspend fun deleteById(bookId: Int): Int =
        DataSource.dbQuery {
            BookTable.deleteWhere { BookTable.bookId.eq(bookId) }
        }


    override suspend fun update(bookId: Int, title: String, author: String, price: Int): Int =
        DataSource.dbQuery {
            BookTable.update ({BookTable.bookId.eq(bookId)}) {book->
                book[BookTable.title] = title
                book[BookTable.author] = author
                book[BookTable.price] = price
            }

        }
}