package dat.backend.model.entities.order;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.item.ToolRoom;
import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Employee;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class CarportOrder {

    private final int id;
    private final Customer customer;

    private Address address;
    private Optional<Employee> employee;
    private OrderStatus orderStatus;
    private Roof roof;
    private Optional<String> remarks;
    private float length;
    private float width;
    private float minHeight;
    private Optional<ToolRoom> toolRoom;
    private Optional<Float> price;
    private float priceFromPartsList;
    private Date createdOn;

    public CarportOrder(int id, Address address, Optional<Employee> employee, Customer customer, OrderStatus orderStatus, Roof roof, Optional<String> remarks, float length, float width, float minHeight, Optional<ToolRoom> toolRoom, Optional<Float> price, float priceFromPartsList) {
        this.id = id;
        this.address = address;
        this.employee = employee;
        this.customer = customer;
        this.orderStatus = orderStatus;
        this.roof = roof;
        this.remarks = remarks;
        this.length = length;
        this.width = width;
        this.minHeight = minHeight;
        this.toolRoom = toolRoom;
        this.price = price;
        this.priceFromPartsList = priceFromPartsList;
    }

    public String getFormattedPrice() {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.GERMAN);
        return formatter.format(this.price.orElse(0.0f));
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getId() {
        return this.id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Customer getCustomer() {
        return this.customer;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Address getAddress() {
        return this.address;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setAddress(Address address) {
        this.address = address;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Optional<Employee> getEmployee() {
        return this.employee;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setEmployee(Optional<Employee> employee) {
        this.employee = employee;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Roof getRoof() {
        return this.roof;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setRoof(Roof roof) {
        this.roof = roof;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Optional<String> getRemarks() {
        return this.remarks;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setRemarks(Optional<String> remarks) {
        this.remarks = remarks;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getLength() {
        return this.length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setLength(float length) {
        this.length = length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getWidth() {
        return this.width;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setWidth(float width) {
        this.width = width;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getMinHeight() {
        return this.minHeight;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Optional<ToolRoom> getToolRoom() {
        return this.toolRoom;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setToolRoom(Optional<ToolRoom> toolRoom) {
        this.toolRoom = toolRoom;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Optional<Float> getPrice() {
        return this.price;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setPrice(Optional<Float> price) {
        this.price = price;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getPriceFromPartsList() {
        return this.priceFromPartsList;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setPriceFromPartsList(float priceFromPartsList) {
        this.priceFromPartsList = priceFromPartsList;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Date getCreatedOn() {
        return this.createdOn;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CarportOrder)) return false;
        CarportOrder carportOrder = (CarportOrder) other;
        return this.getId() == carportOrder.getId() &&
                this.getCustomer().equals(carportOrder.getCustomer());
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getAddress(), this.getEmployee(), this.getCustomer(), this.getOrderStatus(), this.getRoof(), this.getRemarks(), this.getLength(), this.getWidth(), this.getMinHeight(), this.getToolRoom(), this.getPrice());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "CarportOrder{" +
                "id=" + this.id +
                ", address=" + this.address +
                ", employee=" + this.employee +
                ", customer=" + this.customer +
                ", orderStatus=" + this.orderStatus +
                ", roof=" + this.roof +
                ", remarks=" + this.remarks +
                ", length=" + this.length +
                ", width=" + this.width +
                ", minHeight=" + this.minHeight +
                ", toolRoom=" + this.toolRoom +
                ", price=" + this.price +
                ", createdOn=" + this.createdOn +
                "}";
    }
}