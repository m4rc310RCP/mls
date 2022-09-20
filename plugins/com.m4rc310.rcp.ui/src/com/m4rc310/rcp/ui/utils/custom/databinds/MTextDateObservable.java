package com.m4rc310.rcp.ui.utils.custom.databinds;

import java.util.Date;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Text;

//@Creatable
public class MTextDateObservable extends MAbstractObservable<Text, Date> {

	
	

	public MTextDateObservable(MObservableContext context, String stringFormat) {
		super(context);
	}

	public static final MTextDateObservable getInstance(MObservableContext context, String stringFormat) {
		return new MTextDateObservable(context, stringFormat);
	}
	
	@Override
	public void initListeners(Text component) {
		component.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				Text text = (Text) e.widget;
				fireChangeValue(doGet(text));
			}
		});
		
		
//		component.addListener(SWT.Modify, e -> {
//			Text text = (Text) e.widget;
//			fireChangeValue(doGet(text));
//		});
	}
	
	@Override
	public void doSet(Date value) {
		//System.out.println(getType());
	}
	
	@Override
	public Date doGet(Text component) {
		try {
			
			return null;
			
//			component.addModifyListener(listener);
//			return Long.parseLong(component.getText());
		} catch (Exception e) {
			return null;
		}

	}
}
