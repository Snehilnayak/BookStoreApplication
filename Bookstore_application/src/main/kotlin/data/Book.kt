package data

import kotlinx.serialization.Serializable

@Serializable
data class Book(val bookId:Int,val title: String,val author: String,val price: Int)
