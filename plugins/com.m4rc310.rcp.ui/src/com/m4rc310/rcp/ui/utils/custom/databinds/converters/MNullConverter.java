package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class MNullConverter extends MConverter<Object, Object> {

	@Override
	public boolean eq(Class<?> type) {
		return false;
	}

	@Override
	public void process() {
		sendToComponent(component, null, format);
	}

	@Override
	public Object fromComponent(Object component) {
		return null;
	}

	@Override
	public void sendToComponent(Object component, Object value, String format) {
		if (component instanceof Text) {
			Text text = (Text) component;
			text.setText("");
		}

		if (component instanceof Button) {
			Button button = (Button) component;
			button.setSelection(false);
		}

		if (component instanceof ComboViewer) {
			ComboViewer combo = (ComboViewer) component;
			combo.setSelection(new StructuredSelection());
		}

	}

	@Override
	public boolean validate(String svalue) {
		return true;
	}

}
