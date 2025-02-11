package com.example.femalepoint.repository

import android.util.Log

import com.example.femalepoint.common.ResultState
import com.example.femalepoint.constants.Constants
import com.example.femalepoint.data.Category
import com.example.femalepoint.data.OrderDetails
import com.example.femalepoint.data.Product
import com.example.femalepoint.data.Reels
import com.example.femalepoint.data.ReviewDetails
import com.example.femalepoint.data.Userdata
import com.example.femalepoint.data.UsersDetails
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Repository @Inject constructor(private val firebaseinstance:FirebaseFirestore) {
    private val _createuserstate = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val createuserstate = _createuserstate
    private val _loginstate = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val loginstate = _loginstate
    private val auth = FirebaseAuth.getInstance()
    private val currentuser = auth.currentUser?.uid ?: "" //uuid from firebase
    private val time = System.currentTimeMillis()
    private val storage=FirebaseStorage.getInstance()
    private val storageRefrence=storage.reference.child("videos/")
    private val dataForUserDataStore=firebaseinstance.collection(Constants.USERDEATILSFORORDER).document(currentuser).get()

    suspend fun getcategory(): Flow<ResultState<List<Category>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.CATEGORY).get().addOnSuccessListener {
            val data = it.documents.mapNotNull {
                it.toObject(Category::class.java)
            }
            trySend(ResultState.Sucess(data))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }

    suspend fun emailpasswordAuthentication(
        email: String,
        password: String
    ): Flow<ResultState<String>> {
        _createuserstate.value = ResultState.Loading

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                _createuserstate.value = ResultState.Sucess("User Created")
                Log.d("AuthSucess", "createUserWithEmail:success")
                val user = auth.currentUser

            } else {
                // If sign in fails, display a message to the user.
                _createuserstate.value = ResultState.Error(task.exception.toString())
                Log.d("AuthFail", "createUserWithEmail:failure", task.exception)

            }
        }
        return createuserstate

    }

    suspend fun loginUsingEmailPassword(
        email: String,
        password: String
    ): Flow<ResultState<String>> {
        _loginstate.value = ResultState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginstate.value = ResultState.Sucess("Login Success")
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LoginSucess", "signInWithEmail:success")
                    val user = auth.currentUser


                } else {
                    _loginstate.value = ResultState.Error(task.exception.toString())
                    // If sign in fails, display a message to the user.
                    Log.d("LoginFail", "signInWithEmail:failure", task.exception)


                }
            }
        return loginstate
    }

    suspend fun getAllProduct(): Flow<ResultState<List<Product>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.PRODUCT).limit(10).get().addOnSuccessListener {
            val data = it.documents.mapNotNull {
                it.toObject(Product::class.java)

            }
            trySend(ResultState.Sucess(data))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }

    }

    suspend fun addUserLocationDetails(
        name: String, email: String,
        phonenumber: String, phonenumber2: String,
        address: String,
        pincode: String, state: String,
        age: String, nearbyPoints: String
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        //name, email,phonenumber,phonenumber2,address,image,pincode,state,age,nearbyPoints
        val user = Userdata(
            id = currentuser,
            name = name,
            email = email,
            phonenumber = phonenumber,
            phonenumber2 = phonenumber2,
            address = address,
            pincode = pincode,
            state = state,
            age = age,
            nearbyPoints = nearbyPoints
        )
        firebaseinstance.collection(Constants.USERS).document().set(user).addOnSuccessListener {
            trySend(ResultState.Sucess("User Details Added"))

        }.addOnFailureListener {
            trySend(ResultState.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }

    suspend fun addUserDetails(usersDetails: UsersDetails): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseinstance.collection(Constants.USERS).document(currentuser).set(
                usersDetails
            ).addOnSuccessListener {
                trySend(ResultState.Sucess("User Details Added"))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
            awaitClose {
                close()
            }
        }

    suspend fun placeOrder(orderDetails: OrderDetails): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.USERS).document(currentuser)
            .collection(Constants.ORDER)
            .document("$currentuser$time").set(orderDetails).addOnSuccessListener {
                trySend(ResultState.Sucess("Order Placed"))

            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }

    suspend fun getAllOrders(): Flow<ResultState<List<OrderDetails>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.USERS).document(currentuser)
            .collection(Constants.ORDER).get()
            .addOnSuccessListener {
                val data = it.toObjects(OrderDetails::class.java)
                trySend(ResultState.Sucess(data))
                Log.d("FireBaseData", data.toString())
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }

    }

    suspend fun getSpecificProduct(productID: String): Flow<ResultState<Product>> = callbackFlow {

        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.PRODUCT).document(productID).get()
            .addOnSuccessListener {
                it.toObject(Product::class.java)

            }.addOnFailureListener {
            trySend(ResultState.Error(it.message.toString()))
        }
    }

    suspend fun updateStockByIdAndStock(
        newStockvalue: Int,
        productID: String
    ): Flow<ResultState<String>> = callbackFlow {

        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.PRODUCT).document(productID)
            .update("noOfUnits", newStockvalue).addOnSuccessListener {
                trySend(ResultState.Sucess("UPDATED STOCK"))
            }.addOnFailureListener {
                trySend(ResultState.Error("ERROR WHILE UPLOADING"))
            }
        awaitClose {
            close()
        }
    }

    suspend fun getMatchingProduct(category: String): Flow<ResultState<List<Product>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseinstance.collection(Constants.PRODUCT).whereEqualTo("category", category).get()
                .addOnSuccessListener {
                    val data = it.toObjects(Product::class.java)
                    Log.d("MATCHING", data.toString())
                    trySend(ResultState.Sucess(data))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.message.toString()))
                }
            awaitClose {
                close()
            }
        }

    suspend fun getAllProductWithoutLimit(): Flow<ResultState<List<Product>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.PRODUCT).get().addOnSuccessListener {
            val data = it.documents.mapNotNull {
                it.toObject(Product::class.java)

            }
            trySend(ResultState.Sucess(data))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }

    }
    suspend fun addToCart(product: Product):Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.USERS).document(currentuser).collection(Constants.CART).
                document("$currentuser$time").set(product).addOnSuccessListener {
                    trySend(ResultState.Sucess("ADDED TO CART"))
        }.addOnFailureListener {
            trySend(ResultState.Error("FAILED TO ADD TO CART"))
        }
        awaitClose {
            close()
        }
    }
    suspend fun getAllItemsFromCart():Flow<ResultState<List<Product>>> = callbackFlow {

        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.USERS).document(currentuser).collection(Constants.CART).get().addOnSuccessListener {
            val data = it.documents.mapNotNull {
                it.toObject(Product::class.java)
            }
           trySend(ResultState.Sucess(data))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }
   suspend fun reviewProduct(productID: String,imageUrl:String,reviewDetails: ReviewDetails):Flow<ResultState<String>> = callbackFlow{
       trySend(ResultState.Loading)
       firebaseinstance.collection(Constants.REVIEWDETAILS).document(productID).collection(Constants.USERS)
           .document(currentuser).set(reviewDetails).addOnSuccessListener {
               trySend(ResultState.Sucess("REVIEW ADDED Sucessfully"))
           }.addOnFailureListener {
               trySend(ResultState.Error(it.message.toString()))
           }
       awaitClose {
           close()
       }
   }
    suspend fun getAllReviews(productID: String):Flow<ResultState<List<ReviewDetails>>> = callbackFlow{
        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.REVIEWDETAILS).document(productID).collection(Constants.USERS).get().addOnSuccessListener {
            val data = it.documents.mapNotNull {
                it.toObject(ReviewDetails::class.java)
            }
            trySend(ResultState.Sucess(data))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }
    suspend fun searchProduct(search:String):Flow<ResultState<List<Product>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.PRODUCT).whereEqualTo("name",search).get().addOnSuccessListener {
            val data = it.documents.mapNotNull {

                it.toObject(Product::class.java)

            }
            trySend(ResultState.Sucess(data))
            Log.d("FireBaseData", data.toString())
        }.addOnFailureListener {
            trySend(ResultState.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }
    suspend fun getAllReelsVideoFromStorage(): Flow<ResultState<List<Reels>>> = flow {
        emit(ResultState.Loading)

        try {
            val storageRef = Firebase.storage.reference.child("videos/")
            val listResult = storageRef.listAll().await() // Await the list of items

            val reels = listResult.items.mapNotNull { item ->
                try {
                    Log.d("REELS", "Items found: ${listResult.items.size}")

                    val url = item.downloadUrl.await().toString()
                    Reels(url = url) // Assuming `Reels` has a `url` property
                } catch (e: Exception) {
                    null // Skip failed downloads
                }
            }

            emit(ResultState.Sucess(reels))
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "Error fetching reels"))
        }
    }
    suspend fun addUserDetailsForOrder(userDetails:Userdata):Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseinstance.collection(Constants.USERDEATILSFORORDER).document(currentuser)
            .set(userDetails)
            .addOnSuccessListener {
                trySend(ResultState.Sucess("User Details Added"))

            }.addOnFailureListener {
                 trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }
    suspend fun getUserDetailsForOrder():Flow<ResultState<Userdata>> = callbackFlow {
        trySend(ResultState.Loading)
       dataForUserDataStore .addOnSuccessListener {
            val data = it.toObject(Userdata::class.java)
            trySend(ResultState.Sucess(data!!))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }

}

