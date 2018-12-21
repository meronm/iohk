package com.example.mery.maed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    private EditText userNameField, passwordField;
    private String userName, password;
    private TextView messageView;
    private Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameField = (EditText) findViewById(R.id.user_name);
        passwordField = (EditText) findViewById(R.id.password);
        messageView = (TextView) findViewById(R.id.messageView);
        btnSignIn = (Button) findViewById(R.id.login);
    }
    public void login_button(View view)
    {
        String username = userNameField.getText().toString();
        String password = passwordField.getText().toString();
        String link1 = "http://192.168.0.236/iohk/login.php";
        String[] inputs = {"username", "password"};
        new connect_mysql(this, messageView, 0, link1, inputs).execute(username, password);

        String userID = messageView.getText().toString();
        //messageView.setVisibility(View.INVISIBLE);

        messageView.setText(userID);
        //String abc=messageView.getText().toString();
        switch (userID) {
            case "No user":
                Toast.makeText(getBaseContext(), "invalid user", Toast.LENGTH_SHORT).show();
                break;
            case "hello":
                Toast.makeText(getBaseContext(), "invalid user name", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getBaseContext(), "well come to maed", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(this, home.class);
                login.this.finish();
                //userInRole inrole = new userInRole();
                //inrole.setUserId(userID);
                startActivity(i2);
                break;
        }

    }
}
