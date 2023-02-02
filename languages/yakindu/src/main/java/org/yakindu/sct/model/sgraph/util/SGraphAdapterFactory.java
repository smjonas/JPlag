/**
 * Copyright (c) 2011 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.model.sgraph.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.yakindu.base.base.DocumentedElement;
import org.yakindu.base.base.DomainElement;
import org.yakindu.base.base.NamedElement;
import org.yakindu.base.types.AnnotatableElement;
import org.yakindu.base.types.Declaration;
import org.yakindu.base.types.MetaComposite;
import org.yakindu.sct.model.sgraph.*;
import org.yakindu.sct.model.sgraph.Choice;
import org.yakindu.sct.model.sgraph.CompositeElement;
import org.yakindu.sct.model.sgraph.Effect;
import org.yakindu.sct.model.sgraph.Entry;
import org.yakindu.sct.model.sgraph.Exit;
import org.yakindu.sct.model.sgraph.FinalState;
import org.yakindu.sct.model.sgraph.ImportDeclaration;
import org.yakindu.sct.model.sgraph.Pseudostate;
import org.yakindu.sct.model.sgraph.Reaction;
import org.yakindu.sct.model.sgraph.ReactionProperty;
import org.yakindu.sct.model.sgraph.ReactiveElement;
import org.yakindu.sct.model.sgraph.Region;
import org.yakindu.sct.model.sgraph.RegularState;
import org.yakindu.base.SGraphPackage;
import org.yakindu.sct.model.sgraph.Scope;
import org.yakindu.sct.model.sgraph.ScopeMember;
import org.yakindu.sct.model.sgraph.ScopedElement;
import org.yakindu.sct.model.sgraph.SpecificationElement;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Synchronization;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.sgraph.Trigger;
import org.yakindu.sct.model.sgraph.Vertex;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.yakindu.sct.model.sgraph.SGraphPackage
 * @generated
 */
public class SGraphAdapterFactory extends AdapterFactoryImpl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) 2011 committers of YAKINDU and others.\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\nContributors:\r\ncommitters of YAKINDU - initial API and implementation\r\n";

    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static SGraphPackage modelPackage;
    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SGraphSwitch<Adapter> modelSwitch =
            new SGraphSwitch<Adapter>() {
                @Override
                public Adapter casePseudostate(Pseudostate object) {
                    return createPseudostateAdapter();
                }

                @Override
                public Adapter caseVertex(Vertex object) {
                    return createVertexAdapter();
                }

                @Override
                public Adapter caseRegion(Region object) {
                    return createRegionAdapter();
                }

                @Override
                public Adapter caseTransition(Transition object) {
                    return createTransitionAdapter();
                }

                @Override
                public Adapter caseFinalState(FinalState object) {
                    return createFinalStateAdapter();
                }

                @Override
                public Adapter caseChoice(Choice object) {
                    return createChoiceAdapter();
                }

                @Override
                public Adapter caseStatechart(Statechart object) {
                    return createStatechartAdapter();
                }

                @Override
                public Adapter caseEntry(Entry object) {
                    return createEntryAdapter();
                }

                @Override
                public Adapter caseExit(Exit object) {
                    return createExitAdapter();
                }

                @Override
                public Adapter caseReactiveElement(ReactiveElement object) {
                    return createReactiveElementAdapter();
                }

                @Override
                public Adapter caseReaction(Reaction object) {
                    return createReactionAdapter();
                }

                @Override
                public Adapter caseTrigger(Trigger object) {
                    return createTriggerAdapter();
                }

                @Override
                public Adapter caseEffect(Effect object) {
                    return createEffectAdapter();
                }

                @Override
                public Adapter caseReactionProperty(ReactionProperty object) {
                    return createReactionPropertyAdapter();
                }

                @Override
                public Adapter caseSpecificationElement(SpecificationElement object) {
                    return createSpecificationElementAdapter();
                }

                @Override
                public Adapter caseScope(Scope object) {
                    return createScopeAdapter();
                }

                @Override
                public Adapter caseScopedElement(ScopedElement object) {
                    return createScopedElementAdapter();
                }

                @Override
                public Adapter caseSynchronization(Synchronization object) {
                    return createSynchronizationAdapter();
                }

                @Override
                public Adapter caseState(State object) {
                    return createStateAdapter();
                }

                @Override
                public Adapter caseRegularState(RegularState object) {
                    return createRegularStateAdapter();
                }

                @Override
                public Adapter caseCompositeElement(CompositeElement object) {
                    return createCompositeElementAdapter();
                }

                @Override
                public Adapter caseImportDeclaration(ImportDeclaration object) {
                    return createImportDeclarationAdapter();
                }

                @Override
                public Adapter caseScopeMember(ScopeMember object) {
                    return createScopeMemberAdapter();
                }

                @Override
                public Adapter caseNamedElement(NamedElement object) {
                    return createNamedElementAdapter();
                }

                @Override
                public Adapter caseDocumentedElement(DocumentedElement object) {
                    return createDocumentedElementAdapter();
                }

                @Override
                public Adapter caseDomainElement(DomainElement object) {
                    return createDomainElementAdapter();
                }

                @Override
                public Adapter caseAnnotatableElement(AnnotatableElement object) {
                    return createAnnotatableElementAdapter();
                }

                @Override
                public Adapter caseMetaComposite(MetaComposite object) {
                    return createMetaCompositeAdapter();
                }

                @Override
                public Adapter caseDeclaration(Declaration object) {
                    return createDeclarationAdapter();
                }

                @Override
                public Adapter defaultCase(EObject object) {
                    return createEObjectAdapter();
                }
            };

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SGraphAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = SGraphPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject) target);
    }


    /**
     * Creates a new adapter for an object of class '{@link Pseudostate <em>Pseudostate</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Pseudostate
     * @generated
     */
    public Adapter createPseudostateAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Vertex <em>Vertex</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Vertex
     * @generated
     */
    public Adapter createVertexAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.yakindu.base.base.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.yakindu.base.base.NamedElement
     * @generated
     */
    public Adapter createNamedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.yakindu.base.base.DocumentedElement <em>Documented Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.yakindu.base.base.DocumentedElement
     * @generated
     */
    public Adapter createDocumentedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.yakindu.base.base.DomainElement <em>Domain Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.yakindu.base.base.DomainElement
     * @generated
     */
    public Adapter createDomainElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.yakindu.base.types.AnnotatableElement <em>Annotatable Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.yakindu.base.types.AnnotatableElement
     * @generated
     */
    public Adapter createAnnotatableElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.yakindu.base.types.MetaComposite <em>Meta Composite</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.yakindu.base.types.MetaComposite
     * @generated
     */
    public Adapter createMetaCompositeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.yakindu.base.types.Declaration <em>Declaration</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.yakindu.base.types.Declaration
     * @generated
     */
    public Adapter createDeclarationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Region <em>Region</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Region
     * @generated
     */
    public Adapter createRegionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Transition <em>Transition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Transition
     * @generated
     */
    public Adapter createTransitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link FinalState <em>Final State</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see FinalState
     * @generated
     */
    public Adapter createFinalStateAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link State <em>State</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see State
     * @generated
     */
    public Adapter createStateAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link RegularState <em>Regular State</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see RegularState
     * @generated
     */
    public Adapter createRegularStateAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link CompositeElement <em>Composite Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see CompositeElement
     * @generated
     */
    public Adapter createCompositeElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link ImportDeclaration <em>Import Declaration</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see ImportDeclaration
     * @generated
     */
    public Adapter createImportDeclarationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link ScopeMember <em>Scope Member</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see ScopeMember
     * @generated
     */
    public Adapter createScopeMemberAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Choice <em>Choice</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Choice
     * @generated
     */
    public Adapter createChoiceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Statechart <em>Statechart</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Statechart
     * @generated
     */
    public Adapter createStatechartAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Entry <em>Entry</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Entry
     * @generated
     */
    public Adapter createEntryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Trigger <em>Trigger</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Trigger
     * @generated
     */
    public Adapter createTriggerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Effect <em>Effect</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Effect
     * @generated
     */
    public Adapter createEffectAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link ReactionProperty <em>Reaction Property</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see ReactionProperty
     * @generated
     */
    public Adapter createReactionPropertyAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link SpecificationElement <em>Specification Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see SpecificationElement
     * @generated
     */
    public Adapter createSpecificationElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Reaction <em>Reaction</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Reaction
     * @generated
     */
    public Adapter createReactionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link ReactiveElement <em>Reactive Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see ReactiveElement
     * @generated
     */
    public Adapter createReactiveElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Exit <em>Exit</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Exit
     * @generated
     */
    public Adapter createExitAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Scope <em>Scope</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Scope
     * @generated
     */
    public Adapter createScopeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link ScopedElement <em>Scoped Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see ScopedElement
     * @generated
     */
    public Adapter createScopedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link Synchronization <em>Synchronization</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see Synchronization
     * @generated
     */
    public Adapter createSynchronizationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //SGraphAdapterFactory
