/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package jakarta.mail.search;

import jakarta.mail.Message;

/**
 * This class implements the logical AND operator on individual
 * SearchTerms.
 *
 * @author Bill Shannon
 * @author John Mani
 */
public final class AndTerm extends SearchTerm {

    /**
     * The array of terms on which the AND operator should be
     * applied.
     *
     * @serial
     */
    private SearchTerm[] terms;

    private static final long serialVersionUID = -3583274505380989582L;

    /**
     * Constructor that takes two terms.
     * 
     * @param t1 first term
     * @param t2 second term
     */
    public AndTerm(SearchTerm t1, SearchTerm t2) {
	terms = new SearchTerm[2];
	terms[0] = t1;
	terms[1] = t2;
    }

    /**
     * Constructor that takes an array of SearchTerms.
     * 
     * @param t  array of terms
     */
    public AndTerm(SearchTerm[] t) {
	terms = new SearchTerm[t.length]; // clone the array
	for (int i = 0; i < t.length; i++)
	    terms[i] = t[i];
    }

    /**
     * Return the search terms.
     *
     * @return	the search terms
     */
    public SearchTerm[] getTerms() {
	return terms.clone();
    }

    /**
     * The AND operation. <p>
     *
     * The terms specified in the constructor are applied to
     * the given object and the AND operator is applied to their results.
     *
     * @param msg	The specified SearchTerms are applied to this Message
     *			and the AND operator is applied to their results.
     * @return		true if the AND succeds, otherwise false
     */
    @Override
    public boolean match(Message msg) {
	for (int i=0; i < terms.length; i++)
	    if (!terms[i].match(msg))
		return false;
	return true;
    }

    /**
     * Equality comparison.
     */
    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof AndTerm))
	    return false;
	AndTerm at = (AndTerm)obj;
	if (at.terms.length != terms.length)
	    return false;
	for (int i=0; i < terms.length; i++)
	    if (!terms[i].equals(at.terms[i]))
		return false;
	return true;
    }

    /**
     * Compute a hashCode for this object.
     */
    @Override
    public int hashCode() {
	int hash = 0;
	for (int i=0; i < terms.length; i++)
	    hash += terms[i].hashCode();
	return hash;
    }
}
