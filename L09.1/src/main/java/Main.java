import com.google.gson.Gson;
import objects.*;
import org.slf4j.*;
import objecttojson.ObjectToJson;

import java.util.*;

public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();

		Integer[] arrOfInt = {1, 2, 3, 4, 5, 6, 7, 8, 9};

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

		Car car = new Car("black", 4, 4, 165, 2.7, new SerialNumber[]{new SerialNumber("body", 938478273),
				new SerialNumber("engine", 836480530)});

		logger.info(ObjectToJson.toJson(arrOfInt));
		logger.info(gson.toJson(arrOfInt));

		logger.info(ObjectToJson.toJson(arrOfString));
		logger.info(gson.toJson(arrOfString));

		logger.info(ObjectToJson.toJson(stringList));
		logger.info(gson.toJson(stringList));

		logger.info(ObjectToJson.toJson(stringIntegerMap));
		logger.info(gson.toJson(stringIntegerMap));

		logger.info(ObjectToJson.toJson(car));
		logger.info(gson.toJson(car));

		logger.info(ObjectToJson.toJson(new Date().getTime()));
		logger.info(gson.toJson(new Date().getTime()));
	}
}
