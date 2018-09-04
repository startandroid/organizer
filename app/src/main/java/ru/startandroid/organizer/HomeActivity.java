package ru.startandroid.organizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        transaction = getSupportFragmentManager ().beginTransaction ();
        transaction.replace (R.id.activity_catalog_fragment_cont, HomeFragment.newInstance ());
        transaction.commit ();
    }
}
