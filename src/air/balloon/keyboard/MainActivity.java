package air.balloon.keyboard;

import air.balloon.keyboard.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends Activity {
   EditText et1;
   CustomKeyBoard customKeyBoard;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText) findViewById(R.id.et1);
        customKeyBoard=new CustomKeyBoard(this);
       
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				customKeyBoard.showKeyboard(et1);
			}
		}, 1000);
    }
    
    
  @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 
	}
    
}
