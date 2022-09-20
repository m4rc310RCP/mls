package com.m4rc310.rcp.graphql.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

import com.m4rc310.rcp.graphql.constants.MConst;
import com.m4rc310.rcp.ui.utils.PartControl;

public class HandlerLogin implements MConst {
	
	@Inject @Optional PartControl pc;
	
	@Execute
	public void execute(IEventBroker broker) {
		System.out.println(pc);
	}
}
