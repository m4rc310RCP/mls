package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

public class MComboObservable extends MConverter<ComboViewer, Object> {

	
	private IStructuredSelection selection;
	private final ISelectionChangedListener listener = e->{
//		selection =  e.getStructuredSelection();
		selection =  (IStructuredSelection) e.getSelection();
		Object value = selection.getFirstElement();
		fireChangeValue(value, component);
	};

	@Override
	public boolean eq(Class<?> type) {
		return false;
	}
	
	@Override
	public void addListeners() {
		component.addSelectionChangedListener(listener);
		context.registerListener(component, listener);
	}
	
	@Override
	public boolean validate(String svalue) {
		return true;
	}
	
	@Override
	public void sendToComponent(ComboViewer component, Object value, String format) {
		component.setSelection(new StructuredSelection(value));
	}
	
	@Override
	public Object fromComponent(ComboViewer component) {
		return null;
	}

	
}
