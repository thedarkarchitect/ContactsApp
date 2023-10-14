package com.example.contactsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.repository.ContactRepository
import com.example.contactsapp.usesCases.ContactEvent
import com.example.contactsapp.usesCases.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: ContactRepository
): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)//tracks sort type selected
    private val _state = MutableStateFlow(ContactState())//tracks the state of the app
    //track the contacts sorttype state
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _contacts = _sortType
            .flatMapLatest { sortType ->
                //this will activate the contacts to follow the sort type selected
                when(sortType) {
                    SortType.FIRST_NAME -> repository.getContactsOrderedByFirstName()
                    SortType.LAST_NAME -> repository.getContactsOrderedByLastName()
                    SortType.PHONE_NUMBER -> repository.getContactsOrderedByPhoneNumber()
                }
            }//this return sorttype of the contacts will have to be turned to states to be tracked by the whole application
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    //after we shall combine the state and make them public to the application to be used by the app
    //the 3 state are flows and we going to turn them into one flow recieved by the app
    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())
    fun onEvent(event: ContactEvent){
        when(event) {
            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    repository.deleteContact(event.contact)
                }
            }
            ContactEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact  = false
                    )
                }
            }
            ContactEvent.SaveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber

                if(firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
                    return
                }

                val contact = Contact(
                    firstName =firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber
                )

                viewModelScope.launch {
                    repository.upsertContact(contact)//this update or insert the contacts
                }

                //after inserting or updating we have to reset the state in the fields
                _state.update {
                    it.copy(
                        isAddingContact = false,
                        firstName = "",
                        lastName = "",
                        phoneNumber = ""
                    )
                }
            }
            is ContactEvent.SetFirstName -> {
                _state.update {
                    it.copy(
                        firstName = event.firstName//this updates the entered value in field
                    )
                }
            }
            is ContactEvent.SetLastName -> {
                _state.update {
                    it.copy(
                        lastName = event.lastName
                    )
                }
            }
            is ContactEvent.SetPhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }
            ContactEvent.ShowDialog -> {
                return _state.update {
                    it.copy(
                        isAddingContact = true
                    )
                }
            }
            is ContactEvent.SortContacts -> {
                _sortType.value = event.sortType
            }
        }
    }
}