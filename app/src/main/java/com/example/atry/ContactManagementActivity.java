package com.example.atry;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ContactManagementActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "EmergencyContactsPrefs";
    public static final String CONTACTS_KEY = "Contacts";

    private EditText contactNameEditText;
    private EditText contactPhoneEditText;
    private ListView contactsListView;
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_management);

        contactNameEditText = findViewById(R.id.contact_name);
        contactPhoneEditText = findViewById(R.id.contact_phone);
        Button addContactButton = findViewById(R.id.add_contact_button);
        Button removeContactButton = findViewById(R.id.remove_contact_button);
        contactsListView = findViewById(R.id.contacts_list_view);

        // Initialize the contact list and adapter
        contacts = EmergencyContactManager.getEmergencyContacts(this);
        contactAdapter = new ContactAdapter(this, contacts);
        contactsListView.setAdapter(contactAdapter);

        addContactButton.setOnClickListener(this::addContact);
        removeContactButton.setOnClickListener(this::removeContact);
    }

    private void addContact(View view) {
        String name = contactNameEditText.getText().toString().trim();
        String phone = contactPhoneEditText.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please enter both name and phone number.", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact contact = new Contact(name, phone);
        EmergencyContactManager.addEmergencyContact(this, contact);
        contacts.add(contact); // Add contact to the list
        contactAdapter.notifyDataSetChanged(); // Notify adapter to refresh the list
        Toast.makeText(this, "Contact added.", Toast.LENGTH_SHORT).show();
    }

    private void removeContact(View view) {
        String name = contactNameEditText.getText().toString().trim();
        String phone = contactPhoneEditText.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please enter both name and phone number.", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact contact = new Contact(name, phone);
        EmergencyContactManager.removeEmergencyContact(this, contact);
        contacts.remove(contact); // Remove contact from the list
        contactAdapter.notifyDataSetChanged(); // Notify adapter to refresh the list
        Toast.makeText(this, "Contact removed.", Toast.LENGTH_SHORT).show();
    }
}
