package gr.baseapps.baselistapp.product.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.baseapps.baselistapp.product.Product
import gr.baseapps.baselistapp.product.ProductRepository
import gr.baseapps.baselistapp.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _products = MutableStateFlow<UIState<List<Product>>>(UIState.IDLE)
    val products: StateFlow<UIState<List<Product>>> = _products

    fun getProducts(){
        _products.value = UIState.InProgress
        viewModelScope.launch {
            _products.value = try {
                if (repository.isProductsEmpty()){
                    UIState.Result(repository.syncProducts())
                }else{
                    UIState.Result(repository.getProducts())
                }
            }catch (ex: Exception){
                UIState.Error(ex)
            }
        }
    }

    fun updateProducts(){
        _products.value = UIState.Refreshing
        viewModelScope.launch {
            _products.value = try {
                UIState.Result(repository.updateProducts())
            }catch (ex: Exception){
                UIState.Error(ex)
            }
        }
    }

}