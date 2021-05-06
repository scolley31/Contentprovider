package app.appworks.school.stylish.catalog.item

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import app.appworks.school.stylish.catalog.CatalogTypeFilter
import app.appworks.school.stylish.data.Product

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Factory for PagingDataSource
 */
class PagingDataSourceFactory(val type: CatalogTypeFilter) : DataSource.Factory<String, Product>() {

    val sourceLiveData = MutableLiveData<PagingDataSource>()

    override fun create(): DataSource<String, Product> {
        val source = PagingDataSource(type)
        sourceLiveData.postValue(source)
        return source
    }
}