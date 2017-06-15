package com.vpontes.simulator.objects;

import java.io.IOException;

/**
 *
 * @author Vynicius
 */
public class Node {

    private Integer virtualDoor;
    private String virtualAddress;

    public Node(String virtualAddress, Integer virtualDoor) throws IOException {
        this.virtualDoor = virtualDoor;
        this.virtualAddress = virtualAddress;
    }

    public Integer getVirtualDoor() {
        return virtualDoor;
    }

    public String getVirtualAddress() {
        return virtualAddress;
    }

    @Override
    public String toString() {
        return "Node - " + virtualAddress + ":" + virtualDoor;
    }

}
