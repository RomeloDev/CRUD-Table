package com.example.crudtable;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        table = findViewById(R.id.tableLayout);

        String[][] users = {
                {"1", "Alice Johnson", "BSIT 3", "2024-10-15", "14:00"},
                {"2", "Bob Smith", "BSCS 2", "2024-10-15", "14:15"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"},
                {"3", "Cathy Brown", "BSEE 4", "2024-10-15", "14:30"}
        };

        // Add rows dynamically
        for (String[] user : users) {
            TableRow row = new TableRow(this);

            for (String data : user) {
                TextView textView = new TextView(this);
                textView.setText(data);
                textView.setPadding(8, 8, 8, 8);
                row.addView(textView);
            }

            table.addView(row);

        }
    }
}