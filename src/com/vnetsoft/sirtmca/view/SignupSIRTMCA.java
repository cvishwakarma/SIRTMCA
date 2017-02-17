package com.vnetsoft.sirtmca.view;

import android.app.Activity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupSIRTMCA extends Activity {

	protected static final String Tag = "SignupSIRT";
	EditText nameEd, emailEd, passwordEd, confirmPassEd;
	Button signupBtn;
	TextView loginLink;
	String name,email,password,confirmPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup_sirtmca);
		ActionBar mActionBar = getActionBar();
		mActionBar.hide();
		
		nameEd = (EditText)findViewById(R.id.input_nameEd);
		emailEd = (EditText)findViewById(R.id.input_emailEd);
		passwordEd = (EditText)findViewById(R.id.input_passwordEd);
		confirmPassEd = (EditText)findViewById(R.id.input_confirmPassEd);
		signupBtn = (Button)findViewById(R.id.btn_signUp);
		loginLink = (TextView)findViewById(R.id.link_login);
		
		signupBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onSignup();
			}

			@SuppressLint("InlinedApi")
			private void onSignup() {
				Log.d(Tag, "onSignup()");
				if(!validate())
				{  
               	onSignupFailed();
				}
				else 
				{
					signupBtn.setEnabled(false);
					final ProgressDialog progressDialog = new ProgressDialog(SignupSIRTMCA.this,android.R.style.Theme_Material_Dialog);
					progressDialog.setIndeterminate(true);
					progressDialog.setMessage("Creating Account");
					progressDialog.show();
					name = nameEd.getText().toString();
					email= emailEd.getText().toString();
					password = passwordEd.getText().toString();
					
					new android.os.Handler().postDelayed(new Runnable()
					{

						@Override
						public void run() {
							try
							{
			                       onSignUpSuccess();	
							}
							catch(Exception exe)
							{
								exe.printStackTrace();
							}
						   progressDialog.dismiss();

						}

						private void onSignUpSuccess() {
							signupBtn.setEnabled(true);			
							setResult(RESULT_OK,null);	
							Toast.makeText(getBaseContext(), "Account Created", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(getBaseContext(),SIRTMCA.class);
							startActivity(intent);
							finish();
							
						}
						
					},3000);	
				}
				
			}

			private boolean validate() {
				boolean valid = true;
				name = nameEd.getText().toString();
				email= emailEd.getText().toString();
				password = passwordEd.getText().toString();
				confirmPass = confirmPassEd.getText().toString();
				String patternNameValidation = "[A-Z][a-z]+( [A-Z][a-z]+)";
				 
				 if(name.isEmpty() || name.length()<3)
				 {
					 nameEd.setError("enter name ");
					 valid = false;
				 }
				 else if(!java.util.regex.Pattern.matches(patternNameValidation, name))
					{
						nameEd.setError("enter valid full name ");
						return false;	
					}
				  if(email.isEmpty())
				  {
					  emailEd.setError("enter email");
				      valid = false;
				  }
				  if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
				  {
					  emailEd.setError("entered email addess is not valid");
				      valid = false; 
				  }

				  if(password.isEmpty())
				  {
					  passwordEd.setError("enter password");
					  valid = false;
				  }
				 if(password.length()<6||password.length()>16)
				  {
					  passwordEd.setError("between 6 and 16 alphanumeric characters");
					  valid = false;
				  }
				 if(confirmPass.isEmpty())
				 {
					  confirmPassEd.setError("enter confirmation password");
					  valid = false;
				 }
				 if(confirmPass.length()<6||confirmPass.length()>16)
				 {
					 confirmPassEd.setError("between 6 and 16 alphanumeric characters");
					  valid = false;
					 
				 }
				 
				 if(!confirmPass.equals(passwordEd.getText().toString()))
				 {
					confirmPassEd.setError("password did not matching");
					confirmPassEd.setHighlightColor(getResources().getColor(R.color.soft_red));
					passwordEd.setHighlightColor(getResources().getColor(R.color.soft_red));
					valid = false;
				
				 }
		
				return valid;
			}

			private void onSignupFailed() {
				Toast.makeText(getBaseContext(), "Registration Failed", Toast.LENGTH_LONG).show();
				signupBtn.setEnabled(true);
			}
		});
		
		loginLink.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getBaseContext(),LoginSIRTMCA.class);
				loginLink.setTextColor(getResources().getColor(R.color.primaryColorDark));
				startActivity(intent);
				
			}
		});

	}
	
	
	public void onBackPressed()
	{  super.onBackPressed();
		moveTaskToBack(true);
	}
}
