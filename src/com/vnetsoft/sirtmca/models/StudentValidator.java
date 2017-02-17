package com.vnetsoft.sirtmca.models;


import com.vnetsoft.sirtmca.view.R;
import android.content.Context;
import android.widget.EditText;

public class StudentValidator {

	Context _context;
	public StudentValidator(Context context)
	{
		this._context = context;
	}
	
	public void noticeInputValidation(EditText editText, boolean valid)
	{
		if(valid)
		{
			editText.setBackgroundColor(_context.getResources().getColor(R.color.soft_green));
			
		}
		else
		{
			editText.setBackgroundColor(_context.getResources().getColor(R.color.soft_red));
			editText.requestFocus();
			editText.setError("enter"+editText.getTag()+"is not valid");
		}
	}
	
	public boolean isValidText(EditText editText) {		
		
		if(editText.getText().toString().equals("")){
			noticeInputValidation(editText, false);
			return false;
		} 
		else
		{
			noticeInputValidation(editText, true);
		    return true;
		}
	}
}
