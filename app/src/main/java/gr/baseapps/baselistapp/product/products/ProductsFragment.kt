package gr.baseapps.baselistapp.product.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import gr.baseapps.baselistapp.databinding.FragmentProductsBinding
import gr.baseapps.baselistapp.ui.UIState
import gr.baseapps.baselistapp.ui.utils.addTitleElevationAnimation
import gr.baseapps.baselistapp.ui.utils.hide
import gr.baseapps.baselistapp.ui.utils.show
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModels()

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val TAG = ProductsFragment::class.java.simpleName

    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initSubscriptions()
        viewModel.getProducts()
    }

    private fun initViews(){
        binding.productsList.addTitleElevationAnimation(binding.title)
        productAdapter = ProductAdapter {
            findNavController().navigate(ProductsFragmentDirections.actionNavProductsToNavProduct(it.id))
        }
        binding.productsList.adapter = productAdapter


        binding.swipeRefresh.setOnRefreshListener {
            viewModel.updateProducts()
        }
    }

    private fun initSubscriptions(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collect {
                    when (it) {
                        is UIState.Result -> {
                            binding.loader.hide()
                            binding.swipeRefresh.hide()
                            productAdapter.updateItems(it.data)
                        }
                        is UIState.Error -> {
                            binding.loader.hide()
                            binding.swipeRefresh.hide()
                            Toast.makeText(requireContext(),"Error ${it.error.message}",Toast.LENGTH_LONG).show()
                        }
                        UIState.InProgress -> {
                            binding.loader.show()
                            binding.swipeRefresh.hide()
                        }
                        UIState.Refreshing -> {
                            binding.swipeRefresh.show()
                        }
                        else -> {
                            binding.swipeRefresh.hide()
                            binding.loader.hide()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
