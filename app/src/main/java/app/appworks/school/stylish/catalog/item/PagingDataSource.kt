package app.appworks.school.stylish.catalog.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import app.appworks.school.stylish.R
import app.appworks.school.stylish.StylishApplication
import app.appworks.school.stylish.catalog.CatalogTypeFilter
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.data.Result
import app.appworks.school.stylish.network.LoadApiStatus
import app.appworks.school.stylish.util.Util.getString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class PagingDataSource(val type: CatalogTypeFilter) : PageKeyedDataSource<String, Product>() {

    // init load status for observe

    private val _statusInitialLoad = MutableLiveData<LoadApiStatus>()

    val statusInitialLoad: LiveData<LoadApiStatus>
        get() = _statusInitialLoad

    // init load error for observe
    private val _errorInitialLoad = MutableLiveData<String>()

    val errorInitialLoad: LiveData<String>
        get() = _errorInitialLoad

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    /**
     * Initial load api
     */
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Product>) {
//        Logger.d("[${type.value}] loadInitial") // open it if you want to observe status

        coroutineScope.launch {

            _statusInitialLoad.value = LoadApiStatus.LOADING

            val result = StylishApplication.instance.stylishRepository
                .getProductList(type = type.value)
            when (result) {
                is Result.Success -> {
                    _errorInitialLoad.value = null
                    _statusInitialLoad.value = LoadApiStatus.DONE
//                    Logger.d("[${type.value}] loadInitial.result=${result.data.products}") // open it if you want to observe status
//                    Logger.d("[${type.value}] loadInitial.paging=${result.data.paging}") // open it if you want to observe status
                    result.data.products?.let { callback.onResult(it, null, result.data.paging) }
                }
                is Result.Fail -> {
                    _errorInitialLoad.value = result.error
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _errorInitialLoad.value = result.exception.toString()
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
                else -> {
                    _errorInitialLoad.value = getString(R.string.you_know_nothing)
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    /**
     * After initial load, it will according to paging key to load api
     */
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Product>) {
//        Logger.d("[${type.value}] loadAfter.key=${params.key}") // open it if you want to observe status

        coroutineScope.launch {
            val result = StylishApplication.instance.stylishRepository
                .getProductList(type = type.value, paging = params.key)
            when (result) {
                is Result.Success -> {
//                    Logger.d("[${type.value}] loadAfter.result=${result.data}") // open it if you want to observe status
//                    Logger.d("[${type.value}] loadAfter.paging=${result.data.paging}") // // open it if you want to observe status
                    result.data.products?.let { callback.onResult(it, result.data.paging) }
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Product>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}