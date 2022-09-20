package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;

public class MComboEnumObservable extends MConverter<ComboViewer, Enum<?>> {

	
//private ISelection selection;
		private IStructuredSelection selection;
	private final ISelectionChangedListener listener = e->{
//		selection =  e.getStructuredSelection();
		selection =  (IStructuredSelection) e.getSelection();
		Enum<?> value = (Enum<?>) selection.getFirstElement();
		fireChangeValue(value, component);
	};

	@Override
	public boolean eq(Class<?> type) {
		return type.isEnum();
	}
	
	@Override
	public void addListeners() {
		component.addSelectionChangedListener(listener);
		context.registerListener(component, listener);
	}
	

	@Override
	public Enum<?> fromComponent(ComboViewer component) {
		return null;
	}

	@Override
	public boolean validate(String svalue) {
		return false;
	}
}
