package co.com.segurosalfa.siniestros.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ObjectsUtil {

	@SuppressWarnings("unchecked")
	public static <T> T mergeObjects(T first, T second) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
	    Class<?> clazz = first.getClass();
	    Field[] fields = clazz.getDeclaredFields();
	    Object returnValue = clazz.getDeclaredConstructor().newInstance();
	    for (Field field : fields) {
	        field.setAccessible(true);
	        Object value1 = field.get(first);
	        Object value2 = field.get(second);
	        Object value = (value1 != null) ? value1 : value2;
	        field.set(returnValue, value);
	    }
	    return (T) returnValue;
	}
}
