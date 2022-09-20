package com.m4rc310.rcp.ui.utils.custom.m.databinds;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.reflections.Reflections;

import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;
import com.m4rc310.rcp.ui.utils.custom.databinds.converters.MConverter;
import com.m4rc310.rcp.ui.utils.custom.databinds.converters.MNullConverter;

@Creatable
@Singleton
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MObsevableContext {

	private final Collection<MChangeListener> listeners = new ArrayList<>();

	private final List<Class<? extends MConverter>> converters = new ArrayList<>();

	private final Map<Object, MConverter> mapConverters = new HashMap<>();
	private final Map<Object, Collection<MChangeListener>> mapChangeListener = new HashMap<>();

	public MObsevableContext() {
		loadConverters();
	}

	private void loadConverters() {
		Reflections reflections = new Reflections("com.m4rc310.rcp.ui");
		Set<Class<? extends MConverter>> subTypesOf = reflections.getSubTypesOf(MConverter.class);
		for (Class<? extends MConverter> type : subTypesOf) {
			try {
				converters.add(type);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public MConverter getMConverter(Class<? extends MConverter> type) {
		throw new UnsupportedOperationException("Converter not found for: " + type);
	}

	public Class<? extends MConverter> getMConverter(Field field) {
		for (Class<? extends MConverter> type : converters) {
			try {
				if (type.getDeclaredConstructor().newInstance().eq(field.getType())) {
					return type;
				}
			} catch (Exception e) {
			}
		}

		throw new UnsupportedOperationException("Converter not found for field: " + field.getName());
	}

	public void addListener(Object target, MChangeListener listener) {
		if (target == null) {
			return;
		}

		if (!mapChangeListener.containsKey(target)) {
			mapChangeListener.put(target, new ArrayList<>());
		}

		Collection<MChangeListener> clisteners = mapChangeListener.get(target);
		if (!clisteners.contains(listener)) {
			clisteners.add(listener);
		}
	}

	public void addChangeListener(Object target, MChangeListener listener) {
		if (target == null) {
			return;
		}

	}

	public Collection<MChangeListener> getListeners(Object target) {
		return mapChangeListener.getOrDefault(target, new ArrayList<>());
	}

	public void removeMChangeListeners(Object target) {
		mapChangeListener.put(target, new ArrayList<>());
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

	public void observeWidget(Class<? extends MConverter> typeConverter, Object component, String sfield, Object target,
			String format) {

		log("observeWidget com MConverter.class definida");
		try {
			MConverter converter = typeConverter.getDeclaredConstructor().newInstance();
			converter.setComponent(component);
			converter.setContext(this);
			if (target != null) {
				log("target existente %s", target);

				Field field = getField(target.getClass(), sfield);
				field.setAccessible(true);
				converter.setField(field);
			}

			converter.setFormat(format);
			converter.setTarget(target);
			converter.clearListeners();
			converter.addListeners();
//
//			converter.process();
			mapConverters.put(component, converter);

			observeWidget(component, sfield, target, format);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<Object, Collection<Object>> mapListenerRegister = new HashMap<>();

	public void registerListener(Object component, Object listener) {
		if (!mapListenerRegister.containsKey(component)) {
			mapListenerRegister.putIfAbsent(component, new ArrayList<>());
		}

		Collection<Object> collection = mapListenerRegister.get(component);

		if (!collection.contains(listener)) {
			collection.add(listener);
		}
	}

	public Collection<Object> getListener(Object component) {
		return mapListenerRegister.get(component);
	}

	public void observeWidget(Object component, String sfield, Object target, String format) {

		log("target -> %s", target);

		if (target == null) {
			MNullConverter converter = new MNullConverter();
			converter.setComponent(component);
			converter.setContext(this);
			converter.clearListeners();
			converter.process();
//			mapConverters.put(component, converter);
			return;
		}

		MConverter converter = mapConverters.get(component);

		log("%s", converter);

		try {

			if (converter != null) {
				Field field = getField(target.getClass(), sfield);
				field.setAccessible(true);
				converter.setField(field);
				converter.setFormat(format);
				converter.setTarget(target);
				converter.clearListeners();
				converter.addListeners();
				converter.process();
				return;
			}

			Field field = getField(target.getClass(), sfield);
			field.setAccessible(true);

			converter = getMConverter(field).getDeclaredConstructor().newInstance();
			converter.setComponent(component);
			converter.setContext(this);
			converter.setField(field);
			converter.setFormat(format);
			converter.setTarget(target);
			converter.clearListeners();
			converter.addListeners();

			converter.process();
			mapConverters.put(component, converter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Field getField(Class<?> type, String name) {
		try {

			List<Field> fields = new ArrayList<Field>();

			Class<?> i = type;
			while (i != null && i != Object.class) {
				Collections.addAll(fields, i.getDeclaredFields());
				i = i.getSuperclass();
			}

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

	private void log(String text, Object... objects) {
		text = String.format(text, objects);
//		System.out.println(text);
	}

}
