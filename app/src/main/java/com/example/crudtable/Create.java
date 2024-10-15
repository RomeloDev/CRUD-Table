package com.example.crudtable;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Create extends AppCompatActivity {

    private Button submitBtn;
    private EditText nameField, idField, courseyearField;
    private Spinner department;
    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    private String selectedDepartment;
    private List<String> deptOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        idField = findViewById(R.id.idField);
        nameField = findViewById(R.id.nameField);
        courseyearField = findViewById(R.id.courseYearField);
        submitBtn = findViewById(R.id.createSubmitBtn);
        department = findViewById(R.id.spinner);

        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Students");

        deptOptions = new ArrayList<>();
        deptOptions.add("Select Here");
        deptOptions.add("CCIS");
        deptOptions.add("CCJ");
        deptOptions.add("CTAS");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, deptOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(adapter);

        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment = parent.getItemAtPosition(position).toString();
                if (position == 0){
                    Toast.makeText(Create.this, "Please select a department", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Create.this, "Selected Department: " + selectedDepartment, Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Create.this, "No department selected!", Toast.LENGTH_SHORT).show();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idField.getText().toString();
                String name = nameField.getText().toString();
                String courseYr = courseyearField.getText().toString();

                if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(courseYr) && !TextUtils.isEmpty(selectedDepartment)){
                    String userId = usersRef.push().getKey();
                    userData newUser = new userData(id,name, courseYr, selectedDepartment);
                    usersRef.child(userId).setValue(newUser)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    Toast.makeText(Create.this, "User Registered", Toast.LENGTH_SHORT).show();
                                    idField.setText("");
                                    nameField.setText("");
                                    courseyearField.setText("");
                                    department.setVerticalScrollbarPosition(0);
                                }else {
                                    Toast.makeText(Create.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(Create.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}