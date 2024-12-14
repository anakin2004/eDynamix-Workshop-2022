package com.os0.navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText name;
    Button btnContinue;
    ArrayList<User> users = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.SignUpEditTextName);
        email = findViewById(R.id.SignUpEditTextEmail);
        password = findViewById(R.id.SignUpEditTextPassword);
        btnContinue = findViewById(R.id.SignUpButtonContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    private void SignIn() {

        if (!isAccountAlreadyCreated() && isNameCorrect() && CommonLogIn.isEmailCorrect(email) && CommonLogIn.isPasswordCorrect(password)) {
            User account = new User(name.getText().toString(), email.getText().toString(), password.getText().toString());
            users.add(account);

            SharedPreferences SignUpSharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = SignUpSharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(users);
            editor.putString("accounts", json);
            editor.apply();
            SharedPreferences prefBoolean = getApplicationContext().getSharedPreferences("curUserPr", MODE_PRIVATE);
            SharedPreferences.Editor editPref = prefBoolean.edit();
            editPref.putString("currentUser", String.valueOf(name.getText()));
            editPref.apply();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }

    private boolean isNameCorrect() {
        String nameLocal = name.getText().toString();

        if (nameLocal.isEmpty()) {
            name.setError("Field can't be empty");
            return false;
        }
        name.setError(null);
        return true;
    }

    private boolean isAccountAlreadyCreated() {
        SharedPreferences LogInSharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = LogInSharedPreferences.getString("accounts", null);
        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();
        users = gson.fromJson(json, type);

        String emailLocal = email.getText().toString();
        String passLocal = password.getText().toString();

        if (users == null) {
            users = new ArrayList<>();
        }

        if (users != null) {
            for (User item : users) {
                if (Objects.equals(item.getEmail(), emailLocal) && Objects.equals(item.getPassword(), passLocal)) {
                    Toast.makeText(SignUpActivity.this, "This account already exists", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }
}