package com.example.data.remote.firebase

import com.example.data.models.local.RecentlyPlayed
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject


class SongUploaderRemoteDataSource @Inject constructor(
//    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage
) {

    companion object {
        private const val DATABASE_FILE_LOCATION = "songs"
        private const val STORAGE_FILE_LOCATION = "songs"
    }

    fun uploadData(recentlyPlayed: RecentlyPlayed) {
  //      firebaseDatabase.getReference(DATABASE_FILE_LOCATION)
    }

}