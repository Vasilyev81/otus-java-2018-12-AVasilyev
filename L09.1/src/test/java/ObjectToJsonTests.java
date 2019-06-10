import com.google.gson.Gson;
import objects.Car;
import objecttojson.ObjectToJson;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectToJsonTests {
	private static Gson gson;

	@BeforeAll
	static void init() {
		gson = new Gson();
	}

	@Test
	void arrOfPrim_int_Test() throws Exception {
		int[] arrOf_int = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		assertEquals(gson.toJson(arrOf_int), ObjectToJson.toJson(arrOf_int));
	}

	@Test
	void arrOfIntTest() throws Exception {
		Integer[] arrOfInt = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		assertEquals(gson.toJson(arrOfInt), ObjectToJson.toJson(arrOfInt));
	}

	@Test
	void arrOfStringTest() throws Exception {
		String[] arrOfString = {"jgilibln", "hkvetryct", "juytyu5rc", "tureuuih;b", "rtcyutuygkl"};
		assertEquals(gson.toJson(arrOfString), ObjectToJson.toJson(arrOfString));
	}

	@Test
	void listOfStringsTest() throws Exception {
		List<String> stringList = new ArrayList<>();
		stringList.add("gfckbk");
		stringList.add("sseoeevonj");
		stringList.add("rwfuehflkdsn");
		stringList.add("skioajpm");
		stringList.add("sowoipwmrhu");
		assertEquals(gson.toJson(stringList), ObjectToJson.toJson(stringList));
	}

	@Test
	void mapOfStringIntegerTest() throws Exception {
		Map<String,Integer> stringIntegerMap = new HashMap<>();
		stringIntegerMap.put("qiweuri", 1289983492);
		stringIntegerMap.put("oijosro;fnifref", 409584987);
		stringIntegerMap.put("uyyhcmn[wepejr", 429879870);
		assertEquals(gson.toJson(stringIntegerMap), ObjectToJson.toJson(stringIntegerMap));
	}

	@Test
	void customClassTest() throws Exception {
		Car car = new Car("black", 4, 4, 165, 2.7,new Car.SerialNumber("body", 938478273),
				new Car.SerialNumber("engine", 836480530));
		assertEquals(gson.toJson(car), ObjectToJson.toJson(car));
	}

	@Test
	void singletonListTest() throws Exception {
		List singleton = Collections.singletonList(1);
		assertEquals(gson.toJson(singleton), ObjectToJson.toJson(singleton));
	}
}
