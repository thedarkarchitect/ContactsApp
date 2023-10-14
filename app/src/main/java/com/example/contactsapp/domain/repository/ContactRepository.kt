package com.example.contactsapp.domain.repository

import com.example.contactsapp.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun upsertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun getContactsOrderedByFirstName(): Flow<List<Contact>>

    suspend fun getContactsOrderedByLastName(): Flow<List<Contact>>

    suspend fun getContactsOrderedByPhoneNumber(): Flow<List<Contact>>
}