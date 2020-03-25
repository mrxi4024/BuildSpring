package ioc.bean;

/**
 * @author Yixi Wang
 *         Created by sinosoft on 2020/3/25.
 */
public class Wheel {
    private String brand;
    private String specification ;

    public Wheel() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
