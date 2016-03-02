package com.color.colorGame;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class BaseDialog extends Dialog {
	
	public BaseDialog(Context context) {
		super(context);
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

}
