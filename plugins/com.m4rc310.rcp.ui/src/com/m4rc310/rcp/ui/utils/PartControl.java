package com.m4rc310.rcp.ui.utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.inject.Inject;

//import org.eclipse.core.databinding.observable.value.SelectObservableValue;
//import org.eclipse.core.databinding.Binding;
//import org.eclipse.core.databinding.DataBindingContext;
//import org.eclipse.core.databinding.beans.PojoObservables;
//import org.eclipse.core.databinding.beans.typed.BeanProperties;
//import org.eclipse.core.databinding.observable.IChangeListener;
//import org.eclipse.core.databinding.observable.IObservable;
//import org.eclipse.core.databinding.observable.list.IObservableList;
//import org.eclipse.core.databinding.observable.list.WritableList;
//import org.eclipse.core.databinding.observable.value.IObservableValue;
//import org.eclipse.core.databinding.observable.value.SelectObservableValue;
//import org.eclipse.core.databinding.beans.BeanProperties;
//import org.eclipse.core.databinding.beans.PojoObservables;
//import org.eclipse.core.databinding.beans.typed.BeanProperties;
//import org.eclipse.core.databinding.observable.IChangeListener;
//import org.eclipse.core.databinding.observable.IObservable;
//import org.eclipse.core.databinding.observable.list.IObservableList;
//import org.eclipse.core.databinding.observable.list.WritableList;
//import org.eclipse.core.databinding.observable.value.IObservableValue;
//import org.eclipse.core.databinding.observable.value.SelectObservableValue;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.modeling.ElementMatcher;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.osgi.framework.Bundle;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;
import com.m4rc310.rcp.ui.utils.custom.databinds.converters.MCPFConverter;
import com.m4rc310.rcp.ui.utils.custom.databinds.converters.MComboObservable;
import com.m4rc310.rcp.ui.utils.custom.databinds.converters.MConverter;
import com.m4rc310.rcp.ui.utils.custom.m.databinds.MObsevableContext;
import com.m4rc310.rcp.ui.utils.images.ImageAnalyzer;

//import com.m4rc310.rcp.ui.utils.custom.databinds.ComboObservableValue;
//import com.m4rc310.rcp.ui.utils.custom.databinds.DateTimeObservableValue;
//import com.m4rc310.rcp.ui.utils.custom.databinds.ProposalComboObservableValue;
//import com.m4rc310.rcp.ui.utils.custom.databinds.TextCpfCnpjObservableValue;
//import com.m4rc310.rcp.ui.utils.custom.databinds.TextObservableValue;
//import com.m4rc310.rcp.ui.utils.custom.databinds.WidgetFormatterObservableValue;

//import com.m4rc310.pos.rcp.custom.databinds.ComboObservableValue;
//import com.m4rc310.pos.rcp.custom.databinds.DateTimeObservableValue;
//import com.m4rc310.pos.rcp.custom.databinds.TextCpfCnpjObservableValue;
//import com.m4rc310.pos.rcp.custom.databinds.TextObservableValue;
//import com.m4rc310.pos.rcp.custom.databinds.WidgetFormatterObservableValue;
//import com.m4rc310.pos.rcp.utils.DateUtils;
//import com.m4rc310.pos.rcp.utils.MFormatter;

//Dao any: 000009920009952

@Creatable
//@Singleton
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PartControl {

	@Inject
	@Optional
	MApplication application;
	@Inject
	@Optional
	EPartService partService;
	@Inject
	@Optional
	EModelService modelService;

	@Inject
	@Optional
	IEventBroker eventBroker;

	@Inject
	@Optional
	UISynchronize sync;

	@Inject
	IStylingEngine engine;

//	@Inject
//	Shell shell;

	@Inject
	@Optional
	Display display;

	@Inject
	MObsevableContext context;

	@Inject
	@Preference
	IEclipsePreferences prefs;

	private final Map<Object, String> mapReferences = new HashMap<Object, String>();

//	private final DataBindingContext ctx;// new DataBindingContext();

//	private WritableList writableList;
	private GC gc;

	static boolean IS_DEBUG = false;
//	private static final String WEB_CONTENT_DEVELOPMENT_FOLDER = "/WebContent-dev"; //$NON-NLS-1$
//	private static final String WEB_CONTENT_RELEASE_FOLDER = "/WebContent-rel"; //$NON-NLS-1$
//	private static final String RESOURCE_PATH = "/ml/resources/"; //$NON-NLS-1$

//	private static final String WEB_CONTENT_FOLDER = IS_DEBUG ? WEB_CONTENT_DEVELOPMENT_FOLDER
//			: WEB_CONTENT_RELEASE_FOLDER;
	private static final String URL_SPACE = " "; //$NON-NLS-1$
	private static final String URL_SPACE_REPLACEMENT = "%20"; //$NON-NLS-1$
	private static final String URL_SQB_OPEN = "\\["; //$NON-NLS-1$
	private static final String URL_SQB_OPEN_REPLACEMENT = "%5B"; //$NON-NLS-1$
	private static final String URL_SQB_CLOSE = "\\]"; //$NON-NLS-1$
	private static final String URL_SQB_CLOSE_REPLACEMENT = "%5D"; //$NON-NLS-1$
	private static final String GROUP_CONTROLS = "group_controls"; //$NON-NLS-1$
	private static final String DATA_FOREGROUND = "data_foreground"; //$NON-NLS-1$

	// private final Map<Control, List<Control>> mapControls_ = new HashMap<>();
	private final Map<Control, ControlDecoration> mapDecorations = new HashMap<>();

	@Inject
	public PartControl() {
//		ctx = getDatBindingContext();
//		Resource.setNonDisposeHandler(null);
	}

//	private DataBindingContext getDatBindingContext() {
//		try {
//			return new DataBindingContext();
//		} catch (Exception e) {
//			return null;
//		}
//	}

	public void dispose() {
//		ctx.dispose();
	}

	public <T extends MUIElement> T find(String id, Class<T> type) {
		return (T) modelService.find(id, application);
	}

	public void registerDecoration(Control widget) {
		registerDecoration(widget, SWT.TOP | SWT.LEFT);
	}

	public void registerDecoration(Control widget, int position) {
		final ControlDecoration deco = new ControlDecoration(widget, position);
		Image img = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
				.getImage();
		deco.setImage(img);
		deco.setShowOnlyOnFocus(false);

		mapDecorations.put(widget, deco);
	}

	public void showDecoration(Control control, String text) {
		flushMaps();

		if (!mapDecorations.containsKey(control)) {
			registerDecoration(control);
		}

		ControlDecoration decoration = mapDecorations.get(control);
		decoration.setDescriptionText(text);
		decoration.show();
		decoration.showHoverText(text);
	}

	public void hideDecoration(Control control) {
		ControlDecoration deco = mapDecorations.get(control);
		if (deco != null) {
			deco.hide();
		}
	}

	public void flushMaps() {
//		mapControls.forEach((control, list) -> {
//			if (control.isDisposed()) {
//				mapControls.remove(control);
//			}
//		});
	}

	public void setStyledClassnameAndId(Object widget, String classname, String id) {
		engine.setClassnameAndId(widget, classname, id);
	}

	public void setStyledClassname(Object widget, String classname) {
		engine.setClassname(widget, classname);
	}

	public void setStyledId(Object widget, String id) {
		engine.setId(widget, id);
	}

	public void groupControl(Control... controls) {
		if (controls.length > 1) {
			Control master = controls[0];
			List<Control> list = new ArrayList<>();
			for (int i = 1; i < controls.length; i++) {
				Control control = controls[i];
				list.add(control);
			}
			master.setData(GROUP_CONTROLS, list);
		}

//		flushMaps();
//
//		if (controls.length > 1) {
//			Control controlKey = controls[0];
//			if (!mapControls.containsKey(controlKey)) {
//				mapControls.put(controlKey, new ArrayList<>());
//			}
//			
//			mapControls.get(controlKey).addAll(Arrays.asList(controls));
//		}
	}

	public void changeFontSize(Control control, int height) {
		FontData[] fontData = control.getFont().getFontData();
		for (FontData fd : fontData) {
			fd.setHeight(height);
		}

		Font font = new Font(control.getDisplay(), fontData);
		control.setFont(font);

		control.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				font.dispose();
				control.removeDisposeListener(this);
			}
		});
	}

//	public void enableGroup(String key, boolean enabled) {
//
//		if (!mapControls.containsKey(key)) {
//			return;
//		}
//
//		mapControls.get(key).forEach(control -> {
//			control.setEnabled(enabled);
//		});
//	}

	public boolean loadFont(String pluginId, String path) {
		try {

//			final File fontFile = getResourceFile("fonts/Arthure.ttf");

//			System.outs.println(fontFile);

//			URL bundle = Platform.getBundle(pluginId).getResource(path);
//			path = FileLocator.toFileURL(bundle).getFile();

//			Shell shell = new Shell();
//			MessageDialog.openInformation(shell, "", path);
//			MessageDialog.openInformation(shell, "", new File(path).exists() +"");

			return display.loadFont(new File(path).getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean loadFont(String path) {
		return loadFont("com.m4rc310.rcp.ui", path);
	}

	public void stackLayout(Composite parent) {
		parent.setLayout(new StackLayout());
	}

//	public ScrolledComposite getScrolledComposite(Composite parent) {
//
//		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
//
//		return scroll;
//	}

	public Dimension getTextSize(String text, Font font) {
		setFont(font);
		return getTextSize(text);
	}

	public Dimension getStringSize(String string, Font font) {
		setFont(font);
		return getStringSize(string);
	}

	public Dimension getStringSize(String text) {
		return new Dimension(getGc().stringExtent(text));
	}

	public Dimension getTextSize(String text) {
		return new Dimension(getGc().textExtent(text));
	}

	private void setFont(Font font) {
		getGc().setFont(font);
	}

	public GC getGc() {
		if (this.gc == null) {
			this.gc = new GC(new Shell());
		}
		return gc;
	}

	public String toJson(Serializable object) {
		try {

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			return ow.writeValueAsString(object);
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}

	}

	public BufferedImage convertToAWT(ImageData data) {
		ColorModel colorModel = null;
		PaletteData palette = data.palette;
		if (palette.isDirect) {
			colorModel = new DirectColorModel(data.depth, palette.redMask, palette.greenMask, palette.blueMask);
			BufferedImage bufferedImage = new BufferedImage(colorModel,
					colorModel.createCompatibleWritableRaster(data.width, data.height), false, null);
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					RGB rgb = palette.getRGB(pixel);
					bufferedImage.setRGB(x, y, rgb.red << 16 | rgb.green << 8 | rgb.blue);
				}
			}
			return bufferedImage;
		} else {
			RGB[] rgbs = palette.getRGBs();
			byte[] red = new byte[rgbs.length];
			byte[] green = new byte[rgbs.length];
			byte[] blue = new byte[rgbs.length];
			for (int i = 0; i < rgbs.length; i++) {
				RGB rgb = rgbs[i];
				red[i] = (byte) rgb.red;
				green[i] = (byte) rgb.green;
				blue[i] = (byte) rgb.blue;
			}
			if (data.transparentPixel != -1) {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue, data.transparentPixel);
			} else {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue);
			}
			BufferedImage bufferedImage = new BufferedImage(colorModel,
					colorModel.createCompatibleWritableRaster(data.width, data.height), false, null);
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					pixelArray[0] = pixel;
					raster.setPixel(x, y, pixelArray);
				}
			}

			return bufferedImage;
		}
	}

	public ImageData convertToSWT(BufferedImage bufferedImage) {
		if (bufferedImage.getColorModel() instanceof DirectColorModel) {
			DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();
			PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(),
					colorModel.getBlueMask());
			ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
					colorModel.getPixelSize(), palette);
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[3];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					raster.getPixel(x, y, pixelArray);
					int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
					data.setPixel(x, y, pixel);
				}
			}
			return data;
		} else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
			IndexColorModel colorModel = (IndexColorModel) bufferedImage.getColorModel();
			int size = colorModel.getMapSize();
			byte[] reds = new byte[size];
			byte[] greens = new byte[size];
			byte[] blues = new byte[size];
			colorModel.getReds(reds);
			colorModel.getGreens(greens);
			colorModel.getBlues(blues);
			RGB[] rgbs = new RGB[size];
			for (int i = 0; i < rgbs.length; i++) {
				rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
			}
			PaletteData palette = new PaletteData(rgbs);
			ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
					colorModel.getPixelSize(), palette);
			data.transparentPixel = colorModel.getTransparentPixel();
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					raster.getPixel(x, y, pixelArray);
					data.setPixel(x, y, pixelArray[0]);
				}
			}
			return data;
		}
		return null;
	}

	public Composite getStackComposite(Composite parent) {
		Composite stack = getComposite(parent);
		stack.setLayout(new StackLayout());
		return stack;
	}

	public void toTopControl(Control... controls) {
		try {
			for (Control control : controls) {
//			sync.asyncExec(() -> {
				Composite parent = control.getParent();
				Layout layout = parent.getLayout();
				if (layout instanceof StackLayout) {
					StackLayout sl = (StackLayout) layout;
					sl.topControl = control;
					parent.layout();
				}
			}
//			});
		} catch (Exception e) {
		}
	}

	public void removeObserverListener(MChangeListener listener) {
		synchronized (this) {
			context.removeListener(listener);
		}
	}

	public synchronized void removeObserverListener(Object target, MChangeListener listener) {
		synchronized (this) {
			context.addListener(listener);
		}
	}

	public synchronized void addObserverListener(Object target, MChangeListener listener) {
		synchronized (this) {
			context.addListener(target, listener);
		}
	}

	public synchronized void addObserverChangeListener(Object target, MChangeListener listener) {
		synchronized (this) {
			context.addChangeListener(target, listener);
		}
	}

	public synchronized void addObserverListener(MChangeListener listener) {
		synchronized (this) {
			context.addListener(listener);
			throw new UnsupportedOperationException("Alterar");
		}

//		synchronized (this) {
//			IObservableList bindings = ctx.getValidationStatusProviders();
//			for (Object o : bindings) {
//				Binding b = (Binding) o;
//				IObservable target = b.getTarget();
//				target.addChangeListener(listener);
//			}
//		}
	}

	public void addKeyListener(KeyListener listener, Control... controls) {
		for (Control control : controls) {
			DisposeListener disposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					Control ctr = (Control) e.getSource();
					ctr.removeKeyListener(listener);
					ctr.removeDisposeListener(this);
				}
			};

			control.removeKeyListener(listener);
			control.addKeyListener(listener);
			control.addDisposeListener(disposeListener);
		}
	}

	public void addModifyListener(Listener listener, Control... controls) {
		addListener(SWT.Modify, listener, controls);
	}

	private void addListener(int type, Listener listener, Control... controls) {
		for (Control control : controls) {

			DisposeListener disposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					Control ctr = (Control) e.getSource();
					ctr.removeListener(type, listener);
					ctr.removeDisposeListener(this);
				}
			};

			control.removeListener(type, listener);
			control.addListener(type, listener);
			control.addDisposeListener(disposeListener);
		}
	}

//	public <T> SelectObservableValue<T> getSelectObservableValueFor(Class<T> type) {
//		return new SelectObservableValue<T>(type);
//	}

//	public void addOption(SelectObservableValue selectObservableValue, Object value, Widget widget) {
////		WidgetProperties.comboSelection().observe(widget)
////		IObservableValue observeSelection = SWTObservables.observeSelection(widget);
////		selectObservableValue.addOption(value, observeSelection);
//	}

//	public void observeRadio(SelectObservableValue selectObservableValue, String field, Object value) {
//		ctx.bindValue(selectObservableValue, PojoObservables.observeValue(value, field));
//	}
//	public void observeRadio(SelectObservableValue selectObservableValue, String field, Object value) {
////		ctx.bindValue(selectObservableValue, PojoObservables.observeValue(value, field));
//	}

	public void observeCheck(Widget widget, String field, Object bean) {
//		ISWTObservableValue target = WidgetProperties.selection().observe(widget);
//		ISWTObservableValue target = WidgetProperties.buttonSelection().observe((Button) widget);
//		IObservableValue<Object> model = BeanProperties.value(field).observe(bean);
//		ctx.bindValue(target, model);
	}

	public void observeComboListProposal(ComboViewer widget, String field, Object bean) {
//		ProposalComboObservableValue target = new ProposalComboObservableValue(widget);
//		IObservableValue<Object> model = BeanProperties.value(field).observe(bean);
//		ctx.bindValue(target, model);
	}

	public void observeComboList(ComboViewer component, String field, Object target) {
		context.observeWidget(MComboObservable.class, component, field, target, "");
	}

	public void observeTextCpfCnpj(Widget textWidget, String field, Object bean) {
		observeWidgetSelected(MCPFConverter.class, bean, textWidget, field);
	}

	public void observeTextDate(Text textWidget, String field, Object bean, String format) {
//		context.processDateObservable(textWidget, field, bean, format);
	}

	public void observeWidgetLongFormatter(Text widget, String field, Object bean) {
		observeWidgetLongFormatter(widget, field, bean, "%d");
	}

	public void observeWidgetLongFormatter(Text widget, String field, Object bean, String formatter) {
//		context.processLongObservable(widget, field, bean, formatter);
	}

	public void observeTextString(Text textWidget, String field, Object bean) {
		observeTextString(textWidget, field, bean, null);
	}

	public void observeTextString(Text textWidget, String field, Object bean, String formatter) {
		context.observeWidget(textWidget, field, bean, formatter);
//		context.processTextObservable(textWidget, field, bean);
	}

	public void observeWidgetSelected(Class<? extends MConverter> typeConverter, Object target, Widget component,
			String sfield) {
		observeWidgetSelected(typeConverter, target, component, sfield, null);
	}

	public void observeWidgetSelected(Class<? extends MConverter> typeConverter, Object target, Widget component,
			String sfield, String format) {
		context.observeWidget(typeConverter, component, sfield, target, format);
	}

	public void observeWidget(Object widget, String field, Object bean) {
		observeWidget(widget, field, bean, null);
	}

	public void observeWidgetCpfCnpj(Object widget, String field, Object bean) {
		context.observeWidget(MCPFConverter.class, widget, field, bean, null);
//		context.processAuthomaticObservable(widget, field, bean, format);
	}

	public void observeWidget(Object widget, String field, Object bean, String format) {
		context.observeWidget(widget, field, bean, format);
//		context.processAuthomaticObservable(widget, field, bean, format);
	}

	public void registerViewerSupport(Class<?> type, StructuredViewer viewer, List<?> list, String... propertieNames) {
//		this.writableList = new WritableList<>(list, type);
//		ViewerSupport.bind(viewer, writableList, BeanProperties.values(propertieNames));
	}

	public String addNumberToLabel(Object ref, String text, int value) {
		if (mapReferences.containsKey(ref)) {
			return String.format("%s (%d)", mapReferences.get(ref), value);
		}

		mapReferences.put(ref, text);
		return String.format("%s (%d)", text, value);
	}

	private List<Control> getListGroupInControl(Control control) {
		List<Control> ret = new ArrayList<>();
		Object data = control.getData(GROUP_CONTROLS);
		if (data != null) {
			ret = (List<Control>) data;
		}
		return ret;
	}

	public void enabledAndInverter(boolean enabled, Object controlA, Object controlB) {
		enabled(enabled, controlA);
		enabled(!enabled, controlB);
	}

	public void enabled(boolean enabled, Object... controls) {
		sync.asyncExec(() -> {
			for (Object c : controls) {
				if (c instanceof Control) {
					Control w = (Control) c;

					if (w.isDisposed()) {
						continue;
					}

					w.setEnabled(enabled);

					getListGroupInControl(w).forEach(control -> {
						enabled(enabled, control);
					});

//					if (mapControls.containsKey(w)) {
//						for (Control control : mapControls.get(w)) {
//
//							if (control.equals(w)) {
//								continue;
//							}
//
//							enabled(enabled, control);
////							control.setEnabled(enabled);
////							if ((control instanceof Label) || (control instanceof CLabel)) {
////								Color color = getColor(enabled ? SWT.COLOR_WIDGET_FOREGROUND : SWT.COLOR_GRAY);
////								control.setForeground(color);
////							}
//						}
//					}
				}

				if (c instanceof ToolItem) {
					ToolItem item = (ToolItem) c;
					item.setEnabled(enabled);
				}

				if (c instanceof Label || (c instanceof CLabel)) {
//					Label label = (Label) c;
//					Color color = getColor(enabled ? SWT.COLOR_WIDGET_FOREGROUND : SWT.COLOR_GRAY);
//					((Control) c).setForeground(color);
					Control label = (Control) c;
					Object oforeground = label.getData(DATA_FOREGROUND);
					if (oforeground == null) {
						oforeground = label.getForeground();
						label.setData(DATA_FOREGROUND, oforeground);
					}

					Color foreground = enabled ? ((Color) oforeground) : getColor(SWT.COLOR_GRAY);
					label.setForeground(foreground);
				}

				if (c instanceof Viewer) {
					Viewer v = (Viewer) c;
					v.getControl().setEnabled(enabled);
				}
			}
		});
	}

	public void visible(String partUri, boolean visible) {
		MPart part = partService.findPart(partUri);
		if (part == null) {
			part = partService.createPart(partUri);
		}

		for (String variable : part.getVariables()) {
			if (variable.contains("partStack:")) {
				variable = variable.replace("partStack:", "");

				MPartStack stack = modelService.findElements(application, variable, MPartStack.class, null).get(0);
				stack.setVisible(true);

				List<MStackElement> childres = stack.getChildren();
				childres.add(part);

				break;
			}
		}
		if (visible) {
			partService.showPart(part, PartState.ACTIVATE);
		} else {
			partService.hidePart(part);
		}
	}

	public void toTopPart(MPart part) {
		partService.showPart(part, PartState.ACTIVATE);
	}

	public void configureUpperCase(Text... texts) {
		for (Text text : texts) {
			VerifyListener verifyListener = e -> {
				e.text = e.text.toUpperCase();
			};

			DisposeListener diposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					Text source = (Text) e.getSource();
					source.removeVerifyListener(verifyListener);
					source.removeDisposeListener(this);
				}
			};

			text.addVerifyListener(verifyListener);
			text.addDisposeListener(diposeListener);
		}
	}

	public void configureLowerCase(Text... texts) {
		for (Text text : texts) {
			VerifyListener verifyListener = e -> {
				e.text = e.text.toLowerCase();
			};

			DisposeListener diposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					Text source = (Text) e.getSource();
					source.removeVerifyListener(verifyListener);
					source.removeDisposeListener(this);
				}
			};

			text.addVerifyListener(verifyListener);
			text.addDisposeListener(diposeListener);
		}
	}

	public void configureVerifyValues(String regex, Text... texts) {
		for (Text text : texts) {
			final VerifyListener verifyListener = e -> {
				if (e.character == SWT.BS || e.character == SWT.DEL || e.text.isEmpty()) {
					return;
				}
				String s = e.text;
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(s);
				e.doit = matcher.matches();
			};

			DisposeListener diposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					Text source = (Text) e.getSource();
					source.removeVerifyListener(verifyListener);
					source.removeDisposeListener(this);
				}
			};

			text.addVerifyListener(verifyListener);
			text.addDisposeListener(diposeListener);
		}
	}

//	public static File getResourceFile(final String fileName) throws IOException, URISyntaxException {
//
//		final String bundleFileName = WEB_CONTENT_FOLDER + RESOURCE_PATH + fileName;
////		final String bundleFileName = fileName;
//
////		final URL bundleUrl = Activator.getDefault().getBundle().getEntry(bundleFileName);
////
////		if (bundleUrl == null) {
////			System.out.println("File is not available: " + bundleFileName);
////
//////			StatusUtil.log();//$NON-NLS-1$
////			return null;
////		}
//
//		final File file = getEscapedBundleFile(bundleUrl);
//
//		return file;
//	}

	public static File getEscapedBundleFile(final URL bundleUrl) {

		File file = null;

		try {

			final URL fileUrl = FileLocator.toFileURL(bundleUrl);
			final String encodedFileUrl = encodeSpace(fileUrl.toExternalForm());

			final URI uri = new URI(encodedFileUrl);
			file = new File(uri);

		} catch (final Exception e) {
		}

		return file;
	}

	public static String encodeSpace(final String urlString) {

		String escaped;

		escaped = urlString.replaceAll(URL_SPACE, URL_SPACE_REPLACEMENT);
		escaped = escaped.replaceAll(URL_SQB_OPEN, URL_SQB_OPEN_REPLACEMENT);
		escaped = escaped.replaceAll(URL_SQB_CLOSE, URL_SQB_CLOSE_REPLACEMENT);

		return escaped;
	}

	public void configureNumericValues(Text... texts) {
		configureVerifyValues("[0-9,]", texts);
	}

	public static final int TAB_ITEM_SELECTED = 13 << 1;

	public CTabFolder createCTabFolder(Composite parent) {
		CTabFolder cTabFolder = new CTabFolder(parent, SWT.NONE);

		final SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				int i = cTabFolder.getSelectionIndex();
				CTabItem item = cTabFolder.getItem(i);
				Listener[] itens = item.getListeners(TAB_ITEM_SELECTED);
				for (Listener listener : itens) {
					listener.handleEvent(new Event());
				}
			}
		};

		cTabFolder.addSelectionListener(selectionAdapter);

		final DisposeListener diposeListener = new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				cTabFolder.removeSelectionListener(selectionAdapter);
				cTabFolder.removeDisposeListener(this);
			}
		};

		cTabFolder.addDisposeListener(diposeListener);

		cTabFolder.setSelectionBackground(
				Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		return cTabFolder;
	}

	public Composite createCTabItemContainer(CTabFolder folder, String title) {
		return createCTabItemContainer(folder, title, null);
	}

	public void ctabFolderSetSelection(Composite composite) {
		composite.notifyListeners(SWT.SELECTED, new Event());
	}

	public Composite createCTabItemContainer(CTabFolder folder, String title, Image icon) {
		ScrolledComposite sc = getScrolledComposite(folder);
		CTabItem item = createCTabItem(folder, title, icon);
//		Composite content = getComposite(sc);
		Composite content = new Composite(sc, SWT.NONE) {
			@Override
			public void setEnabled(boolean enabled) {
//				int indexOf = folder.indexOf(item);
//				recursiveSetEnabled(folder.getTabList()[indexOf], enabled);
//				String style = enabled? "CTabItemEnableTrue":"CTabItemEnableFalse";
//				setStyledClassname(item, style);
				super.setEnabled(enabled);
			}
		};

		content.setLayout(new GridLayout());

		final Listener tabSelectedListener = e -> {
			for (Listener list : content.getListeners(TAB_ITEM_SELECTED)) {
				list.handleEvent(new Event());
			}
		};

		item.addListener(TAB_ITEM_SELECTED, tabSelectedListener);

		final Listener folderSelectedListener = e -> {
			folder.setSelection(item);
		};

		content.addListener(SWT.SELECTED, folderSelectedListener);

		item.addDisposeListener(e -> {
			item.removeListener(TAB_ITEM_SELECTED, tabSelectedListener);
			content.removeListener(SWT.SELECTED, folderSelectedListener);
		});

		item.setControl(sc);
		return content;
	}

	public CTabItem createCTabItem(CTabFolder tabFolder, String title) {
		return createCTabItem(tabFolder, title, null);
	}

	public CTabItem createCTabItem(CTabFolder tabFolder, String title, Image icon) {
		CTabItem item = new CTabItem(tabFolder, SWT.NONE);
		item.setImage(icon);
		item.setText(title);
		return item;
	}

	public void recursiveSetEnabled(Control ctrl, boolean enabled) {
		if (ctrl instanceof Composite) {
			Composite comp = (Composite) ctrl;
			for (Control c : comp.getChildren())
				recursiveSetEnabled(c, enabled);
		} else {
			ctrl.setEnabled(enabled);
		}
	}

	public void margins(Composite composite, int width, int heigth, int vertical, int horizontal) {
		Layout layout = composite.getLayout();
		if (layout == null) {
			return;
		}

		if (layout instanceof GridLayout) {
			GridLayout gl = (GridLayout) layout;
			gl.verticalSpacing = vertical;
			gl.marginWidth = width;
			gl.marginHeight = heigth;
			gl.horizontalSpacing = horizontal;
		}
	}

	public void setMargins(int width, Composite... composites) {
		for (Composite composite : composites) {
			Layout layout = composite.getLayout();
			if (layout == null) {
				return;
			}

			if (layout instanceof GridLayout) {
				clearMargins((GridLayout) layout, width);
			}
		}
	}

	public void clearMargins(Composite... composites) {
		setMargins(0, composites);
	}

	public void clearMargins(GridLayout gl, int w) {
		gl.verticalSpacing = w;
		gl.marginWidth = w;
		gl.marginHeight = w;
		gl.horizontalSpacing = w;
	}
//	public void clearMargins(GridLayout gl) {
//		gl.verticalSpacing = 0;
//		gl.marginWidth = 0;
//		gl.marginHeight = 0;
//		gl.horizontalSpacing = 0;
//	}

	public Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		gc.dispose();

		// Image data from scaled image and transparent pixel from original

		ImageData imageData = scaled.getImageData();

		imageData.transparentPixel = image.getImageData().transparentPixel;

		// Final scaled transparent image

		Image finalImage = new Image(Display.getDefault(), imageData);

		scaled.dispose();

		return finalImage;

//		Image scaled = new Image(Display.getDefault(), width, height);
//		GC gc = new GC(scaled);
//		gc.setAntialias(SWT.ON);
//		gc.setInterpolation(SWT.HIGH);
//		gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
//		gc.dispose();
//		image.dispose(); // don't forget about me!
//		return scaled;
	}

	public void show(String partUri, Object value) {
		show(partUri, null, value);
	}

	public void show(String partUri, String title, Object value) {
		show(partUri, partUri, title, value);
	}

	private final Map<String, MPart> partsMap = new HashMap<>();

	public void minimizeMaximizePartStack(String partStackUri, boolean minimize) {

		List<MPartStack> findElements = modelService.findElements(application, MPartStack.class, EModelService.ANYWHERE,
				new ElementMatcher(null, MPartStack.class, (List<String>) null));

		findElements.forEach(stack -> {
			if (stack.getTags().contains("MMinimized")) {
				stack.getTags().add(IPresentationEngine.MINIMIZED);
				stack.setVisible(false);
			}
		});

//		List<MPartStack> findElements = modelService.findElements(application, MPartStack.class,EModelService.class, new ArrayList<MPartStack>());
//		System.out.println(findElements);

//		sync.syncExec(() -> {
//			try {
//				List<MPartStack> stacks = modelService.findElements(application, partStackUri, MPartStack.class, null);
//				for (MPartStack stack : stacks) {
//					stack.getTags().add( IPresentationEngine.MINIMIZED);
//					stack.setVisible(false);
//				}
//				
//			} catch (Exception e) {
//			}
//		});
	}

	public void activePart() {
		partService.requestActivation();
	}

	public boolean show(String partUri, String partId, String title, Object value) {
		try {
			MPart part;
//			part = partService.createPart(partUri);
			part = partService.findPart(partId);
			boolean isCreatePart = part == null;
			part = part == null ? partService.createPart(partUri) : part;
			if (title != null) {
				if (!title.isEmpty()) {
					part.setLabel(title);
				}
			}
			if (part != null) {
				part.setObject(value);
				partService.showPart(part, PartState.ACTIVATE);
				part.setElementId(partId);
			}
			return isCreatePart;
		} catch (Exception e) {
			return false;
		}

	}

	public void show(MPart part) {
		partService.showPart(part, PartState.ACTIVATE);
	}

	public MPart makePart(String partUri, String partId) {
		MPart part = partService.findPart(partId);
		part = part == null ? partService.createPart(partUri) : part;
		IEclipseContext childContext = application.getContext().createChild();
		childContext.set(MPart.class, part);
		part.setContext(childContext);
		return part;
	}

	public void show_(String partUri, String partId, String title, Object value) {

		sync.syncExec(() -> {

			try {

				MPart part;

				if (partsMap.containsKey(partId)) {
					part = partsMap.get(partId);
					part.setObject(value);

					for (String variable : part.getVariables()) {
						if (variable.contains("partStack:")) {
							variable = variable.replace("partStack:", "");
							MPartStack stack = modelService.findElements(application, variable, MPartStack.class, null)
									.get(0);
							stack.setVisible(true);
							List<MStackElement> childres = stack.getChildren();
							childres.add(part);
							break;
						}
					}

					partService.showPart(part, PartState.ACTIVATE);
					if (value != null) {
						eventBroker.send("update_part_report", part);
					}

					return;
				}

				part = partService.createPart(partUri);
				part.setElementId(partId);

				if (title != null) {
					part.setLabel(title);
				}

				part.setObject(value);

				for (String variable : part.getVariables()) {
					if (variable.contains("partStack:")) {
						variable = variable.replace("partStack:", "");

						List<MPartStack> res = modelService.findElements(application, variable, MPartStack.class, null);

						if (!res.isEmpty()) {
							MPartStack stack = res.get(0);
							stack.setVisible(true);
							List<MStackElement> childres = stack.getChildren();
							childres.add(part);
							break;
						}
					}
				}

				partService.showPart(part, PartState.ACTIVATE);
				eventBroker.send("update_part_report", part);

				partsMap.put(partId, part);
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

	}

	public BufferedImage getBufferedImage(String symbolicName, String path) {
		try {
			return ImageIO.read(getUrl(symbolicName, path));
		} catch (Exception e) {
			return null;
		}
	}

	public Image getSWTImage(String pluginId, String path) {
		try {
			URL url = getUrl(pluginId, path);
			return SWTResourceManager.getImage(url.getPath());
		} catch (Exception e) {
			return null;
		}
	}

	public URL getUrl(String symbolicName, String path) {
		Bundle bundle = Platform.getBundle(symbolicName);
		if (bundle != null) {
			return bundle.getEntry(path);
		}
		return null;
	}

	public File getFile(String pluginId, String path) {
		Bundle bundle = Platform.getBundle(pluginId);
		return bundle.getDataFile(path);
	}

	public Image getImage(String pluginId, String path) {
		try {
			if (path.startsWith("/")) {
				path = path.substring(1);
			}
			pluginId = String.format("platform:/plugin/%s/%s", pluginId, path);

			return getImage(pluginId);
		} catch (Exception e) {
			return null;
		}
	}

	public Image getImage(String suri) {
		try {
			URI uri = URI.create(suri);
			return ImageDescriptor.createFromURL(new URL(uri.toString())).createImage();
		} catch (Exception e) {
			return null;
		}
	}

	public ImageDescriptor createImageDescriptor(String pluginId, String path) {
		try {
			Bundle bundle = Platform.getBundle(pluginId);
			URL uri = bundle.getEntry(path);
			return ImageDescriptor.createFromURL(uri);
		} catch (Exception e) {
			return null;
		}

	}

	public void show(String partUri, boolean show) {
		MPart part = partService.findPart(partUri);
		part = part == null ? partService.createPart(partUri) : part;
		if (show) {
			partService.showPart(part, PartState.ACTIVATE);
		} else {
			part.setToBeRendered(false);
		}
	}

	public void show(String partUri) {
		show(partUri, null);
	}

	public Composite getComposite(Composite parent, int style) {
		Composite ret = new Composite(parent, style);
		ret.setLayout(new GridLayout());
		clearMargins(ret);
		return ret;
	}

	public Group getGroup(Composite parent, int style) {
		Group ret = new Group(parent, style);
		return ret;
	}

	public Group getGroup(Composite parent) {
		return getGroup(parent, SWT.NONE);
	}

	public Composite getComposite(Composite parent) {
		return getComposite(parent, SWT.NONE);
	}

	public Button getImageButton(Composite parent, String text, Image image) {
		return getImageButton(parent, text, image, null);
	}

	public Button getImageButton(Composite parent, String text, Image image, Listener listener) {
//		Button button = getButton(parent, text, SWT.PUSH, listener);
		Button button = new Button(parent, SWT.PUSH);

		GridData gd = new GridData();
		float height = parent.getFont().getFontData()[0].height;
		gd.heightHint = image.getImageData().height + (int) height;

		button.setLayoutData(gd);

		button.addListener(SWT.Paint, event -> {
			GC gc = event.gc;

			int w = ((GridData) button.getLayoutData()).widthHint;
			int h = ((GridData) button.getLayoutData()).heightHint;

			Point textSize = gc.textExtent(text);

			gc.drawText(text, w / 2 - textSize.x / 2,
					image.getImageData().height - image.getImageData().height / 2 - textSize.y, true);

			gc.drawImage(image, w / 2 - image.getImageData().width / 2,
					h / 2 - image.getImageData().height / 2 + textSize.y / 2);
		});

		return button;
	}

	public Button getButton(Composite parent, String text) {
		return getButton(parent, text, SWT.PUSH, null);
	}

	public Button getButton(Composite parent, String text, Listener listener) {
		return getButton(parent, text, SWT.PUSH, listener);
	}

	public Button getButton(Composite parent, String text, int style) {
		return getButton(parent, text, style, null);
	}

	public Button getButton(Composite parent, String text, int style, final Listener listener) {

		Button ret = new Button(parent, style);
		ret.setText(text);

		if (listener != null) {
			ret.addListener(SWT.Selection, listener);

			ret.addDisposeListener(e -> {
				ret.removeListener(SWT.Selection, listener);
			});

		}
		return ret;
	}

	public ComboViewer getComboViewer(Composite parent) {
		return getComboViewer(parent, SWT.DROP_DOWN);
	}

	public ComboViewer getComboViewer(Composite parent, int style) {
		ComboViewer ret = new ComboViewer(parent, style);
		ret.setContentProvider(ArrayContentProvider.getInstance());
		return ret;
	}

	public Label getLabel(Composite parent, String text) {
		return getLabel(parent, text, SWT.NONE);
	}

	public Label getLabel(Composite parent, String text, int style) {
		Label ret = new Label(parent, style);
		ret.setText(text);
		return ret;
	}

	public CLabel getCLabel(Composite parent, String text) {
		return getCLabel(parent, text, SWT.NONE);
	}

	public CLabel getCLabel(Composite parent, String text, int style) {
		CLabel ret = new CLabel(parent, style);
		ret.setText(text);
		ret.addListener(SWT.Modify, e -> {
		});
		return ret;
	}

	public Label getIcon(Composite parent, String suri) {
		Label ret = getLabel(parent, "");
		Image img = getImage(suri);
		ret.setImage(img);
		return ret;
	}

	public Label getIcon(Composite parent, String plugin, String path) {
		Label ret = getLabel(parent, "");
		Image img = ResourceManager.getPluginImage(plugin, path);
		ret.setImage(img);
		return ret;
	}

	public Text getText(Composite parent, String text) {
		return getText(parent, text, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
	}

	public Text getText(Composite parent, String text, int style) {
		Text ret = new Text(parent, style);
		ret.setText(text);

		final FocusAdapter listener = new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Text t = (Text) e.widget;
				t.selectAll();
				super.focusGained(e);
			}
		};

		ret.addDisposeListener(e -> {
			ret.removeFocusListener(listener);
		});
		return ret;
	}

	public void createColumn(TreeViewer viewer, String title, int width, int style) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, style);
		viewerColumn.getColumn().setWidth(width);
		viewerColumn.getColumn().setText(title);
	}

	public void createColumn(TreeViewer viewer, String title, int width, int style, IBaseLabelProvider labelProvider) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, style);
		viewerColumn.getColumn().setWidth(width);
		viewerColumn.getColumn().setText(title);
		viewerColumn.setLabelProvider((CellLabelProvider) labelProvider);
	}

	public void createCollumn(TableViewer viewer, int style, String title, int width) {
		TableViewerColumn colFirstName = new TableViewerColumn(viewer, style);
		colFirstName.getColumn().setWidth(width);
		colFirstName.getColumn().setText(title);

	}

	public void createCollumn(TableViewer viewer, int style, String title, int width, IBaseLabelProvider provider) {
		TableViewerColumn colFirstName = new TableViewerColumn(viewer, style);
		colFirstName.getColumn().setWidth(width);
		colFirstName.getColumn().setText(title);
		colFirstName.setLabelProvider((CellLabelProvider) provider);
	}

	public void createCollumn(TreeViewer viewer, int style, String title, int numCharacters) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, style);
		viewerColumn.getColumn().setResizable(true);
		viewerColumn.getColumn().setText(title);

		GC gc = new GC(viewer.getControl());
		Point extent = gc.textExtent("X");//$NON-NLS-1$
		int width = numCharacters * extent.x;
		viewerColumn.getColumn().setWidth(width);
	}

	public void createCollumn(TreeViewer viewer, int style, String title, int numCharacters,
			IBaseLabelProvider provider) {
		try {
			TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, style);
			viewerColumn.getColumn().setResizable(true);
			viewerColumn.getColumn().setText(title);
			viewerColumn.setLabelProvider((CellLabelProvider) provider);

			GC gc = new GC(viewer.getControl());
			Point extent = gc.textExtent("X");//$NON-NLS-1$
			int width = numCharacters * extent.x;
			viewerColumn.getColumn().setWidth(width);
		} catch (Exception e) {
		}

	}

	public MCheckboxTableViewer newCheckList(Composite parent) {
		MCheckboxTableViewer ret = newCheckList(parent, SWT.CHECK | SWT.FULL_SELECTION | SWT.VIRTUAL);
		ret.setContentProvider(ArrayContentProvider.getInstance());
		return ret;
	}

	public MCheckboxTableViewer newCheckList(Composite parent, int style) {
		MCheckboxTableViewer viewer = MCheckboxTableViewer.newCheckList(parent, style);
		return viewer;
	}

	public void configureDateField(String format, Text... texts) {
//		configureVerifyValues("[0-9/]", texts);
		for (Text text : texts) {
			FocusAdapter listener = new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					Text t = (Text) e.widget;
					sync.asyncExec(() -> {
						try {
							if (!t.isDisposed()) {
								String sdate = t.getText();

								if (sdate.isEmpty()) {
									return;
								}

								Date date = DateUtils.getDate(sdate);
								sdate = DateUtils.dateToString(date, format);
								t.setText(sdate);
								t.setData(date);
							}
						} catch (Exception e2) {
							t.setFocus();
							t.selectAll();
						}
					});
				}
			};

			text.addFocusListener(listener);

			text.addDisposeListener(e -> {
				text.removeFocusListener(listener);
			});
		}
	}

	public GridData setGridData(Control control) {
		return setGridData(control, SWT.NONE);
	}

	public GridData setGridData(Control control, int style) {
		GridData gd = new GridData(style);
		control.setLayoutData(gd);
		return gd;
	}

	public GridData setGridData(Control control, int horizontalAlignment, int verticalAlignment,
			boolean grabExcessHorizontalSpace, boolean grabExcessVerticalSpace) {
		GridData gd = new GridData(horizontalAlignment, verticalAlignment, grabExcessHorizontalSpace,
				grabExcessVerticalSpace);
		control.setLayoutData(gd);
		return gd;
	}

	public GridData setGridData(Control control, Dimension size) {
		return setGridData(control, size.width, size.height);
	}

	public GridData setGridData(Control control, int w, int h) {
		GridData gd = new GridData(w, h);
		control.setLayoutData(gd);
		return gd;
	}

	public Dimension computeDimensionH(int width, int refW, int refH) {
		BigDecimal bcW = new BigDecimal(width);
		BigDecimal bcRW = new BigDecimal(refW);
		BigDecimal bcRH = new BigDecimal(refH);

		BigDecimal prop = bcRH.divide(bcRW, 10, RoundingMode.CEILING);
		return new Dimension(bcW.intValue(), bcW.multiply(prop).intValue());
	}

	public Dimension computeDimensionW(int height, int refW, int refH) {
		BigDecimal bcH = new BigDecimal(height);
		BigDecimal bcRW = new BigDecimal(refW);
		BigDecimal bcRH = new BigDecimal(refH);

		BigDecimal prop = bcRW.divide(bcRH, 10, RoundingMode.CEILING);
		return new Dimension(bcH.multiply(prop).intValue(), bcH.intValue());
	}

//	public Dimension computeDimensionHeight(int w, float proporcion) {
//		BigDecimal h = new BigDecimal(w).multiply(BigDecimal.valueOf(proporcion));
//		return new Dimension(w, h.intValue());
//	}
//	
//	public Dimension computeDimensionWidth(int h, float proporcion) {
//		BigDecimal w = new BigDecimal(h).multiply(BigDecimal.valueOf(proporcion));
//		return new Dimension(w.intValue(), h);
//	}

	public void setBackground(Control control, int colorId) {
		control.setBackground(getColor(colorId));
	}

	public Composite getImageMock(Composite parent, String text, int w, int h) {
		return getImageMock(parent, text, new Dimension(w, h));
	}

	public Composite getImageMock(Composite parent, String text, Dimension size) {
		Composite logo = getComposite(parent);
		logo.setLayout(new GridLayout());
		setGridData(logo, size);
		setBackground(logo, SWT.COLOR_DARK_GRAY);

		String slogo = String.format("%s\n(%dx%d)", text, size.width, size.height);
		Label labelLogo = getLabel(logo, slogo, SWT.CENTER);
		labelLogo.setForeground(getColor(SWT.COLOR_GRAY));
		setGridData(labelLogo, SWT.CENTER, SWT.CENTER, true, true);
		return logo;
	}

	public Image getImageMock2(Composite parent, String text, Dimension size) {
		int w = size.width;
		int h = size.height;

		Image image = new Image(parent.getDisplay(), w, h);
		GC gc = new GC(image);
		gc.setBackground(getColor(SWT.COLOR_DARK_GRAY));
		gc.fillRectangle(0, 0, w, h);

		gc.setForeground(getColor(SWT.COLOR_GRAY));
		gc.drawLine(0, 0, w, h);
		gc.drawLine(w, 0, 0, h);

		gc.setForeground(getColor(SWT.COLOR_YELLOW));

		String slogo = String.format("(%dx%d)", size.width, size.height);
		Dimension stringSize = getStringSize(slogo);

//	    FontMetrics fm = gc.getFontMetrics();
		int x = 0 + (w - stringSize.width) / 2;
		int y = 0 + (h - stringSize.height) / 2;

		gc.drawString(slogo, x, y, true);

		gc.dispose();

		return image;
	}

	public void addTextModifyListener(final Text text, final Listener listener) {
		text.addDisposeListener(e -> {
			text.removeListener(SWT.Modify, listener);
		});

		text.addListener(SWT.Modify, listener);
	}

	public String toString(String pattern, Object... args) {
		return MessageFormat.format(pattern, args);
	}

	public void setDefaultButton(Button button) {
		if (button != null) {
//			if (!button.isEnabled()) {
			Composite parent = button.getParent();
			parent.getShell().setDefaultButton(button);
//			}
		}
	}

	public Color getColor(int color) {
		return Display.getCurrent().getSystemColor(color);
	}

	public Composite getCompositeWithScrolled(Composite parent) {
//		pc.clearMargins(parent_);
//		ScrolledComposite sc = pc.getScrolledComposite(parent_);
//		Composite scComposite = pc.getComposite(sc);
//		Composite parent = pc.getComposite(scComposite);
//		parent.setLayout(new GridLayout());
//		
//		GridData gd = new GridData();		
//		gd.widthHint = 300;
//		gd.heightHint = 200;
//		
//		parent.setLayoutData(gd);
//		pc.setContentToScrolled(scComposite);

		ScrolledComposite sc = getScrolledComposite(parent);
		Composite master = getComposite(sc);
		Composite ret = getComposite(master);
		ret.setLayout(new GridLayout());

		setContentToScrolled(master);
		return ret;
	}

	public ScrolledComposite getScrolledComposite(Composite parent, int style) {
		ScrolledComposite sc = new ScrolledComposite(parent, style);
//		sc.setAlwaysShowScrollBars(true);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		return sc;
	}

	public ScrolledComposite getScrolledComposite(Composite parent) {
		return getScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	public void setContentToScrolled(Composite content) {
		Composite parent = content.getParent();
		if (parent instanceof ScrolledComposite) {
			ScrolledComposite scrolled = (ScrolledComposite) parent;
			scrolled.setContent(content);
			scrolled.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}

	}

	public int getMod11(String num) {

		// variáveis de instancia
		int soma = 0;
		int resto = 0;
		int dv = 0;
		String[] numeros = new String[num.length() + 1];
		int multiplicador = 2;

		for (int i = num.length(); i > 0; i--) {
			// Multiplica da direita pra esquerda, incrementando o multiplicador de 2 a 9
			// Caso o multiplicador seja maior que 9 o mesmo recomeça em 2
			if (multiplicador > 9) {
				// pega cada numero isoladamente
				multiplicador = 2;
				numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * multiplicador);
				multiplicador++;
			} else {
				numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * multiplicador);
				multiplicador++;
			}
		}

		// Realiza a soma de todos os elementos do array e calcula o digito verificador
		// na base 11 de acordo com a regra.
		for (int i = numeros.length; i > 0; i--) {
			if (numeros[i - 1] != null) {
				soma += Integer.valueOf(numeros[i - 1]);
			}
		}
		resto = soma % 11;
		dv = 11 - resto;

		// retorna o digito verificador
		return dv;
	}

	public int getMod10(String num) {

		// variáveis de instancia
		int soma = 0;
		int resto = 0;
		int dv = 0;
		String[] numeros = new String[num.length() + 1];
		int multiplicador = 2;
		String aux;
		String aux2;
		String aux3;

		for (int i = num.length(); i > 0; i--) {
			// Multiplica da direita pra esquerda, alternando os algarismos 2 e 1
			if (multiplicador % 2 == 0) {
				// pega cada numero isoladamente
				numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * 2);
				multiplicador = 1;
			} else {
				numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * 1);
				multiplicador = 2;
			}
		}

		// Realiza a soma dos campos de acordo com a regra
		for (int i = (numeros.length - 1); i > 0; i--) {
			aux = String.valueOf(Integer.valueOf(numeros[i]));

			if (aux.length() > 1) {
				aux2 = aux.substring(0, aux.length() - 1);
				aux3 = aux.substring(aux.length() - 1, aux.length());
				numeros[i] = String.valueOf(Integer.valueOf(aux2) + Integer.valueOf(aux3));
			} else {
				numeros[i] = aux;
			}
		}

		// Realiza a soma de todos os elementos do array e calcula o digito verificador
		// na base 10 de acordo com a regra.
		for (int i = numeros.length; i > 0; i--) {
			if (numeros[i - 1] != null) {
				soma += Integer.valueOf(numeros[i - 1]);
			}
		}
		resto = soma % 10;
		dv = 10 - resto;

		// retorna o digito verificador
		return dv;
	}

//	public Composite getCompositeScrolled(Composite parent) {
//		ScrolledComposite sc = getScrolledComposite(parent);
//		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//
//		Composite ret = getComposite(sc);
//		sc.setContent(ret);
//		sc.setExpandHorizontal(true);
//		sc.setExpandVertical(true);
//		sc.setMinSize(ret.computeSize(SWT.DEFAULT, SWT.DEFAULT));
//
//		return ret;
//	}

	public void setWidth(Control control, int numCharacters) {
		try {
			Resource.setNonDisposeHandler(null);
			GC gc = new GC(control);
			Point extent = gc.textExtent("X");//$NON-NLS-1$

			if (control.getLayoutData() instanceof GridData) {
				GridData gd = (GridData) control.getLayoutData();// new GridData();
				gd.widthHint = numCharacters * extent.x;
				control.setLayoutData(gd);
			} else if (control.getLayoutData() == null) {
				GridData gd = new GridData();
				gd.widthHint = numCharacters * extent.x;
				control.setLayoutData(gd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GridData setGridDataWidthHint(Control control, int width) {
		GridData gd = new GridData();
		gd.widthHint = width;
		control.setLayoutData(gd);
		return gd;
	}

	public void editable(boolean b, Control... controls) {
		sync.asyncExec(() -> {
			for (Control c : controls) {
				if (c instanceof Text) {
					Text t = (Text) c;
					t.setEditable(b);
				}

				if (c instanceof Button) {
					final Button button = (Button) c;
					final boolean selection = button.getSelection();
					if (!b) {
						button.addListener(SWT.Selection, e -> {
							button.setSelection(selection);
						});
					}
				}
			}
		});
	}

	@Inject
	IEclipseContext eclipseContext;

	public void showDialog(IEclipseContext context, Class type, Object... args) {
		Object make = ContextInjectionFactory.make(type, context);
		if (make instanceof Dialog) {
			Dialog dialog = (Dialog) make;
			dialog.open();
		}
	}

//	@Inject
//	@Optional
	public void addPerspectiveListener(
			@UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) org.osgi.service.event.Event event) {

		System.out.println(event);

		Object value = event.getProperty(EventTags.NEW_VALUE);
		if (value instanceof MPerspective) {
			MPerspective perspective = (MPerspective) value;

			List<MUIElement> elements = modelService.findElements(application, null, MUIElement.class,
					Collections.emptyList(), EModelService.IN_MAIN_MENU | EModelService.IN_TRIM);

			elements.forEach(item -> {
				String sid1 = perspective.getElementId().trim();
				item.getTags().forEach(tag -> {

					if (tag.toLowerCase().contains("perspective:")) {
						item.setToBeRendered(false);
						String sid2 = tag.replace("perspective:", "").trim();
						if (sid1.equalsIgnoreCase(sid2)) {
							item.setToBeRendered(true);
						}
					}
				});
			});

//			System.out.println(perspective.getElementId());

//			application.getAddons().forEach(addon -> {
//				System.out.println(addon.getElementId());
//				
//			});

//			application.getAddons().forEach(addon -> {
//				Class<?> type = addon.getObject().getClass();
//				List<Method> methods = new ArrayList<Method>();
//				methods.addAll(Arrays.asList(type.getMethods()));
//				methods.addAll(Arrays.asList(type.getDeclaredMethods()));
//
//				for (Method method : methods) {
//					if (method.isAnnotationPresent(MActivatePerspective.class)) {
//						MActivatePerspective mActivatePerspective = method.getAnnotation(MActivatePerspective.class);
//						String perspectiveId = mActivatePerspective.perspectiveId();
//						if (perspectiveId.equalsIgnoreCase(perspective.getElementId())) {
//							ContextInjectionFactory.invoke(addon.getObject(), MActivatePerspective.class, application.getContext(), null);
//							break;
//						}
//					}
//				}
//			});

		}
	}

	@Inject
	@Optional
	private MWindow window;

	@Inject
	@Optional
	public void init(@UIEventTopic("startup_window") MWindow window) {
		List<MUIElement> elements = modelService.findElements(application, null, MUIElement.class,
				Collections.emptyList(), EModelService.IN_MAIN_MENU | EModelService.IN_TRIM);

		elements.forEach(item -> {
			String sid1 = modelService.getActivePerspective(window).getElementId().trim();
			item.getTags().forEach(tag -> {

				if (tag.toLowerCase().contains("perspective:")) {
					item.setToBeRendered(false);
					String sid2 = tag.replace("perspective:", "").trim();
					if (sid1.equalsIgnoreCase(sid2)) {
						item.setToBeRendered(true);
					}
				}
			});
		});
	}

	public void setWindowBounds(int w, int h, boolean isCenter) {
		if (window != null && display != null) {
			sync.asyncExec(() -> {
				window.setWidth(w);
				window.setHeight(h);
				if (isCenter) {
					centerWindow();
				}
			});
		}
	}

	public void maximizeWindow() {
		if (window != null && display != null) {
			sync.asyncExec(() -> {
				window.setX(0);
				window.setY(0);
				window.setWidth(display.getClientArea().width);
				window.setHeight(display.getClientArea().height);
			});
		}
	}

	public void centerWindow() {
		if (window != null && display != null) {
			sync.asyncExec(() -> {
				Rectangle bounds = display.getPrimaryMonitor().getBounds();
				int x = (bounds.width / 2) - (window.getWidth() / 2);
				int y = (bounds.height / 2) - (window.getHeight() / 2);

				window.setX(x);
				window.setY(y);
			});
		}
	}

	public Dialog getHidDialog(Class type, Object... args) {
		Dialog ret = null;
//		BusyIndicator.showWhile(display, () -> {
			IEclipseContext context = application.getContext();
			MTrimmedWindow window = (MTrimmedWindow) application.getChildren().get(0);
			IEclipseContext windowContext = window.getContext();
			context.activate();

			for (Object object : args) {
				String name = ObjectUtils.nullSafeClassName(object);
				windowContext.set(name, object);
			}

			windowContext.setParent(context);
			windowContext.activateBranch();

			Object make = ContextInjectionFactory.make(type, windowContext);
			if (make instanceof Dialog) {
				ret = (Dialog) make;
			}
//		});
		return ret;
	}

	public void showDialog(Class type, Object... args) {
//		---
//		IEclipseContext localContext = EclipseContextFactory.create();
//		for (Object object : args) {
//			String name = ObjectUtils.nullSafeClassName(object);
//			localContext.set(name, object);
//		}
//		localContext.setParent(eclipseContext);
//
//		Object make = ContextInjectionFactory.make(type, localContext);
//		if (make instanceof Dialog) {
//			Dialog dialog = (Dialog) make;
//			dialog.open();
//		}
		BusyIndicator.showWhile(display, () -> {
			IEclipseContext context = application.getContext();
			MTrimmedWindow window = (MTrimmedWindow) application.getChildren().get(0);
			IEclipseContext windowContext = window.getContext();
			context.activate();

			for (Object object : args) {
				String name = ObjectUtils.nullSafeClassName(object);
				windowContext.set(name, object);
			}

			windowContext.setParent(context);
			windowContext.activateBranch();

			Object make = ContextInjectionFactory.make(type, windowContext);
			if (make instanceof Dialog) {
				Dialog dialog = (Dialog) make;
				dialog.open();
			}
		});

	}

	public void grabFocus(Control widget) {
		if (widget.isDisposed()) {
			return;
		}
		sync.asyncExec(() -> widget.setEnabled(true));
		sync.asyncExec(() -> widget.setFocus());

		if (widget instanceof Text) {
			Text t = (Text) widget;
			sync.asyncExec(() -> t.setSelection(0));
		}

		try {

			getListGroupInControl(widget).forEach(control -> {
				enabled(widget.getEnabled(), control);
			});

//			for (Control c : mapControls.get(widget)) {
//				if (c != widget) {
//					sync.asyncExec(() -> {
//
//						if (c.isDisposed() || widget.isDisposed()) {
//							return;
//						}
//
//						sync.asyncExec(() -> c.setEnabled(widget.getEnabled()));
////					sync.asyncExec(() -> enabled(widget.isEnabled(), c));
//
//						if (c instanceof CLabel || c instanceof Label) {
//							Color color = getColor(
//									c.getEnabled() ? SWT.COLOR_WIDGET_FOREGROUND : SWT.COLOR_WIDGET_FOREGROUND);
//							c.setForeground(color);
//						}
//					});
//				}
//			}
		} catch (Exception e) {
		}
	}

	public void clear(Control... controls) {
		for (Control c : controls) {
			if (c instanceof Text) {
				Text cc = (Text) c;
				sync.asyncExec(() -> cc.setText(""));
			}
		}
	}

	public GridData fillHorizontalComponent(Control control) {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		control.setLayoutData(gd);
		return gd;
	}

	public <T> List<T> arrayToList(Object[] checkedElements, Class<? extends T> type) {
		List<T> r = new ArrayList<T>();
		for (Object o : checkedElements) {
			r.add(type.cast(o));
		}

		return r;
	}

	public TableViewer getTableViewer(Composite parent, int styles) {
		TableViewer viewer = new TableViewer(parent, styles);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		return viewer;
	}

	public TableViewer getTableViewer(Composite parent) {
		TableViewer ret = getTableViewer(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		ret.getTable().setHeaderVisible(true);
		ret.getTable().setLinesVisible(true);
		ret.setContentProvider(ArrayContentProvider.getInstance());
		return ret;
	}

	public void widtCollumn(TableViewer tw, int collunmIndex, int width) {
		Table table = tw.getTable();
		if (!table.isDisposed()) {
//			GC gc = new GC(table);
//			Point extent = gc.textExtent("X");

			final ControlListener listener = new ControlAdapter() {
				@Override
				public void controlResized(ControlEvent e) {
					int w = 8;
					w = width * w;
					table.getColumn(collunmIndex).setWidth(w);
				}
			};

			table.addControlListener(listener);

			table.addDisposeListener(e -> {
				table.removeControlListener(listener);
			});
		}
	}

	public void widtCollumn(TreeViewer tv, int collunmIndex, float percentual) {
		Tree tree = tv.getTree();
		tree.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				Rectangle area = tree.getClientArea();
				BigDecimal f = BigDecimal.valueOf(100);
				f = BigDecimal.valueOf(percentual).divide(f);
				f = BigDecimal.valueOf(area.width).multiply(f);
				tree.getColumn(collunmIndex).setWidth(f.intValue());
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		});
	}

	public void widtCollumn(TableViewer tw, int collunmIndex, float percentual) {
		Table table = tw.getTable();

		ControlListener controlListener = new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				Rectangle area = table.getClientArea();
				BigDecimal f = BigDecimal.valueOf(100);
				f = BigDecimal.valueOf(percentual).divide(f);
				f = BigDecimal.valueOf(area.width).multiply(f);
				table.getColumn(collunmIndex).setWidth(f.intValue());
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		};

		table.addControlListener(controlListener);

		table.addDisposeListener(e -> {
			table.removeControlListener(controlListener);
		});
	}

	public void getFill(Composite parent) {
		getLabel(parent, "").setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	public boolean validateMod10(String svalue) {
		try {

			if (svalue.length() <= 1) {
				return false;
			}

			String code = svalue.substring(0, svalue.length() - 1);
			int mod10 = getMod10(code);

			return String.format("%s%d", code, mod10).equals(svalue);
		} catch (Exception e) {
			return true;
		}
	}

	public boolean validateMod11(String svalue) {
		try {

			if (svalue.length() <= 1) {
				return false;
			}

			String code = svalue.substring(0, svalue.length() - 1);
			int mod11 = getMod11(code);

			return String.format("%s%d", code, mod11).equals(svalue);
		} catch (Exception e) {
			return true;
		}
	}

	public void addKeyListener(Text text, SListener listener) {
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listener.textValue(((Text) e.widget).getText());
				super.keyReleased(e);
			}
		});
//		addTextModifyListener(text, e -> {
//			Text txt = (Text) e.widget;
//			if (txt.isEnabled()) {
//				listener.textValue(txt.getText());
//			}
//		});
	}

	public void addTextListener(Text text, SListener listener) {
		addTextModifyListener(text, e -> {
			Text txt = (Text) e.widget;
			if (txt.isEnabled()) {
				listener.textValue(txt.getText());
			}
		});
	}

	public interface SListener {
		void textValue(String text);
	}

	public void toTopControlAndDefault(Button button) {
		toTopControl(button);
		setDefaultButton(button);
	}

	public void setForeground(Widget widget, int color) {
		try {
			Method m = widget.getClass().getMethod("setForeground", Color.class);
			m.setAccessible(true);
			m.invoke(widget, getColor(color));
		} catch (Exception e) {
		}
	}

	public void widtCollumnCharacter(TableViewer tableViewer, int index, int width) {
		final Table table = tableViewer.getTable();

		ControlListener listener = new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
//				Rectangle area = table.getClientArea();
				GC gc = new GC(table);
				Point extent = gc.textExtent("X");//$NON-NLS-1$
//				GridData gd = new GridData();
//				gd.widthHint = width * extent.x;
//				control.setLayoutData(gd);

				table.getColumn(index).setWidth(width * extent.x);
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		};

		table.addControlListener(listener);
		table.addDisposeListener(e -> table.removeControlListener(listener));
	}

	public void addLostFocus(Text text, Listener listener) {
		text.addListener(SWT.FocusOut, listener);
	}

	public void margins(Composite composite, int value) {
		margins(composite, value, value, value, value);
	}

	public void switchPerspective(String perspectiveId) {
		IEclipseContext context = application.getContext();
		MTrimmedWindow window = (MTrimmedWindow) application.getChildren().get(0);
		IEclipseContext windowContext = window.getContext();
		context.activate();
		windowContext.setParent(context);
		windowContext.activateBranch();
		EPartService partService = windowContext.get(EPartService.class);
		partService.switchPerspective(perspectiveId);
	}

	public void setToContext(String namedValue, Object value) {
		IEclipseContext context = application.getContext();
		context.set(namedValue, value);
	}

	public void setLayoutData(Object data, Control... controls) {
		for (Control c : controls) {
			c.setLayoutData(data);
		}
	}

	public void updateHandlers() {
		eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
	}

	public <T> T makeInject(Class<T> type, Object... args) {

		IEclipseContext context = application.getContext();
		MTrimmedWindow window = (MTrimmedWindow) application.getChildren().get(0);
		IEclipseContext windowContext = window.getContext();
		context.activate();

		for (Object object : args) {
			String name = ObjectUtils.nullSafeClassName(object);
			windowContext.set(name, object);
		}

		windowContext.setParent(context);
		windowContext.activateBranch();

		return ContextInjectionFactory.make(type, windowContext);
	}

	private long runningTask = 0l;

	public void registerProgressBar(ProgressBar progressBar) {
		NullProgressMonitor monitor = new NullProgressMonitor() {
			@Override
			public void beginTask(String name, int totalWork) {
				sync.asyncExec(() -> {
					progressBar.setVisible(true);

					if (runningTask <= 0) {
						progressBar.setMaximum(totalWork);
						progressBar.setSelection(0);
					} else {
						progressBar.setMaximum(progressBar.getMaximum() + totalWork);
					}

					runningTask++;
				});
			}

			@Override
			public void worked(int work) {
				sync.asyncExec(() -> progressBar.setSelection(work));
			}

			@Override
			public void done() {
				super.done();
				try {
					sync.asyncExec(() -> progressBar.setVisible(false));
				} catch (Exception e) {
				}
			}
		};

		Job.getJobManager().setProgressProvider(new ProgressProvider() {
			@Override
			public IProgressMonitor createMonitor(Job job) {
				if (job != null) {
					job.addJobChangeListener(new JobChangeAdapter() {

						@Override
						public void done(org.eclipse.core.runtime.jobs.IJobChangeEvent event) {
							sync.asyncExec(() -> {
								runningTask--;
								event.getJob().removeJobChangeListener(this);
							});
						};
					});
				}
				return monitor;
			}
		});
	}

//	public InputStream getImageInputStream(String pluginUri, String imagePath) {
//		int type = determineFileType(imagePath);
//		
//		Image img = getImage(pluginUri, imagePath);
//		
//		ImageLoader imageLoader = new ImageLoader();
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		imageLoader.data = new ImageData[] {img.getImageData()};
//		imageLoader.save(baos, type);
//		return new ByteArrayInputStream(baos.toByteArray());
//	}

	public InputStream getImageInputStream(String pluginUri, String imagePath) {
		int type = determineFileType(imagePath);
		return getPrivateImageInputStream(getImage(pluginUri, imagePath), type);
	}

	public InputStream getImageInputStream(String suri) {
		int type = determineFileType(suri);
		return getPrivateImageInputStream(getImage(suri), type);
	}

	private InputStream getPrivateImageInputStream(Image img, int type) {
		ImageLoader imageLoader = new ImageLoader();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		imageLoader.data = new ImageData[] { img.getImageData() };
		imageLoader.save(baos, type);
		return new ByteArrayInputStream(baos.toByteArray());
	}

	private int determineFileType(String filename) {
		String ext = filename.substring(filename.lastIndexOf('.') + 1);
		if (ext.equalsIgnoreCase("gif"))
			return SWT.IMAGE_GIF;
		if (ext.equalsIgnoreCase("ico"))
			return SWT.IMAGE_ICO;
		if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg"))
			return SWT.IMAGE_JPEG;
		if (ext.equalsIgnoreCase("png"))
			return SWT.IMAGE_PNG;
		return SWT.IMAGE_UNDEFINED;
	}

	public void openImageAnalyzer() {
		ImageAnalyzer imageAnalyzer = new ImageAnalyzer();
		imageAnalyzer.open(display);
	}

	public void regiterPreference(Text text, String keyPreference) {
		text.setText(prefs.get(keyPreference, ""));
		addTextListener(text, svalue -> {
			prefs.put(keyPreference, svalue);
		});
	}

	public GridData getGridData() {
		return getGridData(GridData.FILL_HORIZONTAL);
	}

	public GridData getGridData(int style) {
		return new GridData(style);
	}
}
