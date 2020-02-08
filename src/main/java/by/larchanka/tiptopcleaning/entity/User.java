package by.larchanka.tiptopcleaning.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class User implements Serializable, Cloneable {
    private long id;
    private UserType type;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private BigDecimal money;
    private String phoneNumber;
    private String street;
    private String house;
    private String apartment;
    private String entrance;
    private String floor;
    private String intercomCode;

    public User(UserType type, String email, String password, String firstName,
                String lastName) {
        this.type = type;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(UserType type, String email, String firstName, String lastName) {
        this.type = type;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getIntercomCode() {
        return intercomCode;
    }

    public void setIntercomCode(String intercomCode) {
        this.intercomCode = intercomCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (type != user.type) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (money != null ? !money.equals(user.money) : user.money != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
        if (street != null ? !street.equals(user.street) : user.street != null) return false;
        if (house != null ? !house.equals(user.house) : user.house != null) return false;
        if (apartment != null ? !apartment.equals(user.apartment) : user.apartment != null) return false;
        if (entrance != null ? !entrance.equals(user.entrance) : user.entrance != null) return false;
        if (floor != null ? !floor.equals(user.floor) : user.floor != null) return false;
        return intercomCode != null ? intercomCode.equals(user.intercomCode) : user.intercomCode == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
        result = 31 * result + (entrance != null ? entrance.hashCode() : 0);
        result = 31 * result + (floor != null ? floor.hashCode() : 0);
        result = 31 * result + (intercomCode != null ? intercomCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", money=").append(money);
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", house='").append(house).append('\'');
        sb.append(", apartment='").append(apartment).append('\'');
        sb.append(", entrance='").append(entrance).append('\'');
        sb.append(", floor='").append(floor).append('\'');
        sb.append(", intercomCode='").append(intercomCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
