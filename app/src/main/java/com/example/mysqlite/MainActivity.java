package com.example.mysqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycle;
    FloatingActionButton floatButton;
    MyDBHelper myDB;
    ArrayList<String> id, title, description, stock;
    CustomAdapter customadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recycle = findViewById(R.id.recyclerViewTest);
        floatButton = findViewById(R.id.floatingActionButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAddItems = new Intent(MainActivity.this, AddItems.class);
                startActivityForResult(toAddItems, 1);
            }
        });
        myDB = new MyDBHelper(MainActivity.this);
        id = new ArrayList<>();
        title = new ArrayList<>();
        description = new ArrayList<>();
        stock = new ArrayList<>();

        insertDatainArrayFromDatabase();
        customadapter = new CustomAdapter(MainActivity.this,MainActivity.this, id, title, description, stock);
        recycle.setAdapter(customadapter);
        recycle.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void insertDatainArrayFromDatabase(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data inside database", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                title.add(cursor.getString(1));
                description.add(cursor.getString(2));
                stock.add(cursor.getString(3));
            }
        }
    }
}