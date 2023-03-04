/**
 * <copyright>
 * </copyright>
 * <p>
 * $Id$
 */
package org.yakindu.base.types;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see TypesPackage
 * @generated
 */
public interface TypesFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    TypesFactory eINSTANCE = org.yakindu.base.types.impl.TypesFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Package</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Package</em>'.
     * @generated
     */
    Package createPackage();

    /**
     * Returns a new object of class '<em>Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Type</em>'.
     * @generated
     */
    Type createType();

    /**
     * Returns a new object of class '<em>Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Operation</em>'.
     * @generated
     */
    Operation createOperation();

    /**
     * Returns a new object of class '<em>Property</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Property</em>'.
     * @generated
     */
    Property createProperty();

    /**
     * Returns a new object of class '<em>Parameter</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Parameter</em>'.
     * @generated
     */
    Parameter createParameter();

    /**
     * Returns a new object of class '<em>Type Specifier</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Type Specifier</em>'.
     * @generated
     */
    TypeSpecifier createTypeSpecifier();

    /**
     * Returns a new object of class '<em>Event</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Event</em>'.
     * @generated
     */
    Event createEvent();

    /**
     * Returns a new object of class '<em>Enumeration Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Enumeration Type</em>'.
     * @generated
     */
    EnumerationType createEnumerationType();

    /**
     * Returns a new object of class '<em>Primitive Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Primitive Type</em>'.
     * @generated
     */
    PrimitiveType createPrimitiveType();

    /**
     * Returns a new object of class '<em>Complex Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Complex Type</em>'.
     * @generated
     */
    ComplexType createComplexType();

    /**
     * Returns a new object of class '<em>Enumerator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Enumerator</em>'.
     * @generated
     */
    Enumerator createEnumerator();

    /**
     * Returns a new object of class '<em>Type Parameter</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Type Parameter</em>'.
     * @generated
     */
    TypeParameter createTypeParameter();

    /**
     * Returns a new object of class '<em>Generic Element</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Generic Element</em>'.
     * @generated
     */
    GenericElement createGenericElement();

    /**
     * Returns a new object of class '<em>Domain</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Domain</em>'.
     * @generated
     */
    Domain createDomain();

    /**
     * Returns a new object of class '<em>Type Alias</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Type Alias</em>'.
     * @generated
     */
    TypeAlias createTypeAlias();

    /**
     * Returns a new object of class '<em>Annotation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Annotation</em>'.
     * @generated
     */
    Annotation createAnnotation();

    /**
     * Returns a new object of class '<em>Annotatable Element</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Annotatable Element</em>'.
     * @generated
     */
    AnnotatableElement createAnnotatableElement();

    /**
     * Returns a new object of class '<em>Array Type Specifier</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Array Type Specifier</em>'.
     * @generated
     */
    ArrayTypeSpecifier createArrayTypeSpecifier();

    /**
     * Returns a new object of class '<em>Annotation Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Annotation Type</em>'.
     * @generated
     */
    AnnotationType createAnnotationType();

    /**
     * Returns a new object of class '<em>Typed Declaration</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Typed Declaration</em>'.
     * @generated
     */
    TypedDeclaration createTypedDeclaration();

    /**
     * Returns a new object of class '<em>Meta Composite</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Meta Composite</em>'.
     * @generated
     */
    MetaComposite createMetaComposite();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    TypesPackage getTypesPackage();

} //TypesFactory
