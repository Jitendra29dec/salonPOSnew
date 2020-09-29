import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class Gallerydetails(
        @SerializedName("id") val id: Int,
        @SerializedName("image_name") val image_name: String,
        @SerializedName("image") val image: String
)