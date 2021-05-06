package app.appworks.school.stylish.catalog.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.appworks.school.stylish.NavigationDirections
import app.appworks.school.stylish.catalog.CatalogTypeFilter
import app.appworks.school.stylish.databinding.FragmentCatalogItemBinding
import app.appworks.school.stylish.ext.getVmFactory
import app.appworks.school.stylish.network.LoadApiStatus

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class CatalogItemFragment(private val catalogType: CatalogTypeFilter) : Fragment() {

    /**
     * Lazily initialize our [CatalogItemViewModel].
     */
    private val viewModel by viewModels<CatalogItemViewModel> { getVmFactory(catalogType) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentCatalogItemBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.recyclerCatalogItem.adapter = PagingAdapter(PagingAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
                viewModel.onDetailNavigated()
            }
        })

        viewModel.pagingDataProducts.observe(viewLifecycleOwner, Observer {
            (binding.recyclerCatalogItem.adapter as PagingAdapter).submitList(it)
        })

        binding.layoutSwipeRefreshCatalogItem.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it != LoadApiStatus.LOADING)
                    binding.layoutSwipeRefreshCatalogItem.isRefreshing = false
            }
        })

        return binding.root
    }
}