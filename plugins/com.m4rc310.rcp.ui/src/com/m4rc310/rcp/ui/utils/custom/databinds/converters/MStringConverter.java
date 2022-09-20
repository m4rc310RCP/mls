package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class MStringConverter extends MConverter<Text, String> {

	private final Listener modifyListener = e -> {
		String value = ((Text) e.widget).getText();
		fireChangeValue(value, component);
	};

	@Override
	public boolean eq(Class<?> type) {
		return type == String.class;
	}

	@Override
	public void addListeners() {
		component.addListener(SWT.Modify, modifyListener);
		component.addListener(SWT.Dispose, e->{
			clearListeners();
		});
		context.registerListener(component, modifyListener);
	}

	@Override
	public boolean validate(String svalue) {
		return true;
	}

	@Override
	public String fromComponent(Text component) {
		return component.getText();
	}

	@Override
	public void sendToComponent(Text component, String value, String format) {
		component.setText(value);
	}
}
