/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API, implementation and documentation
 *    mwenz - Bug 325084 - Provide documentation for Patterns
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.graphiti.pattern.mapping.data;

import org.eclipse.graphiti.features.IMappingProvider;
import org.eclipse.graphiti.internal.Messages;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;
import org.eclipse.graphiti.pattern.mapping.MappingProviderProvider;


/**
 * The Class DataMapping.
 * 
 * @experimental This API is in an experimental state and should be used by
 *               clients, as it not final and can be removed or changed without
 *               prior notice!
 * 
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public abstract class DataMapping extends MappingProviderProvider implements IDataMapping {

	private static final String DEFAULT_UPDATE_WARNING = Messages.DataMapping_0_xmsg;

	/**
	 * Creates a new {@link DataMapping}.
	 * 
	 * @param mappingProvider
	 *            the mapping provider
	 */
	public DataMapping(IMappingProvider mappingProvider) {
		super(mappingProvider);
	}

	public String getUpdateWarning(PictogramLink pictogramLink) {
		return DEFAULT_UPDATE_WARNING;
	}

	/**
	 * Gets the business object.
	 * 
	 * @param link
	 *            the pictogram link
	 * 
	 * @return the business object
	 */
	protected Object getBusinessObject(PictogramLink link) {
		final IMappingProvider mp = getMappingProvider();
		final PictogramElement pe = link.getPictogramElement();
		return mp.getBusinessObjectForPictogramElement(pe);
	}
}
