package com.dam.p1_alten_felixcorraltejero

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String,
    val email: String,
    val password: String,
    val age: Int,
    val height: Double,
    val imageUrl: String? = null
) : Parcelable {
    companion object {
        val VALID_USERS = listOf(
            User("natalia", "natalia@gmail.com", "Prueba1", 24, 1.60, "https://randomuser.me/api/portraits/women/60.jpg"),
            User("felix", "felix@gmail.com", "Prueba2", 24, 1.70, "https://randomuser.me/api/portraits/men/62.jpg")
        )
    }
}