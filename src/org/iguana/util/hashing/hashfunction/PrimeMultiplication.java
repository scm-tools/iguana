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

package org.iguana.util.hashing.hashfunction;

public class PrimeMultiplication implements HashFunction {

	private static final long serialVersionUID = 1L;

	@Override
	public int hash(int k) {
		return k;
	}

	@Override
	public int hash(int k1, int k2) {
		int result = 17;
		result = 31 * result + k1;
		result = 31 * result + k2;
		return result;
	}

	@Override
	public int hash(int k1, int k2, int k3) {
		int result = 17;
		result = 31 * result + k1;
		result = 31 * result + k2;
		result = 31 * result + k3;
		return result;
	}

	@Override
	public int hash(int k1, int k2, int k3, int k4) {
		int result = 17;
		result = 31 * result + k1;
		result = 31 * result + k2;
		result = 31 * result + k3;
		result = 31 * result + k4;
		return result;
	}

	@Override
	public int hash(int k1, int k2, int k3, int k4, int k5) {
		int result = 17;
		result = 31 * result + k1;
		result = 31 * result + k2;
		result = 31 * result + k3;
		result = 31 * result + k4;
		result = 31 * result + k5;
		return result;
	}

	@Override
	public int hash(int... keys) {
		int result = 17;
		for (int key : keys) {
			result = 31 * result + key;
		}
		return result;
	}

}
