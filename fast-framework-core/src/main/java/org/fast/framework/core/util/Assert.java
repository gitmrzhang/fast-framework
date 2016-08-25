package org.fast.framework.core.util;

public class Assert {
	
	public static void notNull(Object object, String message){
		if(object == null){
			throw new IllegalArgumentException(message);
		}
	}

}
