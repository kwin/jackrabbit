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
package org.apache.jackrabbit.standalone.cli.mixin;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.standalone.cli.CommandHelper;

/**
 * Remove a mixin from the given <code>Node</code>
 */
public class RemoveMixin implements Command {
    /** logger */
    private static Log log = LogFactory.getLog(RemoveMixin.class);

    // ---------------------------- < keys >
    /** node path */
    private String pathKey = "path";

    /** mixin name */
    private String mixinKey = "mixin";

    /**
     * @return the mixin name key
     */
    public String getMixinKey() {
        return mixinKey;
    }

    /**
     * @param mixinNameKey
     *        the mixin name key to set
     */
    public void setMixinKey(String mixinNameKey) {
        this.mixinKey = mixinNameKey;
    }

    /**
     * @return he path key
     */
    public String getPathKey() {
        return pathKey;
    }

    /**
     * @param pathKey
     *        the path key to set
     */
    public void setPathKey(String pathKey) {
        this.pathKey = pathKey;
    }

    /**
     * {@inheritDoc}
     */
    public boolean execute(Context ctx) throws Exception {
        String path = (String) ctx.get(this.pathKey);
        String mixin = (String) ctx.get(this.mixinKey);
        if (log.isDebugEnabled()) {
            log.debug("removing mixin " + mixin + " from node " + path);
        }
        CommandHelper.getNode(ctx, path).removeMixin(mixin);
        return false;
    }
}
