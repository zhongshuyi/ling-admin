package com.mall.common.core.util.ip;



import cn.hutool.http.HTMLFilter;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 钟舒艺
 * @date 2021-07-02-23:51
 **/
@SuppressWarnings("unused")
public class IpUtils {

    /**
     * 未知地址
     */
    public static final String UNKNOWN = "unknown";

    /**
     * 根据请求获取ip地址
     * @param request HttpServletRequest
     * @return ip地址
     */
    public static String getIpAdder(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : new HTMLFilter().filter(ip);
    }

    /**
     * 是否是内部ip
     * @param ip ip字符串
     * @return 是否是内部ip
     */
    public static boolean internalIp(String ip) {
        byte[] adder = textToNumericFormatV4(ip);
        return internalIp(adder) || "127.0.0.1".equals(ip);
    }

    /**
     * 是否是内部ip
     * @param adder ip
     * @return 是否是内部ip
     */
    private static boolean internalIp(byte[] adder) {
        final int minLength = 2;
        if (adder != null && adder.length < minLength) {
            return true;
        }
        assert adder != null;
        final byte b0 = adder[0];
        final byte b1 = adder[1];
        // 10.x.x.x/8
        final byte section1 = 0x0A;
        // 172.16.x.x/12
        final byte section2 = (byte) 0xAC;
        final byte section3 = (byte) 0x10;
        final byte section4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte section5 = (byte) 0xC0;
        final byte section6 = (byte) 0xA8;
        switch (b0) {
            case section1:
                return true;
            case section2:
                if (b1 >= section3 && b1 <= section4) {
                    return true;
                }
            case section5:
                if (b1 == section6) {
                    return true;
                }
            default:
                return false;
        }
    }

    /**
     * 将IPv4地址转换成字节数组
     * IP地址本身是一个32位的二进制数，通常被分割为4个“8位二进制数”（也就是4个字节）,所以ip地址与int是可以互相转换的
     * 形如 192.168.1.1 的ipv4地址都分为4段,每段都是 0~255 之间的数,每段可以用 8 位来装下它,而1个byte的取值范围正好是-128~127,共255,正好可以存下
     * 只需要对0~255的数与255进行位与运算,就可以得出-128~127之间的数,需要转换回来的时候再对其与255进行位与运算就可以了
     * 该方法可以转换4种情况
     * 1. 传入为int 如 4294967295 转为 255.255.255.255 这是最大的情况 存储到byte数组中是[-1, -1, -1, -1]
     * 2. 传入 255.16777215 转为 255.255.255.255
     * 3. 传入 255.255.65535 转为 255.255.255.255
     * 4. 传入正常的ip地址
     * @param text IPv4地址
     * @return byte 字节
     */
    public static byte[] textToNumericFormatV4(String text) {

        if (text.length() == 0) {
            return null;
        }
        // 32位无符号整数的十进制最大值
        final long max32 = 4294967295L;

        // 24位无符号整数的十进制最大值
        final long max24 = 16777215;

        // 16位无符号整数的十进制最大值
        final long max16 = 65535;

        // 8位无符号整数的十进制最大值
        final long max8 = 255L;

        byte[] bytes = new byte[4];
        // “.”有特殊含义，需要进行转义。
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > max32)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > max8)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > max24)) {
                        return null;
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i <  elements.length-1; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > max8)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > max16)) {
                        return null;
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < elements.length; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > max8)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

    /**
     * 将byte数组转为正常ipv4地址字符串
     * @param bytes byte数组
     * @return ip地址
     */
    public static String byteArrayToStringV4(byte[] bytes){
        // ipv4 有4个部分
        final byte ipv4Section = 4;
        if (bytes == null || bytes.length != ipv4Section){
            return null;
        }
        StringBuilder ip= new StringBuilder();
        for (byte value : bytes) {
            ip.append(value & 0xFF).append(".");
        }
        return ip.toString();


    }

    /**
     * 将ipv4地址转换成long,IP地址本身是一个32位的二进制数，通常被分割为4个“8位二进制数”（也就是4个字节）,所以ip地址与int是可以互相转换的
     * @param text 地址
     * @return 转换后long值
     */
    public static Long textToLong(String text){
        long ipLong = 0L;
        String[] ipNumbers = text.split("\\.");
        for (String ipNumber : ipNumbers) {
            ipLong = ipLong << 8 | Integer.parseInt(ipNumber);
        }
        return ipLong;
    }

    /**
     * 根据hostname(主机名)来获取本机IP地址
     * @return 本机ip
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {
        }
        return "127.0.0.1";
    }

    /**
     * 获取HostName(主机名)
     * @return HostName
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ignored) {
        }
        return "未知";
    }
}
