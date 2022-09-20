package com.m4rc310.rcp.graphql.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.framework.FrameworkUtil;

import com.m4rc310.rcp.graphql.constants.MConst;

public class GraphQLPreferenceInitializer extends AbstractPreferenceInitializer implements MConst {

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = DefaultScope.INSTANCE.getNode(FrameworkUtil.getBundle(getClass()).getSymbolicName());
		if(node!=null) {
			node.putBoolean(PREFERENCE$graphql_is_local_server, true);
			node.put(PREFERENCE$graphql_url_local_server, "http://localhost:8080");
			node.put(PREFERENCE$graphql_url_server, "https://corumbatai.herokuapp.com");
			
			node.put(PREFERENCE$graphql_url_websocket, "ws://corumbatai.herokuapp.com/ws/websocket");
			node.put(PREFERENCE$graphql_url_local_websocket, "ws://localhost:8080/ws/websocket");
			try {
				node.flush();
			} catch (Exception e) {
			}
		}
	}

}
