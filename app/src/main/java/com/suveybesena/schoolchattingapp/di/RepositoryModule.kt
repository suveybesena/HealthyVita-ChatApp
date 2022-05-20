package com.suveybesena.schoolchattingapp.di

import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseAuthRepository
import com.suveybesena.schoolchattingapp.data.firebase.auth.source.FirebaseAuthRepositoryImpl
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseFirestoreRepository
import com.suveybesena.schoolchattingapp.data.firebase.firestore.source.FirebaseFirestoreRepositoryImpl
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseStorageRepository
import com.suveybesena.schoolchattingapp.data.firebase.storage.source.FirebaseStorageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFirebaseAuthRepository(firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl): FirebaseAuthRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseFirestoreRepository(firebaseFirestoreRepositoryImpl: FirebaseFirestoreRepositoryImpl): FirebaseFirestoreRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseStorageRepository(firebaseStorageRepositoryImpl: FirebaseStorageRepositoryImpl): FirebaseStorageRepository


}