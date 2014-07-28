package cn.oliver.shnu.keyboard;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;

import cn.oliver.shnu.customkeyboard.R;

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
	private ArrayList<String> usrList = new ArrayList<String>();
	private Button btn_remember, btn_login, btn_history;

	private TextView tv_find_password, tv_register;

	private ButtonOnClickListener listener_btn = null;

	private boolean isRemember = true;

	private LayoutInflater layoutInflater;
	private ViewFlipper viewFilpper;
	public PopupWindow popup;
	private View keyboardsView;
	private View popView;
	private LinearLayout container;
	private int keyboardIndex = 0;
	private Button btn_number, btn_character, btn_symbol, btn_digital, btn_clear, btn_shift;
	public boolean isCapital = false, isShowing = false;
	private ViewPager g_ll;

	private boolean isAlReadyLogin = false;
	private String olderPhoneNumber = "";

	private Gallery gallery_head;
	private String[] numberList;
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

	public void showKeyboard(EditText et_password) {

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
		viewFilpper.setInAnimation(AnimationUtils.loadAnimation(context, R.drawable.animation_in_from_bottom));
		viewFilpper.setFlipInterval(600000);

		popup.setFocusable(true);
		popup.setWidth(LayoutParams.FILL_PARENT);
		popup.setHeight(LayoutParams.WRAP_CONTENT);
		popup.setBackgroundDrawable(new BitmapDrawable());
		if (!popup.isShowing())
			popup.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
		viewFilpper.startFlipping();

		Button btn_pre = (Button) popView.findViewById(R.id.btn_pre);
		btn_pre.setOnClickListener(listener_btn);
		Button btn_item_next = (Button) popView.findViewById(R.id.btn_next);
		btn_item_next.setOnClickListener(listener_btn);
		Button btn_finish = (Button) popView.findViewById(R.id.btn_finish);
		btn_finish.setOnClickListener(listener_btn);
		btn_number = (Button) popView.findViewById(R.id.btn_number);
		btn_number.setOnClickListener(listener_btn);
		btn_character = (Button) popView.findViewById(R.id.btn_character);
		btn_character.setOnClickListener(listener_btn);
		btn_symbol = (Button) popView.findViewById(R.id.btn_symbol);
		btn_symbol.setOnClickListener(listener_btn);

		popView.findViewById(R.id.btn_keyboard_ok).setOnClickListener(listener_btn);

		container = (LinearLayout) popView.findViewById(R.id.llkeyboard);

		switchView(0);
		// container.addView(keyboardsView);
	}

	public void switchView(int index) {
		container.removeAllViews();

		if (index == 0) {
			keyboardsView = activity.getLayoutInflater().inflate(R.layout.keyboard_digitals, null);

			OrderEncoder re = new OrderEncoder();
			int[] digital = re.reOrder();
			btn_clear = (Button) keyboardsView.findViewById(R.id.btn_clear);
			btn_clear.setOnClickListener(listener_btn);

			for (int i = 0; i < 10; i++) {
				String btnStr = MessageFormat.format("btn_digital{0}", Integer.toString(i + 1));
				int id = context.getResources().getIdentifier(btnStr, "id", context.getPackageName());
				Button btn_digital = (Button) keyboardsView.findViewById(id);
				btn_digital.setText(Integer.toString(digital[i]));
				btn_digital.setTag(digital[i]);
				btn_digital.setOnClickListener(listener_btn);
			}
			container.addView(keyboardsView);
		} else if (index == 1) {

			if (isCapital)
				keyboardsView = activity.getLayoutInflater().inflate(R.layout.keyboard_character_capital, null);
			else
				keyboardsView = activity.getLayoutInflater().inflate(R.layout.keyboard_character, null);
			btn_clear = (Button) keyboardsView.findViewById(R.id.btn_clear);
			btn_clear.setOnClickListener(listener_btn);
			btn_shift = (Button) keyboardsView.findViewById(R.id.btn_shift);
			btn_shift.setOnClickListener(listener_btn);
			for (int i = 0; i < 26; i++) {
				String btnStr = MessageFormat.format("btn_character{0}", Integer.toString(i + 1));
				int id = context.getResources().getIdentifier(btnStr, "id", context.getPackageName());
				Button btn_character = (Button) keyboardsView.findViewById(id);
				btn_character.setTag(0x41 + i);
				btn_character.setOnClickListener(listener_btn);
			}
			// initKeyBoard();
			container.addView(keyboardsView);
		} else {
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

			else if (v.getId() == R.id.btn_keyboard_ok) {

			} else if (v.getId() == R.id.btn_number) {
				// switch to digital
				btn_character.setBackgroundResource(R.drawable.key_btn_normal);
				btn_character.setTextColor(Color.BLACK);
				btn_symbol.setBackgroundResource(R.drawable.key_btn_normal);
				btn_symbol.setTextColor(Color.BLACK);
				if (keyboardIndex != 0) {
					keyboardIndex = 0;
					switchView(keyboardIndex);
					v.setBackgroundResource(R.drawable.keyboard_number);
					((Button) v).setTextColor(Color.WHITE);
				}
			} else if (v.getId() == R.id.btn_character) {
				// switch to digital
				btn_number.setBackgroundResource(R.drawable.key_btn_normal);
				btn_symbol.setBackgroundResource(R.drawable.key_btn_normal);
				btn_number.setTextColor(Color.BLACK);
				btn_symbol.setTextColor(Color.BLACK);
				if (keyboardIndex != 1) {
					keyboardIndex = 1;
					switchView(keyboardIndex);
					v.setBackgroundResource(R.drawable.keyboard_character);
					((Button) v).setTextColor(Color.WHITE);
				}
			} else if (v.getId() == R.id.btn_symbol) {
				// switch to digital
				btn_number.setBackgroundResource(R.drawable.key_btn_normal);
				btn_character.setBackgroundResource(R.drawable.key_btn_normal);
				btn_number.setTextColor(Color.BLACK);
				btn_character.setTextColor(Color.BLACK);
				if (keyboardIndex != 2) {
					keyboardIndex = 2;
					switchView(keyboardIndex);
					v.setBackgroundResource(R.drawable.keyboard_symbol);
					((Button) v).setTextColor(Color.WHITE);
				}
			} else if (v.getId() == R.id.btn_clear) {

				et_password.setText("");

			} else if (v.getId() == R.id.btn_shift) {
				if (isCapital == true) {
					// v.setBackgroundResource(R.drawable.shift_btn_normal);
					isCapital = false;
				} else {
					// v.setBackgroundResource(R.drawable.shift_btn_touch);
					isCapital = true;
				}
				switchView(1);
			} else {
				String str = "";
				int index = et_password.getSelectionEnd();
				if (keyboardIndex == 0 || keyboardIndex == 2) {
					str = ((Button) v).getText().toString().replaceAll(" ", "");
				} else {
					if (isCapital) {
						str = ((Button) v).getText().toString().toUpperCase();
					} else {
						str = ((Button) v).getText().toString();
					}
				}

				StringBuffer strNew = new StringBuffer(et_password.getText().toString());
				str = strNew.insert(index, str).toString();

				index++;

				et_password.setText(str);
				et_password.setSelection(index);
			}
		}

	};

	private void clearViews() {
		keyboardsView = null;
		if (container != null) {
			container.removeAllViews();
		}
		container = null;
		btn_digital = null;
		btn_character = null;
		btn_symbol = null;
		btn_clear = null;
		btn_shift = null;

		viewFilpper = null;
		popView = null;

		if (popup != null && popup.isShowing())
			popup.dismiss();
		popup = null;
		System.gc();
	}

}
