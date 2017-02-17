package com.vnetsoft.sirtmca.view;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LoginSIRTMCA extends Activity {
	protected static final int REQUEST_SIGNUP = 1;
	protected static final String Tag = "LoginSIRT";
	EditText emailEd, passwordEd;
	Button loginBtn, signupBtn;
	RadioButton rbStudent, rbAccount;
	String email ,password;
    final String emailSirt = "sirtmca@bhopal.com";
    final String passwordSirt = "sirt@123";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_sirtmca);
		ActionBar mActionBar = getActionBar();
		mActionBar.hide();
		
		emailEd = (EditText)findViewById(R.id.input_emailEd);
		passwordEd = (EditText)findViewById(R.id.input_passwordEd);
		loginBtn = (Button)findViewById(R.id.btn_login);
		signupBtn = (Button)findViewById(R.id.btn_createAccount);
		
		loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				onLogin();
			}


			@SuppressLint("InlinedApi")
			private void onLogin() {
				Log.d(Tag, "onLogin()");
				if(!validate())
				{
					onLoginFailed();
				}
				else
				{

		            if(emailSirt.equals(emailEd.getText().toString()) && passwordSirt.equals(passwordEd.getText().toString()))
		            {
		        	    loginBtn.setEnabled(false);
		      	        final ProgressDialog progressDialog = new ProgressDialog(LoginSIRTMCA.this,android.R.style.Theme_Material_Dialog);
		      	        progressDialog.setIndeterminate(true);
		      	        progressDialog.setMessage("Authentication");
		      	        progressDialog.show();
		      	        
		      	        new android.os.Handler().postDelayed(new Runnable()
		      	        {

		      				@Override
		      				public void run() {
		      		
		      						onLoginSuccess();
			      					progressDialog.dismiss();
		      					
		      				}

		      				private void onLoginSuccess() {
		      				    try
		      				    {

		      				    		loginBtn.setEnabled(true);
				      					Intent intent = new Intent(getBaseContext(),SIRTMCA.class);
				      		        	startActivity(intent);
				      					finish();

		      				    }
		      					catch(Exception e)
		      					{
		      						e.printStackTrace();
		      					}
		      				}
		      	        	
		      	        },2500);
		            }
		            else
		            {
						  emailEd.setError("email addess is not authorized");
                          passwordEd.setError("password is not authorized");

		            }

				}
			}

			private boolean validate() {
				 boolean valid =true;
			     email = emailEd.getText().toString();
			     password = passwordEd.getText().toString();
			  if(email.isEmpty())
			  {
				  emailEd.setError("enter email");
			      valid = false;
			  }
			  else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
			  {
				  emailEd.setError("entered email addess is not valid");
			      valid = false; 
			  }

			  if(password.isEmpty())
			  {
				  passwordEd.setError("enter password");
				  valid = false;
			  }

				return valid;
			}

			private void onLoginFailed() {
				Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();	
			}
		});
		
		signupBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),SignupSIRTMCA.class);
				startActivityForResult(intent,REQUEST_SIGNUP);
				signupBtn.setBackgroundColor(getResources().getColor(R.color.primaryColorDark));
				signupBtn.setTextColor(getResources().getColor(R.color.white));
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode,Intent data)
	{
		if(requestCode == REQUEST_SIGNUP)
		{
			if(resultCode==RESULT_OK)
			{
				this.finish();
			}
		}
		
	}

}
