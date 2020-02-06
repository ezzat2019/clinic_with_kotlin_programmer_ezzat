package com.example.programmer.click.pojo

import java.io.Serializable

data class ClinicModel(
    val key: String = "", val adress: String = ""
    , val img: String = "", val rate: Double = 0.0, val time: String = "00:00-00:00 am"
) : Serializable {
    override fun toString(): String {
        return "ClinicModel(adress='$adress', img='$img', rate='$rate', time='$time')"
    }
}