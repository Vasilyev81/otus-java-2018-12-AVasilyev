import com.google.gson.Gson;
import objects.*;
import org.slf4j.*;
import objecttojson.ObjectToJson;

import java.util.*;

public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	private static Gson gson = new Gson();
	public static void main(String[] args) throws Exception {

		int[] arrOfInt = {1, 2, 3, 4, 5, 6, 7, 8, 9};

		String[] arrOfString = {"jgilibln", "hkvetryct", "juytyu5rc", "tureuuih;b", "rtcyutuygkl"};

		List<String> stringList = new ArrayList<>();
		stringList.add("gfckbk");
		stringList.add("sseoeevonj");
		stringList.add("rwfuehflkdsn");
		stringList.add("skioajpm");
		stringList.add("sowoipwmrhu");

		Map<String,Integer> stringIntegerMap = new HashMap<>();
		stringIntegerMap.put("qiweuri", 1289983492);
		stringIntegerMap.put("oijosro;fnifref", 409584987);
		stringIntegerMap.put("uyyhcmn[wepejr", 429879870);

		Car car = new Car("black", 4, 4, 165, 2.7, new Car.SerialNumber("body", 938478273),
				new Car.SerialNumber("engine", 836480530));

		testPrint(arrOfInt);
		testPrint(arrOfString);
		testPrint(stringList);
		testPrint(stringIntegerMap);
		testPrint(car);
		testPrint(Collections.singletonList(1));
	}

	private static void testPrint(Object obj) throws Exception {
		logger.info(ObjectToJson.toJson(obj));
		logger.info(gson.toJson(obj));
	}
}