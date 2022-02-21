package com.example.mareu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.ui.add.AddMeetingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.main_vp);
        viewPager.setAdapter(new MainPagerAdapter(this, getSupportFragmentManager()));

        FloatingActionButton fab = findViewById(R.id.main_fab_add);
        fab.setOnClickListener(v -> startActivity(AddMeetingActivity.navigate(this)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_date:
                Toast.makeText(MainActivity.this, "Date", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_room:
                Toast.makeText(MainActivity.this, "Salle", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
