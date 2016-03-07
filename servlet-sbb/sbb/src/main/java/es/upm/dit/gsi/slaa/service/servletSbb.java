package es.upm.dit.gsi.slaa.service;

import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.slee.*;
import javax.slee.facilities.Tracer;

import net.java.slee.resource.http.HttpServletRaActivityContextInterfaceFactory;
import net.java.slee.resource.http.HttpServletRaSbbInterface;

import org.mobicents.slee.*;

public abstract class servletSbb implements Sbb, servlet {
	
	Tracer tracer;
	private HttpServletRaSbbInterface httpSbbInterface;
	private HttpServletRaActivityContextInterfaceFactory httpActivityContextInterfaceFactory;

	public void onServiceStartedEvent(javax.slee.serviceactivity.ServiceStartedEvent event, ActivityContextInterface aci/*, EventContext eventContext*/) {
	    tracer.info("SERVLET SBB CREATED");
	}

	public void onRequestGET(net.java.slee.resource.http.events.HttpServletRequestEvent event, ActivityContextInterface aci/*, EventContext eventContext*/) {
		// detach from HttpServletRequestActivity
		aci.detach(sbbContext.getSbbLocalObject());
		HttpServletResponse response = event.getResponse();
		try {
		PrintWriter w = response.getWriter();
		w.print("onGet OK! Served by SBB = ");
		w.flush();
		response.flushBuffer();
		tracer
		.info("HttpServletRAExampleSbb: GET Request received and OK! response sent.");
		} catch (Exception e) {
		tracer.info("ERROR!"+e);
		}
		
	}

	public void onSessionGET(net.java.slee.resource.http.events.HttpServletRequestEvent event, ActivityContextInterface aci/*, EventContext eventContext*/) {
		tracer.info("onSessionGET");
	}


	
	// TODO: Perform further operations if required in these methods.
	public void setSbbContext(SbbContext context) { 
		this.sbbContext = (SbbContextExt) context; 
		tracer = this.getSbbContext().getTracer(this.getClass().getSimpleName());
		
		try {
			Context myEnv = (Context) new InitialContext()
			.lookup("java:comp/env");
			httpSbbInterface = (HttpServletRaSbbInterface) myEnv
			.lookup("slee/resources/mobicents/httpservlet/sbbrainterface");
			httpActivityContextInterfaceFactory = (HttpServletRaActivityContextInterfaceFactory) myEnv
			.lookup("slee/resources/mobicents/httpservlet/acifactory");

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
	 * Method to retrieve a named SBB Usage Parameter set.
	 * @param name the SBB Usage Parameter set to retrieve
	 * @return the SBB Usage Parameter set
	 * @throws javax.slee.usage.UnrecognizedUsageParameterSetNameException if the named parameter set does not exist
	 */

	public abstract es.upm.dit.gsi.slaa.service.servletSbbUsage getSbbUsageParameterSet(String name) throws javax.slee.usage.UnrecognizedUsageParameterSetNameException;

	/**
	 * Method to retrieve the default SBB usage parameter set.
	 * @return the default SBB usage parameter set
	 */

	public abstract es.upm.dit.gsi.slaa.service.servletSbbUsage getDefaultSbbUsageParameterSet();

	
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

}
