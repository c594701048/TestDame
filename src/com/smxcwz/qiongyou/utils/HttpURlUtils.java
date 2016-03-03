package com.smxcwz.qiongyou.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURlUtils {

	/**
	 * 此方法用于根据网络地址，获取到对应的Byte[]数组
	 * 
	 * @param baseUrl
	 * @return
	 * @throws IOException
	 */
	public static byte[] getImage(String baseUrl) throws IOException {
		URL url;
		InputStream inputStream = null;
		ByteArrayOutputStream baos = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(baseUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = connection.getInputStream();
				baos = new ByteArrayOutputStream();
				byte[] b = new byte[1024 * 100];
				int len = 0;
				while ((len = inputStream.read(b)) != -1) {
					baos.write(b, 0, len);
				}
				return baos.toByteArray();
			}

		} finally {

			if (baos != null) {
				baos.close();
				baos = null;
			}
			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
		return null;
	}

	/**
	 * 根据网络地址，获取json数据：String
	 * 
	 * @param baseUrl
	 * @return
	 * @throws IOException
	 */

	public static String getJson(String baseUrl) throws IOException {
		byte[] data = null;
		for (int i = 0; i < 2 && data == null; i++) {

			data = getImage(baseUrl);
		}

		return new String(data);
	}

	/**
	 * 根据指定的网址，获取输入流 ,默认为get请求
	 * 
	 * @param baseUrl
	 * @return
	 */

	public static InputStream getInputStream(String baseUrl) throws IOException {
		URL url = new URL(baseUrl);
		InputStream inputStream = null;
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = connection.getInputStream();
				return inputStream;
			}
		} finally {

			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
		return null;

	}

}
