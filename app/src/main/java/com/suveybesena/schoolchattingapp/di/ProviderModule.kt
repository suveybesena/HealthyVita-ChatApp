package com.suveybesena.schoolchattingapp.di

import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseAuthSource
import com.suveybesena.schoolchattingapp.data.firebase.auth.source.FirebaseAuthSourceProvider
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseFirestoreSource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.source.FirebaseFirestoreSourceProvider
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseStorageSource
import com.suveybesena.schoolchattingapp.data.firebase.storage.source.FirebaseStorageSourceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {

    @Binds
    @Singleton
    abstract fun bindFirebaseAuthProvider(firebaseAuthSourceProvider: FirebaseAuthSourceProvider): FirebaseAuthSource

    @Binds
    @Singleton
    abstract fun bindFirebaseFirestoreProvider(firebaseFirestoreSourceProvider: FirebaseFirestoreSourceProvider): FirebaseFirestoreSource

    @Binds
    @Singleton
    abstract fun bindFirebaseStorageProvider(firebaseStorgaeSourceProvider: FirebaseStorageSourceProvider): FirebaseStorageSource


}