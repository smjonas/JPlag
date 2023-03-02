/**
 * Copyright (c) 2011 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.model.sgraph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.yakindu.base.types.Declaration;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scope</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Scope#getDeclarations <em>Declarations</em>}</li>
 *   <li>{@link Scope#getEvents <em>Events</em>}</li>
 *   <li>{@link Scope#getVariables <em>Variables</em>}</li>
 *   <li>{@link Scope#getReactions <em>Reactions</em>}</li>
 *   <li>{@link Scope#getMembers <em>Members</em>}</li>
 * </ul>
 *
 * @see org.yakindu.sct.model.sgraph.SGraphPackage#getScope()
 * @model
 * @generated
 */
public interface Scope extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) 2011 committers of YAKINDU and others.\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\nContributors:\r\ncommitters of YAKINDU - initial API and implementation\r\n";

    /**
     * Returns the value of the '<em><b>Declarations</b></em>' reference list.
     * The list contents are of type {@link org.yakindu.base.types.Declaration}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Declarations</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Declarations</em>' reference list.
     * @see org.yakindu.sct.model.sgraph.SGraphPackage#getScope_Declarations()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    EList<Declaration> getDeclarations();

    /**
     * Returns the value of the '<em><b>Events</b></em>' reference list.
     * The list contents are of type {@link org.yakindu.base.types.Event}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Events</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Events</em>' reference list.
     * @see org.yakindu.sct.model.sgraph.SGraphPackage#getScope_Events()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    EList<Event> getEvents();

    /**
     * Returns the value of the '<em><b>Variables</b></em>' reference list.
     * The list contents are of type {@link org.yakindu.base.types.Property}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Variables</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Variables</em>' reference list.
     * @see org.yakindu.sct.model.sgraph.SGraphPackage#getScope_Variables()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    EList<Property> getVariables();

    /**
     * Returns the value of the '<em><b>Reactions</b></em>' reference list.
     * The list contents are of type {@link Reaction}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Reactions</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Reactions</em>' reference list.
     * @see org.yakindu.sct.model.sgraph.SGraphPackage#getScope_Reactions()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    EList<Reaction> getReactions();

    /**
     * Returns the value of the '<em><b>Members</b></em>' containment reference list.
     * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Members</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Members</em>' containment reference list.
     * @see org.yakindu.sct.model.sgraph.SGraphPackage#getScope_Members()
     * @model containment="true" resolveProxies="true"
     * @generated
     */
    EList<EObject> getMembers();

} // Scope
