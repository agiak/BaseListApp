package gr.baseapps.baselistapp.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import gr.baseapps.baselistapp.product.Product
import gr.baseapps.baselistapp.product.storage.ProductDao

@Database(
    entities = [Product::class],
    version = 1
)
abstract class MyDatabase: RoomDatabase() {

    abstract fun getRunDao(): ProductDao
}
