package com.example.mysqlite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateItems extends AppCompatActivity {
    EditText title, description, stock;
    Button update, delete;
    String id, TITLE, DESCRIPTION, STOCK;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_items);
        //retrieving data from database


        back = findViewById(R.id.imageButton);

        title = findViewById(R.id.updateTitle);
        description = findViewById(R.id.updateDescription);
        stock = findViewById(R.id.updateStock);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //retrieving data from DB
        getData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentTitle = title.getText().toString();
                String currDesc = description.getText().toString();
                int currStock = Integer.parseInt(stock.getText().toString());
                if(currentTitle.equals(TITLE) && currDesc.equals(DESCRIPTION) && currStock == Integer.parseInt(STOCK)){
                Toast.makeText(UpdateItems.this, "\uD83D\uDE45\u200D♂\uFE0F No changes made", Toast.LENGTH_SHORT).show();
                }else{
                    MyDBHelper myDB = new MyDBHelper(UpdateItems.this);
                    myDB.updateData(id, currentTitle, currDesc, currStock);
                    Toast.makeText(UpdateItems.this, "✅ Data updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDeletionDialog();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //outside protected void onCreate
    void getData(){
        if(getIntent().hasExtra("id" ) && getIntent().hasExtra("title" )
                && getIntent().hasExtra("description") && getIntent().hasExtra("stock" )){
            //get intent data
            id = getIntent().getStringExtra("id");
            TITLE  = getIntent().getStringExtra("title");
            DESCRIPTION = getIntent().getStringExtra("description");
            STOCK = getIntent().getStringExtra("stock");
            //set intent data

            title.setText(TITLE);
            description.setText(DESCRIPTION);
            stock.setText(STOCK);
        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDeletionDialog(){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Delete [" + TITLE + "] ?");
        build.setMessage("Are you sure you want to delete?" + TITLE + " ?");
        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper myDB = new MyDBHelper(UpdateItems.this);
                myDB.deleteOneRow(id);
            }
        });
        build.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UpdateItems.this, "\uD83D\uDE45\u200D♂\uFE0F No changes made", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = build.create();
        alert.show();
    }
}

