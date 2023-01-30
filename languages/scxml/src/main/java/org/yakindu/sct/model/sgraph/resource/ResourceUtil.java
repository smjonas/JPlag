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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yakindu.base.SGraphPackage;
import org.yakindu.sct.model.sgraph.Statechart;

import java.io.File;
import java.util.Map;

/**
 *
 * @author andreas muelder - Initial contribution and API
 *
 */
public class ResourceUtil {

	private static final Logger logger = LoggerFactory.getLogger(ResourceUtil.class);

	public static Statechart loadStatechart(File file) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		EPackage.Registry.INSTANCE.put("http://www.yakindu.org/sct/sgraph/2.0.0", SGraphPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put("http://www.eclipse.org/gmf/runtime/1.0.2/notation", NotationPackage.eINSTANCE);

		try {
			Resource resource = resourceSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);
			return (Statechart) EcoreUtil.getObjectByType(resource.getContents(), SGraphPackage.Literals.STATECHART);
		} catch (WrappedException exception) {
			logger.error("Could not load {}: {}", file, exception.getCause().getMessage());
		}
		return null;
	}
}
