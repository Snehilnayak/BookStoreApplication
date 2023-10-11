package data.dao

import data.Book

interface BookDao {
    suspend fun  insert(
        title: String,
        author: String,
        price: Int
    ): Book?

    suspend fun getAllBooks(): List<Book>?

    suspend fun getBookById(bookId: Int): Book?

    suspend fun  deleteById(bookId: Int): Int

    suspend fun update(bookId: Int,title: String, author: String, price: Int):Int
}