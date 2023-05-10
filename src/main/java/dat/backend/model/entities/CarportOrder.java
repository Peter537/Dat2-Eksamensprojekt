package dat.backend.model.entities;

import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Employee;

import java.util.List;

public class CarportOrder {

    private int id;
    private Address address;
    private Employee employee;
    private Customer customer;
    private Roof roof;
    private List<Lumber> lumberList;
    private List<String> remarks;
    private int length;
    private int width;
    private int minHeight;
    private int toolRoomWidth;
    private int toolRoomLength;
    private int price;

    public CarportOrder(int id, Address address, Employee employee, Customer customer, Roof roof, List<Lumber> lumberList, List<String> remarks, int length, int width, int minHeight, int toolRoomWidth, int toolRoomLength) {
        this.id = id;
        this.address = address;
        this.employee = employee;
        this.customer = customer;
        this.roof = roof;
        this.lumberList = lumberList;
        this.remarks = remarks;
        this.length = length;
        this.width = width;
        this.minHeight = minHeight;
        this.toolRoomWidth = toolRoomWidth;
        this.toolRoomLength = toolRoomLength;
    }

    public CarportOrder(Address address, Employee employee, Customer customer, Roof roof, List<Lumber> lumberList, List<String> remarks, int length, int width, int minHeight, int toolRoomWidth, int toolRoomLength) {
        this.address = address;
        this.employee = employee;
        this.customer = customer;
        this.roof = roof;
        this.lumberList = lumberList;
        this.remarks = remarks;
        this.length = length;
        this.width = width;
        this.minHeight = minHeight;
        this.toolRoomWidth = toolRoomWidth;
        this.toolRoomLength = toolRoomLength;
    }

    public CarportOrder(int id, Address address, Employee employee, Customer customer, Roof roof, List<Lumber> lumberList, List<String> remarks, int length, int width, int minHeight, int toolRoomWidth, int toolRoomLength, int price) {
        this.id = id;
        this.address = address;
        this.employee = employee;
        this.customer = customer;
        this.roof = roof;
        this.lumberList = lumberList;
        this.remarks = remarks;
        this.length = length;
        this.width = width;
        this.minHeight = minHeight;
        this.toolRoomWidth = toolRoomWidth;
        this.toolRoomLength = toolRoomLength;
        this.price = price;
    }

    public CarportOrder(int id, Address address, Customer customer, int length, int width) {
        this.id = id;
        this.address = address;
        this.customer = customer;
        this.length = length;
        this.width = width;
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

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Roof getRoof() {
        return this.roof;
    }

    public void setRoof(Roof roof) {
        this.roof = roof;
    }

    public List<Lumber> getLumberList() {
        return this.lumberList;
    }

    public void setLumberList(List<Lumber> lumberList) {
        this.lumberList = lumberList;
    }

    public List<String> getRemarks() {
        return this.remarks;
    }

    public void setRemarks(List<String> remarks) {
        this.remarks = remarks;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getToolRoomWidth() {
        return this.toolRoomWidth;
    }

    public void setToolRoomWidth(int toolRoomWidth) {
        this.toolRoomWidth = toolRoomWidth;
    }

    public int getToolRoomLength() {
        return this.toolRoomLength;
    }

    public void setToolRoomLength(int toolRoomLength) {
        this.toolRoomLength = toolRoomLength;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}