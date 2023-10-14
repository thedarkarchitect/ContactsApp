package com.example.contactsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactsapp.domain.model.Contact

@Database(
    entities = [Contact::class],
    version = 1,
    exportSchema = false
)
abstract class ContactDatabase : RoomDatabase() {
    abstract val dao: ContactDao
}