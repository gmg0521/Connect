package com.andoid.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinPage extends AppCompatActivity {

    private static final String TAG = "";
    private FirebaseAuth mAuth;
    private Intent i = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_page);
        i = getIntent();

        mAuth = FirebaseAuth.getInstance();

        final TextView nationText = (TextView) findViewById(R.id.nation);
        EditText idtex = (EditText) findViewById(R.id.idText);
        EditText pwtex = (EditText) findViewById(R.id.pwText);
        EditText pwcirtex = (EditText) findViewById(R.id.pwcirText);

        nationText.setText(i.getStringExtra("selected_nation"));

        if (i.getStringExtra("selected_nation").equals("Korean")) {
            // Read from the database
            idtex.setHint("인증이 가능한 이메일을 써주세요.");
            pwtex.setHint("8~20자리의 비밀번호를 입력해주세요");
            pwcirtex.setHint("위의 비밀번호를 다시 입력해주세요 :D");
        } else {
            idtex.setHint("Please insert email can be authenticate");
            pwtex.setHint("8~20 Characters, No special characters.");
            pwcirtex.setHint("Please Input Your Password Again :D");
        }

    }

    public boolean isValidPasswd(String target) {
            String Password_PATTERN = "^(?=.*[a-zA-Z]+)(?=.*[0-9]+).{1,6}$";
            Pattern pattern = Pattern.compile(Password_PATTERN);
            Matcher matcher = pattern.matcher(target);
            return matcher.matches();
        }

    public boolean isValidEmail(String target) {
        if (target == null || TextUtils.isEmpty(target)) {
            return false;
        }
        else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private void createUser() {

        i = getIntent();

        EditText idtex = (EditText) findViewById(R.id.idText);
        EditText pwtex = (EditText) findViewById(R.id.pwText);

        final String email = idtex.getText().toString();
        final String password = pwtex.getText().toString();


        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!isValidEmail(email)){
                        Log.e(TAG, "createAccount: email is not valid ");
                        Toast.makeText(JoinPage.this, "Email is not valid",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (isValidPasswd(password)){
                        Log.e(TAG, "createAccount: password is not valid ");
                        Toast.makeText(JoinPage.this, "Password is not valid",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(task.isSuccessful()) {

                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        EditText idtex = (EditText) findViewById(R.id.idText);
                        Intent intent = new Intent(JoinPage.this, LoginPage.class);
                        Toast.makeText(JoinPage.this, "회원가입에 성공 했습니다.이메일을 인증해 주세요.", Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        user.sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Email sent.");
                                        }
                                    }
                                });

                    }

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(JoinPage.this, R.string.auth_failed,
                                Toast.LENGTH_SHORT).show();
                    }

                    // ...
                }
            });
    }

    public void onNextClick(View view){
        createUser();
    }


}