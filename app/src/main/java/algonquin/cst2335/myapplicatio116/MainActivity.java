package algonquin.cst2335.myapplicatio116;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Jiaying Qiu
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /** This holds the text at the center of the screen*/
    private TextView hint = null;

    /** This holds the password input*/
    private EditText passwordInput = null;

    /** This holds the login button*/
    private Button btn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hint = findViewById(R.id.textview);
        passwordInput = findViewById(R.id.editPassword);
        btn = findViewById(R.id.loginbutton);

        btn.setOnClickListener(clk->{
            String password = passwordInput.getText().toString();
            if(checkPasswordComplexity(password)){
                hint.setText("Your password meets the requirements");
            } else {
                hint.setText("You shall not pass");
            };
        });
    }

    /** This function checks the complexity of a password
     *
     * @param pw The String object that is to be checked
     * @return Returns true is the password is complex enough, false if it is too simple.
     */
    boolean checkPasswordComplexity(String pw){
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;
        for(int i = 0; i < pw.length(); i++){
            if(Character.isDigit(pw.charAt(i))){
                foundNumber = true;
            }
            if(Character.isUpperCase(pw.charAt(i))){
                foundUpperCase = true;
            }
            if(Character.isLowerCase(pw.charAt(i))){
                foundLowerCase = true;
            }
            if(isSpecialCharacter(pw.charAt(i))){
                foundSpecial = true;
            }
        }
        if(!foundUpperCase)
        {
            Toast.makeText(this, "Missing upper case letter", Toast.LENGTH_SHORT).show(); // Say that they are missing an upper case letter;
            return false;
        }

        else if( ! foundLowerCase)
        {
            Toast.makeText(this,"Missing lower case letter", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
            return false;
        }

        else if( ! foundNumber) {
            Toast.makeText(this, "Missing number", Toast.LENGTH_SHORT).show();// Say that they are missing number;
            return false;
        }

        else if(! foundSpecial) {
            Toast.makeText(this, "Missing special character", Toast.LENGTH_SHORT).show();// Say that they are missing special character;
            return false;
        }
        else
            return true;
    }

    /** This function checks is a character is a special character
     *
     * @param c The character to be checked
     * @return Return true if it is one of the special characters, false if it is not.
     */
    boolean isSpecialCharacter(char c){
        switch (c){
            case '#':
            case '?':
            case '$':
            case '%':
            case '^':
            case '*':
            case '&':
            case '!':
            case '@':
                return true;
            default:
                return false;
        }
    }

}