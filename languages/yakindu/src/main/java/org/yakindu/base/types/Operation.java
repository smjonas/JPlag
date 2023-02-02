/**
 * <copyright>
 * </copyright>
 * <p>
 * $Id$
 */
package org.yakindu.base.types;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Operation#getParameters <em>Parameters</em>}</li>
 *   <li>{@link Operation#isVariadic <em>Variadic</em>}</li>
 * </ul>
 *
 * @see TypesPackage#getOperation()
 * @model
 * @generated
 */
public interface Operation extends TypedDeclaration, GenericElement {
    /**
     * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
     * The list contents are of type {@link Parameter}.
     * It is bidirectional and its opposite is '{@link Parameter#getOwningOperation <em>Owning Operation</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameters</em>' containment reference list.
     * @see TypesPackage#getOperation_Parameters()
     * @see Parameter#getOwningOperation
     * @model opposite="owningOperation" containment="true"
     * @generated
     */
    EList<Parameter> getParameters();

    /**
     * Returns the value of the '<em><b>Variadic</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Variadic</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Variadic</em>' attribute.
     * @see TypesPackage#getOperation_Variadic()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    boolean isVariadic();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    int getVarArgIndex();

} // Operation
