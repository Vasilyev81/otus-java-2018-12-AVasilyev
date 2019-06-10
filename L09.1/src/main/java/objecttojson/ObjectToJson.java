package objecttojson;

import org.slf4j.*;

import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class ObjectToJson {
	private static Logger log = LoggerFactory.getLogger(ObjectToJson.class);

	public static String toJson(Object obj) throws Exception {
		if (obj == null) return null;
		if (isString(obj)) {
			return "\"" + obj.toString() + "\"";
		}
		if (isSimpleType(obj.getClass())) {
			return obj.toString();
		}
		if (isArray(obj)) {
			return "[" + printArray(obj) + "]";
		}
		if (obj instanceof List) {
			return "[" + printList((List) obj) + "]";
		}
		if (obj instanceof Set) {
			return "{" + printSet((Set) obj) + "}";
		}
		if (obj instanceof Map) {
			return "{" + printMap((Map) obj) + "}";
		}
		return "{" + printObjFields(obj) + "}";
	}

	private static String printObjFields(Object obj) throws Exception {
		if (obj == null) return null;
		StringBuilder sb = new StringBuilder();
		List<String> fields = printFieldsOfInstance(obj, obj.getClass());
		if (fields.isEmpty()) {
			log.info("No fields in object");
		} else {
			sb.append(String.join(",", fields));
		}
		return sb.toString();
	}

	private static List<String> printFieldsOfInstance(Object obj, Class clazz) throws Exception {
		List<String> strings = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		if (fields.length > 0) {
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers())) {
					field.setAccessible(true);
					String val = toJson(field.get(obj));
					if (val != null) {
						strings.add("\"" + field.getName() + "\":" + val);
					}
					field.setAccessible(false);
				}
			}
		}
		if (!clazz.getName().contains("Object")) {
			printFieldsOfInstance(obj, clazz.getSuperclass());
		}
		return strings;
	}

	private static boolean isSimpleType(Class clazz) {
		if (clazz == null) return false;
		return clazz.equals(Byte.class) ||
				clazz.equals(Short.class) ||
				clazz.equals(Integer.class) ||
				clazz.equals(Long.class) ||
				clazz.equals(Float.class) ||
				clazz.equals(Double.class) ||
				clazz.equals(Boolean.class);
	}

	private static boolean isString(Object obj) {
		return obj.getClass().getName().equals("java.lang.String");
	}

	private static boolean isArray(Object obj) {
		return obj.getClass().isArray();
	}

	private static String printMap(Map<Object, Object> map) throws Exception {
		Set<Map.Entry<Object, Object>> entries = map.entrySet();
		List<String> lines = new ArrayList<>();
		for (Map.Entry<Object, Object> entry : entries) {
			String key;
			if (isSimpleType(entry.getKey().getClass())) {
				key = "\"" + toJson(entry.getKey()) + "\"";
			} else {
				key = toJson(entry.getKey());
			}
			String value = toJson(entry.getValue());
			lines.add(String.format("%1$s:%2$s", key, value));
		}
		String delimiter = ",";
		return String.join(delimiter, lines);
	}

	private static String printList(List<Object> list) throws Exception {
		if (list == null) return null;
		return printArray(list.toArray(new Object[]{}));
	}

	private static String printSet(Set<Object> set) throws Exception {
		if (set == null) return null;
		return printArray(set.toArray(new Object[]{}));
	}

	private static String printArray(Object object) throws Exception {
		Class<?> clazz = object.getClass().getComponentType();
		StringBuilder builder = new StringBuilder();
		if (clazz.getSimpleName().equals("boolean")) {
			boolean[] booleans = (boolean[]) object;
			builder.append(printArray(Helper.wrapArray(booleans)));
		} else if (clazz.getSimpleName().equals("byte")) {
			byte[] bytes = (byte[]) object;
			builder.append(printArray(Helper.wrapArray(bytes)));
		} else if (clazz.getSimpleName().equals("short")) {
			short[] shorts = (short[]) object;
			builder.append(printArray(Helper.wrapArray(shorts)));
		} else if (clazz.getSimpleName().equals("int")) {
			int[] ints = (int[]) object;
			builder.append(printArray(Helper.wrapArray(ints)));
		} else if (clazz.getSimpleName().equals("long")) {
			long[] longs = (long[]) object;
			builder.append(printArray(Helper.wrapArray(longs)));
		} else if (clazz.getSimpleName().equals("float")) {
			float[] floats = (float[]) object;
			builder.append(printArray(Helper.wrapArray(floats)));
		} else if (clazz.getSimpleName().equals("double")) {
			double[] doubles = (double[]) object;
			builder.append(printArray(Helper.wrapArray(doubles)));
		} else if (clazz.getSimpleName().equals("String")) {
			String[] strings = (String[]) object;
			builder.append(printArray(strings));
		} else {
			Object[] objects = (Object[]) object;
			builder.append(printArray(objects));
		}
		return builder.toString();
	}

	private static <T> String printArray(T[] array) throws Exception {
		if (array == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		List<String> strings = new ArrayList<>();
		for (T element : array) {
			strings.add(toJson(element));
		}
		if (!strings.isEmpty()) {
			builder.append(String.join(",", strings));
		}
		return builder.toString();
	}

	static class Helper {
		static Boolean[] wrapArray(boolean[] booleans) {
			if (booleans == null) return null;
			Boolean[] wrapped = new Boolean[booleans.length];
			for (int i = 0; i < booleans.length; i++) {
				wrapped[i] = booleans[i];
			}
			return wrapped;
		}

		static Byte[] wrapArray(byte[] bytes) {
			if (bytes == null) return null;
			Byte[] wrapped = new Byte[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				wrapped[i] = bytes[i];
			}
			return wrapped;
		}

		static Short[] wrapArray(short[] shorts) {
			if (shorts == null) return null;
			Short[] wrapped = new Short[shorts.length];
			for (int i = 0; i < shorts.length; i++) {
				wrapped[i] = shorts[i];
			}
			return wrapped;
		}

		static Integer[] wrapArray(int[] ints) {
			if (ints == null) return null;
			Integer[] wrapped = new Integer[ints.length];
			for (int i = 0; i < ints.length; i++) {
				wrapped[i] = ints[i];
			}
			return wrapped;
		}

		static Long[] wrapArray(long[] longs) {
			if (longs == null) return null;
			Long[] wrapped = new Long[longs.length];
			for (int i = 0; i < longs.length; i++) {
				wrapped[i] = longs[i];
			}
			return wrapped;
		}

		static Float[] wrapArray(float[] floats) {
			if (floats == null) return null;
			Float[] wrapped = new Float[floats.length];
			for (int i = 0; i < floats.length; i++) {
				wrapped[i] = floats[i];
			}
			return wrapped;
		}

		static Double[] wrapArray(double[] doubles) {
			if (doubles == null) return null;
			Double[] wrapped = new Double[doubles.length];
			for (int i = 0; i < doubles.length; i++) {
				wrapped[i] = doubles[i];
			}
			return wrapped;
		}
	}
}
