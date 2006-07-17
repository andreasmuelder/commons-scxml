/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.scxml.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.digester.Digester;
import org.apache.commons.scxml.SCXMLExecutor;
import org.apache.commons.scxml.SCXMLTestHelper;

public class CustomActionTest extends TestCase {

    public CustomActionTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(CustomActionTest.class);
    }

    public static void main(String args[]) {
        String[] testCaseName = { CustomActionTest.class.getName()};
        junit.textui.TestRunner.main(testCaseName);
    }

    private URL hello01, custom01, external01;
    private Digester digester;
    private SCXMLExecutor exec;

    /**
     * Set up instance variables required by this test case.
     */
    public void setUp() {
        hello01 = this.getClass().getClassLoader().
            getResource("org/apache/commons/scxml/hello-world.xml");
        custom01 = this.getClass().getClassLoader().
            getResource("org/apache/commons/scxml/custom-hello-world-01.xml");
        external01 = this.getClass().getClassLoader().
            getResource("org/apache/commons/scxml/external-hello-world.xml");
    }

    /**
     * Tear down instance variables required by this test case.
     */
    public void tearDown() {
        hello01 = custom01 = external01 = null;
        digester = null;
        exec = null;
    }

    public void testAddGoodCustomAction01() {
        try {
            new CustomAction("http://my.actions.domain/CUSTOM", "hello",
                Hello.class);
        } catch (IllegalArgumentException iae) {
            fail("Failed to add custom action &quot;Hello&quot;");
        }
    }

    public void testAddBadCustomAction01() {
        try {
            new CustomAction(null, "hello", Hello.class);
            fail("Added custom action with illegal namespace");
        } catch (IllegalArgumentException iae) {
            // Expected
        }
    }

    public void testAddBadCustomAction02() {
        try {
            new CustomAction("  ", "hello", Hello.class);
            fail("Added custom action with illegal namespace");
        } catch (IllegalArgumentException iae) {
            // Expected
        }
    }

    public void testAddBadCustomAction03() {
        try {
            new CustomAction("http://my.actions.domain/CUSTOM", "",
                Hello.class);
            fail("Added custom action with illegal local name");
        } catch (IllegalArgumentException iae) {
            // Expected
        }
    }

    public void testAddBadCustomAction04() {
        try {
            new CustomAction("http://my.actions.domain/CUSTOM", "  ",
                Hello.class);
            fail("Added custom action with illegal local name");
        } catch (IllegalArgumentException iae) {
            // Expected
        }
    }

    public void testAddBadCustomAction05() {
        try {
            new CustomAction("http://my.actions.domain/CUSTOM", "foo",
                this.getClass());
            fail("Added custom action which is not an Action class subtype");
        } catch (IllegalArgumentException iae) {
            // Expected
        }
    }

    // Hello World example using the SCXML <log> action
    public void testHelloWorld() {
        // (1) Get a SCXMLExecutor
        exec = SCXMLTestHelper.getExecutor(hello01);
        // (2) Single, final state
        assertEquals("hello", ((State) exec.getCurrentStatus().getStates().
                iterator().next()).getId());
        assertTrue(exec.getCurrentStatus().isFinal());
    }

    // Hello World example using a custom <hello> action
    public void testCustomActionHelloWorld() {
        // (1) Form a list of custom actions defined in the SCXML
        //     document (and any included documents via "src" attributes)
        CustomAction ca1 =
            new CustomAction("http://my.custom-actions.domain/CUSTOM1",
                             "hello", Hello.class);
        // Register the same action under a different name, just to test
        // multiple custom actions
        CustomAction ca2 =
            new CustomAction("http://my.custom-actions.domain/CUSTOM2",
                             "bar", Hello.class);
        List customActions = new ArrayList();
        customActions.add(ca1);
        customActions.add(ca2);
        // (2) Parse the document with a custom digester.
        SCXML scxml = SCXMLTestHelper.digest(custom01, customActions);
        // (3) Get a SCXMLExecutor
        exec = SCXMLTestHelper.getExecutor(scxml);
        // (4) Single, final state
        assertEquals("custom", ((State) exec.getCurrentStatus().getStates().
                iterator().next()).getId());
        assertTrue(exec.getCurrentStatus().isFinal());
    }

    // Hello World example using custom <my:hello> action
    // as part of an external state source (src attribute)
    public void testCustomActionExternalSrcHelloWorld() {
        // (1) Form a list of custom actions defined in the SCXML
        //     document (and any included documents via "src" attributes)
        CustomAction ca =
            new CustomAction("http://my.custom-actions.domain/CUSTOM",
                             "hello", Hello.class);
        List customActions = new ArrayList();
        customActions.add(ca);
        // (2) Parse the document with a custom digester.
        SCXML scxml = SCXMLTestHelper.digest(external01, customActions);
        // (3) Get a SCXMLExecutor
        exec = SCXMLTestHelper.getExecutor(scxml);
        // (4) Single, final state
        assertEquals("custom", ((State) exec.getCurrentStatus().getStates().
            iterator().next()).getId());
    }

    // The custom action defined by Hello.class should be called
    // to execute() exactly 4 times upto this point
    public void testCustomActionCallbacks() {
        assertEquals(4, Hello.callbacks);
    }

}
