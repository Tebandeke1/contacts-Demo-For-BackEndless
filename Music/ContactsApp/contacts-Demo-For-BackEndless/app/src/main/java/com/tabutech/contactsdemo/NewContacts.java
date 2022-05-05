package com.tabutech.contactsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class NewContacts extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    private EditText etName,etPhone,etEmail;
    private Button btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contacts);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etName = findViewById(R.id.etNewName);
        etPhone = findViewById(R.id.etNewPhone);
        etEmail = findViewById(R.id.etNewEmail);

        btnCreate = findViewById(R.id.btnCreateNew);

        btnCreate.setOnClickListener(v ->{


            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();


            if (name.isEmpty()||phone.isEmpty()||email.isEmpty()){
                Toast.makeText(this, "Make sure non of the fields is empty!!!", Toast.LENGTH_SHORT).show();
            }else {

                Contact contact = new Contact();

                contact.setName(name);
                contact.setNumber(phone);
                contact.setEmail(email);
                contact.setUserEmail(ApplicationClass.user.getEmail());

                showProgress(true);
                tvLoad.setText("Creating a new contact.....please wait!!!");

                Backendless.Persistence.save(contact, new AsyncCallback<Contact>() {
                    @Override
                    public void handleResponse(Contact response) {
                        Toast.makeText(NewContacts.this, "New Contact saved successfully!!!", Toast.LENGTH_SHORT).show();
                        showProgress(false);
                        etName.setText("");
                        etPhone.setText("");
                        etEmail.setText("");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(NewContacts.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                        showProgress(false);
                    }
                });


            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}