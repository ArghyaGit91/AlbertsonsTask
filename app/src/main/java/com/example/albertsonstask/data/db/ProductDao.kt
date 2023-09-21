package com.example.albertsonstask.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.albertsonstask.data.model.ProductsItem

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getSavedProducts(): List<ProductsItem>

    @Query("SELECT * FROM product WHERE category LIKE '%' || (:category) || '%'")
    fun getSavedProducts(category: String): List<ProductsItem>

    @Query("SELECT * FROM product WHERE id = :id")
    fun getSavedProduct(id: Int): ProductsItem

    @Insert
    fun save(product: ProductsItem): Long

    @Delete
    fun remove(product: ProductsItem): Int
}