package in.parthabobbysarathy.myfirstapi.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

import in.parthabobbysarathy.myfirstapi.pojo.RegisterResponse;
import in.parthabobbysarathy.myfirstapi.presenter.RegisterPresenter;
import in.parthabobbysarathy.myfirstapi.utils.RetrofitClient;
import in.parthabobbysarathy.myfirstapi.view.RegisterView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterModel implements RegisterPresenter {

  //  private Context context;
    private RegisterView registerView;

    public RegisterModel( RegisterView registerView) {
       // this.context = context;
        this.registerView = registerView;
    }

    @Override
    public void performRegistration(String name, String school, String email, String password, String confirm_password) {

        boolean empty = checkEmpty(name, school, email, password, confirm_password);

        if (empty)
        {
           boolean length =  checkLength(name, school, password);

           if (length)
           {
              boolean pattern = checkPattern(email);

              if (pattern)
              {
                  register(name, school, email, password);
              }
           }
        }
    }

    private void register(String name, String school, String email, String password) {

        Call<RegisterResponse> registerResponseCall = RetrofitClient
                .getInstance()
                .getApi()
                .register(name,school,email,password);

        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {

                if (response.code() == 201)
                {
                    RegisterResponse registerResponse = response.body();

                    assert registerResponse != null;
                    String message = registerResponse.getMessage();
                    if (!TextUtils.isEmpty(message))
                    {
                        registerView.registrationSuccess(message);
                    }
                    else
                    {
                        registerView.registrationFailed("201 : Message empty");
                    }
                }
                else
                {
                    registerView.registrationFailed(response.code() + " : Error");
                }

            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {

                registerView.registrationFailed(t.getMessage());

            }
        });
    }

    private boolean checkPattern(String email) {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            registerView.invalidEmail();
            return false;
        }
        return true;
    }

    private boolean checkLength(String name, String school, String password) {

        if (name.length()<3 || name.length()>15)
        {
            registerView.inValidLength(0);
            return false;
        }
        if (school.length()<3 || school.length()>50)
        {
            registerView.inValidLength(1);
            return false;
        }
        if (password.length()<3 || password.length()>15)
        {
            registerView.inValidLength(3);
            return false;
        }
        return true;
    }


    private boolean checkEmpty(String name, String school, String email, String password, String confirm_password)
    {
        if (TextUtils.isEmpty(name))
        {
            registerView.isEmpty(0);
            return false;
        }
        if (TextUtils.isEmpty(school))
        {
            registerView.isEmpty(1);
            return false;
        }
        if (TextUtils.isEmpty(email))
        {
            registerView.isEmpty(2);
            return false;
        }
        if (TextUtils.isEmpty(password))
        {
            registerView.isEmpty(3);
            return false;
        }
        if (TextUtils.isEmpty(confirm_password))
        {
            registerView.isEmpty(4);
            return false;
        }

        return true;
    }
}
