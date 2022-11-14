package gr.baseapps.baselistapp.product

import gr.baseapps.baselistapp.product.Product
import retrofit2.http.GET

interface ProductService {

    @GET("products")
    suspend fun getProducts(): List<Product>

}