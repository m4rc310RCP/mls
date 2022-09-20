package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.brazilutils.br.cpfcnpj.CpfCnpj;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class MCPFConverter extends MConverter<Text, String> {

	private boolean verify = true;

	@Override
	public boolean eq(Class<?> type) {
		return false;
	}

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
		verify = false;
		String value = fromComponent((Text) e.widget);
		value = new CpfCnpj(value).getCpfCnpj();
		sendToComponent(component, value, "");
		verify = true;
	};

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
	public String fromComponent(Text component) {
		return component.getText();
	}

	@Override
	public boolean validate(String svalue) {
		return true;
	}

	@Override
	public void reset() {
		sendToComponent(component, "", format);
	}

//	@Override
//	public void sendToComponent(Text component, String value, String format) {
//		component.setText(value);
//	}

	@Override
	public void sendToComponent(Text component, String value, String format ) {
		String cpf = new CpfCnpj(value).getCpfCnpj();
		component.setText(cpf);
	}
}
