package com.mobdeve.s13.Group17.MCO2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityLoginBinding;

/*
MCO4
GROUP 17
CHUA, JASMIN
SHI, KAYE
TAN, HAILY
*/

public class Login1 extends AppCompatActivity {

    static final String UNAME = "Username";
    private Button login;
    private FirebaseFirestore db;
    private EditText uname, password;
    private TextView signUpHere;
    private ActivityLoginBinding viewBinding;

    boolean isUserLoggedIn = StartPage.Companion.getIsLoggedIn();

    SharedPreferences sharedPrefs;


    private static String getUname = null;

    public static String getUserName() {
        return getUname;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this activity using ViewBinding
        viewBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        Intent intent1 = new Intent(this, Register.class);

        // Get the shared preferences for the activity
        sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Initialize the UI elements
        login = viewBinding.loginbtn1;
        uname = viewBinding.loginunametext;
        password = viewBinding.loginpasswordtext;
        signUpHere = viewBinding.signuphere;

        //Initialize firebase firestore
        db = FirebaseFirestore.getInstance();

        // Set click listeners for the login and sign-up buttons


        login.setOnClickListener(this::onClick);
        signUpHere.setOnClickListener(this::onClick);

    }

    public void onClick(View view) {

            if (view.getId()==viewBinding.loginbtn1.getId()) {
                // Check if the email and password fields are empty
                if (uname.getText().toString().isEmpty()) {
                    Toast.makeText(Login1.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(Login1.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                } else {
                    // Get the user credentials from the Firestore database
                    db.collection("UserInfo")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    boolean isMatchFound = false;
                                    if (task.isSuccessful()) {
                                        // Loop through the documents in the collection to find a matching email and password
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            String a = doc.getString("Username");
                                            String b = doc.getString("Password");
                                            String a1 = uname.getText().toString();
                                            String b1 = password.getText().toString();
                                            if (a.equalsIgnoreCase(a1) && b.equalsIgnoreCase(b1)) {
                                                Toast.makeText(Login1.this, "Logged In Successful", Toast.LENGTH_SHORT).show();
                                                // Navigate to the main activity and set the logged-in user's email
                                                Intent home = new Intent(Login1.this, MainActivity.class);
                                                home.putExtra(MainActivity.UNAME, uname.getText().toString());
                                                startActivity(home);

                                                // Set the user as logged in
                                                isMatchFound = true;
                                                isUserLoggedIn = true;

                                                // Update the shared preferences with the user's email and logged-in status
                                                sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPrefs.edit();
                                                editor.putBoolean("isLoggedIn", true);
                                                editor.putString("username", uname.getText().toString());
                                                editor.apply();
                                                break;
                                            }
                                        }
                                        if (!isMatchFound) {
                                            Toast.makeText(Login1.this, "Cannot login, Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });

                }
            }
                else if(view.getId()==viewBinding.signuphere.getId()) {
                // Navigate to the Register activity when the user clicks the sign-up button
                    Intent register_view = new Intent(Login1.this, Register.class);
                    startActivity(register_view);
                }

        }

}





