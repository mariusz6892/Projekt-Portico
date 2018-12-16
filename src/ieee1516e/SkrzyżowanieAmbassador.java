/*
 *   Copyright 2012 The Portico Project
 *
 *   This file is part of portico.
 *
 *   portico is free software; you can redistribute it and/or modify
 *   it under the terms of the Common Developer and Distribution License (CDDL) 
 *   as published by Sun Microsystems. For more information see the LICENSE file.
 *   
 *   Use of this software is strictly AT YOUR OWN RISK!!!
 *   If something bad happens you do not have permission to come crying to me.
 *   (that goes for your lawyer as well)
 *
 */
package ieee1516e;

import hla.rti1516.jlc.HLAASCIIstring;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.FederateHandleSet;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandle;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.SynchronizationPointFailureReason;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.HLAinteger16BE;
import hla.rti1516e.encoding.HLAinteger32BE;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.time.HLAfloat64Time;

/**
 * This class handles all incoming callbacks from the RTI regarding a particular
 * {@link ExampleFederate}. It will log information about any callbacks it
 * receives, thus demonstrating how to deal with the provided callback information.
 */
public class Skrzy¿owanieAmbassador extends NullFederateAmbassador
{
	//----------------------------------------------------------
	//                    STATIC VARIABLES
	//----------------------------------------------------------

	//----------------------------------------------------------
	//                   INSTANCE VARIABLES
	//----------------------------------------------------------
	private Skrzy¿owanieFederate federate;

	// these variables are accessible in the package
	protected double federateTime        = 0.0;
	protected double federateLookahead   = 1.0;
	
	protected boolean isRegulating       = false;
	protected boolean isConstrained      = false;
	protected boolean isAdvancing        = false;
	
	protected boolean isAnnounced        = false;
	protected boolean isReadyToRun       = false;
	
	private int nr_samochodu;
	private int nr_sygnalizatora;
	private int kierunek_jazdy;

	//----------------------------------------------------------
	//                      CONSTRUCTORS
	//----------------------------------------------------------

	public Skrzy¿owanieAmbassador(Skrzy¿owanieFederate federate )
	{
		this.federate = federate;
	}

	//----------------------------------------------------------
	//                    INSTANCE METHODS
	//----------------------------------------------------------
	private void log( String message )
	{
		System.out.println( "FederateAmbassador: " + message );
	}
	

	private int decodeint( byte[] bytes )
	{
		HLAinteger32BE value = federate.encoderFactory.createHLAinteger32BE();
		// decode
		try
		{
			value.decode( bytes );
			return value.getValue();
		}
		catch( DecoderException de )
		{
			de.printStackTrace();
			return 0;
		}
	}
	
	private Boolean decodeboolean( byte[] bytes )
	{
		hla.rti1516e.encoding.HLAboolean value = federate.encoderFactory.createHLAboolean();
		// decode
		try
		{
			value.decode( bytes );
			return value.getValue();
		}
		catch( DecoderException de )
		{
			de.printStackTrace();
			return null;
		}
	}

	//////////////////////////////////////////////////////////////////////////
	////////////////////////// RTI Callback Methods //////////////////////////
	//////////////////////////////////////////////////////////////////////////
	@Override
	public void synchronizationPointRegistrationFailed( String label,
	                                                    SynchronizationPointFailureReason reason )
	{
		log( "Failed to register sync point: " + label + ", reason="+reason );
	}

	@Override
	public void synchronizationPointRegistrationSucceeded( String label )
	{
		log( "Successfully registered sync point: " + label );
	}

	@Override
	public void announceSynchronizationPoint( String label, byte[] tag )
	{
		log( "Synchronization point announced: " + label );
		if( label.equals(Skrzy¿owanieFederate.READY_TO_RUN) )
			this.isAnnounced = true;
	}

	@Override
	public void federationSynchronized( String label, FederateHandleSet failed )
	{
		log( "Federation Synchronized: " + label );
		if( label.equals(Skrzy¿owanieFederate.READY_TO_RUN) )
			this.isReadyToRun = true;
	}

	/**
	 * The RTI has informed us that time regulation is now enabled.
	 */
	@Override
	public void timeRegulationEnabled( LogicalTime time )
	{
		this.federateTime = ((HLAfloat64Time)time).getValue();
		this.isRegulating = true;
	}

	@Override
	public void timeConstrainedEnabled( LogicalTime time )
	{
		this.federateTime = ((HLAfloat64Time)time).getValue();
		this.isConstrained = true;
	}

	@Override
	public void timeAdvanceGrant( LogicalTime time )
	{
		this.federateTime = ((HLAfloat64Time)time).getValue();
		this.isAdvancing = false;
	}

	@Override
	public void discoverObjectInstance( ObjectInstanceHandle theObject,
	                                    ObjectClassHandle theObjectClass,
	                                    String objectName )
	    throws FederateInternalError
	{
		log( "Discoverd Object: handle=" + theObject + ", classHandle=" +
		     theObjectClass + ", name=" + objectName );
	}


/*	public void reflectAttributeValues( ObjectInstanceHandle theObject,
	                                    AttributeHandleValueMap theAttributes,
	                                    byte[] tag,
	                                    OrderType sentOrder,
	                                    TransportationTypeHandle transport,
	                                    SupplementalReflectInfo reflectInfo )
	    throws FederateInternalError
	{
			// just pass it on to the other method for printing purposes
			// passing null as the time will let the other method know it
			// it from us, not from the RTI
			reflectAttributeValues( theObject,
			                        theAttributes,
			                        tag,
			                        sentOrder,
			                        transport,
			                        null,
			                        sentOrder,
			                        reflectInfo );
	}*/


	public void reflectAttributeValues( ObjectInstanceHandle theObject,
	                                    AttributeHandleValueMap theAttributes,
	                                    byte[] tag,
	                                    OrderType sentOrdering,
	                                    TransportationTypeHandle theTransport,
	                                    LogicalTime time,
	                                    OrderType receivedOrdering,
	                                    SupplementalReflectInfo reflectInfo )
	    throws FederateInternalError
	{		
		if (theAttributes.size() == 2) {
			for( AttributeHandle attributeHandle : theAttributes.keySet() )
			{
				// print the attibute handle

				// if we're dealing with Flavor, decode into the appropriate enum value
				if( attributeHandle.equals(federate.nr_sygnalizatoraHandle) )
				{
					nr_sygnalizatora = decodeint(theAttributes.get(attributeHandle));
					federate.sygnalizatory[nr_sygnalizatora] = new Sygnalizator(nr_sygnalizatora);
					federate.Sygnalizator_semafor = true;
				}
				else if( attributeHandle.equals(federate.swiatloHandle) )
				{
					federate.sygnalizatory[nr_sygnalizatora].setZielone(decodeboolean(theAttributes.get(attributeHandle)));
					String kolor;
					if (decodeboolean(theAttributes.get(attributeHandle)))  kolor = "Zielone";
					else kolor = "Czerwone";
					System.out.println("Sygnalizator numer " + nr_sygnalizatora + " zmienia œwiat³o na " + kolor);
				}
				else
				{
				}
				
			}
		}
		if (theAttributes.size() == 4) {
			for( AttributeHandle attributeHandle : theAttributes.keySet() )
			{
				// print the attibute handle
				// if we're dealing with Flavor, decode into the appropriate enum value
				if( attributeHandle.equals(federate.nr_samochoduHandle) )
				{
					nr_samochodu = decodeint(theAttributes.get(attributeHandle));
					federate.samochody[nr_samochodu] = new Samochód(nr_samochodu,false);
				}
				else if( attributeHandle.equals(federate.uprzywilejowanyHandle) )
				{
					federate.samochody[nr_samochodu].setUprzywilejowany(decodeboolean(theAttributes.get(attributeHandle)));
				}
				else if( attributeHandle.equals(federate.kierunek_jazdyHandle) )
				{
					federate.samochody[nr_samochodu].setKierunek_jazdy(decodeint(theAttributes.get(attributeHandle)));
					kierunek_jazdy = decodeint(theAttributes.get(attributeHandle));
				}
				else if( attributeHandle.equals(federate.aktualna_pozycjaHandle) )
				{
					federate.samochody[nr_samochodu].setAktualna_pozycja(decodeint(theAttributes.get(attributeHandle)));
					if (federate.samochody[nr_samochodu].isUprzywilejowany()) {
						federate.drogi[ decodeint(theAttributes.get(attributeHandle))].dodajuprzywilejowany(nr_samochodu, kierunek_jazdy);
						System.out.println("Dodano pojazd uprzywilejowany nr " + nr_samochodu + " do drogi nr " + decodeint(theAttributes.get(attributeHandle)) + " jad¹cy w stronê drogi nr " + kierunek_jazdy);
					}
					else  {
						federate.drogi[ decodeint(theAttributes.get(attributeHandle))].dodaj(nr_samochodu, kierunek_jazdy);
						System.out.println("Dodano pojazd nr " + nr_samochodu + " do drogi nr " + decodeint(theAttributes.get(attributeHandle)) + " jad¹cy w stronê drogi nr " + kierunek_jazdy);
					}
				}
				else
				{
				}
				
			}
		}
	}

	@Override
	public void removeObjectInstance( ObjectInstanceHandle theObject,
	                                  byte[] tag,
	                                  OrderType sentOrdering,
	                                  SupplementalRemoveInfo removeInfo )
	    throws FederateInternalError
	{
		log( "Object Removed: handle=" + theObject );
	}

	//----------------------------------------------------------
	//                     STATIC METHODS
	//----------------------------------------------------------
}
