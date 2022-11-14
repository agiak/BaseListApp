package gr.baseapps.baselistapp.product

import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.baseapps.baselistapp.product.storage.ProductDao
import gr.baseapps.baselistapp.storage.MyDatabase
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductModule {

    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService = retrofit.create(ProductService::class.java)

    @Provides
    fun provideRunDao(db: MyDatabase) = db.getRunDao()

    @Singleton
    @Provides
    fun provideProductRepo(productDao: ProductDao, service: ProductService) = ProductRepository(productDao, service)
}