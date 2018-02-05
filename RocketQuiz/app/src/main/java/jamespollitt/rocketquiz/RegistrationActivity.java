package jamespollitt.rocketquiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;

    private static final String TAG = "CreateUser::";

    private DialogController dialogController = new DialogController();
    private Context context = this;

    private String fullName;
    private String userEmail;
    private String userPassword;

    private String userId;
    private String displayName;
    private String email;
    private Uri photoUrl;

    private EditText regName;
    private EditText regSurname;
    private EditText regEmail;
    private EditText regPassword;
    private EditText confirmPass;

    private boolean termsState;
    private boolean validEmail;

    public static final String AppPREFERENCES = "RocketQuizPrefs" ;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        sharedPreferences = getSharedPreferences(AppPREFERENCES, Context.MODE_PRIVATE);

        termsState = false; // Default value for terms and conditions agreed to.
        validEmail = false; // Default value for user having a valid email address.

        // UI widget binding.
        regName = (EditText) findViewById(R.id.nameField);
        regSurname = (EditText) findViewById(R.id.surnameField);
        regEmail = (EditText) findViewById(R.id.regEmailField);
        regPassword = (EditText) findViewById(R.id.regPassField);
        confirmPass = (EditText) findViewById(R.id.confField);
        CheckBox termsCheck = (CheckBox)findViewById(R.id.termsCheckBox);

        TextView termsText = (TextView)findViewById(R.id.termsCheckText);
        String termsTextContent = "By checking the box, you state you have read and agree to the <u>terms and conditions</u>.";
        termsText.setText(Html.fromHtml(termsTextContent));
        termsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent termsIntent = new Intent(view.getContext(), TermsActivity.class);
                startActivity(termsIntent);
            }
        });

        // 'I already have an account' TextView event handler.
        TextView goLogin = (TextView) findViewById(R.id.goToSignin);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(view.getContext(), LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

        // Firebase set up
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        // Sign up button binding
        Button registerUserBtn = (Button) findViewById(R.id.regBtn);
        registerUserBtn.setOnClickListener(signupButtonHandler);
    }

    public void checkClicked(View v){
        if (((CheckBox) v).isChecked()) {
            termsState = true;
        }
        else {
            termsState = false;
        }
    }

    /*
    This OnClickListener handles the event of the user clicking the 'Sign Up' button.
    The onClick method will handle all of the checks required to successfully complete the sign up
    procedure.
    Checks performed include; all fields completed, valid email address, password length, password
    matching and whether the user agrees to the terms and conditions. Any element which fails the
    checking process will generate an AlertDialog.
    */
    View.OnClickListener signupButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String nameInput = regName.getText().toString().trim();
            String surnameInput = regSurname.getText().toString().trim();
            String emailInput = regEmail.getText().toString().trim();
            String passInput = regPassword.getText().toString().trim();
            String confInput = confirmPass.getText().toString().trim();

            // check whether the user has completed all fields
            if (nameInput.equals("") || surnameInput.equals("") || emailInput.equals("") || passInput.equals("") || confInput.equals("")) {
                confirmPass.setText("");
                dialogController.missingFieldsDialog(context);
            }
            else {
                // check password length, has to be 6 or more
                if (passInput.length() >= 6) {
                    // check password matches confirm password
                    if (confInput.equals(passInput)) {
                        userEmail = emailInput;
                        userPassword = passInput;
                        checkEmailValid(userEmail);
                        fullName = nameInput + " " + surnameInput;
                        if(validEmail){
                            if(termsState){
                                createAccount();
                            }
                            else {
                                dialogController.okDialog("Accept Terms", "You must accept the terms and conditions before registering.", context);
                            }
                        }
                        else {
                            dialogController.okDialog("Invalid Email", "Please use a valid email format.\n\nExample:\n\njohn@gmail.com", context);
                            regEmail.setText("");
                        }
                    }
                    else {
                        dialogController.okDialog("Password Error", "Passwords do not match.", context);
                        regPassword.setText("");
                        confirmPass.setText("");
                    }
                }
                else {
                    dialogController.okDialog("Password Error", "Your password must be at least 6 characters long.", context);
                    regPassword.setText("");
                    confirmPass.setText("");
                }
            }
        }
    };

    /**
     * Check that the user has input a valid email address in the sign up fields.
     * @param email the user's String input
     */
    private void checkEmailValid(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        validEmail = pattern.matcher(email).matches();
    }

    /*
      Create an account with Firebase.
      This method also sets the Firebase User's profile elements such as display name, email and
      photoUrl before creating an entry in the Firebase Realtime Database under the users unique
      user ID (UID).
      The method will check whether an email address has already been registered with Firebase and
      generating an AlertDialog if there is a clash of email address.
      Finally, the method will open the home page and close any running background activities.
     */
    private void createAccount() {
        final ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registering...");
        progressDialog.show();
        Log.d(TAG, "createAccount: " + userEmail);
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                Log.d(TAG, "onComplete: malformed_email");
                                progressDialog.dismiss();
                                dialogController.okDialog("Email Address Issue", "There is an issue with the email address that you entered.", context);
                            }
                            catch (FirebaseAuthUserCollisionException existEmail) {
                                Log.d(TAG, "onComplete: exist_email");
                                progressDialog.dismiss();
                                dialogController.okDialog("User Already Exists", "A user with the email " + userEmail + " already exists.", context);
                                regEmail.setText("");
                            }
                            catch (Exception e) {
                                Log.d(TAG, "onComplete: " + e.getMessage());
                            }
                        }
                        else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null) {
                                UserProfileChangeRequest profileName = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(fullName).build();
                                user.updateProfile(profileName);
                                //TODO: set user photoUrl to a default image in Firebase storage.

                                // Retrieve the users Firebase profile information.
                                userId = mAuth.getCurrentUser().getUid();
                                displayName = mAuth.getCurrentUser().getDisplayName();
                                email = mAuth.getCurrentUser().getEmail();
                                photoUrl = user.getPhotoUrl();

                                // Setting SharedPreferences information.
                                SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
                                preferenceEditor.putString("nameKey", displayName);
                                preferenceEditor.apply();

                                user.sendEmailVerification(); // Firebase sends verification email.
                                createDatabaseEntry(userId); // Create an entry in the Firebase Realtime Database.
                                openHome(); // Open HomeActivity with Intent.
                            }
                        }
                    }
                });
    }

    /**
     * This method creates an entry within the Firebase Realtime Database for the user with a
     * mixture of provided information (name & email) and default data (picture url and score).
     * @param userId the Firebase user's unique identifier
     */
    private void createDatabaseEntry(String userId) {
        databaseReference.child("users").child(userId).child("name").setValue(fullName);
        databaseReference.child("users").child(userId).child("email").setValue(userEmail);
        databaseReference.child("users").child(userId).child("picture").setValue("astronaut");
        databaseReference.child("users").child(userId).child("score").setValue("0");
        databaseReference.child("users").child(userId).child("credits").setValue("0");
        databaseReference.child("users").child(userId).child("correct").setValue("0");
        databaseReference.child("users").child(userId).child("incorrect").setValue("0");
        databaseReference.child("users").child(userId).child("games").setValue("0");
        databaseReference.child("users").child(userId).child("filmtv").setValue("0");
        databaseReference.child("users").child(userId).child("genknow").setValue("0");
        databaseReference.child("users").child(userId).child("geography").setValue("0");
        databaseReference.child("users").child(userId).child("literature").setValue("0");
        databaseReference.child("users").child(userId).child("maths").setValue("0");
        databaseReference.child("users").child(userId).child("science").setValue("0");
        databaseReference.child("users").child(userId).child("tech").setValue("0");
    }

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
     Handle the final step of registration by opening the home activity and closing any running
     background activities.
    */
    private void openHome(){
        Intent homeIntent = new Intent(this, HomeActivity.class);
        finishAffinity(); // This line closes any remaining activities such as the landing activity.
        homeIntent.putExtra("userName", fullName); // Pass the users (display)name for home UI.
        homeIntent.putExtra("userEmail", email); // Pass the users email address to home.
        //homeIntent.putExtra("userPhoto", photoUrl); // Pass the users photo, for home UI.
        startActivity(homeIntent);
        finish();
    }
}