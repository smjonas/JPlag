/**
 */
package org.yakindu.base.types;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enumeration Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link EnumerationType#getEnumerator <em>Enumerator</em>}</li>
 * </ul>
 *
 * @see TypesPackage#getEnumerationType()
 * @model
 * @generated
 */
public interface EnumerationType extends ComplexType {

	/**
	 * Returns the value of the '<em><b>Enumerator</b></em>' containment reference list.
	 * The list contents are of type {@link Enumerator}.
	 * It is bidirectional and its opposite is '{@link Enumerator#getOwningEnumeration <em>Owning Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumerator</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumerator</em>' containment reference list.
	 * @see TypesPackage#getEnumerationType_Enumerator()
	 * @see Enumerator#getOwningEnumeration
	 * @model opposite="owningEnumeration" containment="true"
	 * @generated
	 */
	EList<Enumerator> getEnumerator();
} // EnumerationType
