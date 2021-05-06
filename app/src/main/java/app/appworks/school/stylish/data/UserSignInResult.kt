package app.appworks.school.stylish.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created by Wayne Chen in Jul. 2019.
 */
@Parcelize
data class UserSignInResult(
    val error: String? = null,
    @Json(name = "data") val userSignIn: UserSignIn? = null
    ) : Parcelable
