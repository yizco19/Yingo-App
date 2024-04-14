package com.zy.proyecto_final.relation

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Product

@Entity
data class CategoryWithProducts (
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products: List<Product>


)