/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects;

/**
 *
 * @author Vynicius
 */
public class RoutingRule {

    private String subnetAddress;
    private String subnetMask;
    private Integer index;

    public RoutingRule(String subnetAddress, String subnetMask, Integer index) {
        this.subnetAddress = subnetAddress;
        this.subnetMask = subnetMask;
        this.index = index;
    }

    public String getSubnetAddress() {
        return subnetAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public Integer getIndex() {
        return index;
    }
}
