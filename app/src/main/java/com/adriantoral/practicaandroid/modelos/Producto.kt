package com.adriantoral.practicaandroid.modelos

class Producto(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val category: String = "",
    var firebase_id: String = ""
) {
    override fun toString(): String {
        return "Producto(id=$id, fib=$firebase_id, title='$title', description='$description', price=$price, image='$image', category='$category')"
    }
}