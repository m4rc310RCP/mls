package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class MLongConverter extends MConverter<Text, Long> {

	private boolean verify = true;

	private final Listener verifyListener = e -> {
		if (!verify) {
			e.doit = true;
			return;
		}

		if (e.character == SWT.BS || e.character == SWT.DEL || e.text.isEmpty()) {
			return;
		}

		String s = e.text;
		Pattern pattern = Pattern.compile("[0-9]");
		Matcher matcher = pattern.matcher(s);
		e.doit = matcher.matches();
	};

	private final Listener modifyListener = e -> {
		if (verify) {
			fireChangeValue(fromComponent(component), component);
		}
	};

	private final Listener focusListener = e -> {
		Text text = (Text) e.widget;
		Long value = fromComponent(text);
		verify = false;
		sendToComponent(text, value, format);
		verify = true;
	};

	@Override
	public boolean eq(Class<?> type) {
		return type == Long.class || type == long.class;
	}

	@Override
	public void addListeners() {
		component.addListener(SWT.Modify, modifyListener);
		component.addListener(SWT.FocusOut, focusListener);
		component.addListener(SWT.Verify, verifyListener);

		context.registerListener(component, modifyListener);
		context.registerListener(component, focusListener);
		context.registerListener(component, verifyListener);
	}

	@Override
	public void reset() {
		sendToComponent(component, null, format);
	}

	@Override
	public Long fromComponent(Text component) {
		String svalue = component.getText();
		if (validate(svalue)) {
			return Long.parseLong(svalue);
		}
		return null;
	}

	@Override
	public void sendToComponent(Text component, Long value, String format) {

		verify = false;
		try {
			String svalue = value == null ? "" : String.format(format, value);
			component.setText(svalue);
		} catch (Exception e) {
			component.setText("");
		}
		verify = true;
	}

	@Override
	public boolean validate(String svalue) {
		return svalue.matches("\\d+");
	}

}
