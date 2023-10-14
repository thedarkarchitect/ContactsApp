package com.example.contactsapp.presentation

import androidx.lifecycle.ViewModel
import com.example.contactsapp.data.local.ContactDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val dao: ContactDao
): ViewModel() {

}