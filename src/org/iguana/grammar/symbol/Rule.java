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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.iguana.parser.HashFunctions;
import org.iguana.util.generator.ConstructorCode;

/**
 * 
 * @authors Ali Afroozeh, Anastasia Izmaylova
 *
 */
public class Rule implements ConstructorCode, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final List<Symbol> body;
	
	private final Nonterminal head;
	
	/**
	 * An arbitrary data object that can be put in this grammar slot and
	 * retrieved later when traversing the parse tree.
	 * This object can be accessed via the getObject() method of a nonterminal symbol node.
	 */
	private final Serializable object;
	
	private final Nonterminal layout;
	
	private final LayoutStrategy layoutStrategy;
	
	private final Recursion recursion;
	
	private final Associativity associativity;
	private final AssociativityGroup associativityGroup;
	
	private final int precedence;
	private final PrecedenceLevel precedenceLevel;
	
	private final String label;
	
	public Rule(Builder builder) {
		this.body = builder.body;
		this.head = builder.head;
		this.object = builder.object;
		this.layout = builder.layout;
		this.layoutStrategy = builder.layoutStrategy;
		this.recursion = builder.recursion;
		this.associativity = builder.associativity;
		
		this.associativityGroup = builder.associativityGroup;
		this.precedence = builder.precedence;
		this.precedenceLevel = builder.precedenceLevel;
		
		this.label = builder.label;
	}
		
	public Nonterminal getHead() {
		return head;
	}
	
	public List<Symbol> getBody() {
		return body;
	}
	
	public int size() {
		return body == null ? 0 : body.size();
	}
	
	public Symbol symbolAt(int i) {
		if (i > body.size())
			throw new IllegalArgumentException(i + " cannot be greater than " + body.size());
		
		return body.get(i);
	}
	
	public Serializable getObject() {
		return object;
	}
	
	public Nonterminal getLayout() {
		return layout;
	}
	
	public LayoutStrategy getLayoutStrategy() {
		return layoutStrategy;
	}
	
	public boolean isUnary() {
		return recursion == Recursion.LEFT_REC || recursion == Recursion.RIGHT_REC;
	}
	
	public boolean isLeftRecursive() {
		return recursion == Recursion.LEFT_RIGHT_REC || recursion == Recursion.LEFT_REC;
	}
	
	public boolean isRightRecursive() {
		return recursion == Recursion.LEFT_RIGHT_REC || recursion == Recursion.RIGHT_REC;
	}
	
	public boolean isLeftOrRightRecursive() {
		return recursion == Recursion.LEFT_RIGHT_REC || recursion == Recursion.LEFT_REC || recursion == Recursion.RIGHT_REC;
	}
	
	public Recursion getRecursion() {
		return recursion;
	}
	
	public Associativity getAssociativity() {
		return associativity;
	}
	
	public AssociativityGroup getAssociativityGroup() {
		return associativityGroup;
	}
	
	public int getPrecedence() {
		return precedence;
	}
	
	public PrecedenceLevel getPrecedenceLevel() {
		return precedenceLevel;
	}
	
	public String getLabel() {
		return label;
	}
	
	public boolean hasLayout() {
		return layout != null;
	}
	
	@Override
	public String toString() {
		
		if(body == null) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(head).append(" ::= ");
		for(Symbol s : body) {
			sb.append(s).append(" ");
		}
		return sb.toString() + 
				" {" + associativity.name() + "," + precedence + "," + recursion + "} " + 
				(associativityGroup != null? associativityGroup + " " : "") +
				(precedenceLevel != null? precedenceLevel + " ": "") +
				(label != null? label : "");
	} 
	
	public boolean equals(Object obj) {
		
		if(this == obj)
			return true;
		
		if(!(obj instanceof Rule))
			return false;
		
		Rule other = (Rule) obj;
		
		return head.equals(other.head) && body == null ? other.body == null : body.equals(other.body);
	}
	
	@Override
	public int hashCode() {
		return HashFunctions.defaulFunction.hash(head.hashCode(), body == null ? 0 : body.hashCode());
	}
	
	public Position getPosition(int i) {
		if (i < 0)
			throw new IllegalArgumentException("i cannot be less than zero.");
		
		if (i > size())
			throw new IllegalArgumentException("i cannot be greater than the size.");
		
		return new Position(this, i);
	}
	
	public Position getPosition(int i, int j) {
		if (i < 0)
			throw new IllegalArgumentException("i cannot be less than zero.");
		
		if (i > size())
			throw new IllegalArgumentException("i cannot be greater than the size.");
		
		return new Position(this, i, j);
	}
	
	public Builder copyBuilder() {
		return new Builder(this);
	}
	
	public Builder copyBuilderButWithHead(Nonterminal nonterminal) {
		Builder builder = new Builder(this);
		builder.head = nonterminal;
		return builder;
	}
	
	public static Builder withHead(Nonterminal nonterminal) {
		return new Builder(nonterminal);
	}
	
	public static class Builder {
		
		private Nonterminal head;
		private List<Symbol> body;
		private Serializable object;
		private LayoutStrategy layoutStrategy = LayoutStrategy.INHERITED;
		private Nonterminal layout;
		
		private Recursion recursion = Recursion.UNDEFINED;
		
		private Associativity associativity = Associativity.UNDEFINED;
		private AssociativityGroup associativityGroup;
		
		private int precedence;
		private PrecedenceLevel precedenceLevel;
		
		private String label;

		public Builder(Nonterminal head) {
			this.head = head;
			this.body = new ArrayList<>();
		}
		
		public Builder(Rule rule) {
			this.head = rule.head;
			this.body = rule.body;
			this.object = rule.object;
			this.layoutStrategy = rule.layoutStrategy;
			this.layout = rule.layout;
			this.recursion = rule.recursion;
			this.associativity = rule.associativity;
			
			this.associativityGroup = rule.associativityGroup;
			this.precedence = rule.precedence;
			this.precedenceLevel = rule.precedenceLevel;
			
			this.label = rule.label;
		}
		
		public Builder addSymbol(Symbol symbol) {
			body.add(symbol);
			return this;
		}
		
		public Builder addSymbols(Symbol...symbols) {
			body.addAll(Arrays.asList(symbols));
			return this;
		}
		
		public Builder addSymbols(List<Symbol> symbols) {
			if (symbols == null) {
				body = null;
			} else {
				body.addAll(symbols);
			}
			return this;
		}
		
		public Builder setSymbols(List<Symbol> symbols) {
			body = symbols;
			return this;
		}
		
		public Builder setLayoutStrategy(LayoutStrategy layoutStrategy) {
			this.layoutStrategy = layoutStrategy;
			return this;
		}
		
		public Builder setObject(Serializable object) {
			this.object = object;
			return this;
		}
		
		public Builder setLayout(Nonterminal layout) {
			this.layout = layout;
			return this;
		}
		
		public Builder setRecursion(Recursion recursion) {
			this.recursion = recursion;
			return this;
		}
		
		public Builder setAssociativity(Associativity associativity) {
			this.associativity = associativity;
			return this;
		}
		
		public Builder setAssociativityGroup(AssociativityGroup associativityGroup) {
			this.associativityGroup = associativityGroup;
			return this;
		}
		
		public Builder setPrecedence(int precedence) {
			this.precedence = precedence;
			return this;
		}
		
		public Builder setPrecedenceLevel(PrecedenceLevel precedenceLevel) {
			this.precedenceLevel = precedenceLevel;
			return this;
		}
		
		public Builder setLabel(String label) {
			this.label = label;
			return this;
		}
		
		public Rule build() {
			return new Rule(this);
		}
	}

	@Override
	public String getConstructorCode() {
		return Rule.class.getSimpleName() + ".withHead(" + head.getConstructorCode() + ")" + 
				(body == null ? "" : body.stream().map(s -> ".addSymbol(" + s.getConstructorCode() + ")").collect(Collectors.joining())) +
				(layout == null ? "" : ".setLayout(" + layout.getConstructorCode() + ")") +
				(layoutStrategy == LayoutStrategy.INHERITED ? "" : ".setLayoutStrategy(" + layoutStrategy + ")") +
				
				".setRecursion(" + recursion.getConstructorCode() + ")" +
				
				".setAssociativity(" + associativity.getConstructorCode() + ")" +
				".setPrecedence(" + precedence + ")" +
				
				(associativityGroup != null? ".setAssociativityGroup(" + associativityGroup.getConstructorCode() + ")" : "") +
				(precedenceLevel != null? ".setPrecedenceLevel(" + precedenceLevel.getConstructorCode() + ")" : "") +
				
				(label != null? ".setLabel(\"" + label + "\")" : "") +
				
				".build()";
	}
}
