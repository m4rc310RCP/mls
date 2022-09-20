package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.rcp.ui.utils.DateUtils;

public class MCalendarConverter extends MConverter<Text, Calendar> {
	
	String svalue = "";

	@Override
	public boolean eq(Class<?> type) {
		return type == Calendar.class;
	}
	
	private final Listener focusListener = e->{
		Text text = (Text) e.widget;
		String sdate = text.getText();
		
		if(validate(sdate)) {
			Calendar value = fromComponent(text);
			fireChangeValue(value, text);
			sendToComponent(text, value, format);
		}
	};
	
	
	@Override
	public void addListeners() {
		component.addListener(SWT.FocusOut, focusListener);
		context.registerListener(component, focusListener);
	}
	
	@Override
	public boolean validate(String svalue) {
		return !this.svalue.equals(svalue);
	}

	@Override
	public Calendar fromComponent(Text component) {
		try {
			Date date = DateUtils.getDate(component.getText());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void reset() {
		component.setText("");
		this.svalue = "";
	}
	
	@Override
	public void sendToComponent(Text component, Calendar value, String format) {
		if(value!=null) {
			String text = DateUtils.dateToString(value.getTime(), format);
			component.setText(text);
			this.svalue = text;
		}else {
			component.setFocus();
			component.selectAll();
		}
	}


}
