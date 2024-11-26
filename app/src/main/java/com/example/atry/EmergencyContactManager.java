package com.example.atry;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EmergencyContactManager {

    public static ArrayList<Contact> getEmergencyContacts(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ContactManagementActivity.PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(ContactManagementActivity.CONTACTS_KEY, null);
        Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
        ArrayList<Contact> contacts = gson.fromJson(json, type);
        if (contacts == null) {
            contacts = new ArrayList<>();
        }
        return contacts;
    }

    public static void addEmergencyContact(Context context, Contact contact) {
        ArrayList<Contact> contacts = getEmergencyContacts(context);
        contacts.add(contact);
        saveContacts(context, contacts);
    }

    public static void removeEmergencyContact(Context context, Contact contact) {
        ArrayList<Contact> contacts = getEmergencyContacts(context);
        contacts.remove(contact);  // Uses the equals method from Contact class
        saveContacts(context, contacts);
    }

    private static void saveContacts(Context context, ArrayList<Contact> contacts) {
        SharedPreferences prefs = context.getSharedPreferences(ContactManagementActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(contacts);
        editor.putString(ContactManagementActivity.CONTACTS_KEY, json);
        editor.apply();
    }
}
