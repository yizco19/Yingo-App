package com.zy.proyecto_final

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zy.proyecto_final.dao.CarDao
import com.zy.proyecto_final.dao.CategoryDao
import com.zy.proyecto_final.dao.OrderDao
import com.zy.proyecto_final.dao.ProductDao
import com.zy.proyecto_final.dao.UserDao
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Order
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.pojo.User

@Database(
    entities = [
        User::class,
    Category::class,
    Product::class,
    Car::class,
    Order::class
    ], version = 7, exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase()  {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun carDao(): CarDao
    abstract fun orderDao(): OrderDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    "aplicacion_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCallback)
                    .build()

            return instance!!
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

            }
        }
    }

}