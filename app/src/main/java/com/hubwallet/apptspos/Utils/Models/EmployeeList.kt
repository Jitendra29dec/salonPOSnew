import androidx.annotation.Keep
import com.clover.remote.client.messages.BaseResponse
import com.google.gson.annotations.SerializedName



@Keep
data class EmployeeList (

	//	@SerializedName("status") var status : Int,
		@SerializedName("result") var result : EmployeeData?
		/*@SerializedName("service") var service : List<EmployeeService>,
		@SerializedName("schedule") var schedule : List<String>,
		@SerializedName("biodata") var biodata : Biodata*/
): com.hubwallet.apptspos.base.BaseResponse()