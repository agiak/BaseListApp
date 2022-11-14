package gr.baseapps.baselistapp.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.baseapps.baselistapp.BuildConfig
import gr.baseapps.baselistapp.product.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val productsRepository: ProductRepository
) : ViewModel() {

    private val _showSplash = MutableStateFlow(true)
    val showSplash = _showSplash.asStateFlow()

    init {
        showSplashDuration()
    }

    private fun showSplashDuration() {
        viewModelScope.launch {
           /* if (productsRepository.isProductsEmpty()) {
                Log.d("SplashViewModel","Products are empty. Try to sync them ")
                productsRepository.syncProducts("SplashViewModel")
            }*/
            delay(BuildConfig.SPLASH_DURATION.toLong())
            _showSplash.value = false
        }
    }
}