/**
 *
 */
package org.yakindu.base.types;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Specifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link TypeSpecifier#getType <em>Type</em>}</li>
 *   <li>{@link TypeSpecifier#getTypeArguments <em>Type Arguments</em>}</li>
 * </ul>
 *
 * @see TypesPackage#getTypeSpecifier()
 * @model
 * @generated
 */
public interface TypeSpecifier extends EObject {
    /**
     * Returns the value of the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' reference.
     * @see #setType(Type)
     * @see TypesPackage#getTypeSpecifier_Type()
     * @model
     * @generated
     */
    Type getType();

    /**
     * Sets the value of the '{@link TypeSpecifier#getType <em>Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' reference.
     * @see #getType()
     * @generated
     */
    void setType(Type value);

    /**
     * Returns the value of the '<em><b>Type Arguments</b></em>' containment reference list.
     * The list contents are of type {@link TypeSpecifier}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type Arguments</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type Arguments</em>' containment reference list.
     * @see TypesPackage#getTypeSpecifier_TypeArguments()
     * @model containment="true"
     * @generated
     */
    EList<TypeSpecifier> getTypeArguments();

} // TypeSpecifier
