package es.upm.dit.gsi.slaa.ra;

import javax.slee.*;
import javax.slee.facilities.*;
import javax.slee.resource.*;

public class SocialNetworkResourceAdaptor implements ResourceAdaptor {



	/**
	 * The ResourceAdaptorContext interface is implemented by the SLEE. It 
	 * provides the Resource Adaptor with the required capabilities in the SLEE
	 * to execute its work. The ResourceAdaptorContext object holds references
	 * to a number of objects that are of interest to many Resource Adaptors.
	 * A resource adaptor object is provided with a ResourceAdaptorContext
	 * object when the setResourceAdaptorContext method of the ResourceAdaptor
	 * interface is invoked on the resource adaptor object.
	 */
	private ResourceAdaptorContext raContext;

	/**
	 * The SLEE Endpoint defines the interface used by a Resource Adaptor to
	 * start and end Activities within the SLEE, fire Events on those
	 * Activities, and to suspend an Activity. A SLEE must implement the
	 * SLEEEndpoint interface to be both multi-thread safe and reentrant safe.
	 */
	private SleeEndpoint sleeEndpoint;
	
	/**
	 * Resource Adaptors access the Event Lookup Facility through an
	 * EventLookupFacility object that implements the EventLookupFacility
	 * interface. An EventLookupFacility object can be obtained by a resource
	 * adaptor entity via its ResourceAdaptorContext.
	 */
	private EventLookupFacility eventLookupFacility;
	
	/**
	 * A tracer is represented in the SLEE by the Tracer interface.
	 * Notification sources access the Tracer Facility through a Tracer object
	 * that implements the Tracer interface. A Tracer object can be obtained by
	 * SBBs via the SbbContext interface, by resource adaptor entities via the
	 * ResourceAdaptorContext interface, and by profiles via the ProfileContext
	 * interface.
	 */
	private Tracer tracer;

	/**
	 * A Marshaler is used by the SLEE to convert Events and Activity Handles
	 * between object and marshaled forms. If a Resource Adaptor does not 
	 * provide an implementation of the Marshaler interface then the Resource 
	 * Adaptor is not permitted to use the SLEE_MAY_MARSHAL flag of the 
	 * ActivityFlags and EventFlags class when starting Activities or firing
	 * Events.
	 */
	private Marshaler marshaler = new SocialNetworkMarshaler();

	/**
	 * for all events we are interested in knowing when the event failed to be processed
	 */
	public static final int DEFAULT_EVENT_FLAGS = EventFlags.REQUEST_PROCESSING_FAILED_CALLBACK;

	public static final int UNREFERENCED_EVENT_FLAGS = EventFlags.setRequestEventReferenceReleasedCallback(DEFAULT_EVENT_FLAGS);

	public static final int NON_MARSHABLE_ACTIVITY_FLAGS = ActivityFlags.REQUEST_ENDED_CALLBACK;//.NO_FLAGS;
	
	public static final int MARSHABLE_ACTIVITY_FLAGS = ActivityFlags.setSleeMayMarshal(NON_MARSHABLE_ACTIVITY_FLAGS);
	
	/**
	 * Default constructor
	 */
	public SocialNetworkResourceAdaptor() {
		// TODO: Set default values here
	}

	// Lifecycle methods ------------------------------------------------------

	public void setResourceAdaptorContext(ResourceAdaptorContext context) {
		this.raContext = context;
		this.tracer = context.getTracer("SocialNetworkResourceAdaptor");
		this.sleeEndpoint = context.getSleeEndpoint();
		this.eventLookupFacility = context.getEventLookupFacility();
	}

	public void unsetResourceAdaptorContext() {
		this.raContext = null;
		this.tracer = null;
		this.sleeEndpoint = null;
		this.eventLookupFacility = null;
	}

	public void raConfigure(ConfigProperties properties) {
		// TODO: inspect the configuration properties provided to determine any
		//       appropriate internal action necessary to realize the requested
		//       configuration.

		if (tracer.isFineEnabled()) {
			tracer.fine("SocialNetwork Resource Adaptor configured.");
		}
	}
	
	public void raUnconfigure() {
		// TODO: release any system resources it has allocated in the
		//       corresponding raConfigure method.

		if (tracer.isFineEnabled()) {
			tracer.fine("SocialNetwork Resource Adaptor unconfigured.");
		}
	}
	
	public void raActive() {
		// TODO: allocate any system resources required to allow the resource
		//       adaptor to interact with the underlying resource.

		if (tracer.isFineEnabled()) {
			tracer.fine("SocialNetwork Resource Adaptor entity active.");
		}   
	}		

	public void raStopping() {
		// TODO: update any internal state required such that it does not
		//       attempt to start any new Activities once this method returns

		if (tracer.isFineEnabled()) {
			tracer.fine("SocialNetwork Resource Adaptor entity stopping.");
		}
	}

	public void raInactive() {
		// TODO: deallocate any system resources allocated to allow the resource
		//       adaptor to interact with the underlying resource.

		if (tracer.isFineEnabled()) {
			tracer.fine("SocialNetwork Resource Adaptor entity inactive.");
		}	   
	}

	// Configuration Management -----------------------------------------------

	public void raVerifyConfiguration(ConfigProperties properties) throws InvalidConfigurationException {
		// TODO: verify that the configuration properties specified by the
		//       Administrator for a resource adaptor entity of the Resource
		//       Adaptor are valid. During this method the invoked resource
		//       adaptor object should inspect the configuration properties it
		//       is provided with to determine their validity. 
		//       If the configuration properties are considered valid, this
		//       method should return silently. If the configuration properties
		//       are considered invalid, this method should throw an 
		//       InvalidConfigurationException with an appropriate message.

		if (tracer.isFineEnabled()) {
			tracer.fine("SocialNetwork Resource Adaptor configuration verified.");
		}	   
	}

	public void raConfigurationUpdate(ConfigProperties properties) {
		raConfigure(properties);		
	}

	// Interface Access -------------------------------------------------------

	public Object getResourceAdaptorInterface(String className) {
		if("es.upm.dit.gsi.slaa.ratype.SocialNetworkRAProvider".equals(className)) {
			return new SocialNetworkRAProviderImpl();
		}

		return null;
	}

	public Marshaler getMarshaler() {
		return marshaler; // return null if no marshaler is to be used.
	}   

	// Event Filter -----------------------------------------------------------

	public void serviceActive(ReceivableService receivableService) {
		// TODO: update any internal state related to event filters as required
	}

	public void serviceStopping(ReceivableService receivableService) {
		// TODO: update any internal state related to event filters as required
	}

	public void serviceInactive(ReceivableService receivableService) {
		// TODO: update any internal state related to event filters as required
	}

	// Mandatory Callbacks ----------------------------------------------------

	public void queryLiveness(ActivityHandle handle) {
		// TODO: check the underlying resource to see if the Activity is still
		//       active. For example, it may be possible that ending of an 
		//       Activity did not occur due to message loss in the SLEE. In 
		//       this case the SLEE would retain the Activity Context of the
		//       Activity and SBBs attached to the Activity Context. If the 
		//       Activity is not alive the Resource Adaptor is expected to end
		//       the activity (via the SleeEndpoint interface). If the Activity
		//       is still alive the Resource Adaptor is not expected to do
		//       anything.
	}

	public Object getActivity(ActivityHandle handle) {
		// TODO: return a non-null object, to provide access to the Activity
		//       object for an Activity Handle.
		return null; // FIXME
	}
	
	public ActivityHandle getActivityHandle(Object activity) {
		// TODO: return an Activity Handle for an Activity object.
		return null; // FIXME
	}

	public void administrativeRemove(ActivityHandle handle) {
		// TODO: remove any internal state related to the Activity.
		//       Additionally may perform a protocol level operation to clean
		//       up any protocol-peer related state.
	}

	// Optional Callbacks -----------------------------------------------------

	public void eventProcessingSuccessful(ActivityHandle handle, FireableEventType eventType, Object event, Address address, ReceivableService service, int flags) {
		// used to inform the resource adaptor object that the specified Event 
		// was processed successfully by the SLEE.
	}

	public void eventProcessingFailed(ActivityHandle handle, FireableEventType eventType, Object event, Address address, ReceivableService service, int flags, FailureReason reason) {
		// used to inform the resource adaptor object that the specified Event 
		// could not be processed successfully by the SLEE.
	}

	public void eventUnreferenced(ActivityHandle handle, FireableEventType eventType, Object event, Address address, ReceivableService service, int flags) {
		// used to inform the Resource Adaptor that the SLEE no longer
		// references an Event object which was previously fired by the
		// resource adaptor object.
	}

	public void activityEnded(ActivityHandle handle) {
		// TODO: release any resources related to this Activity as the SLEE
		//       will not ask for it again.
	}

	public void activityUnreferenced(ActivityHandle handle) {
		// TODO: decide to end the Activity at this time if desired.
	}

	// TODO: Add SocialNetwork Resource Adaptor specific logic.
}
