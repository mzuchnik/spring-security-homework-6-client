package pl.mzuchnik.springsecurityhomework6client.model;

public class Car {


    private String name;
    private String carColor;

    public Car() {
    }

    public Car(String name, String carColor) {
        this.name = name;
        this.carColor = carColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", color=" + carColor +
                '}';
    }
}
