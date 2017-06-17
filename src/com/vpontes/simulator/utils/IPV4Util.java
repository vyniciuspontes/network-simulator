/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.utils;

/**
 *
 * @author Vynicius
 */
public class IPV4Util {

    int baseIPnumeric;
    int netmaskNumeric;

    public IPV4Util(String ip, String netmask) throws NumberFormatException {

        String[] st = ip.split("\\.");

        if (st.length != 4) {
            throw new NumberFormatException("IP invalido: " + ip);
        }

        int i = 24;
        baseIPnumeric = 0;

        for (String st1 : st) {
            int value = Integer.parseInt(st1);
            if (value != (value & 0xff)) {

                throw new NumberFormatException("IP invalido: " + ip);
            }
            baseIPnumeric += value << i;
            i -= 8;
        }

        st = netmask.split("\\.");

        if (st.length != 4) {
            throw new NumberFormatException("Endereco de subrede invalido: "
                    + netmask);
        }

        i = 24;
        netmaskNumeric = 0;

        if (Integer.parseInt(st[0]) < 255) {

            throw new NumberFormatException(
                    "O primeiro numero da subrede nao pode ser menor que 255");
        }
        for (String st1 : st) {
            int value = Integer.parseInt(st1);
            if (value != (value & 0xff)) {

                throw new NumberFormatException("Endereco de subrede invalido: " + netmask);
            }
            netmaskNumeric += value << i;
            i -= 8;
        }

        boolean found = false;
        int maskBitPattern = 1;

        for (i = 0; i < 32; i++) {

            if ((netmaskNumeric & maskBitPattern) != 0) {

                found = true;
            } else {
                if (found == true) {
                    throw new NumberFormatException("Mas cara de rede inválida: " + netmask + " (bit " + (i + 1) + ")");
                }
            }

            maskBitPattern = maskBitPattern << 1;
        }
    }

    static public boolean verifyIp(String ip) {
        String[] st = ip.split("\\.");

        if (st.length != 4) {
            return false;
        }

        int i = 24;
        int calIp = 0;

        for (String st1 : st) {
            int value = Integer.parseInt(st1);
            if (value != (value & 0xff)) {

                return false;
            }
            calIp += value << i;
            i -= 8;
        }

        return true;
    }

    static public boolean verifyNetmask(String netmask) {
        
        String[] st = netmask.split("\\.");

        if (st.length != 4) {
            return false;
        }

        int i = 24;
        int netmaskNumeric = 0;

        if (Integer.parseInt(st[0]) < 255) {

            return false;
        }
        for (String st1 : st) {
            int value = Integer.parseInt(st1);
            if (value != (value & 0xff)) {

                return false;
            }
            netmaskNumeric += value << i;
            i -= 8;
        }

        boolean encounteredOne = false;
        int ourMaskBitPattern = 1;

        for (i = 0; i < 32; i++) {

            if ((netmaskNumeric & ourMaskBitPattern) != 0) {

                encounteredOne = true;
            } else {
                if (encounteredOne == true) {
                    return false;
                }
            }

            ourMaskBitPattern = ourMaskBitPattern << 1;
        }

        return true;
    }

    public int getVirtualIp() {
        return baseIPnumeric;
    }

    private String convertNumericIpToSymbolic(Integer ip) {
        StringBuilder sb = new StringBuilder(15);

        for (int shift = 24; shift > 0; shift -= 8) {

            sb.append(Integer.toString((ip >>> shift) & 0xff));

            sb.append('.');
        }
        sb.append(Integer.toString(ip & 0xff));

        return sb.toString();
    }

    public String getNetmask() {
        StringBuilder sb = new StringBuilder(15);

        for (int shift = 24; shift > 0; shift -= 8) {

            sb.append(Integer.toString((netmaskNumeric >>> shift) & 0xff));

            sb.append('.');
        }
        sb.append(Integer.toString(netmaskNumeric & 0xff));

        return sb.toString();
    }

    public String getNetworkAddress() {

        return convertNumericIpToSymbolic(baseIPnumeric & netmaskNumeric);
    }

    public boolean contains(String IPaddress) {

        Integer checkingIP = 0;
        String[] st = IPaddress.split("\\.");

        if (st.length != 4) {
            throw new NumberFormatException("Endereco de IP invalido: " + IPaddress);
        }

        int i = 24;
        for (String st1 : st) {
            int value = Integer.parseInt(st1);
            if (value != (value & 0xff)) {

                throw new NumberFormatException("Endereco de IP invalido: "
                        + IPaddress);
            }
            checkingIP += value << i;
            i -= 8;
        }

        return (baseIPnumeric & netmaskNumeric) == (checkingIP & netmaskNumeric);
    }
}
