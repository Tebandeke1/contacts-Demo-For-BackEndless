package com.tabutech.contactsdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import static com.tabutech.contactsdemo.ContactsList.TEXT_IND;

public class ContactInfo extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    private TextView tvChar,tvName;
    private EditText etName,etPhone,etEmail;
    private Button btnSubmit;
    private ImageView ivCall,ivMail,ivEdit,ivDelete;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        layout = findViewById(R.id.editViews);
        layout.setVisibility(View.GONE);

        final int index = getIntent().getIntExtra(TEXT_IND,0);

        tvChar = findViewById(R.id.tvChar);
        tvName = findViewById(R.id.tvName);

        etName = findViewById(R.id.etReName);
        etEmail = findViewById(R.id.etReEmail);
        etPhone = findViewById(R.id.etRePhone);

        btnSubmit  = findViewById(R.id.btnSubmit);

        ivCall = findViewById(R.id.ivCall);
        ivMail = findViewById(R.id.ivMail);
        ivEdit = findViewById(R.id.ivEdit);
        ivDelete = findViewById(R.id.ivDelete);

        etName.setText(ApplicationClass.contacts.get(index).getName());
        etEmail.setText(ApplicationClass.contacts.get(index).getEmail());
        etPhone.setText(ApplicationClass.contacts.get(index).getNumber());

        tvChar.setText(ApplicationClass.contacts.get(index).getName().toUpperCase().charAt(0)+"");
        tvName.setText(ApplicationClass.contacts.get(index).getName());

        ivCall.setOnClickListener(v ->{

            String uri = "tel:"+ApplicationClass.contacts.get(index).getNumber();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        });

        ivMail.setOnClickListener(v->{

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_EMAIL,ApplicationClass.contacts.get(index).getEmail());
            startActivity(Intent.createChooser(intent,"Send email to "+ApplicationClass.contacts.get(index).getName()));
        });
        ivEdit.setOnClickListener(v->{
            layout.setVisibility(View.VISIBLE);

        });
        ivDelete.setOnClickListener(v->{

            final AlertDialog.Builder builder = new AlertDialog.Builder(ContactInfo.this);
            builder.setTitle("Deleting a contact!!!");
            builder.setMessage("Are you sure you want to delete this contact?");

            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                showProgress(true);
                tvLoad.setText("Deleting Contact Please wait.............");

                Backendless.Persistence.of(Contact.class).remove(ApplicationClass.contacts.get(index), new AsyncCallback<Long>() {
                    @Override
                    public void handleResponse(Long response) {
                        ApplicationClass.contacts.remove(index);
                        Toast.makeText(ContactInfo.this, "Contact deleted successfully.", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        ContactInfo.this.finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(ContactInfo.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            });

            builder.setNegativeButton("No", (dialogInterface, i) -> {

            });
            builder.show();

        });

        btnSubmit.setOnClickListener( v-> {

            String name = etName.getText().toString().trim();
            String tel = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (name.isEmpty()||tel.isEmpty()||email.isEmpty()){
                Toast.makeText(this, "Make sure all fields are not empty.", Toast.LENGTH_SHORT).show();
            }else {

                ApplicationClass.contacts.get(index).setName(name);
                ApplicationClass.contacts.get(index).setNumber(tel);
                ApplicationClass.contacts.get(index).setEmail(email);
                showProgress(true);
                tvLoad.setText("Updating contact...Please wait.....");

                Backendless.Persistence.save(ApplicationClass.contacts.get(index), new AsyncCallback<Contact>() {
                    @Override
                    public void handleResponse(Contact response) {

                        tvChar.setText(ApplicationClass.contacts.get(index).getName().toUpperCase().charAt(0)+"");
                        tvName.setText(ApplicationClass.contacts.get(index).getName());
                        Toast.makeText(ContactInfo.this, "Contact updated successfully.", Toast.LENGTH_SHORT).show();
                        showProgress(false);
                        layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(ContactInfo.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
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