/** 
 * Copyright (c) 2015 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.model.sgraph.resource;

import de.jplag.yakindu.Language;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.yakindu.base.SGraphPackage;
import org.yakindu.sct.model.sgraph.Statechart;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * 
 */
public class ResourceUtil {

	public static Resource loadResource(String filename) {
		URI uri = URI.createPlatformResourceURI(filename, true);
		ResourceSet resourceSet = new ResourceSetImpl();
		String extension = Language.FILE_ENDING.substring(1);
		Resource resource = resourceSet.createResource(uri, extension);
		resourceSet.getResource(uri, true);
		assert resource != null;
		resourceSet.getResources().add(resource);
		try {
			resource.load(Collections.EMPTY_MAP);
			return resource;
		} catch (IOException e) {
			throw new IllegalStateException("Error loading resource", e);
		}
	}

	public static Statechart loadStatechart(File file) {
		Resource resource = loadResource(file.getAbsolutePath());
		return (Statechart) EcoreUtil.getObjectByType(
				resource.getContents(), SGraphPackage.Literals.STATECHART);
	}

}
