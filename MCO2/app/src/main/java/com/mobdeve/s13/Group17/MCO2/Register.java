package com.mobdeve.s13.Group17.MCO2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityRegister1Binding;

import java.util.HashMap;
import java.util.Map;

//MCO3 Group 17- Chua, Shi, Tan
public class Register extends AppCompatActivity {

    private EditText  Email, Password, Username, Bio, ConPassword, FirstName,LastName;
    private Button  Reg1Btn;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegister1Binding viewBinding = ActivityRegister1Binding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        FirstName=viewBinding.firstnametext;
        LastName=viewBinding.lastnametext;
        Email=viewBinding.emailtext;
        Password=viewBinding.editTextTextPassword;
        ConPassword=viewBinding.editTextTextPassword2;
        Username=viewBinding.usertext;
        Bio=viewBinding.biotext;

        firebaseFirestore = FirebaseFirestore.getInstance();

        Intent intent = new Intent(this, Login1.class);

        Reg1Btn = viewBinding.nextpage4;
        Reg1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Username.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Please type a username", Toast.LENGTH_SHORT).show();
                }
                else if (Email.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Please type an email", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {
                    Toast.makeText(Register.this, "Please enter correct email format", Toast.LENGTH_SHORT).show();
                }
                else if (Password.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Please type a password", Toast.LENGTH_SHORT).show();
                }
                else if (!Password.getText().toString().equals(ConPassword.getText().toString())) {
                    Toast.makeText(Register.this, "Password and confirm password does not match", Toast.LENGTH_SHORT).show();
                }
                else if (Password.getText().toString().length() < 8) {
                    Toast.makeText(Register.this, "Password must have at least 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if (FirstName.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Please type your First Name", Toast.LENGTH_SHORT).show();
                }
                else if (LastName.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Please type your Last Name", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseFirestore.collection("UserInfo")
                            .whereEqualTo("Username", Username.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (!task.getResult().isEmpty()) {
                                            Toast.makeText(Register.this, "Sorry, this username already exists", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Check if email already exists
                                            firebaseFirestore.collection("UserInfo")
                                                    .whereEqualTo("Email", Email.getText().toString())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                if (!task.getResult().isEmpty()) {
                                                                    Toast.makeText(Register.this, "Sorry, this email already exists", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    // Proceed with user registration
                                                                    Map<String, Object> reg_entry = new HashMap<>();
                                                                    reg_entry.put("Username", Username.getText().toString());
                                                                    reg_entry.put("Email", Email.getText().toString());
                                                                    reg_entry.put("Password", Password.getText().toString());
                                                                    reg_entry.put("Name", FirstName.getText().toString() +" "+ LastName.getText().toString());
                                                                    reg_entry.put("Bio", Bio.getText().toString());

                                                                    firebaseFirestore.collection("UserInfo")
                                                                            .add(reg_entry)
                                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                @Override
                                                                                public void onSuccess(DocumentReference documentReference) {
                                                                                    Toast.makeText(Register.this, "User successfully registered", Toast.LENGTH_SHORT).show();
                                                                                    startActivity(intent);
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Log.d("Error", e.getMessage());
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        }
                                                    });

                                        }
                                    }
                                }
                            });
                }
            }
        });
    }
}
