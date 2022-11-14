package gr.baseapps.baselistapp.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.baseapps.baselistapp.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _product = MutableStateFlow<UIState<Product>>(UIState.IDLE)
    val product: StateFlow<UIState<Product>> = _product

    fun getProduct(productID: Long){
        _product.value = UIState.InProgress
        viewModelScope.launch {
            _product.value = try {
                UIState.Result(repository.getProduct(productID))
            }catch (ex: Exception){
                UIState.Error(ex)
            }
        }
    }

}