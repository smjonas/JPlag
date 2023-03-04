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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reaction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Reaction#getTrigger <em>Trigger</em>}</li>
 *   <li>{@link Reaction#getEffect <em>Effect</em>}</li>
 *   <li>{@link Reaction#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @see org.yakindu.sct.model.sgraph.SGraphPackage#getReaction()
 * @model abstract="true"
 * @generated
 */
public interface Reaction extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) 2011 committers of YAKINDU and others.\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\nContributors:\r\ncommitters of YAKINDU - initial API and implementation\r\n";

    /**
     * Returns the value of the '<em><b>Trigger</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger</em>' containment reference.
     * @see #setTrigger(Trigger)
     * @see org.yakindu.sct.model.sgraph.SGraphPackage#getReaction_Trigger()
     * @model containment="true" resolveProxies="true" transient="true"
     * @generated
     */
    Trigger getTrigger();

    /**
     * Sets the value of the '{@link Reaction#getTrigger <em>Trigger</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger</em>' containment reference.
     * @see #getTrigger()
     * @generated
     */
    void setTrigger(Trigger value);

    /**
     * Returns the value of the '<em><b>Effect</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Effect</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Effect</em>' containment reference.
     * @see #setEffect(Effect)
     * @see org.yakindu.sct.model.sgraph.SGraphPackage#getReaction_Effect()
     * @model containment="true" resolveProxies="true" transient="true"
     * @generated
     */
    Effect getEffect();

    /**
     * Sets the value of the '{@link Reaction#getEffect <em>Effect</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Effect</em>' containment reference.
     * @see #getEffect()
     * @generated
     */
    void setEffect(Effect value);

    /**
     * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
     * The list contents are of type {@link ReactionProperty}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Properties</em>' containment reference list.
     * @see org.yakindu.sct.model.sgraph.SGraphPackage#getReaction_Properties()
     * @model containment="true" resolveProxies="true" transient="true"
     * @generated
     */
    EList<ReactionProperty> getProperties();

} // Reaction
