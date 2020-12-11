package com.example.election

class district {
    companion object Factory {
        fun create(): district = district()
    }

    var district_name: String? = null
    var province_name: String? = null
}

