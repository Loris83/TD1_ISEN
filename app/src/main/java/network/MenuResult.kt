package network

import com.google.gson.annotations.SerializedName

class MenuResult(@SerializedName("data") val data: List<Category>): java.io.Serializable {
//"serializedname "data" -> notre variable val data va chercher ses valeurs dans le JSON


}