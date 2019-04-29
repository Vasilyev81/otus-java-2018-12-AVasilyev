import com.google.gson.Gson;
import objects.Car;
import otj.ObjectToJson;

import java.util.ArrayList;
import java.util.List;

public class MyObjectToJson {
	public static void main(String[] args) {

		ObjectToJson otj = new ObjectToJson();
		Gson gson = new Gson();


		Integer[] arrOfInt = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		String[] arrOfString = {"jgilibln", "hkvetryct", "juytyu5rc", "tureuuih;b", "rtcyutuygkl"};
		List<String> stringList = new ArrayList<>();
		stringList.add("gfckbk");
		stringList.add("sseoeevonj");
		stringList.add("rwfuehflkdsn");
		stringList.add("skioajpm");
		stringList.add("sowoipwmrhu");
		Car car = new Car();
		System.out.println("=================================");
		System.out.println(otj.toJson(arrOfInt));
		System.out.println(gson.toJson(arrOfInt));
		System.out.println(otj.toJson(arrOfString));
		System.out.println(gson.toJson(arrOfString));
		System.out.println(otj.toJson(stringList));
		System.out.println(gson.toJson(stringList));
		System.out.println(gson.toJson(car));



	}
}
