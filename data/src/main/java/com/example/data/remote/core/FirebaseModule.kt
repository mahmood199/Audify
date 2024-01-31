package com.example.data.remote.core

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase(
        @ApplicationContext context: Context
    ): FirebaseDatabase {
        return FirebaseDatabase.getInstance("")
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(
        @ApplicationContext context: Context
    ): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }


}