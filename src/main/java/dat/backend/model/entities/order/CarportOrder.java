package dat.backend.model.entities.order;

import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.item.ToolRoom;
import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Employee;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class CarportOrder {

    private int id;
    private Address address;
    private Optional<Employee> employee;
    private Customer customer;
    private OrderStatus orderStatus;
    private Roof roof;
    private Optional<String> remarks;
    private float length;
    private float width;
    private float minHeight;
    private Optional<ToolRoom> toolRoom;
    private Optional<Float> price;

    public CarportOrder(int id, Address address, Optional<Employee> employee, Customer customer, OrderStatus orderStatus, Roof roof, Optional<String> remarks, float length, float width, float minHeight, Optional<ToolRoom> toolRoom, Optional<Float> price) {
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
    }

    public CarportOrder(int id, Address address, Optional<Employee> employee, Customer customer, OrderStatus orderStatus, Roof roof, Optional<String> remarks, float length, float width, float minHeight, Optional<ToolRoom> toolRoom) {
        this(id, address, employee, customer, orderStatus, roof, remarks, length, width, minHeight, toolRoom, Optional.empty());
    }

    public CarportOrder(int id, Address address, Customer customer, OrderStatus orderStatus, Roof roof, float length, float width, float minHeight) {
        this(id, address, Optional.empty(), customer, orderStatus, roof, Optional.empty(), length, width, minHeight, Optional.empty());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Optional<Employee> getEmployee() {
        return this.employee;
    }

    public void setEmployee(Optional<Employee> employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Roof getRoof() {
        return this.roof;
    }

    public void setRoof(Roof roof) {
        this.roof = roof;
    }

    public Optional<String> getRemarks() {
        return this.remarks;
    }

    public void setRemarks(Optional<String> remarks) {
        this.remarks = remarks;
    }

    public float getLength() {
        return this.length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getMinHeight() {
        return this.minHeight;
    }

    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    public Optional<ToolRoom> getToolRoom() {
        return this.toolRoom;
    }

    public void setToolRoom(Optional<ToolRoom> toolRoom) {
        this.toolRoom = toolRoom;
    }

    public Optional<Float> getPrice() {
        return this.price;
    }

    public void setPrice(Optional<Float> price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CarportOrder)) return false;
        CarportOrder carportOrder = (CarportOrder) other;
        return this.getId() == carportOrder.getId() &&
                this.getAddress().equals(carportOrder.getAddress()) &&
                this.getCustomer().equals(carportOrder.getCustomer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getAddress(), this.getEmployee(), this.getCustomer(), this.getOrderStatus(), this.getRoof(), this.getRemarks(), this.getLength(), this.getWidth(), this.getMinHeight(), this.getToolRoom(), this.getPrice());
    }

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
                '}';
    }
}