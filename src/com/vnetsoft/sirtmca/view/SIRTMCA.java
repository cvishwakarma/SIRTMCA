package com.vnetsoft.sirtmca.view;

import java.util.List;
import com.vnetsoft.sirtmca.controllers.StudentDataSource;
import com.vnetsoft.sirtmca.models.Student;
import com.vnetsoft.sirtmca.models.StudentAdapter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.vnetsoft.sirtmca.view.R;

public class SIRTMCA extends ListActivity  {

	StudentAdapter adapter = null;
	List<Student> students = null;
	StudentDataSource studentDS = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		studentDS = new StudentDataSource(this);
		studentDS.open(true); // open DB connection in writable mode, because of Delete option
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		students = studentDS.getStudents(); // retrieve data from DB
		adapter = new StudentAdapter(this, students, studentDS);
		adapter.notifyDataSetChanged();
		this.setListAdapter(adapter);
		// register the listview for context menu
		this.registerForContextMenu(this.getListView());
	}
		

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    
	    this.getMenuInflater().inflate(R.menu.student_records_context_menu, menu);
	    
	    if (v.getId() == this.getListView().getId()) {
	        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
	        Student stu = (Student) getListView().getItemAtPosition(info.position);
	        menu.setHeaderTitle(stu.get_name());
	    }
	}

	
	@SuppressLint("InlinedApi")
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final Student stu = (Student) getListView().getItemAtPosition(info.position);
		
		
		
		switch (item.getItemId()) {
			case R.id.context_updateStudent:
				Intent intent = new Intent(this, StudentUpdate.class);
				intent.putExtra("student_id", stu.get_id());			
				this.startActivity(intent);
			return true;
			
			// delete employee
			case R.id.context_deleteStudent:
				// build a confirmation dialog
			    new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog)
	        	.setIcon(android.R.drawable.ic_menu_delete)
	        	.setTitle(R.string.remove_student)
	        	.setMessage(this.getResources().getString(R.string.remove_confirm_message) + " " + stu.get_name()+" ?")
	        	.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int which) {
	        			final ProgressDialog progressDialog = new ProgressDialog(SIRTMCA.this,android.R.style.Theme_Material_Dialog);
		      	        progressDialog.setIndeterminate(true);
		      	        progressDialog.setMessage("Deleting");
		      	        progressDialog.show();
		      	        
		      	        new android.os.Handler().postDelayed(new Runnable()
		      	        {

							@Override
							public void run() {
			        			int deleted = studentDS.deleteStudent(stu);
			        			if(deleted != 0) {
			        				students.remove(info.position);
			        				adapter.refresh();
				        			progressDialog.cancel();
			        			}
			        			progressDialog.dismiss();

								
							}
		      	        	
		      	        },1000);
	        		}
	        	}).setNegativeButton(R.string.cancel, null).show();
			return true;
			
			default:
				return super.onContextItemSelected(item);
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		CreateMenu(menu);
		//getMenuInflater().inflate(R.menu.student_records, menu);
		return true;
	}
	
	private void CreateMenu(Menu menu) {
		MenuItem addstudent = menu.add(0,0,0,"add student");
		{
		   addstudent.setIcon(android.R.drawable.ic_input_add);
		   addstudent.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	    MenuItem logout = menu.add(0, 1, 1, "logout");
	    {   
	    	logout.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	    }
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuChoice(item);
	}
	
	
	private boolean MenuChoice(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent studentCreateIntent = new Intent(this, StudentCreate.class);
			this.startActivity(studentCreateIntent);
		return true;
		case 1:
			Intent loginIntent = new Intent(this,LoginSIRTMCA.class);
			startActivity(loginIntent);

		}
		
		return false;
	}


	@Override
	protected void onDestroy() {
		studentDS.close(); // close DB connection, before calling super.onDestroy()
		super.onDestroy();
	}	
	
}
