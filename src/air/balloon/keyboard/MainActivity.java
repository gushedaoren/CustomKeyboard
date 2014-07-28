package air.balloon.keyboard;

import air.balloon.keyboard.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText et1;
	CustomKeyBoard customKeyBoard;
	
	String TAG="MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et1 = (EditText) findViewById(R.id.et1);

		customKeyBoard = new CustomKeyBoard(this);
		
		
		

		et1.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				Log.i(TAG, "fosus:"+hasFocus);
				
				
				if (hasFocus) {
					customKeyBoard.showKeyboard(et1);
				} else {
					customKeyBoard.hideKeyboard();
				}

			}
		});

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				customKeyBoard.showKeyboard(et1);
			}
		}, 300);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

}
