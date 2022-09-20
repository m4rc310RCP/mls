package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.rcp.ui.utils.DateUtils;

public class MDateConverter extends MConverter<Text, Date> {

	@Override
	public boolean eq(Class<?> type) {
		return type == Date.class ;
	}
	
	private final Listener focusListener = e->{
		Text text = (Text) e.widget;
		Date date = fromComponent(text);
		fireChangeValue(date, text);
		sendToComponent(text, date, format);
	};

	
	@Override
	public void addListeners() {
		component.addListener(SWT.FocusOut, focusListener);
		context.registerListener(component, focusListener);
	}
	
	
	
	@Override
	public void reset() {
		sendToComponent(component, null, format);
	}

	@Override
	public boolean validate(String svalue) {
		return true;
	}

	@Override
	public Date fromComponent(Text component) {
		try {
			return DateUtils.getDate(component.getText());
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public void sendToComponent(Text component, Date value, String format) {
		if (value == null) {
			component.setFocus();
			component.selectAll();
		} else {
			String text = DateUtils.dateToString(value, format);
			component.setText(text);
		}
	}

}
