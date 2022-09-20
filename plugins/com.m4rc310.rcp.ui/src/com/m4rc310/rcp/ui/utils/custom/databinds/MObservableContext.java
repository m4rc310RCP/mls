package com.m4rc310.rcp.ui.utils.custom.databinds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.widgets.Text;
import org.reflections.Reflections;

import com.m4rc310.rcp.ui.utils.custom.databinds.converters.MConverter;

@Creatable
@SuppressWarnings("rawtypes")
public class MObservableContext {

	private final Collection<MChangeListener> listeners;

	
	private final Collection<MConverter> converters;

	public MObservableContext() {
		this.converters = new ArrayList<>();
		this.listeners = new ArrayList<>();
		loadAllConverters();
	}

	private void loadAllConverters() {
		Reflections reflections = new Reflections("com.m4rc310.rcp.ui");
		Set<Class<? extends MConverter>> subTypesOf = reflections.getSubTypesOf(MConverter.class);
		for (Class<? extends MConverter> class1 : subTypesOf) {
			try {
				MConverter converter = class1.getDeclaredConstructor().newInstance();
				converters.add(converter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public MConverter getConverter(Class<?> type) {
		for (MConverter converter : converters) {
			if (converter.eq(type)) {
				return converter;
			}
		}

		throw new UnsupportedOperationException("Converter not found");
	}

	public void addListener(MChangeListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeListener(MChangeListener listener) {
		listeners.remove(listener);
	}

	public Collection<MChangeListener> getListeners() {
		return listeners;
	}

	public void processTextObservable(Text component, String sfield, Object target) {
		MTextObservable observable = MTextObservable.getInstance(this);
		observable.observe(target, component, sfield);
	}

	public void processDateObservable(Text component, String sfield, Object target, String formatter) {
		MTextDateObservable observable = MTextDateObservable.getInstance(this, formatter);
		observable.observe(target, component, sfield);
	}

	public void processLongObservable(Text component, String sfield, Object target, String formatter) {
		MTextLongObservable observable = MTextLongObservable.getInstance(this, formatter);
		observable.observe(target, component, sfield);
	}
}
