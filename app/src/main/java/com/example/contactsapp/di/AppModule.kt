package com.example.contactsapp.di

import android.app.Application
import androidx.room.Room
import com.example.contactsapp.ContactApp
import com.example.contactsapp.data.local.ContactDatabase
import com.example.contactsapp.data.repository.ContactRepositoryImpl
import com.example.contactsapp.domain.repository.ContactRepository
import com.example.contactsapp.utils.Constants.CONTACT_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContactDatabase(
        app: Application
    ): ContactDatabase{
        return Room.databaseBuilder(
            app,
            ContactDatabase::class.java,
            CONTACT_DB
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactRepository(
        db: ContactDatabase
    ): ContactRepository{
        return ContactRepositoryImpl(
            dao = db.dao
        )
    }
}