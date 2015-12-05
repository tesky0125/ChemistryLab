package com.whut.chemistrylab.listener;

import java.util.List;

import android.os.Bundle;

public interface IInActivityCallback {
	public void onPopup(Bundle data);
	public void onSendSms(List<String> list);
}
