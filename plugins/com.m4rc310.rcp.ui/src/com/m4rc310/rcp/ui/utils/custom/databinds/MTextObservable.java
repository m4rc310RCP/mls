package com.m4rc310.rcp.ui.utils.custom.databinds;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;


//@Creatable
public class MTextObservable extends MAbstractObservable<Text, Object> {

	public MTextObservable(MObservableContext context) {
		super(context);
	}

	public static final MTextObservable getInstance(MObservableContext context) {
		return new MTextObservable(context);
	}

	@Override
	public void initListeners(Text component) {
		component.addListener(SWT.Modify, e -> {
		});
	}

	@Override
	public void doSet(Object value) {
		
//		component.setText(converter.toString(value));
	}

	@Override
	public Object doGet(Text component) {
		return component.getText();
	}
}
