package in.parthabobbysarathy.myfirstapi.view;

public interface RegisterView {

    void invalidEmail();
    void isEmpty(int editText);
    void registrationFailed(String s);
    void registrationSuccess(String message);
    void inValidLength(int editText);
}
