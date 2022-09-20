package com.m4rc310.rcp.graphql.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import javax.inject.*;

import com.m4rc310.rcp.graphql.constants.MConst;

public class DialogLogin extends Dialog implements MConst{

	@Inject
	public DialogLogin(Shell parentShell) {
		super(parentShell);
	}

}
