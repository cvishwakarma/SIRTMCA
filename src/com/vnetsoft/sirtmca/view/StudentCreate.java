package com.vnetsoft.sirtmca.view;

import com.vnetsoft.sirtmca.controllers.StudentDataSource;
import com.vnetsoft.sirtmca.models.Student;
import com.vnetsoft.sirtmca.models.StudentValidator;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentCreate extends Activity  {
	
	private static final String Tag = "StudentAdd";
	EditText etName, etRollnum, etFatherName, etPhone, etEmail;
	Button btnAdd;
	StudentValidator validator = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_create);
		ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
		validator = new StudentValidator(this);
		
		etName = (EditText) findViewById(R.id.add_etName);
		etName.setTag(getResources().getString(R.string.name));
		etRollnum = (EditText) findViewById(R.id.add_etRollnum);
		etRollnum.setTag(getResources().getString(R.string.rollnumber));
		etFatherName = (EditText) findViewById(R.id.add_etFatheName);
		etFatherName.setTag(getResources().getString(R.string.fathername));
		etPhone = (EditText) findViewById(R.id.add_etPhone);
		etPhone.setTag(getResources().getString(R.string.phone));
		etEmail = (EditText) findViewById(R.id.add_etEmail);
		etEmail.setTag(getResources().getString(R.string.email));
		
		btnAdd = (Button) findViewById(R.id.add_btnAdd);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 studentAdd();
				
			}
		});
	}
	
	
	private void studentAdd() {
		 Log.d(Tag, "CreateStudent");
	        if(!validate())
	        {
	        	studentAddFailed();
	        	return;
	        }
	        else
	        {
	        	if(validator.isValidText(etName) && validator.isValidText(etRollnum) && validator.isValidText(etFatherName) 
						&& validator.isValidText(etPhone) && validator.isValidText(etEmail)) {
	        		btnAdd.setEnabled(false);
					Student student = new Student();
					student.set_name(etName.getText().toString());
					student.set_rollnumber(etRollnum.getText().toString());
					student.set_fathername(etFatherName.getText().toString());
					student.set_phone(etPhone.getText().toString());
					student.set_email(etEmail.getText().toString());
					addStudent(student);
	        	
	        }
	        	
	        }
	        
	        
		
	}


	@SuppressLint("InlinedApi")
	private void studentAddFailed() {
		new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert).setIcon(android.R.drawable.ic_dialog_info)
		.setTitle("Please fill all the blanks field")
		.setMessage(R.string.not_added_student_msg)
		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface arg0, int which) {
						
					}
			
				}).setNegativeButton(R.string.cancel, null)
		.show();
		
	}


	private boolean validate() {
		boolean valid = true;
		String name = etName.getText().toString();
		String rollnumber = etRollnum.getText().toString();
		String fathername = etFatherName.getText().toString();
		String phone = etPhone.getText().toString();
		String email = etEmail.getText().toString();
		String patternNameValidation = "[A-Z][a-z]+( [A-Z][a-z]+)";
		
		if(rollnumber.isEmpty())
		{
			etRollnum.setError("enter rollnumber");
			return false;
		}
		 if(name.isEmpty())
		{
			etName.setError("enter full name");
			return false;
		}
		 
		else if(!java.util.regex.Pattern.matches(patternNameValidation, name))
		{
			etName.setError("enter valid full name ");
			return false;	
		}
		 
		 if(fathername.isEmpty()||fathername.length()<5)
		{
			etFatherName.setError("enter father name");
			return false;
		}
		else if(!java.util.regex.Pattern.matches(patternNameValidation, fathername))
		{
			etFatherName.setError("enter valid father name");
			return false;	
		}
	
		 if(phone.isEmpty())
		{
			etPhone.setError("enter phone number");
			return false;
		}
		 else if(!android.util.Patterns.PHONE.matcher(phone).matches())
		 {
			etPhone.setError("enter valid phone number");
			return false; 
		 }
	     if(email.isEmpty())
		{
			etEmail.setError("enter email address");
			return false;
		}
	    else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
		 {
		    etEmail.setError("enter valid email address");
		    return false;
		 }

		return valid;
	}
	
	
	public void onBackPressed()
	{   super.onBackPressed();
		moveTaskToBack(true);
	}
	

	


	@SuppressLint("InlinedApi")
	private void addStudent(Student student) {
		StudentDataSource studentDS = new StudentDataSource(this);
		studentDS.open(true); // open DB connection in writable mode to add Student register
		if(studentDS.addStudent(student) != -1){
			new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle(R.string.added_student_msg)
				.setMessage(student.get_name() + " " + getResources().getString(R.string.added_student_msg))
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent intent = new Intent(getBaseContext(),SIRTMCA.class);
						startActivity(intent);
						
					}
				}).setNegativeButton("Cancle", null)
				.show();
		}
		studentDS.close(); 
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		CreateMenu(menu);
		//getMenuInflater().inflate(R.menu.student_records, menu);
		return true;
	}
	
	private void CreateMenu(Menu menu) {
		MenuItem savestudent = menu.add(0,0,0,"save student");
		{
		   savestudent.setIcon(android.R.drawable.ic_menu_save);
		   savestudent.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case android.R.id.home:
            Intent intent = new Intent(getBaseContext(),SIRTMCA.class);
            startActivity(intent);
			return true;
		case 0:
			try
			{
				studentAdd();	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}

			return true;

		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}

