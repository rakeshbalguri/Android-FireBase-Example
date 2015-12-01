package com.rakesh.users;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ListUsersActivity extends AppCompatActivity {

    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Users");

//

        Firebase.setAndroidContext(this);

        final ListView listUsers = (ListView) findViewById(R.id.listUsers);

        firebaseRef = new Firebase("https://user-information.firebaseio.com/Users");

        final ArrayList<String> usersList = new ArrayList<String>();

        String[] usersArray = usersList.toArray(new String[usersList.size()]);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListUsersActivity.this,
                android.R.layout.simple_list_item_1, usersArray);
        // Assign adapter to List
        adapter.setNotifyOnChange(true);
        listUsers.setAdapter(adapter);

        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " users");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User users = postSnapshot.getValue(User.class);
                    System.out.println("User is " + users);
                    usersList.add(users.getFirstName() + " " + users.getLastName());

                    listUsers.invalidate();
                    String[] usersArray = usersList.toArray(new String[usersList.size()]);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListUsersActivity.this,
                            android.R.layout.simple_list_item_1, usersArray);
                    // Assign adapter to List
                    adapter.setNotifyOnChange(true);
                    listUsers.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });


        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String itemValue = (String) parent.getSelectedItem();
                Toast.makeText(ListUsersActivity.this, "Item Position: " + adapter.getItem(position), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
