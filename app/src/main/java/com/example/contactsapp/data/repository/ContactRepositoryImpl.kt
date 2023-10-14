package com.example.contactsapp.data.repository

import com.example.contactsapp.data.local.ContactDao
import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow

class ContactRepositoryImpl (
    private val dao: ContactDao
): ContactRepository {
    override suspend fun upsertContact(contact: Contact) = dao.upsertContact(contact)

    override suspend fun deleteContact(contact: Contact) = dao.deleteContact(contact)

    override suspend fun getContactsOrderedByFirstName(): Flow<List<Contact>> = dao.getContactsOrderedByFirstName()

    override suspend fun getContactsOrderedByLastName(): Flow<List<Contact>> = dao.getContactsOrderedByLastName()

    override suspend fun getContactsOrderedByPhoneNumber(): Flow<List<Contact>> = dao.getContactsOrderedByPhoneNumber()
}