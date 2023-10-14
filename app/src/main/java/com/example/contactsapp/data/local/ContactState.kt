package com.example.contactsapp.data.local

import com.example.contactsapp.usesCases.SortType
import com.example.contactsapp.domain.model.Contact

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME//this makes the contacts to be displayed by default sorted by firstName
)
