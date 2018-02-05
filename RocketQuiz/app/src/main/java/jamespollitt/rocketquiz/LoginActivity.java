package jamespollitt.rocketquiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private Context context = this;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "LoginUser::"; // Tag to see where the error is occurring.

    private EditText emailField; // The EditText field where the user will type their email address.
    private EditText passwordField; // The EditText field where the user will type their password.

    private String email; // The String the user inputs for their email.
    private String password; //  The String the user inputs for password.

    private boolean validEmail; // Variable is changed dependant upon the email verification check.

    private DialogController dialogController = new DialogController(); // Dialog Controller

    /*
    Handle the creation of the activity. Set up UI components and event handlers.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        validEmail = false; // Default value for the valid email check.

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
                else {
                    Log.d(TAG, "onAuthStateChanged:signed_out:");
                }
            }
        };
        // UI widget binding.
        emailField = (EditText)findViewById(R.id.loginEmailField);
        passwordField = (EditText)findViewById(R.id.loginPassword);
        TextView forgotPassword = (TextView)findViewById(R.id.forgotPasswordText);
        forgotPassword.setOnClickListener(forgotPasswordHandler);
        Button loginButton = (Button)findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(loginButtonHandler);
    }

    /**
     * Check that the user has input a valid email address in the sign up fields.
     * @param email the user's String input
     */
    private void checkEmailValid(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        validEmail = pattern.matcher(email).matches();
    }

    /*
    OnClickListener for the 'Forgot password?' TextView. Requires the user to input their email
    address within the email field, stores it into a variable (email). If the user does not input
    their email address into the field before clicking the TextView, they will be greeted with an
    Alert Dialog informing them to do so. If the user has input their email address, a check will be
    performed to see whether the user has input a valid email address. If the email is valid, a
    password reset email will be sent via Firebase.
    */
    View.OnClickListener forgotPasswordHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            email = emailField.getText().toString();
            if(email.equals("")){
                dialogController.okDialog("Input Email Address",
                        "You must supply your email address so we can send you a password reset email.",
                        context);
            }
            else {
                checkEmailValid(email);
                if(validEmail){
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Password reset email sent");
                                        Toast.makeText(LoginActivity.this, "Password reset email sent",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Log.d(TAG, "Password reset email failed to send");
                                        Toast.makeText(LoginActivity.this, "Password reset email failed to send",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    dialogController.okDialog("Invalid Email Address",
                            "Please supply a valid email address so we can send you a password reset email.",
                            context);
                }
            }
        }
    };

    /*
    OnClickListener for the 'Sign In' button. When the user clicks the button, if they have entered
    all of the required details, a validation check will be performed on the email address to make
    sure the provided email is in a valid format. If the email is valid, the user will be shown a
    progress dialog in order to provide a visual cue of 'something happening' whilst Firebase takes
    a while to authenticate the user. Any checks which fail (no input/invalid email) will be shown
    an AlertDialog.
    */
    View.OnClickListener loginButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            email = emailField.getText().toString();
            password = passwordField.getText().toString();
            if(!email.equals("") && !password.equals("")){
                checkEmailValid(email);
                if(validEmail){
                    signIn();
                }
                else{
                    dialogController.okDialog("Invalid Email", "Please use a valid email format.\n\nExample:\n\njohn@gmail.com", context);
                }
            }
            else {
                dialogController.missingFieldsDialog(context);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /*
    This method is where the users input and email address will be authenticated with Firebase. This
    method is only called from the OnClickListener for the 'Sign In' button, when the user has met
    the various checks against email and password input.
    */
    private void signIn(){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog); // Progress dialog for displaying 'doing something' message to user.
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if(!task.isSuccessful()){
                            Log.w(TAG, "signInWithEmail:failed: ", task.getException());
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseNetworkException noInternet) {
                                Log.d(TAG, "onComplete: bad_network_connection");
                                progressDialog.dismiss();
                                dialogController.okDialog("Network Error",
                                        "Please check your network connection", context);
                            }
                            catch (FirebaseAuthInvalidUserException invalidUser){
                                Log.d(TAG, "onComplete: invalid_user");
                                progressDialog.dismiss();
                                dialogController.okDialog("Account Error",
                                        "User not found, please sign up or an account", context);
                            }
                            catch (FirebaseAuthInvalidCredentialsException invalidCredentials){
                                Log.d(TAG, "onComplete: invalid_user");
                                progressDialog.dismiss();
                                dialogController.okDialog("Incorrect Credentials",
                                        "The email and password combination did not match any in our records",
                                        context);
                            }
                            catch (Exception e) {
                                Log.d(TAG, "onComplete: " + e.getMessage());
                            }
                        }
                        else{
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String name = "";
                            if(user != null){
                                name = user.getDisplayName();
                                email = user.getEmail();
                            }
                            progressDialog.dismiss(); // Make sure the progress dialog isn't continuing running in the background.
                            openHome(name); // open the home page with Intent.
                        }
                    }
                });
    }

    /**
     * Handle the final step of registration by opening the home activity and closing any running
     * background activities.
     * @param name The users name to be passed from Firebase to here to the home screen.
     */
    private void openHome(String name){
        Intent homeIntent = new Intent(this, HomeActivity.class);
        finishAffinity(); // this line closes any remaining activities such as the landing activity
        homeIntent.putExtra("userEmail", email); // pass the users email address.
        homeIntent.putExtra("userName", name); // pass the users (display)name, for home UI.
        //homeIntent.putExtra("userPhoto", photo); // Pass the users photo URI, for home UI.
        startActivity(homeIntent);
        finish();
    }
}
