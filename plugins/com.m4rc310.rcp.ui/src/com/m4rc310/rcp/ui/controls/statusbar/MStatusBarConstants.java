package com.m4rc310.rcp.ui.controls.statusbar;

public interface MStatusBarConstants {
	public enum TypeMessage{
		INFO("platform:/plugin/com.m4rc310.rcp.ui/icons/16x16/others/bell.png"),
		NONE("platform:/plugin/com.m4rc310.rcp.ui/icons/16x16/others/shading.png"),
		ERROR("platform:/plugin/com.m4rc310.rcp.ui/icons/16x16/others/error.png");
		
		private final String PATH;
		
		private TypeMessage(String path) {
			this.PATH = path;
		}
		
		public String getIconUri() {
			return PATH;
		}
	}
	
	public static final String ID$toolcontrol_streaming = "com.m4rc310.rcp.ui.toolcontrol.streaming";
	
	public static final String NAMED$statusbar_streaming_icon = "statusbar_streaming_icon";
	public static final String NAMED$statusbar_streaming_message = "statusbar_streaming_message";
	public static final String NAMED$statusbar_streaming_message_color = "statusbar_streaming_message_color";
	
	public static final String EVENT_TOPIC$statusbar_alert_icon = "event_topic_statusbar_alert_icon";
	public static final String EVENT_TOPIC$statusbar_alert_message = "event_topic_statusbar_alert_message";
}
