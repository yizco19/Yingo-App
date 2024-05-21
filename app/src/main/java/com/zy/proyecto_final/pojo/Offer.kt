package com.zy.proyecto_final.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "offer")
data class Offer(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var title : String?= null,
    var description : String?= null,
    var discount : Double?= null,
    var startDate : Date ?= null,
    var endDate : Date ?= null
)