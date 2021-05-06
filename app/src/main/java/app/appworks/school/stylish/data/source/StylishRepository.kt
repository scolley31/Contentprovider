package app.appworks.school.stylish.data.source

import android.database.Cursor
import androidx.lifecycle.LiveData
import app.appworks.school.stylish.data.*

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Interface to the Stylish layers.
 */
interface StylishRepository {

    suspend fun getMarketingHots(): Result<List<HomeItem>>

    suspend fun getProductList(type: String, paging: String? = null): Result<ProductListResult>

    suspend fun getUserProfile(token: String): Result<User>

    suspend fun userSignIn(fbToken: String): Result<UserSignInResult>

    suspend fun checkoutOrder(token: String, orderDetail: OrderDetail): Result<CheckoutOrderResult>

    fun getProductsInCart(): LiveData<List<Product>>

    fun getAllProductsToCursor(): Cursor

    suspend fun isProductInCart(id: Long, colorCode: String, size: String): Boolean

    suspend fun insertProductInCart(product: Product)

    suspend fun updateProductInCart(product: Product)

    suspend fun removeProductInCart(id: Long, colorCode: String, size: String)

    suspend fun clearProductInCart()

    suspend fun getUserInformation(key: String?): String

    suspend fun getDetailProduct(productId: Long): Result<SuccessDetail>
}
