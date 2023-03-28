package com.mobdeve.s13.Group17.MCO2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityRegister1Binding;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText FullName, Email, Password, Username, Bio;
    private Button  Reg1Btn;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegister1Binding viewBinding = ActivityRegister1Binding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        mAuth=FirebaseAuth.getInstance();

        Reg1Btn=viewBinding.nextpage4;
        Reg1Btn.setOnClickListener(this::onClick);

        FullName=viewBinding.firstnametext;
        Email=viewBinding.emailtext;
        Password=viewBinding.editTextTextPassword;
        Username=viewBinding.usertext;
        Bio=viewBinding.biotext;


    }

    @Override
    public void onClick(View view) {

        String email =Email.getText().toString().trim();
        String username =Username.getText().toString().trim();
        String password =Password.getText().toString().trim();
        String name =FullName.getText().toString().trim();
        String bio =Bio.getText().toString().trim();

       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                    User user =new User(name,username,email,password,bio);
                   FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       Toast.makeText(
                                               Register.this,
                                       "Successful",
                                               Toast.LENGTH_LONG
            ).show();
                                   }
                               }
                           });
               }
           }
       });

    }
}
