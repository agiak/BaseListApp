package gr.baseapps.baselistapp.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class Product(

    @field:SerializedName("Id")
    @PrimaryKey
    val id: Long,

    @field:SerializedName("Name")
    @ColumnInfo(name = "name")
    val name: String?,

    @field:SerializedName("Price")
    @ColumnInfo(name = "price")
    val price: String?,

    @field:SerializedName("Thumbnail")
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String?,

    @field:SerializedName("Description")
    @ColumnInfo(name = "description")
    val description: String?,

    @field:SerializedName("Image")
    @ColumnInfo(name = "image")
    val image: String?
)
