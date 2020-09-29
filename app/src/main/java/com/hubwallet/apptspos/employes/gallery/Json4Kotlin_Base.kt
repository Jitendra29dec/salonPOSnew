import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.hubwallet.apptspos.base.BaseResponse

@Keep
data class Json4Kotlin_Base(
        @SerializedName("result")
        val result: ArrayList<Gallerydetails> = ArrayList<Gallerydetails>()
) : BaseResponse()