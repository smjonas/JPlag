/**
 *
 */
package org.yakindu.base.types;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Annotation#getType <em>Type</em>}</li>
 *   <li>{@link Annotation#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @see TypesPackage#getAnnotation()
 * @model
 * @generated
 */
public interface Annotation extends EObject {
    /**
     * Returns the value of the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' reference.
     * @see #setType(AnnotationType)
     * @see TypesPackage#getAnnotation_Type()
     * @model
     * @generated
     */
    AnnotationType getType();

    /**
     * Sets the value of the '{@link Annotation#getType <em>Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' reference.
     * @see #getType()
     * @generated
     */
    void setType(AnnotationType value);

    /**
     * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
     * The list contents are of type {@link Expression}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Arguments</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Arguments</em>' containment reference list.
     * @see TypesPackage#getAnnotation_Arguments()
     * @model containment="true"
     * @generated
     */
    EList<Expression> getArguments();

} // Annotation
