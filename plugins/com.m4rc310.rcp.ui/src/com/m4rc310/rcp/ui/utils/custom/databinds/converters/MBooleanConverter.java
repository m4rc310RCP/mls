package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;

public class MBooleanConverter extends MConverter<Button, Boolean> {

	@Override
	public boolean eq(Class<?> type) {
		return type == Boolean.class || type == boolean.class;
	}
	
	private final Listener selectionListener = e->{
		Button button = (Button)e.widget;
		fireChangeValue(button.getSelection(), button);
	};
	
	
	@Override
	public void addListeners() {
		component.addListener(SWT.Selection, selectionListener);
		context.registerListener(component, selectionListener);
	}
	

	@Override
	public boolean validate(String svalue) {
		return true;
	}
	
	@Override
	public void reset() {
		sendToComponent(component, false, format);
	}

	@Override
	public Boolean fromComponent(Button component) {
		return component.getSelection();
	}

	@Override
	public void sendToComponent(Button component, Boolean value, String format) {
		component.setSelection(value);
	}
	
}
