package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Car {
	private final String color;
	private final int doors;
	private final int drive;
	private final int maxSpeed;
	private final double engineVolume;
	private final List<SerialNumber> serialNumbers;

	public Car(String color, int doors, int drive, int maxSpeed, double engineVolume, SerialNumber... serialNumbers) {
		this.color = color;
		this.doors = doors;
		this.drive = drive;
		this.maxSpeed = maxSpeed;
		this.engineVolume = engineVolume;
		this.serialNumbers = new ArrayList<>();
		this.serialNumbers.addAll(Arrays.asList(serialNumbers));
	}

	public static class SerialNumber {
		private String carPart;
		private int serialNumber;

		public SerialNumber(String carPart, int serialNumber) {
			this.carPart = carPart;
			this.serialNumber = serialNumber;
		}

		public String getCarPart() {
			return carPart;
		}

		public int getSerialNumber() {
			return serialNumber;
		}
	}
}
