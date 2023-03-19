package learn.carbackend.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddCarRequest {
    @NotBlank
    @Size(min = 2, max = 30)
    private String brand, model, color, number;

    @NotBlank
    private int year1, price;

    private List<String> imageUuids;

    public AddCarRequest() {}

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getYear1() {
        return year1;
    }

    public void setYear1(int year1) {
        this.year1 = year1;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getImageUuids() {
        return imageUuids;
    }

    public void setImageUuids(List<String> imageUuids) {
        this.imageUuids = imageUuids;
    }
}
