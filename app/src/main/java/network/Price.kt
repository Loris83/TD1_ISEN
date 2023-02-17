package network

import com.google.gson.annotations.SerializedName

class Price(
    @SerializedName("price") val price: String
): java.io.Serializable
