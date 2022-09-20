
package com.m4rc310.rcp.graphql.addons;

import java.util.Date;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;

import com.m4rc310.rcp.graphql.constants.MConst;
import com.m4rc310.rcp.ui.utils.PartControl;

public class AddonStartup implements MConst  {
	
	@Inject
	@Preference(value = "refresh_token")
	private String refreshToken;
	
	@Inject
	@Preference(value = "token_expires_in")
	private Date dateTokenExpiresIn;
	
//	private boolean oldStatus = false;

//	@PrincipalMode
//	public void start(@Optional MInject inject) {
//		inject.invoke(MTest.class);
//	}
	
//	@PrincipalMode
//	public void startup(MGraphQL gql, MTrimmedWindow window, MInject inject) {
//		inject.invoke(MGraphQLConnection.class);
//		CronUtils.cronExecute("0/5 * * * * *", e -> {
//			if (oldStatus != gql.isServerAvaliable()) {
//				oldStatus = gql.isServerAvaliable();
//				inject.invoke(MGraphQLConnection.class);
//			}
//		});
//	}
	
//	@MAppStartup
	public void init(@Optional PartControl pc) {
		System.out.println("graphql");
		System.out.println(pc);
	}
	
//	@Inject @Optional
//	public void appStartup(@EventTopic("app_startup") Date date, @Optional PartControl pc) {
//		System.out.println(pc);
//	}
//	
//	@Inject
//	@Optional
//	public void authentication(@EventTopic(TOPIT$authenticate) int action, @Optional PartControl pc) {
//		System.out.println(pc);
//	}
	
}
