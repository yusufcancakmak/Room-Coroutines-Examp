package com.yusufcancakmak.roomdatabase_withcoroutine

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(

    var firstname:String="",
    var lastName:String="",
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}