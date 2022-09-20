package com.m4rc310.rcp.ui.utils.custom.databinds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;

public class MChangeEvent extends EventObject {
	private static final long serialVersionUID = -5593374248843450919L;

	private Object[] values;
	private Object component;
//	private String hash

	public MChangeEvent(Object source) {
		this(source, null);
	}

	public MChangeEvent(Object source, Object component, Object... values) {
		super(source);
		this.component = component;
		this.values = values;
	}

	public Object getComponent() {
		return component;
	}

	public boolean equalsFor(Object component) {
		return component.equals(this.component);
	}
	
	@Override
	public Object getSource() {
		return super.getSource();
	}

	public Object getValue() {
		return getValue(0);
	}

	public Object getValue(int index) {
		return values[index];
	}

	public <T extends Object> T getValue(Class<T> type) {
		return getValue(0, type);
	}

	public <T extends Object> T getValue(int index, Class<T> type) {
		@SuppressWarnings("unchecked")
		T ret = (T) values[index];
		return ret;
	}

	public <T> List<T> getListValue(int index, Class<? extends T> clazz) {
		Collection<?> c = getValue(0, List.class);
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}
	
	public static MChangeEvent event(Object source, Object component, Object... values) {
		return new MChangeEvent(source, component, values);
	}

}
