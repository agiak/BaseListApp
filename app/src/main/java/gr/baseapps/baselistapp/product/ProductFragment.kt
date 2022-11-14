package gr.baseapps.baselistapp.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import gr.baseapps.baselistapp.R
import gr.baseapps.baselistapp.databinding.FragmentProductBinding
import gr.baseapps.baselistapp.ui.UIState
import gr.baseapps.baselistapp.ui.utils.hide
import gr.baseapps.baselistapp.ui.utils.show
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()

    private val args: ProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initSubscriptions()
        viewModel.getProduct(args.productID)
    }

    private fun initSubscriptions(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.product.collect {
                    when (it) {
                        is UIState.Result -> {
                            binding.loader.hide()
                            updateViews(it.data)
                        }
                        is UIState.Error -> {
                            binding.loader.hide()
                            Toast.makeText(requireContext(),"Error ${it.error.message}", Toast.LENGTH_LONG).show()
                        }
                        UIState.InProgress -> {
                            binding.loader.show()
                        }
                        else -> {
                            binding.loader.hide()
                        }
                    }
                }
            }
        }
    }

    private fun initViews(){
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateViews(product: Product){
        Glide.with(requireContext())
            .load(product.image)
            .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.ic_product_placeholder))
            .into(binding.image)

        binding.nameCost.text = "${product.name} ${product.price}"
        binding.description.text = product.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
