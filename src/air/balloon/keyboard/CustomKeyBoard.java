package air.balloon.keyboard;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;




import air.balloon.keyboard.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class CustomKeyBoard extends View {

	Context context;
	Activity activity;
	
	private ButtonOnClickListener listener_btn = null;

	
	private LayoutInflater layoutInflater;
	private ViewFlipper viewFilpper;
	public PopupWindow popup;
	private View keyboardsView;
	private View popView;
	private LinearLayout container;
	private int keyboardIndex = 0;
	private Button  btn_clear;
	public boolean isCapital = false, isShowing = false;
	
	public SharedPreferences mSettings;
	public ArrayList<HashMap<String, Object>> params = new ArrayList<HashMap<String, Object>>();
	EditText et_password;

	public CustomKeyBoard(Context context) {
		super(context);
		this.context = context;
		activity = (Activity) context;

		listener_btn = new ButtonOnClickListener();

	}

	public void hideKeyboard() {
		final View v0 = activity.getWindow().peekDecorView();
		if (v0 != null && v0.getWindowToken() != null) {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v0.getWindowToken(), 0);
		}
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public void showKeyboard(final EditText et_password) {

		this.et_password = et_password;

		if (popup != null && popup.isShowing()) {
			return;
		}

		keyboardIndex = 0;
		layoutInflater = activity.getLayoutInflater();
		popView = layoutInflater.inflate(R.layout.popwindow_keyboard, null);
		if (popup == null)
			popup = new PopupWindow(popView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);

		viewFilpper = (ViewFlipper) popView.findViewById(R.id.viewFlipper1);
	
		viewFilpper.setFlipInterval(600000);

		popup.setFocusable(true);
		popup.setWidth(LayoutParams.FILL_PARENT);
		popup.setHeight(LayoutParams.WRAP_CONTENT);
		popup.setBackgroundDrawable(new BitmapDrawable());
		if (!popup.isShowing())
			popup.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
		viewFilpper.startFlipping();
  
		
		
	
		

		popView.findViewById(R.id.btn_keyboard_ok).setOnClickListener(listener_btn);

		container = (LinearLayout) popView.findViewById(R.id.llkeyboard);

		switchView(0);
		
		
		
	    
	}

	public void switchView(int index) {
		container.removeAllViews();

		
			keyboardsView = activity.getLayoutInflater().inflate(R.layout.keyboard_symbol, null);
			btn_clear = (Button) keyboardsView.findViewById(R.id.btn_clear);
			btn_clear.setOnClickListener(listener_btn);
			for (int i = 0; i < 10; i++) {
				String btnStr = MessageFormat.format("btn_symbol{0}", Integer.toString(i + 1));
				int id = context.getResources().getIdentifier(btnStr, "id", context.getPackageName());
				Button btn_symbol = (Button) keyboardsView.findViewById(id);
				btn_symbol.setTag(i);
				btn_symbol.setOnClickListener(listener_btn);
			}
			container.addView(keyboardsView);
		
	}

	class ButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			final View v0 = activity.getWindow().peekDecorView();
			if (v0 != null && v0.getWindowToken() != null) {
				InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v0.getWindowToken(), 0);
			}

			if (v.getId() == R.id.btn_keyboard_ok) {

			
			
			} else if (v.getId() == R.id.btn_clear) {

				et_password.setText("");

			} else {
				String str = "";
				int index = et_password.getSelectionEnd();
				if (keyboardIndex == 0 || keyboardIndex == 2) {
					str = ((Button) v).getText().toString().replaceAll(" ", "");
					if(str.equals("sin")){
						str="sin(";
					}else if(str.equals("log")){
						str="log(";
					}
					
					
					
					
				} else {
					if (isCapital) {
						str = ((Button) v).getText().toString().toUpperCase();
					} else {
						str = ((Button) v).getText().toString();
					}
				}

				StringBuffer strNew = new StringBuffer(et_password.getText().toString());
				
				
				
			//	str = strNew.insert(index, str).toString();
              
				
                str=strNew.append(str).toString();
                
                index=str.length();
				et_password.setText(str);
				et_password.setSelection(index);
				//index=index+str.length();
			}
		}

	};

	private void clearViews() {
		keyboardsView = null;
		if (container != null) {
			container.removeAllViews();
		}
		container = null;
		
		
		btn_clear = null;
		
		viewFilpper = null;
		popView = null;

		if (popup != null && popup.isShowing())
			popup.dismiss();
		popup = null;
		System.gc();
	}

}
