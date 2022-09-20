package com.m4rc310.rcp.ui.utils.custom.databinds;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;

@SuppressWarnings("unchecked")
public abstract class MAbstractObservable<C extends Widget, O> {

	protected Object target;
	protected C component;

	protected Field field;

//	protected Class<?> type;

	protected final MObservableContext context;

	public MAbstractObservable(MObservableContext context) {
		this.context = context;

	}

	public Color getColor(int color) {
		return Display.getCurrent().getSystemColor(color);
	}

	public void observe(Object target, C component, String sfield) {
		this.target = target;
		this.component = component;

		validVerify(component);

		try {
			Class<? extends Object> type = target.getClass();
			this.field = getField(type, sfield);
			field.setAccessible(true);

			if (field != null) {
				O value = (O) field.get(target);
				doSet(value);
			}

			initListeners(component);
		} catch (Exception e) {
			this.field = null;
			e.printStackTrace();
		}
	}

	public void validVerify(C component) {
	}
	
	protected Class<?> getType(){
		return (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

//	protected Class<O> getType() {
//		return (Class<O>) ((ParameterizedType) getClass()
//                .getGenericSuperclass()).getActualTypeArguments()[0];	
//	}

	private Field getField(Class<?> type, String name) {
		try {
			List<Field> fields = new ArrayList<Field>();
			fields.addAll(Arrays.asList(type.getFields()));
			fields.addAll(Arrays.asList(type.getDeclaredFields()));

			for (Field field : fields) {
				if (name.equalsIgnoreCase(field.getName())) {
					return field;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	protected void fireChangeValue(O value) {
		try {
			field.set(target, value);
			context.getListeners().forEach(listener -> {
				listener.eventChanged(MChangeEvent.event(target, component, value));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void initListeners(C component);

	public abstract void doSet(O value);

	public abstract O doGet(C component);
}
