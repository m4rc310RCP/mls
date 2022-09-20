
package com.m4rc310.rcp.graphql.preferences;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.graphql.actions.ActionGraphQL;
import com.m4rc310.rcp.graphql.actions.ActionGraphQLParams;
import com.m4rc310.rcp.graphql.constants.MConst;
import com.m4rc310.rcp.graphql.i18n.M;
import com.m4rc310.rcp.ui.utils.PartControl;

public class GraphQLPreferences extends FieldEditorPreferencePage implements MConst, ActionGraphQLParams {

	@Inject
	@Translation
	private M m;

	@Inject
	PartControl pc;

	@Inject
	ActionGraphQL action;

	@Inject
	@Optional
	private IEventBroker eventBroker;

	@Inject
	MGraphQL gql;

	@Inject
	@Preference(value = PREFERENCE$name_station)
	private String nameStation;

	public GraphQLPreferences() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		Composite parent = pc.getComposite(getFieldEditorParent());
		parent.setLayout(new GridLayout(1, false));
//		pc.fillHorizontalComponent(group);

		Group group = pc.getGroup(parent);
		group.setLayout(new GridLayout(2, false));
		group.setText(m.preferenceTitleServer);
		pc.fillHorizontalComponent(group);
//		pc.clearMargins(group, getFieldEditorParent());

		BooleanFieldEditor isLocalServer = new BooleanFieldEditor(PREFERENCE$graphql_is_local_server,
				m.preferenceIsLocalServer, group);
		addField(isLocalServer);

//		Composite c = pc.getComposite(group);
//		addField(new SpacerFieldEditor(group));
		addField(new StringFieldEditor(PREFERENCE$graphql_url_local_server, m.preferenceUrlLocalServer, group));
		addField(new StringFieldEditor(PREFERENCE$graphql_url_server, m.preferenceUrlServer, group));
		addField(new SpacerFieldEditor(group));
		addField(new StringFieldEditor(PREFERENCE$graphql_url_local_websocket, m.preferenceUrlLocalWebsocket, group));
		addField(new StringFieldEditor(PREFERENCE$graphql_url_websocket, m.preferenceUrlWebsocket, group));

//		group = pc.getGroup(parent);
//		group.setLayout(new GridLayout(1, false));
//		group.setText("Parâmetros de configuração");
//		pc.fillHorizontalComponent(group);
//
//		Composite stack = pc.getStackComposite(group);
//		stack.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

//		Composite compositeWait = pc.getComposite(stack);
//		makeCompositeWait(compositeWait);

//		Composite compositeRequestParams = pc.getComposite(stack);
//		makeCompositeRequestParams(compositeRequestParams);

//		Composite compositeConnectionParams = pc.getComposite(stack);
//		makeCompositeConnectionParams(compositeConnectionParams);

		action.addListener(this, EVENT$on_error, e -> {
			setErrorMessage(e.getValue(String.class));
		});

		action.addListener(this, EVENT$change_to_wait, e -> {
			setMessage("Waiting...");
		});

//		pc.toTopControl(compositeRequestParams);

//		addField(new SpacerFieldEditor(group));
	}

	@SuppressWarnings("unused")
	private void makeCompositeWait(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		Label label = pc.getLabel(parent, "Waiting...", SWT.NONE | SWT.CENTER);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
//		label.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	@SuppressWarnings("unused")
	private void makeCompositeRequestParams(Composite parent_) {
		parent_.setLayout(new GridLayout(1, false));

		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(4, false));

		Label label = pc.getLabel(parent, "PIM:");

		Text textUser = pc.getText(parent, "");
		pc.setWidth(textUser, 10);

		Text textToken = pc.getText(parent, "", SWT.BORDER | SWT.CENTER | SWT.PASSWORD);
		pc.setWidth(textToken, 10);
		pc.groupControl(textToken, label, textUser);

		Button buttonRequestParams = pc.getButton(parent, "Obter Parâmetros", e -> {
			action.requestParams(textUser.getText(), textToken.getText());
		});
		Button buttonTest = pc.getButton(parent, "Test", e -> {
			String query = "{test_server}";
			java.util.Optional<String> data;
			try {
				data = gql.query(query).getData("test_server", String.class);
				if (data.isPresent()) {
					System.out.println(data.get());
				}
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		action.addListener(this, EVENT$on_error, e -> {
			pc.enabled(true, buttonRequestParams, textToken);
			pc.grabFocus(textToken);
		});

		action.addListener(this, EVENT$change_to_wait, e -> {
			pc.enabled(false, buttonRequestParams, textToken);
		});

//		pc.getButton(parent, "Update", e->{
//			getContainer().updateTitle();
//		});
	}

	@SuppressWarnings("unused")
	private void makeCompositeConnectionParams(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
	}

	@Override
	public boolean performCancel() {
		action.removeListeners(this);
		return super.performCancel();
	}

	@Override
	protected void performApply() {
		super.performApply();
		action.removeListeners(this);
		eventBroker.send("refresh_title", nameStation);
	}

}
