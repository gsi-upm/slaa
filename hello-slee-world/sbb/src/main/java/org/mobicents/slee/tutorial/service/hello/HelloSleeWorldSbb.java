package org.mobicents.slee.tutorial.service.hello;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.slee.*;
import javax.slee.facilities.Tracer;

import net.java.client.slee.resource.http.HttpClientActivity;
import net.java.client.slee.resource.http.HttpClientActivityContextInterfaceFactory;
import net.java.client.slee.resource.http.HttpClientResourceAdaptorSbbInterface;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.mobicents.slee.*;

public abstract class HelloSleeWorldSbb implements Sbb{
	
	Tracer tracer;
	private HttpClientResourceAdaptorSbbInterface httpSbbInterface;
	private HttpClientActivityContextInterfaceFactory httpActivityContextInterfaceFactory;
	
	public void onServiceStartedEvent(javax.slee.serviceactivity.ServiceStartedEvent event, ActivityContextInterface aci/*, EventContext eventContext*/) {
		tracer.info("Hello SLEE HTTP World!");
		
		HttpGet request = new HttpGet("http://meneame.net");
		try {
		HttpClientActivity clientActivity = httpSbbInterface
		.createHttpClientActivity(true,null);
		ActivityContextInterface clientAci = httpActivityContextInterfaceFactory
		.getActivityContextInterface(clientActivity);
		clientAci.attach(sbbContext.getSbbLocalObject());
		clientActivity.execute(request,"http://meneame.net");
		} catch (Throwable e) {
		tracer.severe("Error while creating HttpClientActivity",e);
		}

	}


	
	// TODO: Perform further operations if required in these methods.
	public void setSbbContext(SbbContext context) { 
		this.sbbContext = (SbbContextExt) context; 
		tracer = this.getSbbContext().getTracer(this.getClass().getSimpleName());
		
		try {
			Context myEnv = (Context) new InitialContext()
			.lookup("java:comp/env");
			httpSbbInterface = (HttpClientResourceAdaptorSbbInterface) myEnv
			.lookup("slee/resources/http-client/sbbinterface");
			httpActivityContextInterfaceFactory = (HttpClientActivityContextInterfaceFactory) myEnv
			.lookup("slee/resources/http-client/acifactory");

			} catch (NamingException ne) {
			tracer.severe("Could not set SBB context:" + ne.getMessage(), ne);
			}

		
		}
	public void unsetSbbContext() { this.sbbContext = null; }

	// TODO: Implement the lifecycle methods if required
	public void sbbCreate() throws javax.slee.CreateException {}
	public void sbbPostCreate() throws javax.slee.CreateException {}
	public void sbbActivate() {}
	public void sbbPassivate() {}
	public void sbbRemove() {}
	public void sbbLoad() {}
	public void sbbStore() {}
	public void sbbExceptionThrown(Exception exception, Object event, ActivityContextInterface activity) {}
	public void sbbRolledBack(RolledBackContext context) {}
	

	
	/**
	 * Convenience method to retrieve the SbbContext object stored in setSbbContext.
	 * 
	 * TODO: If your SBB doesn't require the SbbContext object you may remove this 
	 * method, the sbbContext variable and the variable assignment in setSbbContext().
	 *
	 * @return this SBB's SbbContext object
	 */
	
	protected SbbContextExt getSbbContext() {
		return sbbContext;
	}

	private SbbContextExt sbbContext; // This SBB's SbbContext

	public void onResponseEvent(net.java.client.slee.resource.http.event.ResponseEvent event, ActivityContextInterface aci/*, EventContext eventContext*/) {
		HttpResponse response = event.getHttpResponse();
		tracer.info("********** onResponseEvent **************");
		tracer.info("URI = " + event.getRequestApplicationData());
		tracer.info("Status Code = " +
		response.getStatusLine().getStatusCode());
		try {
		tracer.info("Response Body = "
				+ EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
		tracer.severe("Failed reading response body", e);
		}
		tracer.info("*****************************************");

	}

}
