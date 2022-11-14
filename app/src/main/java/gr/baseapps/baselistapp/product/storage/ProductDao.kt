package gr.baseapps.baselistapp.product.storage

import androidx.room.*
import gr.baseapps.baselistapp.product.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>

    @Query("SELECT * from products WHERE id = :id")
    suspend fun get(id: Long): Product

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<Product>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Product)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(items: List<Product>)

    @Delete
    suspend fun delete(item: Product)

    @Delete
    suspend fun delete(items: List<Product>)

    @Query("DELETE FROM products")
    suspend fun deleteAll()
}