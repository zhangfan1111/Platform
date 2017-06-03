package com.memory.platform.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		return ip;
	}

	public static String getMACAddr() throws SocketException, UnknownHostException {
		// 获得ＩＰ
		NetworkInterface netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
		// 获得Mac地址的byte数组
		byte[] macAddr = netInterface.getHardwareAddress();
		StringBuilder builder = new StringBuilder();
		// 循环输出
		for (byte b : macAddr) {
			// 这里的toHexString()是自己写的格式化输出的方法，见下步。
			// System.out.print(toHexString(b));
			builder.append(toHexString(b));
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		try {
			getMACAddr();
			String ip = getIp();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private static String toHexString(int integer) {
		// 将得来的int类型数字转化为十六进制数
		String str = Integer.toHexString((int) (integer & 0xff));
		// 如果遇到单字符，前置0占位补满两格
		if (str.length() == 1) {
			str = "0" + str;
		}
		return str;
	}

	public static String getIp() {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
 
        Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
	        InetAddress ip = null;
	        boolean finded = false;// 是否找到外网IP
	        while (netInterfaces.hasMoreElements() && !finded) {
	            NetworkInterface ni = netInterfaces.nextElement();
	            Enumeration<InetAddress> address = ni.getInetAddresses();
	            while (address.hasMoreElements()) {
	                ip = address.nextElement();
	                if (!ip.isSiteLocalAddress() 
	                        && !ip.isLoopbackAddress() 
	                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
	                    netip = ip.getHostAddress();
	                    finded = true;
	                    break;
	                } else if (ip.isSiteLocalAddress() 
	                        && !ip.isLoopbackAddress() 
	                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
	                    localip = ip.getHostAddress();
	                }
	            }
	        }
		} catch (SocketException e) {
		}
     
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
	}

}
