package com.frc.games.guessnumber.core;

import java.util.Scanner;

import org.junit.Test;

public class GuessNumber {
	protected String id;
	protected String answer = "1234";
	protected int count[] = new int[10];
	
	public String createNumber(boolean allowRepeat, int length) {
		if (allowRepeat) {
			answer = createNumberAllowRepeat(length);
		} else {
			answer = createNumberNotAllowRepeat(length);
		}
		for (int i = 0; i < 10; i++) {
			count[i] = 0;
		}
		for (int i = 0; i < answer.length(); i++) {
			char ch = answer.charAt(i);
			int x = (int)(ch) - (int)('0');
			count[x]++;
		}
		return answer;
	}
	
	public String guess(String number) {
		if (number == null || number.length() != answer.length()) {
			return "Length not match";
		}
		int a = 0, b = 0;
		int c[] = new int[10];
		for (int i = 0; i < 10; i++) {
			c[i] = 0;
		}
		for (int i = 0; i < number.length(); i++) {
			char ch = number.charAt(i);
			if (ch == answer.charAt(i)) {
				a++;
			}
			int x = (int)(ch) - (int)('0');
			c[x]++;
		}
		for (int i = 0; i < 10; i++) {
			b += c[i] < count[i] ? c[i] : count[i];
		}
		b -= a;
		String ans = String.format("%dA %dB", a, b);
		return ans;
	}
	
	public String createNumberAllowRepeat(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int x = (int)Math.round(Math.random() * 100) % 10;
			sb.append(x);
		}
		return sb.toString();
	}
	
	public String createNumberNotAllowRepeat(int length) {
		if (length > 10) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		boolean b[] = new boolean[10];
		for (int i = 0; i < 10; i++) {
			b[i] = false;
		}
		for (int i = 0; i < length; i++) {
			int x = (int)Math.round(Math.random() * 100) % 10;
			while (b[x]) {
				x = (int)Math.round(Math.random() * 100) % 10;
			}
			b[x] = true;
			sb.append(x);
		}
		return sb.toString();
	}

	@Test
	public void startTest() {
		Scanner cin = new Scanner(System.in);
		createNumber(false, 4);
		while (cin.hasNext()) {
			String number = cin.next();
			String rs = guess(number);
			System.out.println(rs);
		}
	}
}
