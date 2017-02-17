package com.vnetsoft.sirtmca.view;

import java.util.List;

import com.vnetsoft.sirtmca.controllers.StudentDataSource;
import com.vnetsoft.sirtmca.models.Student;
import com.vnetsoft.sirtmca.models.StudentAdapter;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;

public class StudentDetails extends Activity  {
	
	Student student;
	StudentDataSource studentDS = null;
	List<Student> students = null;
	StudentAdapter adapter = null;
	int studentId;
	ActionBar mActionBar ;
	private TextView tvId, tvName, tvRollnumber, tvFathername, tvEmail, tvPhone;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_details);
		ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);


		Bundle bundle = getIntent().getExtras();	
		studentId = bundle.getInt("student_id");
		
		tvId = (TextView) findViewById(R.id.detail_id);
		tvRollnumber = (TextView) findViewById(R.id.detail_rollnum);
		tvName = (TextView) findViewById(R.id.detail_name);
		tvFathername = (TextView) findViewById(R.id.detail_fathername);
		tvEmail = (TextView) findViewById(R.id.detail_email);
		tvPhone = (TextView) findViewById(R.id.detail_phone);
		
		studentDS = new StudentDataSource(this);
		studentDS.open(false);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		student = studentDS.getStudent(studentId);
		fillStudentDetailView(student);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		CreateMenu(menu);
		return true;
	}
	
	private void CreateMenu(Menu menu) {
		MenuItem editstudent = menu.add(0,0,0,"edit student");
		{
		   editstudent.setIcon(android.R.drawable.ic_menu_edit);
		   editstudent.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case android.R.id.home:
            Intent intent = new Intent(getBaseContext(),SIRTMCA.class);
            startActivity(intent);
			return true;
		case 0:
			Intent intentUpdate = new Intent(this, StudentUpdate.class);
			intentUpdate.putExtra("student_id", Integer.parseInt(tvId.getText().toString()));	
			this.startActivity(intentUpdate);
		return true;
		}
		return false;
	}

	/**
	 * @param stu, Student object
	 * @param bundle, Bundle to get Student values from the selected Student
	 */
	private void fillStudentDetailView(Student stu) {
		
		tvId.setText(Integer.toString(stu.get_id()));
		tvRollnumber.setText(stu.get_rollnumber());
		tvName.setText(stu.get_name());
		tvFathername.setText(stu.get_fathername());
		tvEmail.setText(stu.get_email());
		tvPhone.setText(stu.get_phone());
	}
	
	/**
	 * need to override onDestroy in order to close DB connection
	 */
	@Override
	protected void onDestroy() {
		studentDS.close(); // close DB connection, before calling super.onDestroy()
		super.onDestroy();
	}
	
	public void onBackPressed()
	{
		moveTaskToBack(true);
	}
		
	
}
