package com.example.femalepoint.presenation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.femalepoint.domain.common.ResultState
import com.example.femalepoint.domain.common.ResultState.Error
import com.example.femalepoint.domain.common.AddToCartState
import com.example.femalepoint.domain.common.AddUserState
import com.example.femalepoint.domain.common.AllReviewDetailsState
import com.example.femalepoint.domain.common.AuthState
import com.example.femalepoint.domain.common.GetAllCartItemsState
import com.example.femalepoint.domain.common.GetAllProductsWithoutLimitState
import com.example.femalepoint.domain.common.GetMatchingState
import com.example.femalepoint.domain.common.GetProductByIdStateQ
import com.example.femalepoint.domain.common.GetProfilePictureAfterUpdateState
import com.example.femalepoint.domain.common.GetProfilePictureByUserIDState
import com.example.femalepoint.domain.common.GetReelsMappedWithProductIDState
import com.example.femalepoint.data.model.OrderDetails
import com.example.femalepoint.domain.common.OrderProductState
import com.example.femalepoint.domain.common.OrderState
import com.example.femalepoint.data.model.Product
import com.example.femalepoint.domain.common.ProfileUpdateState
import com.example.femalepoint.domain.common.ProfileUserDataState
import com.example.femalepoint.domain.common.ReelsState
import com.example.femalepoint.domain.common.ReviewDeatailsUploadState
import com.example.femalepoint.data.model.ReviewDetails
import com.example.femalepoint.domain.common.SearchProductState
import com.example.femalepoint.domain.common.StoreUserDataForOrderState
import com.example.femalepoint.domain.common.UpdateStockState
import com.example.femalepoint.domain.common.UserDataStoreState
import com.example.femalepoint.data.model.Userdata
import com.example.femalepoint.data.model.UsersDetails
import com.example.femalepoint.domain.common.getCategorgy
import com.example.femalepoint.domain.common.getProduct
import com.example.femalepoint.domain.common.getProductByIdState
import com.example.femalepoint.domain.common.userState
import com.example.femalepoint.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MyViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    private val _matchingProductState = MutableStateFlow(GetMatchingState())
    val matchingProductState = _matchingProductState.asStateFlow()
    private val _getAllCategoryState = MutableStateFlow(getCategorgy())
    val getAllCategoryState = _getAllCategoryState.asStateFlow()
    private val _createuserstate = MutableStateFlow(AuthState())
    val createuserstate = _createuserstate.asStateFlow()
    private val _getproducts = MutableStateFlow(getProduct())
    val getproducts = _getproducts.asStateFlow()
    private val _loginstate = MutableStateFlow(AuthState())
    val loginstate = _loginstate.asStateFlow()
    private val _userDataState = MutableStateFlow(userState())
    val userDataState = _userDataState.asStateFlow()
    private val _addUserLocationState= MutableStateFlow(userState())
    val addUserLocationState=_addUserLocationState.asStateFlow()
    private val _addUserState= MutableStateFlow(AddUserState())
    val addUserState=_addUserState.asStateFlow()
    private val _orderproductstate=MutableStateFlow(OrderState())
    val orderproductstatestate=_orderproductstate.asStateFlow()
    private val  _getAllOrdersState=MutableStateFlow(OrderProductState())
    val getAllOrderState=_getAllOrdersState.asStateFlow()
    private val _getProductBySpecificID =MutableStateFlow(getProductByIdState())
    val getProductBySpecificID =_getProductBySpecificID.asStateFlow()
    private val _updateStockState= MutableStateFlow(UpdateStockState())
    val updateStockState=_updateStockState.asStateFlow()
    private val _getAllProductsWithoutLimitState= MutableStateFlow(GetAllProductsWithoutLimitState())
    val getAllProductsWithoutLimitState=_getAllProductsWithoutLimitState.asStateFlow()
    private val _addToCart = MutableStateFlow(AddToCartState())
    val addToCart=_addToCart.asStateFlow()
    private val _allCartItmesList= MutableStateFlow(GetAllCartItemsState())
    val addCartItemsList = _allCartItmesList.asStateFlow()
    private val _reviewDetailsUpload= MutableStateFlow(ReviewDeatailsUploadState())
    val reviewDetailsUpload=_reviewDetailsUpload.asStateFlow()
    private val _allRevivewDeils=MutableStateFlow(AllReviewDetailsState())
    val allReviewDetails=_allRevivewDeils.asStateFlow()
    val _searchProductState=MutableStateFlow(SearchProductState())
    val searchProductState=_searchProductState.asStateFlow()
    private val _getAllReelVideosFromStorage=MutableStateFlow(ReelsState())
    val getAllReelVideosFromStorage=_getAllReelVideosFromStorage.asStateFlow()
    private val _userDetailsForOrderState= MutableStateFlow(StoreUserDataForOrderState())
    val userDetailsForOrderState= _userDetailsForOrderState.asStateFlow()
    private val _getUserDataForStoring=MutableStateFlow(UserDataStoreState())
    val getUserDataForStoring=_getUserDataForStoring.asStateFlow()
    private  val _profilePicUpdateState= MutableStateFlow(ProfileUpdateState())
    val profilePicUpdateState=_profilePicUpdateState.asStateFlow()
    private val _getProfileAfterUpdate =MutableStateFlow(GetProfilePictureAfterUpdateState())
    val getProfileAfterUpdate=_getProfileAfterUpdate.asStateFlow()
    private val _getProfilePictureByUserIDState= MutableStateFlow(GetProfilePictureByUserIDState())
    val getProfilePictureByUserIDState=_getProfilePictureByUserIDState.asStateFlow()
    private val  _getUserProfileDeailsDataState=MutableStateFlow(ProfileUserDataState())
    val getUserProfileDeailsDataState=_getUserProfileDeailsDataState.asStateFlow()
    private val _getProductByIDState= MutableStateFlow(GetProductByIdStateQ())
    val getProductByIDStateState= _getProductByIDState.asStateFlow()
    private val _getReelsMappedWithProductIDState = MutableStateFlow(
        GetReelsMappedWithProductIDState()
    )
    val getReelsMappedWithProductIDState=_getReelsMappedWithProductIDState.asStateFlow()

    fun getAllCategories() {
        viewModelScope.launch {
            repository.getcategory().collectLatest { it ->
                when (it) {
                    is Error -> {
                        _getAllCategoryState.value = getCategorgy(error = it.message.toString())
                    }

                    is ResultState.Loading -> {
                        _getAllCategoryState.value = getCategorgy(isloading = true)
                    }

                    is ResultState.Sucess -> {
                        _getAllCategoryState.value = getCategorgy(data = it.data)
                    }
                }
            }
        }
    }

    fun emailPasswordAuthentication(email: String, password: String) {
        viewModelScope.launch {

            repository.emailpasswordAuthentication(email, password).collectLatest { it ->
                when (it) {
                    is ResultState.Loading -> {
                        _createuserstate.value = AuthState(isloading = true)

                    }

                    is Error -> {
                        _createuserstate.value = AuthState(error = it.message.toString())
                    }

                    is ResultState.Sucess -> {
                        _createuserstate.value = AuthState(data = it.data)
                    }
                }
            }


        }

    }

    fun loginusingEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            repository.loginUsingEmailPassword(email, password).collectLatest { it ->
                when (it) {
                    is ResultState.Loading -> {
                        _loginstate.value = AuthState(isloading = true)
                    }

                    is Error -> {
                        _loginstate.value = AuthState(error = it.message.toString())
                    }

                    is ResultState.Sucess -> {
                        _loginstate.value = AuthState(data = it.data)

                    }
                }
            }

        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            repository.getAllProduct().collectLatest { it ->
                when (it) {
                    is ResultState.Sucess -> {
                        _getproducts.value = getProduct(data = it.data)
                    }

                    is Error -> {
                        _getproducts.value = getProduct(error = it.message.toString())
                    }

                    is ResultState.Loading -> {
                        _getproducts.value = getProduct(isloading = true)
                    }
                }
            }

        }

    }

    fun addUserLocationDetails(
        name: String, email: String, phonenumber: String, phonenumber2: String,
        address: String,  pincode: String, state: String,
        age: String, nearbyPoints: String
    ) {
        viewModelScope.launch {
            repository.addUserLocationDetails(
                name,
                email,
                phonenumber,
                phonenumber2,
                address,
                pincode,
                state,
                age,
                nearbyPoints
            ).collectLatest { it ->
             when(it){
                 is ResultState.Loading->{
                     _addUserLocationState.value= userState(isloading = true)
                 }
                 is ResultState.Sucess->{
                     _addUserLocationState.value=userState(data = it.data,isloading = false)
                 }
                 is Error->{
                     _addUserLocationState.value=userState(error = it.message.toString(),isloading = false)
                 }
             }

            }

        }
    }

    fun addUserDetails(usersDetails: UsersDetails) {
        viewModelScope.launch {
            repository.addUserDetails(usersDetails=usersDetails).collectLatest { it ->
                when(it){
                  is ResultState.Loading->{
                      _addUserState.value= AddUserState(error = "", isloading = true)

                  }
                  is ResultState.Sucess->{
                      _addUserState.value= AddUserState(data = it.data,isloading = false)
                  }
                       is Error->{
                        _addUserState.value= AddUserState(error = it.message.toString(),isloading = false)
                    }


                }

            }

        }


    }
    fun placeOrder(orderDetails: OrderDetails){
        viewModelScope.launch {
            repository.placeOrder(orderDetails).collectLatest { it ->
                when(it){
                    is ResultState.Loading->{
                      _orderproductstate.value= OrderState(error = "",isloading = true)
                    }
                    is ResultState.Sucess->{
                        _orderproductstate.value= OrderState(error = "",data = it.data,isloading = false)
                    }
                    is Error->{
                       _orderproductstate.value= OrderState(error = it.message.toString(),isloading = false)
                    }
                }
            }
        }

    }
    fun getAllOrders(){

        viewModelScope.launch {
            repository.getAllOrders().collectLatest {
                when(it){
                    is ResultState.Loading->{
                      _getAllOrdersState.value=OrderProductState(error = "",isloading = true)
                    }
                    is ResultState.Sucess->{
                      _getAllOrdersState.value=
                          OrderProductState(error = "",data = it.data,isloading = false)
                    }
                    is Error->{
                      _getAllOrdersState.value=OrderProductState(error = it.message.toString(),isloading = false)
                    }
                }
            }
        }
    }
    fun getSpecificProductByID(productID:String) {
      viewModelScope.launch {
          repository.getSpecificProduct(productID = productID).collectLatest {
              when(it){
                  is ResultState.Loading->{
                      _getProductBySpecificID.value = getProductByIdState(error = "", isloading = true)

                  }
                  is ResultState.Sucess->{
                      _getProductBySpecificID.value = getProductByIdState(error = "", data = it.data, isloading = false)
                  }
                  is Error->{
                      _getProductBySpecificID.value = getProductByIdState(error = it.message.toString(), isloading = false)
                  }
              }
          }
      }

    }
    fun updateStockByIdAndStock(productID:String,stock:Int){
        viewModelScope.launch {
            repository.updateStockByIdAndStock(newStockvalue = stock, productID = productID).collectLatest {
                when(it){
                    is ResultState.Loading->{
                        _updateStockState.value= UpdateStockState(error = "", isloading = true)
                    }
                    is ResultState.Sucess->{
                        _updateStockState.value=UpdateStockState(error = "", data = it.data , isloading = false)
                    }
                    is Error->{
                        _updateStockState.value=UpdateStockState(error = it.message, isloading = false)
                    }
                }



            }
        }

    }
    fun getMatchingProduct(category:String){

        viewModelScope.launch {
            repository.getMatchingProduct(category).collectLatest {
                when(it){
                    is ResultState.Loading->_matchingProductState.value= GetMatchingState(isloading = true)
                    is ResultState.Sucess->_matchingProductState.value=GetMatchingState(data = it.data)
                    is Error->_matchingProductState.value=GetMatchingState(error = it.message.toString())

                }
            }

        }
    }
    fun getAllProductWithoutLimit(){
        viewModelScope.launch {
            repository.getAllProductWithoutLimit().collectLatest {
                when(it){
                    is ResultState.Loading->{
                        _getAllProductsWithoutLimitState.value=GetAllProductsWithoutLimitState(error = "",isloading = true)
                    }
                   is  ResultState.Sucess->{
                        _getAllProductsWithoutLimitState.value=GetAllProductsWithoutLimitState(error = "",data = it.data,isloading = false)
                    }
                   is  Error->{
                        _getAllProductsWithoutLimitState.value=GetAllProductsWithoutLimitState(error = it.message.toString(),isloading = false)
                    }
                }
            }
        }
    }
    fun addToCart(product: Product){
        viewModelScope.launch {
          repository.addToCart(product = product).collectLatest {
              when(it){
                  is Error->{
                      _addToCart.value= AddToCartState(error = it.message, isloading = false)
                  }
                  is ResultState.Sucess->{
                      _addToCart.value= AddToCartState(data = it.data, isloading = false)
                  }
                  is ResultState.Loading->{
                      _addToCart.value=AddToCartState(isloading = true)
                  }
              }
          }

        }
    }
    fun getAllCartProducts(){
        viewModelScope.launch {
            repository.getAllItemsFromCart().collectLatest {
                when(it){
                    is ResultState.Loading->{
                      _allCartItmesList.value= GetAllCartItemsState(isloading = true)
                    }
                    is ResultState.Sucess->{
                        _allCartItmesList.value= GetAllCartItemsState(data = it.data,isloading = false)
                    }
                    is Error->{
                        _allCartItmesList.value= GetAllCartItemsState(error = it.message.toString(),isloading = false)
                    }
                }
            }
        }
    }
    fun reviewDeatilsUpload(productID: String,imageUrl:String,reviewDetails: ReviewDetails){
        viewModelScope.launch {

            repository.reviewProduct(productID = productID,imageUrl = imageUrl
                ,reviewDetails = reviewDetails).collectLatest { it->
                    when(it){
                        is ResultState.Loading->{
                            _reviewDetailsUpload.value= ReviewDeatailsUploadState(isloading = true)
                        }
                        is ResultState.Sucess->{
                            _reviewDetailsUpload.value= ReviewDeatailsUploadState(data = it.data,isloading = false)
                        }
                        is Error->{
                            _reviewDetailsUpload.value= ReviewDeatailsUploadState(error = it.message.toString(),isloading = false)
                        }
                    }


            }
        }

    }
    fun allrevivewDetails(productID: String){

        viewModelScope.launch {
            repository.getAllReviews(productID = productID).collectLatest {
                when(it){
                    is ResultState.Loading->{
                      _allRevivewDeils.value= AllReviewDetailsState(isloading = true)
                    }
                    is ResultState.Sucess->{
                       _allRevivewDeils.value= AllReviewDetailsState(data = it.data,isloading = false)
                    }
                    is Error->{
                        _allRevivewDeils.value= AllReviewDetailsState(error = it.message.toString(),isloading = false)
                    }
                }
            }
        }
    }
    fun searchProduct(search:String){
        viewModelScope.launch {
            repository.searchProduct(search = search).collectLatest {
                when(it){
                    is ResultState.Loading->{
                   _searchProductState.value= SearchProductState(isloading = true)
                    }
                    is ResultState.Sucess->{
                        _searchProductState.value= SearchProductState(data = it.data,isloading = false)
                    }
                    is Error->{
                        _searchProductState.value= SearchProductState(error = it.message.toString(),isloading = false)
                    }
                }
            }
        }
    }
    fun getAllVideosFromServer(){
        viewModelScope.launch {
            repository.getAllReelsVideoFromStorage().collectLatest {
                when(it){
                    is ResultState.Loading->{
                        _getAllReelVideosFromStorage.value=ReelsState(isloading = true)
                    }
                    is ResultState.Sucess->{
                        _getAllReelVideosFromStorage.value=ReelsState(data = it.data,isloading = false)
                    }
                    is Error->{
                        _getAllReelVideosFromStorage.value=ReelsState(error = it.message.toString(),isloading = false)
                    }
                }
            }
        }
    }
    fun addUserDetailsForOrder(userData:Userdata){
        viewModelScope.launch {
            repository.addUserDetailsForOrder(userDetails = userData).collectLatest {
                when(it){
                    is ResultState.Loading->{
                        _userDetailsForOrderState.value=StoreUserDataForOrderState(isloading = true)

                    }
                    is Error->{
                        _userDetailsForOrderState.value=StoreUserDataForOrderState(error = it.message.toString(), isloading = false)
                    }
                    is ResultState.Sucess->{
                        _userDetailsForOrderState.value=StoreUserDataForOrderState(isloading = false, data = it.data)
                    }
                }

            }


        }

    }
    fun getUserDetailsForOrder(){
        viewModelScope.launch {

            repository.getUserDetailsForOrder().collectLatest {
                when(it){
                    is ResultState.Loading->{
                        _getUserDataForStoring.value=UserDataStoreState(isloading = true)
                    }
                    is Error->{
                        _getUserDataForStoring.value=UserDataStoreState(error = it.message.toString(), isloading = false)
                    }
                    is ResultState.Sucess->{
                        _getUserDataForStoring.value=UserDataStoreState(isloading = false, data = it.data)
                    }
                }
            }
        }



    }
    fun updateProfileImage(imageUrl: String) {
        viewModelScope.launch {
            repository.updateProfilePicture(uri = Uri.parse(imageUrl)).collectLatest {
                when(it){
                    is ResultState.Loading->{
                       _profilePicUpdateState.value= ProfileUpdateState(isloading = true)
                    }
                    is Error->{
                        _profilePicUpdateState.value= ProfileUpdateState(error = it.message.toString(), isloading = false)
                    }
                    is ResultState.Sucess->{
                        _profilePicUpdateState.value= ProfileUpdateState(isloading = false, data = it.data)
                    }
                }

            }
        }

    }
    fun getProfilePictureAfterUpdate(){
        viewModelScope.launch {
            repository.getUserProfilePicAfterUpload().collectLatest {
                when(it){
                    is Error->{
                        _getProfileAfterUpdate.value=GetProfilePictureAfterUpdateState(isloading = false, error = it.message.toString())
                    }
                    is ResultState.Loading->{
                        _getProfileAfterUpdate.value=GetProfilePictureAfterUpdateState(isloading = true)
                    }
                    is ResultState.Sucess-> {
                        _getProfileAfterUpdate.value =
                            GetProfilePictureAfterUpdateState(isloading = false, data = it.data)
                    }
                }
            }

        }

    }
    fun getProfilePictureByUserID(userID:String){
        viewModelScope.launch {
            repository.getProfilePictureByUserId(userID = userID).collectLatest {
               when(it){
                   is ResultState.Loading->{
                      _getProfilePictureByUserIDState.value=GetProfilePictureByUserIDState(isloading = true)
                   }
                   is Error->{
                      _getProfilePictureByUserIDState.value=GetProfilePictureByUserIDState(isloading = false, error = it.message.toString())
                   }
                   is ResultState.Sucess->{
                      _getProfilePictureByUserIDState.value=GetProfilePictureByUserIDState(isloading = false, data = it.data)
                   }
               }

            }
        }

    }
    fun getUserProfileDetailsDataByUserID(userID: String){
        viewModelScope.launch {
            repository.getUserDetailsByUserId(userID = userID).collectLatest {
                when(it){
                    is ResultState.Loading->{
                        _getUserProfileDeailsDataState.value= ProfileUserDataState(isloading = true)
                    }
                    is Error ->{
                        _getUserProfileDeailsDataState.value= ProfileUserDataState(isloading = false, error = it.message.toString())
                    }
                    is ResultState.Sucess->{
                        _getUserProfileDeailsDataState.value= ProfileUserDataState(isloading = false, data = it.data)
                    }

                }

            }

        }
    }

    fun getProductByID(productID:String){
        viewModelScope.launch {
            repository.getProductByID(productID = productID).collectLatest {
                when(it){
                    is ResultState.Loading->{
                        _getProductByIDState.value=GetProductByIdStateQ(isloading = true)
                    }
                   is Error ->{
                       _getProductByIDState.value= GetProductByIdStateQ(error = it.message.toString(),isloading = false)

                   }
                    is ResultState.Sucess->{
                        _getProductByIDState.value=GetProductByIdStateQ(isloading = false, data = it.data)
                    }
                }

            }
        }

    }

    fun  getReelsMappedWithProductID(){
        viewModelScope.launch {
            repository. getReelsMappedWithProductID().collectLatest {
                when(it){
                    is ResultState.Loading->{
                        _getReelsMappedWithProductIDState.value= GetReelsMappedWithProductIDState(isloading = true)
                    }
                    is Error->{
                        _getReelsMappedWithProductIDState.value=
                            GetReelsMappedWithProductIDState(error = it.message, isloading = false)
                    }
                    is ResultState.Sucess->{
                        _getReelsMappedWithProductIDState.value=GetReelsMappedWithProductIDState(isloading = false, data = it.data)
                    }

                }

            }

        }

    }

}
