package com.m4rc310.rcp.ui.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.quartz.CronExpression;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@Creatable
@Singleton
public class CronUtils {

	// https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm

	// private ThreadPoolTaskScheduler scheduler;
	
	private Map<String, ThreadPoolTaskScheduler> maps = new HashMap<>();
	
	@Inject
	public CronUtils() {
		Properties prop = new Properties();
		prop.setProperty("log4j.rootLogger", "WARN");
		PropertyConfigurator.configure(prop);
	}

	/**
	 * Register a cron action
	 * 
	 * @deprecated This method is no longer acceptable to compute time between
	 *             versions.
	 *             <p>
	 *             Use {@link CronUtils#cronExecute(CronExpression, Runnable)}
	 *             instead.
	 *
	 */
	@Deprecated
	public ThreadPoolTaskScheduler cron(String cron, Runnable run) {
		try {
			ThreadPoolTaskScheduler scheduler = createTaskScheduler(5);
			scheduler.initialize();
			scheduler.schedule(run, new CronTrigger(cron));
			return scheduler;
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public void cronExecute(String id, Date date, MCronListener listener) {
		String cron = toCron(date);
		cronExecute(id, cron, listener);
	}
	
	public void cronExecute(String id, String cron, MCronListener listener) {
		if (maps.containsKey(id)) {
			maps.get(id).destroy();
			
			try {
				ThreadPoolTaskScheduler scheduler = createTaskScheduler(5);
				scheduler.setDaemon(true);
//				scheduler.initialize();
				
				Runnable run = () -> {
					listener.eventChange(new MCronEvent(scheduler));
				};
				
				maps.put(id, scheduler);
				scheduler.schedule(run, new CronTrigger(cron));
			} catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}else {
			try {
				ThreadPoolTaskScheduler scheduler = createTaskScheduler(5);
				scheduler.setDaemon(true);
//				scheduler.initialize();
				
				Runnable run = () -> {
					listener.eventChange(new MCronEvent(scheduler));
				};
				
				maps.put(id, scheduler);
				scheduler.schedule(run, new CronTrigger(cron));
			} catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}
	
	public void cancel(String id) {
		if (maps.containsKey(id)) {
			maps.get(id).destroy();
		}
	}
	
	
	public static void cronExecute(Date date, MCronListener listener) {
		cronExecute(toCron(date), listener);
	}

	public static void cronExecute(String cron, MCronListener listener) {
		try {
			ThreadPoolTaskScheduler scheduler = createTaskScheduler(5);
			scheduler.setDaemon(false);
			scheduler.initialize();
			
			Runnable run = () -> {
				listener.eventChange(new MCronEvent(scheduler));
			};

			scheduler.schedule(run, new CronTrigger(cron));
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public static String toCron(final Date date) {
		DateFormat format = new SimpleDateFormat("ss m H dd M '?'", Locale.US);
		return format.format(date);
//		String format = "{0,date,ss m H dd M EEE}";
//		return MessageFormat.format(format, date);
	}

	public static String toCron(final String mins, final String hrs, final String dayOfMonth, final String month,
			final String dayOfWeek, final String year) {
		return String.format("%s %s %s %s %s %s", mins, hrs, dayOfMonth, month, dayOfWeek, year);
	}

	public void shutdown() {
		// scheduler.shutdown();

	}

	public static void main(String[] args) {
		new CronUtils().test();
	}

	private void test() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 5);

		String cron = toCron(cal.getTime());

		cronExecute(cron, e -> {
			e.stop();
			test();
		});
	}

	public static ThreadPoolTaskScheduler createTaskScheduler(int poolSize) {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(poolSize);

		scheduler.setRejectedExecutionHandler(new CallerRunsPolicy());
		scheduler.afterPropertiesSet();
		return scheduler;
	}

	public class MyRunnable extends Thread {
		public Thread runn() {
			super.run();
			return this;
		}
	}

}
