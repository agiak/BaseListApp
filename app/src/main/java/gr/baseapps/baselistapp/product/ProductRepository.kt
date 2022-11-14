package gr.baseapps.baselistapp.product

import gr.baseapps.baselistapp.BuildConfig
import gr.baseapps.baselistapp.product.storage.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val service: ProductService
) {

    suspend fun insertProduct(product: Product) = productDao.insert(product)

    suspend fun deleteProduct(product: Product) = productDao.delete(product)

    suspend fun insertProducts(products: List<Product>) = productDao.insert(products)

    suspend fun deleteProducts(products: List<Product>) = productDao.delete(products)

    suspend fun getProducts(): List<Product> = withContext(Dispatchers.IO) {
        productDao.getAll()
    }

    suspend fun isProductsEmpty(): Boolean = withContext(Dispatchers.IO) {
        val products = productDao.getAll()
        products.isEmpty()
    }

    suspend fun getProduct(productID: Long): Product = withContext(Dispatchers.IO) {
        productDao.get(productID)
    }

    suspend fun updateProducts(): List<Product> =
        withContext(Dispatchers.IO) {
            val newProducts = if (BuildConfig.IS_DEMO_APP)
                getDummyProducts() else service.getProducts()
            productDao.deleteAll()
            insertProducts(newProducts)
            newProducts
        }

    suspend fun syncProducts(): List<Product> =
        withContext(Dispatchers.IO) {
            insertProducts(
                if (BuildConfig.IS_DEMO_APP) getDummyProducts() else service.getProducts()
            )
            getProducts()
        }

    private fun getDummyProducts(): List<Product> {
        val randomSize = (1..1000).random()
        return ArrayList<Product>().apply {
            for (i in 1 until randomSize) {
                this.add(
                    Product(
                        id = i.toLong(),
                        name = "Product $i",
                        price = "${i * 5}",
                        thumbnail = null,
                        description = "Product $i description.",
                        image = null
                    )
                )
            }
        }
    }
}