package com.example.atry;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class SharedPreferenceUtil {

    private static final String PREFS_NAME = "com.example.atry.PREFS";
    private static final String KEY_CONTACTS = "contacts";

    public static void saveContacts(Context context, Set<String> contacts) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(contacts);
        editor.putString(KEY_CONTACTS, json);
        editor.apply();
    }

    public static Set<String> getContacts(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(KEY_CONTACTS, null);
        Type type = new TypeToken<Set<String>>() {}.getType();
        Set<String> contacts = gson.fromJson(json, type);
        if (contacts == null) {
            contacts = new HashSet<>();
        }
        return contacts;
    }

    public static void removeContact(Context context, String contact) {
        Set<String> contacts = getContacts(context);
        contacts.remove(contact);
        saveContacts(context, contacts);
    }
}
