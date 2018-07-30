package id.revze.myfriendapp.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Friend(@Id var id: Long = 0,
             var name: String,
             var birthDate: String,
             var gender: Int)