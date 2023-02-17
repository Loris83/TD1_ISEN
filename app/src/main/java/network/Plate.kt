package network

import com.google.gson.annotations.SerializedName

class Plate(
    @SerializedName("name_fr") val name: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("prices") val prices: List<Price>,
    @SerializedName("ingredients") val ingredients: List<Ingredient>
    ): java.io.Serializable

