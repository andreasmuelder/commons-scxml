/*
 *
 *   Copyright 2005 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.commons.scxml.model;

/**
 * The class in this SCXML object model that corresponds to the
 * &lt;exit&gt; SCXML element, which is a shorthand notation for
 * an empty anonymous final state.
 *
 */
public class Exit extends Action {

    /**
     * The optional expression.
     */
    private String expr;

    /**
     * The optional namelist.
     */
    private String namelist;

    /**
     * Constructor.
     */
    public Exit() {
        super();
    }

    /**
     * Get the expression.
     *
     * @return String Returns the expr.
     */
    public final String getExpr() {
        return expr;
    }

    /**
     * Set the expression.
     *
     * @param expr The expr to set.
     */
    public final void setExpr(final String expr) {
        this.expr = expr;
    }

    /**
     * Get the namelist.
     *
     * @return String Returns the namelist.
     */
    public final String getNamelist() {
        return namelist;
    }

    /**
     * Set the namelist.
     *
     * @param namelist The namelist to set.
     */
    public final void setNamelist(final String namelist) {
        this.namelist = namelist;
    }

}

