package ru.mirea.survillo.mireaproject.ui.autentification;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.survillo.mireaproject.MainActivity;
import ru.mirea.survillo.mireaproject.R;

public class Autification extends AppCompatActivity implements View.OnClickListener{
    private EditText emailEditText;
    private EditText passwordTextEdit;
    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            openMainActivity();
        } else {
            setContentView(R.layout.autification);

            emailEditText = findViewById(R.id.editEmail);
            passwordTextEdit = findViewById(R.id.editPassword);

            findViewById(R.id.btnSign).setOnClickListener(this);
            findViewById(R.id.btnCreate).setOnClickListener(this);

            findViewById(R.id.editPassword).setVisibility(View.VISIBLE);
            findViewById(R.id.editEmail).setVisibility(View.VISIBLE);
            findViewById(R.id.btnSign).setVisibility(View.VISIBLE);
            findViewById(R.id.btnCreate).setVisibility(View.VISIBLE);
        }
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = emailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Required.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }
        String password = passwordTextEdit.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordTextEdit.setError("Required.");
            valid = false;
        } else {
            passwordTextEdit.setError(null);
        }
        return valid;
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        openMainActivity();
                    } else {
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        openMainActivity();
                    } else {
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btnCreate) {
            createAccount(emailEditText.getText().toString(),
                    passwordTextEdit.getText().toString());
        } else if (i == R.id.btnSign) {
            signIn(emailEditText.getText().toString(),
                    passwordTextEdit.getText().toString());
        }
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
