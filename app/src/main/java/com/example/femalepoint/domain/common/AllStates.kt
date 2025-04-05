package com.example.femalepoint.domain.common

import com.example.femalepoint.data.model.Category
import com.example.femalepoint.data.model.OrderDetails
import com.example.femalepoint.data.model.Product
import com.example.femalepoint.data.model.ProfilePicture
import com.example.femalepoint.data.model.Reels
import com.example.femalepoint.data.model.ReelsWithProductRefrence
import com.example.femalepoint.data.model.ReviewDetails
import com.example.femalepoint.data.model.Userdata

data class getCategorgy(
    val error:String="",
    val isloading:Boolean=false,
    val data:List<Category?> =emptyList()
)
data class AuthState(val error:String="",val isloading:Boolean=false,val data:String="")

data class getProduct(
    val error:String="",
    val isloading:Boolean=false,
    val data:List<Product?> =emptyList()
)

data class userState(
    val error:String = "",
    val isloading:Boolean=false,
    val data:String = "")

data class AddUserState(
    val error:String="",
    val isloading:Boolean=false,
    val data:String=""
)
data class OrderState(
    val error:String="",
    val isloading:Boolean=false,
    val data:String=""
)
data class OrderProductState(
    val error:String="",
    val isloading:Boolean=false,
    val data:List<OrderDetails> = emptyList()
)
data class getProductByIdState(
    val error: String="",
    val data: Product? = null,
    val isloading: Boolean=false
)
data class UpdateStockState(
    val error:String="",
    val data:String="",
    val isloading: Boolean=false
)
data class GetAllProductsWithoutLimitState(
    val error: String="",
    val data:List<Product> = emptyList(),
    val isloading: Boolean=false
)
data class AddToCartState(
    val error: String="",
    val data: String="",
    val isloading: Boolean=false

)
data class GetAllCartItemsState(
    val error: String="",
    val data: List<Product?> = emptyList(),
    val isloading: Boolean=false
)
data class ReviewDeatailsUploadState(
    val error: String="",
    val data: String="",
    val isloading: Boolean=false
)
data class GetMatchingState(val data:List<Product> = emptyList(),
                            val error:String ="",
                            val isloading:Boolean = false)
data class AllReviewDetailsState(
    val error: String="",
    val data: List<ReviewDetails> = emptyList(),
    val isloading: Boolean=false
)
data class SearchProductState(
    val error: String="",
    val data: List<Product> = emptyList(),
    val isloading: Boolean=false
)
data class ReelsState(
    val error: String="",
    val data: List<Reels> = emptyList(),
    val isloading: Boolean=false
)
data class StoreUserDataForOrderState(
    val error: String="",
    val data: String="",
    val isloading: Boolean=false
)
data class UserDataStoreState(
    val error: String="",
    val data: Userdata?=null,
    val isloading: Boolean=false
)

data class ProfileUpdateState(
    val error: String="",
    val data: String="",
    val isloading: Boolean=false
)

data class GetProfilePictureAfterUpdateState(
    val error: String="",
    val data: ProfilePicture?=null,
    val isloading: Boolean=false
)

data class GetProfilePictureByUserIDState(
    val error: String="",
    val data: ProfilePicture?=null,
    val isloading: Boolean=false
)

data class ProfileUserDataState(
    val error: String="",
    val data: Userdata?=null,
    val isloading: Boolean=false
)

data class GetProductByIdStateQ(
    val error: String="",
    val data: Product?=null,
    val isloading: Boolean=false
)

data class  GetReelsMappedWithProductIDState(
    val error: String="",
    val data:List<ReelsWithProductRefrence> = emptyList(),
    val isloading: Boolean=false
)