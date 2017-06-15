/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects;

import java.io.Serializable;

/**
 *
 * @author Vynicius
 */
public class IPV4Datagram implements Serializable {

    private int lenght;
    private final int typeOfService = 0;
    private final int flags = 0;
    private final int fragment = 0;
    private final int offset = 0;
    private final int upper = 0;
    private final int layer = 0;
    private final int checksum = 0;

    private String sourceIPAddress;
    private String destinationIPAddress;

    private int ttl = 7;

    private byte[] content;

    public IPV4Datagram(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public int getLenght() {
        return lenght;
    }

    public String getSourceIPAddress() {
        return sourceIPAddress;
    }

    public void setSourceIPAddress(String sourceIPAddress) {
        this.sourceIPAddress = sourceIPAddress;
    }

    public String getDestinationIPAddress() {
        return destinationIPAddress;
    }

    public void setDestinationIPAddress(String destinationIPAddress) {
        this.destinationIPAddress = destinationIPAddress;
    }
}
