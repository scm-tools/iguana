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

package org.iguana.traversal;

import org.iguana.sppf.NonterminalNode;
import org.iguana.sppf.PackedNode;
import org.iguana.sppf.SPPFNode;

/**
 * 
 * 
 * @author Ali Afroozeh
 *
 */
public class SPPFVisitorUtil {
	
	public static void visitChildren(SPPFNode node, SPPFVisitor visitor) {
		for(SPPFNode child : node.getChildren()) {
			child.accept(visitor);
		}
	}

	/**
	 * Removes the intermediate nodes under a nonterminal symbol node.
	 * 
	 * If the intermediate node is not ambiguous, the node is simply replaced
	 * by its children.
	 * <br>
	 * If the intermediate node is ambiguous, children of each of its packed nodes
	 * and its other children are merged to create new packed nodes for the parent.
	 * 
	 *               N                                          N
	 *             /   \                                    /        \
	 *            I     Other   =>                        P            P
	 *          /   \                                   / | \        / | \
	 *         P     P                               c1 c2 Other  c3 c4 Other
	 *        / \   / \
	 *       c1 c2 c3 c4
	 *       
	 * The parent node is visited again to remove any newly introduced intermediate node. 
	 * The process terminates when all intermediate nodes are removed.
	 *       
	 */
	public static void removeIntermediateNode(NonterminalNode node) {
		
//		if(node.getChildAt(0) instanceof IntermediateNode) {
//			
//			IntermediateNode intermediateNode = (IntermediateNode) node.getChildAt(0);
//
//			if(intermediateNode.isAmbiguous()) {
//				List<NonPackedNode> restOfChildren = new ArrayList<>();
//
//				node.removeChild(intermediateNode);
//
//				while(node.childrenCount() > 0) {
//					restOfChildren.add(node.getChildAt(0));
//					node.removeChild(node.getChildAt(0));
//				}
//
//				for(PackedNode child : intermediateNode.getChildren()) {
//					// For each packed node of the intermediate node create a new packed node
//					PackedNode newPackedNode = null; //new PackedNode(node.getFirstPackedNodeGrammarSlot(), node.childrenCount(), node);
//					for(NonPackedNode sn : child.getChildren()) {
//						newPackedNode.addChild(sn);					
//					}
//
//					for(NonPackedNode c : restOfChildren) {
//						newPackedNode.addChild(c);
//					}
//
//					node.addChild(newPackedNode);
//					removeIntermediateNode(newPackedNode);
//				}
//
//			} else {
//				node.replaceWithChildren(intermediateNode);
//				removeIntermediateNode(node);
//			}
//		}
	}


	/**
	 * Removes the intermediate nodes under a packed node. 
     *
	 * If the intermediate node is not ambiguous, the node is simply replaced
	 * by its children. 
	 * 
	 * If the intermediate node is ambiguous, then the parent, which is a 
	 * packed node, should be replaced by n new packed nodes, where 
	 * n is the number of packed nodes under the intermediate node.
	 * The children of each packed node under the intermediate node are
	 * merged with other children of the parent packed node and form a 
	 * new packed node which will be added to the parent of the parent packed
	 * node. 
	 *  
	 * 
	 *                  N                   
	 *                /   \
	 *               P     P...                                        N         
	 *             /   \                                    /          |          \
	 *            I     Other   =>                        P            P          Other
	 *          /   \                                   / | \        / | \        
	 *         P     P                               c1 c2 Other  c3 c4 Other     
	 *        / \   / \
	 *       c1 c2 c3 c4
	 *       
	 *       
	 * The parent or the original parent packed node is visited again to 
	 * remove any newly introduced intermediate node. 
	 * The process terminates when all intermediate nodes are removed.
	 * 
	 */
	public static void removeIntermediateNode(PackedNode parent) {
//		if(parent.getChildAt(0) instanceof IntermediateNode) {
//
//			IntermediateNode intermediateNode = (IntermediateNode) parent.getChildAt(0);
//
//			if(intermediateNode.isAmbiguous()) {
//				NonterminalOrIntermediateNode parentOfPackedNode = (NonterminalOrIntermediateNode) parent.getParent();
//
//				List<SPPFNode> restOfChildren = new ArrayList<>();
//
//				parentOfPackedNode.removeChild(parent);
//				parent.removeChild(intermediateNode);
//
//				for(SPPFNode sn : parent.getChildren()) {
//					restOfChildren.add(sn);
//				}
//
//				for(SPPFNode child : intermediateNode.getChildren()) {
//					// For each packed node of the intermediate node create a new packed node
//					PackedNode pn = (PackedNode) child;
//					PackedNode newPackedNode = null; //new PackedNode(parentOfPackedNode.getFirstPackedNodeGrammarSlot(), parentOfPackedNode.childrenCount(), parentOfPackedNode);
//					for(SPPFNode sn : pn.getChildren()) {
//						newPackedNode.addChild(sn);					
//					}
//
//					for(SPPFNode c : restOfChildren) {
//						newPackedNode.addChild(c);
//					}
//
//					parentOfPackedNode.addChild(newPackedNode);
//					removeIntermediateNode(newPackedNode);
//				}
//
//			} else {
//				parent.replaceWithChildren(intermediateNode);
//				removeIntermediateNode(parent);
//			}
//		}
	}

	public static void removeCollapsibleNode(NonterminalNode node) {
		
		// Check for keyword nodes that their children are not yet created.
		if(node.childrenCount() == 0) {
			return;
		}
		
//		if(!node.isAmbiguous()) {
//			if(node.getChildAt(node.childrenCount() - 1) instanceof CollapsibleNode) {
//				CollapsibleNode child = (CollapsibleNode) node.getChildAt(node.childrenCount() - 1);
//				removeIntermediateNode(child);
//				node.replaceWithChildren(child);
//				
//				// Push the saved object to the parent.
////				LastGrammarSlot slot = (LastGrammarSlot) node.getFirstPackedNodeGrammarSlot();
////				slot.setObject(((LastGrammarSlot)child.getFirstPackedNodeGrammarSlot()).getObject());
//				
//				removeCollapsibleNode(node);
//			}
//		}
	}
	
}
