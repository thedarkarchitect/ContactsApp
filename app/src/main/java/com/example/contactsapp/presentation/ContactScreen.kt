package com.example.contactsapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactsapp.usesCases.ContactEvent
import com.example.contactsapp.usesCases.SortType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    modifier: Modifier = Modifier,
    state: ContactState,
    onEvent: (ContactEvent) -> Unit
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                //when add button is tapped thats an event and which should trigger an event to show dialog which is controlled by a boolean
                onEvent(ContactEvent.ShowDialog)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Contact")
            }
        },
        modifier = modifier.padding(16.dp)
    ) {innerPadding ->
        if(state.isAddingContact) {//this triggers the dialog based on the boolean condition which is triggered by the event in the floating action button
            AddContactDialog(
                state = state,
                onEvent = onEvent,
            )
        }

        LazyColumn(
            contentPadding = innerPadding,
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            item{
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState()),//this makes the row scrollable and to have state on scroll
                    verticalAlignment = CenterVertically
                ){
                    SortType.values().forEach { sortType ->  
                        Row(
                            modifier = modifier 
                                .clickable { 
                                    onEvent(ContactEvent.SortContacts(sortType))
                                },
                            verticalAlignment = CenterVertically
                        ){
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(ContactEvent.SortContacts(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.contacts) {contact ->
                Row(
                    modifier = modifier
                        .fillMaxSize()
                ){
                    Column(
                        modifier = modifier.weight(1f)
                    ){
                        Text(
                            text = "${contact.firstName} ${contact.lastName}",
                            fontSize = 20.sp
                        )
                        Text(text = contact.phoneNumber, fontSize = 12.sp)
                    }
                    IconButton(onClick = {
                        onEvent(ContactEvent.DeleteContact(contact))
                    }){
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete contact"
                        )
                    }
                }
            }
        }
    }
}