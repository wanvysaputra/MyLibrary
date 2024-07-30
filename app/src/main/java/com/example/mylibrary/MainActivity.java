package com.example.mylibrary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText inputText;
    Button storeButton, getNameButton;
    TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 150, systemBars.right, systemBars.bottom);
            return insets;
        });
        // coding dibawah sini :
        dbHelper = new DatabaseHelper(this);
        inputText = findViewById(R.id.inputText);
        storeButton = findViewById(R.id.storeButton);
        getNameButton = findViewById(R.id.getNameButton);
        displayText = findViewById(R.id.displayText);

        // ini coding untuk menjalankan tombol store
        storeButton.setOnClickListener(v -> {
            saveName();
        });

        // ini coding untuk menjalankan tombol getName
        getNameButton.setOnClickListener(v -> {
            displayNames();
        });
    }

    private void saveName(){
        String name = inputText.getText().toString();
        //kita lakukan pengecekan inputan apakah kosong atau tidak
        if (!name.isEmpty()){
            //coding untuk menyimpan nama
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO names (name) VALUES ('" + name + "');");
            db.close();
            inputText.setText("");
        }
    }

    private void displayNames(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c1 = db.rawQuery("SELECT name FROM names", null);
        StringBuilder names = new StringBuilder();
        while (c1.moveToNext()){
            names.append(c1.getString(0)).append("\n");
        }
        c1.close();
        displayText.setText(names.toString());
    }
}