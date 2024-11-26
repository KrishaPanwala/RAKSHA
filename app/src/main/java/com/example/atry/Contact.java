package com.example.atry;

import java.util.Objects;

public class Contact {
    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if they are the same object
        if (o == null || getClass() != o.getClass()) return false; // Check for null and ensure same class
        Contact contact = (Contact) o; // Cast the object to Contact
        return name.equals(contact.name) && phoneNumber.equals(contact.phoneNumber); // Compare properties
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber); // Generate hash based on properties
    }
}
