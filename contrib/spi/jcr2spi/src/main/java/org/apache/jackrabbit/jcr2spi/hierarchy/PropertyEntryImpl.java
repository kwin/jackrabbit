/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.jcr2spi.hierarchy;

import org.apache.jackrabbit.spi.Name;
import org.apache.jackrabbit.spi.Path;
import org.apache.jackrabbit.spi.PropertyId;
import org.apache.jackrabbit.jcr2spi.state.PropertyState;
import org.apache.jackrabbit.jcr2spi.state.ItemState;
import org.apache.jackrabbit.jcr2spi.state.Status;

import javax.jcr.RepositoryException;
import javax.jcr.ItemNotFoundException;

/**
 * <code>PropertyEntryImpl</code> implements a reference to a property state.
 */
public class PropertyEntryImpl extends HierarchyEntryImpl implements PropertyEntry {

    /**
     * Creates a new <code>PropertyEntryImpl</code>.
     *
     * @param parent    the parent <code>NodeEntry</code> where the property
     *                  belongs to.
     * @param name      the name of the property.
     * @param factory
     */
    private PropertyEntryImpl(NodeEntryImpl parent, Name name, EntryFactory factory) {
        super(parent, name, factory);
    }

    /**
     * Creates a new <code>PropertyEntry</code>.
     *
     * @param parent
     * @param name
     * @param factory
     * @return new <code>PropertyEntry</code>
     */
    static PropertyEntry create(NodeEntryImpl parent, Name name, EntryFactory factory) {
        return new PropertyEntryImpl(parent, name, factory);
    }

    //------------------------------------------------------< HierarchyEntryImpl >---
    /**
     * @inheritDoc
     * @see HierarchyEntryImpl#doResolve()
     * <p/>
     * Returns a <code>PropertyState</code>.
     */
    ItemState doResolve() throws ItemNotFoundException, RepositoryException {
        return factory.getItemStateFactory().createPropertyState(getWorkspaceId(), this);
    }

    /**
     * @see HierarchyEntryImpl#buildPath(boolean)
     */
    Path buildPath(boolean workspacePath) throws RepositoryException {
        Path parentPath = parent.buildPath(workspacePath);
        return factory.getPathFactory().create(parentPath, getName(), true);
    }

    //------------------------------------------------------< PropertyEntry >---
    /**
     * @see PropertyEntry#getId()
     */
    public PropertyId getId() {
        return factory.getIdFactory().createPropertyId(parent.getId(), getName());
    }

    /**
     * @see PropertyEntry#getWorkspaceId()
     */
    public PropertyId getWorkspaceId() {
        return factory.getIdFactory().createPropertyId(parent.getWorkspaceId(), getName());
    }

    /**
     * @see PropertyEntry#getPropertyState()
     */
    public PropertyState getPropertyState() throws ItemNotFoundException, RepositoryException {
        return (PropertyState) getItemState();
    }

    //-----------------------------------------------------< HierarchyEntry >---
    /**
     * Returns false.
     *
     * @inheritDoc
     * @see HierarchyEntry#denotesNode()
     */
    public boolean denotesNode() {
        return false;
    }

    /**
     * @inheritDoc
     * @see HierarchyEntry#remove()
     */
    public void remove() {
        removeEntry(this);
        if (getStatus() != Status.STALE_DESTROYED) {
            parent.internalRemovePropertyEntry(getName());
        }
    }
}
