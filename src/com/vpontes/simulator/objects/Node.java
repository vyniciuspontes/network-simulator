package com.vpontes.simulator.objects;

/**
 *
 * @author Vynicius
 */
public class Node {

    private String virtualSubmask;
    private String subnetAddress;
    private String virtualAddress;
    private String address;
    private Integer door;
    private Integer index;

    public Node() {
    }

    public String getVirtualSubmask() {
        return virtualSubmask;
    }

    public void setVirtualSubmask(String virtualSubmask) {
        this.virtualSubmask = virtualSubmask;
    }

    public String getSubnetAddress() {
        return subnetAddress;
    }

    public void setSubnetAddress(String subnetAddress) {
        this.subnetAddress = subnetAddress;
    }

    public String getVirtualAddress() {
        return virtualAddress;
    }

    public void setVirtualAddress(String virtualAddress) {
        this.virtualAddress = virtualAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDoor() {
        return door;
    }

    public void setDoor(Integer door) {
        this.door = door;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Node {" + "virtualSubmask=" + virtualSubmask + ", subnetAddress=" + subnetAddress + ", virtualAddress=" + virtualAddress + ", address=" + address + ", door=" + door + ", index=" + index + '}';
    }

}
