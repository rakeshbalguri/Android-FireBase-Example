package com.rakesh.users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class CreateUserActivity extends AppCompatActivity {

    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        Firebase.setAndroidContext(this);

        firebaseRef = new Firebase("https://user-information.firebaseio.com/Users");
        final Firebase newPostRef = firebaseRef.push();

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createUsersIntent = new Intent(CreateUserActivity.this, ButtonsActivity.class);
                startActivity(createUsersIntent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText firstName = (EditText) findViewById(R.id.firstNameEditText);
                EditText lastName = (EditText) findViewById(R.id.lastNameEditText);
                EditText email = (EditText) findViewById(R.id.emailEditText);
                EditText phone = (EditText) findViewById(R.id.phoneEditText);

                Toast.makeText(CreateUserActivity.this, "First Name:" + firstName.getText() + "\n Last Name:" + lastName.getText() + "\n Email:" + email.getText() + "\nPhone:" + phone.getText(), Toast.LENGTH_SHORT).show();

                User newUser = new User();
                newUser.setFirstName(firstName.getText().toString());
                newUser.setLastName(lastName.getText().toString());
                newUser.setEmail(email.getText().toString());
                newUser.setPhone(phone.getText().toString());

                newPostRef.setValue(newUser);

                Intent listUsersIntent = new Intent(CreateUserActivity.this, ListUsersActivity.class);
                startActivity(listUsersIntent);
            }
        });
    }
}
