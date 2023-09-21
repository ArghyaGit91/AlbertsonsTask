package com.example.albertsonstask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.albertsonstask.data.model.Converters
import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.presentation.utils.ProductConstants

@Database(entities = [ProductsItem::class], version = ProductConstants.DATABASE_VERSION)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}