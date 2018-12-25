package in.parthabobbysarathy.myfirstapi.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import in.parthabobbysarathy.myfirstapi.R;
import in.parthabobbysarathy.myfirstapi.model.RegisterModel;
import in.parthabobbysarathy.myfirstapi.presenter.RegisterPresenter;
import in.parthabobbysarathy.myfirstapi.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    EditText name, school, email, password, confirm_password;
    ProgressDialog progressDialog;

    RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterModel( this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please Wait...");

        name=findViewById(R.id.name);
        school=findViewById(R.id.school);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
    }

    public void isRegisterClicked(View view) {
        progressDialog.show();
        registerPresenter.performRegistration(name.getText().toString(),
                school.getText().toString(), email.getText().toString(), password.getText().toString().trim(),
                confirm_password.getText().toString().trim());
    }

    @Override
    public void invalidEmail() {
        progressDialog.dismiss();
        email.setError("Invalid Email address");
        email.requestFocus();
    }

    @Override
    public void isEmpty(int editText) {
        progressDialog.dismiss();
        switch (editText)
        {
            case 0 :
                name.setError("Mandatory");
                name.requestFocus();
                break;
            case 1 :
                school.setError("Mandatory");
                school.requestFocus();
                break;
            case 2 :
                email.setError("Mandatory");
                email.requestFocus();
                break;
            case 3 :
                password.setError("Mandatory");
                password.requestFocus();
                break;
            case 4 :
                confirm_password.setError("Mandatory");
                confirm_password.requestFocus();
                break;
        }

    }

    @Override
    public void registrationFailed(String s) {
        progressDialog.dismiss();
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registrationSuccess(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void inValidLength(int editText) {
        progressDialog.dismiss();
        switch (editText)
        {
            case 0 :
                name.setError("should be between 3 and 15 characters");
                name.requestFocus();
                break;
            case 1 :
                school.setError("should be between 3 and 50 characters");
                school.requestFocus();
                break;
            case 3 :
                password.setError("should be between 3 and 15 characters");
                password.requestFocus();
                break;
        }


    }
}
