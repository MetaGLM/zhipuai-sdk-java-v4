package com.zhipu.oapi.utils;

public class PrintStringDiff {

	String previous = "";

	public void accept(String str) {
		if (str.startsWith(previous)) {
			System.out.print(str.substring(previous.length()));
			System.out.flush();
		} else {
			int lastLineIndex = str.lastIndexOf('\n') + 1;
			if (previous.startsWith(str.substring(0, lastLineIndex))) {
				System.out.printf("\r%s", str.substring(lastLineIndex));
				System.out.flush();
			} else {
				System.out.println();
				System.out.println("[Previous]" + previous);
			}
		}
		previous = str;
	}

	public void reset() {
		previous = "";
	}

}
