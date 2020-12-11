package com.example.election

class User {
    companion object Factory {
        fun create(): User = User()
    }

    var user_nic: String? = null
    var user_name: String? = null
    var user_password: String? = null
    var user_age: Int? = 0
    var user_mobile: String? = null
    var user_role: String? = null

}
