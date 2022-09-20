package com.m4rc310.rcp.ui.utils;

import java.lang.reflect.ParameterizedType;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class MLabelProvider<T> extends LabelProvider {
	
	private Class<T> type;
	
	@SuppressWarnings("unchecked")
	public MLabelProvider() {
		this.type = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public Image getMImage(T element) {
		return super.getImage(element);
	}
	
	public String getMText(T element) {
		return String.format("%s", element.toString());
	}
	
	@Override
	public String getText(Object element) {
		return getMText(type.cast(element));
	}
	
	
	
}
