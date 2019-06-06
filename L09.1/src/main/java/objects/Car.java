package objects;

public class Car {
	private final String color;
	private final int doors;
	private final int drive;
	private final int maxSpeed;
	private final double engineVolume;
	private final SerialNumber[] serialNumbers;

	public Car(String color, int doors, int drive, int maxSpeed, double engineVolume, SerialNumber[] serialNumbers) {
		this.color = color;
		this.doors = doors;
		this.drive = drive;
		this.maxSpeed = maxSpeed;
		this.engineVolume = engineVolume;
		this.serialNumbers = serialNumbers;
	}
}
