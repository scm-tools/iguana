package org.jgll.grammar.symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgll.grammar.condition.Condition;
import org.jgll.regex.Sequence;
import org.jgll.regex.automaton.Automaton;
import org.jgll.util.Input;

public class Keyword extends AbstractRegularExpression {

	private static final long serialVersionUID = 1L;
	
	private final Sequence<Character> seq;
	
	public static Keyword from(String s) {
		return from(Input.toIntArray(s));
	}
	
	public static Keyword from(int[] chars) {
		return new Builder(toCharSequence(chars)).build();
	}
	
	public Keyword(Sequence<Character> seq, String label, Set<Condition> conditions, Object object) {
		super(seq.getName(), label, conditions, object);
		this.seq = seq;
	}
	
	private static Sequence<Character> toCharSequence(int[] chars) {
		List<Character> list = new ArrayList<>();
		for(int c : chars) {
			list.add(Character.from(c));
		}
		
		return Sequence.from(list);
	}
		
	public int size() {
		return seq.size();
	}
	
	public Sequence<Character> getSequence() {
		return seq;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Keyword)) {
			return false;
		}
		
		Keyword other = (Keyword) obj;
		
		return seq.equals(other.seq);
	}
	
	@Override
	public int hashCode() {
		return seq.hashCode();
	}
	
	@Override
	protected Automaton createAutomaton() {
		return seq.getAutomaton();
	}
	
	@Override
	public boolean isNullable() {
		return false;
	}

	@Override
	public Set<Range> getFirstSet() {
		return seq.getFirstSet();
	}
	
	@Override
	public Set<Range> getNotFollowSet() {
		return Collections.emptySet();
	}

	public static class Builder extends SymbolBuilder<Keyword> {
		
		private Sequence<Character> seq;
				
		public Builder(String s) {
			this.seq = toCharSequence(Input.toIntArray(s));
		}
		
		public Builder(Sequence<Character> seq) {
			this.seq = seq;
		}
		
		public Builder(Keyword keyword) {
			super(keyword);
			this.seq = keyword.seq;
		}

		@Override
		public Keyword build() {
			return new Keyword(seq, label, conditions, object);
		}
		
	}

	@Override
	public SymbolBuilder<Keyword> builder() {
		return new Builder(this);
	}
	
	@Override
	public String toCode() {
		StringBuilder sb = new StringBuilder();
		sb.append("new Keyword(")
		  .append(seq.toCode() + ", ")
		  .append(label + ", ")
		  .append("null")
		  .append(")");
		return sb.toString();
	}

}
