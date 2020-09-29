import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class EmployeeData(

        @SerializedName("login_id") var login_id: Int,
        @SerializedName("email") var email: String,
        @SerializedName("pin") var pin: String,
        @SerializedName("firstname") var firstname: String,
        @SerializedName("lastname") var lastname: String,
        @SerializedName("nickname") var nickname: String,
        @SerializedName("phone") var phone: String,
        @SerializedName("alternate_phone") var alternate_phone: String,
        @SerializedName("country_id") var country_id: Int,
        @SerializedName("state_id") var state_id: String,
        @SerializedName("city") var city: String,
        @SerializedName("postal_code") var postal_code: String,
        @SerializedName("address") var address: String,
        @SerializedName("stylist_type") var stylist_type: Int,
        @SerializedName("photo") var photo: String
)