package com.example.programmer.click.pojo

import java.io.Serializable

data class DoctorModel(
    val key: String = "",
    val fee: String = "",
    val name: String = "",
    val spec: String = "",
    val rate: Double = 0.0
    ,
    val img: String = "",
    val experience: String = "",
    val books: String = ""
) : Serializable {
    override fun toString(): String {
        return "DotctorModel(name='$name', spec='$spec', rate=$rate, img='$img', experience='$experience', books='$books')"
    }
}