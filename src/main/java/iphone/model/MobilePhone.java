package iphone.model;

import lombok.Data;

@Data
public class MobilePhone {
    private Long id;
    private String brand;
    private String model;
    private Integer year;

    // No-arg constructor required by frameworks / usage
    public MobilePhone() {
    }

    // Constructor used throughout the code (brand, model, year)
    public MobilePhone(String brand, String model, Integer year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    // Full-arg constructor (optional, kept for completeness)
    public MobilePhone(Long id, String brand, String model, Integer year) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    @Override
    public String toString() {
        return "MobilePhone{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}
