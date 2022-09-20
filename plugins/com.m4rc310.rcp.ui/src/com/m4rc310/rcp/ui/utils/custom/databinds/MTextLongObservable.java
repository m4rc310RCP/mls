package com.m4rc310.rcp.ui.utils.custom.databinds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

//@Creatable
public class MTextLongObservable extends MAbstractObservable<Text, Long> {

	private String stringFormat;
	
	
	private boolean verify = true;

	public MTextLongObservable(MObservableContext context, String stringFormat) {
		super(context);
		this.stringFormat = stringFormat;
	}

	public static final MTextLongObservable getInstance(MObservableContext context, String stringFormat) {
		return new MTextLongObservable(context, stringFormat);
	}
	
	

	@Override
	public void initListeners(Text component) {
		component.addListener(SWT.Modify, e -> {
			Text text = (Text) e.widget;
			fireChangeValue(doGet(text));
		});
	}
	
	@Override
	public void validVerify(Text component) {
		component.addVerifyListener(e->{
			
			if(!verify) {
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
		});
	}

	@Override
	public void doSet(Long value) {
		verify = false;
		component.setText(String.format(stringFormat, value));
		verify = true;
	}

	@Override
	public Long doGet(Text component) {
		try {
			
//			component.addModifyListener(listener);
			return Long.parseLong(component.getText());
		} catch (Exception e) {
			return null;
		}

	}
}
