package com.adriantoral.practicaandroid.modelos

class Producto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
) {
    override fun toString(): String {
        return "Producto(id=$id, title='$title', description='$description', price=$price, image='$image', category='$category')"
    }
}