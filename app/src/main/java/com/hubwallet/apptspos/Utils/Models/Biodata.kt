import com.google.gson.annotations.SerializedName

data class Biodata (

		@SerializedName("experience_year") val experience_year : String,
		@SerializedName("experience_month") val experience_month : String,
		@SerializedName("work_location") val work_location : String,
		@SerializedName("note") val note : String,
		@SerializedName("services") val services : String
)