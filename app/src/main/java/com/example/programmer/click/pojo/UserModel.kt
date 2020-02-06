package com.example.programmer.click.pojo

import java.io.Serializable

data class UserModel(
    val name: String = "", val email: String = "", val photoUrl: String? = ""
    , val phone: String? = "", val gender: String? = ""
) : Serializable {

    override fun toString(): String {
        return "UserModel(name='$name', email='$email', photoUrl=$photoUrl, phone=$phone, gender=$gender)"
    }
}