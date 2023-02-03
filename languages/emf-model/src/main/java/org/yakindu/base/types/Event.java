/**
 * <copyright>
 * </copyright>
 * <p>
 * $Id$
 */
package org.yakindu.base.types;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Event#getDirection <em>Direction</em>}</li>
 * </ul>
 *
 * @see TypesPackage#getEvent()
 * @model
 * @generated
 */
public interface Event extends TypedDeclaration {

    /**
     * Returns the value of the '<em><b>Direction</b></em>' attribute.
     * The literals are from the enumeration {@link Direction}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Direction</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Direction</em>' attribute.
     * @see Direction
     * @see #setDirection(Direction)
     * @see TypesPackage#getEvent_Direction()
     * @model
     * @generated
     */
    Direction getDirection();

    /**
     * Sets the value of the '{@link Event#getDirection <em>Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Direction</em>' attribute.
     * @see Direction
     * @see #getDirection()
     * @generated
     */
    void setDirection(Direction value);
} // Event
