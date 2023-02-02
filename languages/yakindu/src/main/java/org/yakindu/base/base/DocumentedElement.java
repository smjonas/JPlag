/**
 *
 */
package org.yakindu.base.base;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Documented Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link DocumentedElement#getDocumentation <em>Documentation</em>}</li>
 * </ul>
 *
 * @see BasePackage#getDocumentedElement()
 * @model
 * @generated
 */
public interface DocumentedElement extends EObject {
    /**
     * Returns the value of the '<em><b>Documentation</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Documentation</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Documentation</em>' attribute.
     * @see #setDocumentation(String)
     * @see BasePackage#getDocumentedElement_Documentation()
     * @model default=""
     * @generated
     */
    String getDocumentation();

    /**
     * Sets the value of the '{@link DocumentedElement#getDocumentation <em>Documentation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Documentation</em>' attribute.
     * @see #getDocumentation()
     * @generated
     */
    void setDocumentation(String value);

} // DocumentedElement
