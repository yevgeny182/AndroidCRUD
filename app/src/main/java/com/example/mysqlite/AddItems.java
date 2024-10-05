package com.example.mysqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddItems extends AppCompatActivity {
    ImageButton back;
    EditText title, description, stock;
    Button submit;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_items);

        back = findViewById(R.id.imageButton);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        stock = findViewById(R.id.stock);

        submit = findViewById(R.id.submit);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().trim().isEmpty() &&
                    description.getText().toString().trim().isEmpty() &&
                        stock.getText().toString().trim().isEmpty()) {

                    Toast.makeText(AddItems.this, "Fields must not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    MyDBHelper myDb = new MyDBHelper(AddItems.this);
                    myDb.addData(
                            title.getText().toString().trim(),
                            description.getText().toString().trim(),
                            Integer.valueOf(stock.getText().toString().trim())
                    );
                    finish();

                }
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}