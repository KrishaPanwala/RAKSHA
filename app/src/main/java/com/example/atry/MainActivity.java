package com.example.atry;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_LOCATION = 1;
    private static final int PERMISSIONS_REQUEST_CALL = 2;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        Button btnSendLocation = findViewById(R.id.btn_send_location);
        Button btnCallEmergency = findViewById(R.id.btn_call_emergency);

        btnSendLocation.setOnClickListener(v -> {
            if (checkLocationPermissions()) {
                sendLocationToContacts();
            }
        });

        btnCallEmergency.setOnClickListener(v -> {
            if (checkLocationPermissions() && checkCallPermission()) {
                callEmergency();
            }
        });
    }

    private boolean checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
            return false;
        }
        return true;
    }

    private boolean checkCallPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CALL);
            return false;
        }
        return true;
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_manage_contacts) {
            startActivity(new Intent(MainActivity.this, ContactManagementActivity.class));
        } else if (id == R.id.nav_laws) {
            startActivity(new Intent(MainActivity.this, LawsActivity.class));
        } else if (id == R.id.nav_self_defense) {
            startActivity(new Intent(MainActivity.this, SelfDefenseActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendLocationToContacts() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                handleLocation(location);
            } else {
                Toast.makeText(this, "Unable to get location. Please try again.", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Location is null.");
            }
        }).addOnFailureListener(e -> {
            Log.e("MainActivity", "Failed to get location", e);
            Toast.makeText(this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void handleLocation(Location location) {
        String locationUrl = "https://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
        ArrayList<Contact> contactList = EmergencyContactManager.getEmergencyContacts(this);
        Set<String> contacts = new HashSet<>();
        for (Contact contact : contactList) {
            contacts.add(contact.getPhoneNumber());
        }

        if (contacts.isEmpty()) {
            Toast.makeText(this, "No contacts found.", Toast.LENGTH_SHORT).show();
            return;
        }

        SmsManager smsManager = SmsManager.getDefault();
        for (String contact : contacts) {
            smsManager.sendTextMessage(contact, null, "Emergency! My location: " + locationUrl, null, null);
        }
        Toast.makeText(this, "Location sent to emergency contacts.", Toast.LENGTH_SHORT).show();
    }

    private void callEmergency() {
        ArrayList<Contact> contactList = EmergencyContactManager.getEmergencyContacts(this);
        if (contactList.isEmpty()) {
            Toast.makeText(this, "No contacts found.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> contactMap = new HashMap<>();
        for (Contact contact : contactList) {
            contactMap.put(contact.getName(), contact.getPhoneNumber());
        }

        CharSequence[] contactNames = contactMap.keySet().toArray(new CharSequence[0]);

        new AlertDialog.Builder(this)
                .setTitle("Select Contact")
                .setItems(contactNames, (dialog, which) -> {
                    String selectedContactName = (String) contactNames[which];
                    String selectedContactNumber = contactMap.get(selectedContactName);

                    if (selectedContactNumber != null && selectedContactNumber.matches("\\d{10}")) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + selectedContactNumber));
                        startActivity(callIntent);
                    } else {
                        Toast.makeText(this, "Invalid contact number: " + selectedContactNumber, Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendLocationToContacts();
            } else {
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSIONS_REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callEmergency();
            } else {
                Toast.makeText(this, "Call permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
