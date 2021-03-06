/*
 * Copyright (c) 2015, Ali Afroozeh and Anastasia Izmaylova, Centrum Wiskunde & Informatica (CWI)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this 
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this 
 *    list of conditions and the following disclaimer in the documentation and/or 
 *    other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 *
 */

package org.iguana.grammar.symbol;

import org.iguana.regex.RegularExpression;
import org.iguana.regex.automaton.Automaton;
import org.iguana.regex.matcher.Matcher;
import org.iguana.regex.matcher.MatcherFactory;


public abstract class AbstractRegularExpression extends AbstractSymbol implements RegularExpression {

	private static final long serialVersionUID = 1L;
	
	protected transient Automaton automaton;
	
	protected transient Matcher matcher;
	
	protected transient Matcher backwardsMatcher;
	
	public AbstractRegularExpression(SymbolBuilder<? extends RegularExpression> builder) {
		super(builder);
	}
	
	@Override
	public final Automaton getAutomaton() {
		if (automaton == null) {
			automaton = createAutomaton();
		}
		return automaton;
	}
	
	@Override
	public Matcher getMatcher() {
		if (matcher == null) {
			matcher = MatcherFactory.getMatcher(this);
		}
		return matcher;
	}
	
	@Override
	public Matcher getBackwardsMatcher() {
		if (backwardsMatcher == null) {
			backwardsMatcher = MatcherFactory.getBackwardsMatcher(this);
		}
		return backwardsMatcher;
	}
	
	protected abstract Automaton createAutomaton();
	
	@Override
	public void initMatcher() {
		backwardsMatcher = MatcherFactory.getBackwardsMatcher(this);
		matcher = MatcherFactory.getMatcher(this);
	}
}
