package com.zy.proyecto_final.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.zy.proyecto_final.enum.OrderStatus
import java.net.Inet4Address
import java.util.Date

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            childColumns = ["user_id"],
            parentColumns = ["id"],
        ),
        ForeignKey(
            entity = Product::class,
            childColumns = ["product_id"],
            parentColumns = ["id"],
        )
    ]
)
data class Order(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var user_id: Int? = 0,
    var product_id: Int? = 0,
    var product_count: Int? = 0,
    var address: String? = "",
    var mobile: String? = "",
)
