package com.example.crudtable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TableLayout table;
    private Button createBtn, updateBtn, deleteBtn;
    private DatabaseReference studentsRef;

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
        createBtn = findViewById(R.id.createBtn);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        studentsRef = FirebaseDatabase.getInstance().getReference("Students");

        /*
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
        */

        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                table.removeViews(1, Math.max(0, table.getChildCount() - 1));
                for (DataSnapshot studentSnapshot : snapshot.getChildren()) {
                    String name = studentSnapshot.child("name").getValue(String.class);
                    String id = studentSnapshot.child("id").getValue(String.class);
                    String courseYr = studentSnapshot.child("courseYr").getValue(String.class);
                    String department = studentSnapshot.child("department").getValue(String.class);

                    addStudentRow(id, name, courseYr, department);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Create.class);
                startActivity(intent);
            }
        });
    }

    private void addStudentRow(String id, String name, String courseYr, String department) {
        TableRow row = new TableRow(this);

        row.addView(createTextView(id));
        row.addView(createTextView(name));
        row.addView(createTextView(courseYr));
        row.addView(createTextView(department));

        table.addView(row);
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        textView.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        return textView;
    }
}