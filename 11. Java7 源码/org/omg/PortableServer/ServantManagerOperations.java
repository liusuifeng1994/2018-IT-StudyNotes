package org.omg.PortableServer;


/**
* org/omg/PortableServer/ServantManagerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ../../../../src/share/classes/org/omg/PortableServer/poa.idl
* Wednesday, December 21, 2011 1:04:11 AM PST
*/


/**
	 * A servant manager supplies a POA with the ability 
	 * to activate objects on demand when the POA receives 
	 * a request targeted at an inactive object. A servant 
	 * manager is registered with a POA as a callback object, 
	 * to be invoked by the POA when necessary.
	 * ServantManagers can either be ServantActivators or
	 * ServantLocators. A ServantManager object must be 
	 * local to the process containing the POA objects 
	 * it is registered with.
	 */
public interface ServantManagerOperations 
{
} // interface ServantManagerOperations
