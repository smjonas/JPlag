/**
 */
package org.yakindu.base.types;

import org.eclipse.emf.common.util.EList;
import org.yakindu.base.base.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link GenericElement#getTypeParameters <em>Type Parameters</em>}</li>
 * </ul>
 *
 * @see TypesPackage#getGenericElement()
 * @model
 * @generated
 */
public interface GenericElement extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Type Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link TypeParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Parameters</em>' containment reference list.
	 * @see TypesPackage#getGenericElement_TypeParameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeParameter> getTypeParameters();

} // GenericElement
