package com.m4rc310.rcp.ui.utils.custom.databinds.converters;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;

import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeEvent;
import com.m4rc310.rcp.ui.utils.custom.m.databinds.MObsevableContext;

@SuppressWarnings("unchecked")
public abstract class MConverter<C extends Object, O> {

	protected MObsevableContext context;
	protected Field field;
	protected Object target;
	protected C component;
	protected String format;
	protected final List<Listener> listeners = new ArrayList<>();

	public abstract boolean eq(Class<?> type);

	public abstract boolean validate(String svalue);

	public abstract O fromComponent(C component);

	public void process() {
		try {

			if (target == null) {
				reset();
			}

			O value = (O) field.get(target);
			sendToComponent(component, value, format);
		} catch (Exception e) {
		}

	}

	public void clearListeners() {
		removeListeners(component, SWT.FocusIn);
		removeListeners(component, SWT.FocusOut);
		removeListeners(component, SWT.Modify);
		removeListeners(component, SWT.Verify);
		removeListeners(component, SWT.Selection);
		
		context.removeMChangeListeners(target);
	}

	protected void removeListeners(Object w, int eventType) {
		Collection<Object> registedListeners = context.getListener(w);
		if (registedListeners != null) {
			for (Object olistener : registedListeners) {
				
				Method method = null;
				
				if (olistener instanceof Listener) {
					Listener listener = (Listener) olistener;
					try {
						method = w.getClass().getMethod("removeListener", int.class, Listener.class);
						method.setAccessible(true);
						method.invoke(w, eventType, listener);
					} catch (Exception e) {
					}
				}
				
				if (registedListeners instanceof ISelectionChangedListener) {
					ISelectionChangedListener listener = (ISelectionChangedListener) registedListeners;
					try {
						method = w.getClass().getMethod("removeSelectionChangedListener", ISelectionChangedListener.class);
						method.setAccessible(true);
						method.invoke(w, listener);
					} catch (Exception e) {
					}
				}
				
			}
			
//			for (Listener listener : registedListeners) {
//				try {
//					Method method = w.getClass().getMethod("removeListener", int.class, Listener.class);
//					method.setAccessible(true);
//					method.invoke(w, eventType, listener);
//					
//					method = w.getClass().getMethod("removeSelectionChangedListener", ISelectionChangedListener.class);
//					method.setAccessible(true);
//					method.invoke(w, listener);
//					
//				} catch (Exception e) {
//
//				}
//			}
		}

	}

	public void addListeners() {
		
	}

	public void reset() {
	}

	public void setInput(Object input) {
	}

	public void sendToComponent(C component, O value, String format) {
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setComponent(C component) {
		this.component = component;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public void setField(Field field) {
		this.field = field;

	}

	public void setContext(MObsevableContext context) {
		this.context = context;
	}

	public void fireChangeValue(O value, C component) {
		try {
			if (target == null) {
				return;
			}

			field.set(target, value);
			
			context.getListeners(target).forEach(listener -> {
				listener.eventChanged(MChangeEvent.event(target, component, value));
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
