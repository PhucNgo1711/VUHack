package ntk.android.hackathon2015;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Activity which displays a login screen to the user.
 */
public class SignUpActivity extends Activity {
  // UI references.
  private EditText firstnameEditText;
  private EditText lastnameEditText;
  private EditText usernameEditText;
  private EditText passwordEditText;
  private EditText passwordAgainEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_signup);

    // Set up the signup form.
    firstnameEditText = (EditText) findViewById(R.id.firstname_edit_text);
    lastnameEditText = (EditText) findViewById(R.id.lastname_edit_text);
    usernameEditText = (EditText) findViewById(R.id.username_edit_text);
    passwordEditText = (EditText) findViewById(R.id.password_edit_text);
    passwordAgainEditText = (EditText) findViewById(R.id.password_again_edit_text);
    passwordAgainEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == R.id.edittext_action_signup ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
          signup();
          return true;
        }
        return false;
      }
    });

    // Set up the submit button click handler
    Button mActionButton = (Button) findViewById(R.id.action_button);
    mActionButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        signup();
      }
    });
  }

  private void signup() {
    String firstname = firstnameEditText.getText().toString().trim();
    String lastname = lastnameEditText.getText().toString().trim();
    String username = usernameEditText.getText().toString().trim();
    String password = passwordEditText.getText().toString().trim();
    String passwordAgain = passwordAgainEditText.getText().toString().trim();

    // Validate the sign up data
    boolean validationError = false;
    StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
    if (firstname.length() == 0) {
      validationError = true;
      validationErrorMessage.append(getString(R.string.error_blank_firstname));
    }
    if (lastname.length() == 0) {
      if (validationError) {
        validationErrorMessage.append(getString(R.string.error_join));
      }
      validationError = true;
      validationErrorMessage.append(getString(R.string.error_blank_lastname));
    }
    if (username.length() == 0) {
      if (validationError) {
        validationErrorMessage.append(getString(R.string.error_join));
      }
      validationError = true;
      validationErrorMessage.append(getString(R.string.error_blank_username));
    }
    if (password.length() == 0) {
      if (validationError) {
        validationErrorMessage.append(getString(R.string.error_join));
      }
      validationError = true;
      validationErrorMessage.append(getString(R.string.error_blank_password));
    }
    if (!password.equals(passwordAgain)) {
      if (validationError) {
        validationErrorMessage.append(getString(R.string.error_join));
      }
      validationError = true;
      validationErrorMessage.append(getString(R.string.error_mismatched_passwords));
    }
    validationErrorMessage.append(getString(R.string.error_end));

    // If there is a validation error, display the error
    if (validationError) {
      Toast.makeText(SignUpActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
              .show();
      return;
    }

    // Set up a progress dialog
    final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
    dialog.setMessage(getString(R.string.progress_signup));
    dialog.show();

    // Set up a new Parse user
    final ParseUser user = new ParseUser();
    user.setUsername(username);
    user.setPassword(password);
    user.put("First_Name", firstname);
    user.put("Last_Name", lastname);


    // Call the Parse signup method
    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        dialog.dismiss();
        if (e != null) {
          // Show the error message
          Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        } else {
          ParseObject userPrefs = new ParseObject("userPrefs");
          userPrefs.put("grocery", 0);
          userPrefs.put("dinner", 0);
          userPrefs.put("rent", 0);
          userPrefs.put("ops", 0);
          userPrefs.put("wlb", 0);
          userPrefs.put("culture", 0);
          userPrefs.put("leadership", 0);
          userPrefs.put("image", 0);
          userPrefs.put("pollution", 0);
          userPrefs.put("healthcare", 0);
          userPrefs.put("traffic", 0);
          userPrefs.put("crime", 0);
          userPrefs.put("temp", 0);
          userPrefs.put("snow", 0);
          userPrefs.put("rain", 0);
          user.put("prefs", userPrefs);
          // Start an intent for the dispatch activity
          Intent intent = new Intent(SignUpActivity.this, DispatchActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        }
      }
    });
  }
}
