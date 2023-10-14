package com.example.contactsapp.usesCases

import com.example.contactsapp.domain.model.Contact

//these are the events the user will invoke during use
sealed class ContactEvent() {
    data object SaveContact: ContactEvent()
    //putting data in the fields by the user
    data class SetFirstName(val firstName: String): ContactEvent()
    data class SetLastName(val lastName: String): ContactEvent()
    data class SetPhoneNumber(val phoneNumber: String): ContactEvent()
    //the dialog box showing and hiding is controlled by user actions(events)
    data object ShowDialog: ContactEvent()
    data object HideDialog: ContactEvent()
    //contacts can be sorted using the different different radio buttons set in the UI
    data class SortContacts(val sortType: SortType): ContactEvent()
    data class DeleteContact(val contact: Contact): ContactEvent()
}
