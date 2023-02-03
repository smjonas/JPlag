/**
 *
 */
package org.yakindu.base.types;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link AnnotationType#getProperties <em>Properties</em>}</li>
 *   <li>{@link AnnotationType#getTargets <em>Targets</em>}</li>
 * </ul>
 *
 * @see TypesPackage#getAnnotationType()
 * @model
 * @generated
 */
public interface AnnotationType extends Type {
    /**
     * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
     * The list contents are of type {@link Property}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Properties</em>' containment reference list.
     * @see TypesPackage#getAnnotationType_Properties()
     * @model containment="true"
     * @generated
     */
    EList<Property> getProperties();

    /**
     * Returns the value of the '<em><b>Targets</b></em>' reference list.
     * The list contents are of type {@link EObject}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Targets</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Targets</em>' reference list.
     * @see TypesPackage#getAnnotationType_Targets()
     * @model
     * @generated
     */
    EList<EObject> getTargets();

} // AnnotationType
