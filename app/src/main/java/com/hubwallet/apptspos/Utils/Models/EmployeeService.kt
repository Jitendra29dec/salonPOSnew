import com.google.gson.annotations.SerializedName

data class EmployeeService (

		@SerializedName("id") val id : Int,
		@SerializedName("stylist_id") val stylist_id : Int,
		@SerializedName("service_id") val service_id : Int,
		@SerializedName("price") val price : Double,
		@SerializedName("duration") val duration : Int,
		@SerializedName("service_name") val service_name : String
)