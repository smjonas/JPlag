/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.yakindu.base.types;

import org.yakindu.base.base.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Parameter#getOwningOperation <em>Owning Operation</em>}</li>
 *   <li>{@link Parameter#isVarArgs <em>Var Args</em>}</li>
 *   <li>{@link Parameter#isOptional <em>Optional</em>}</li>
 * </ul>
 *
 * @see TypesPackage#getParameter()
 * @model
 * @generated
 */
public interface Parameter extends TypedElement, NamedElement, AnnotatableElement {
	/**
	 * Returns the value of the '<em><b>Owning Operation</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link Operation#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owning Operation</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owning Operation</em>' container reference.
	 * @see #setOwningOperation(Operation)
	 * @see TypesPackage#getParameter_OwningOperation()
	 * @see Operation#getParameters
	 * @model opposite="parameters" transient="false"
	 * @generated
	 */
	Operation getOwningOperation();

	/**
	 * Sets the value of the '{@link Parameter#getOwningOperation <em>Owning Operation</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owning Operation</em>' container reference.
	 * @see #getOwningOperation()
	 * @generated
	 */
	void setOwningOperation(Operation value);

	/**
	 * Returns the value of the '<em><b>Var Args</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Var Args</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var Args</em>' attribute.
	 * @see #setVarArgs(boolean)
	 * @see TypesPackage#getParameter_VarArgs()
	 * @model default="false"
	 * @generated
	 */
	boolean isVarArgs();

	/**
	 * Sets the value of the '{@link Parameter#isVarArgs <em>Var Args</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Var Args</em>' attribute.
	 * @see #isVarArgs()
	 * @generated
	 */
	void setVarArgs(boolean value);

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(boolean)
	 * @see TypesPackage#getParameter_Optional()
	 * @model default="false"
	 * @generated
	 */
	boolean isOptional();

	/**
	 * Sets the value of the '{@link Parameter#isOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #isOptional()
	 * @generated
	 */
	void setOptional(boolean value);

} // Parameter
