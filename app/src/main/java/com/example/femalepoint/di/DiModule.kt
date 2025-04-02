package com.example.femalepoint.di


import com.example.femalepoint.data.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DiModule {
    @Provides
    fun providefirebasefirestore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }
    @Provides
    fun providerepo(): Repository {
        return Repository(firebaseinstance = providefirebasefirestore())
    }
    @Provides
    fun authInstance():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

}