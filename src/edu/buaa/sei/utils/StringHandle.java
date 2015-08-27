package edu.buaa.sei.utils;

public class StringHandle {
	public static String delUnusedStr(String str) {
		String rstr = null;
		
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '{' || str.charAt(i) == '}' || str.charAt(i) == ' ') {
				continue;
			} else {
				if (rstr == null)
					rstr = String.valueOf(str.charAt(i));
				else
					rstr += str.charAt(i);
			}
		}
		
		return rstr;
	}
	
	public static int getKbFromStr(String str) {
		char metric = str.charAt(str.length()-1);
		int multiSize = 0;
		if (metric == 'M' || metric == 'm')
			multiSize = 1024;
		else if (metric == 'G' || metric == 'g')
			multiSize = 1024*1024;
		else if (metric == 'K' || metric == 'k')
			multiSize = 1;
		else
			System.out.println("no such metric : " + metric);
		
		String num = str.substring(0, str.length()-1);
		
		int rv = Integer.valueOf(num);
		
		return rv*multiSize;
	}
}
