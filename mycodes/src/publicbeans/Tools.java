/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package publicbeans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import DbBeans.DbPool;

/**
 * @author Administrator
 */
public class Tools {

	public static final void dataToFile(String filename, ArrayList<String> data) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			for (int n = 0, size = data.size() - 1; n <= size; n++) {
				// tools.print(date.get(n));
				out.write(data.get(n));
				out.newLine();
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static final void getDataFromFile(String filename, ArrayList<String> data) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			while (true) {
				String oneLine = in.readLine();
				if (oneLine == null) {
					in.close();
					break;
				}
				data.add(oneLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// public static void sortByFastSort(int[] a) {
	//
	// }
	public static void sortByFastSortV2(int left, int right, int[] a) {
		int q, i;
		if (right > left) {
			i = left - 1;
			for (int j = left; j < right; j++) {
				if (a[j] <= a[right]) {
					i++;
					swap(i, j, a);
				}
			}
			swap(i + 1, right, a);

			q = i + 1;
			sortByFastSortV2(left, q - 1, a);
			sortByFastSortV2(q + 1, right, a);
		}

	}

	public static void sortByFastSort(int left, int right, int[] a) {
		if (left > right)
			return;

		int temp = a[left];
		int i = left;
		int j = right;

		while (i != j) {
			while (a[j] >= temp && i < j) {
				j--;
			}
			while (a[i] <= temp && i < j) {
				i++;
			}
			if (i < j) {
				swap(i, j, a);
			}

		}
		swap(left, i, a);
		sortByFastSort(i + 1, right, a);
		sortByFastSort(left, i - 1, a);

	}

	public static void swap(int a, int b, int[] array) {
		if (a == b)
			return;
		array[a] = array[a] + array[b];
		array[b] = array[a] - array[b];
		array[a] = array[a] - array[b];
	}

	public static final String getDateTime(String format, int field, int amount) {
		String ts = null;
		Calendar currTime = new GregorianCalendar();
		currTime.add(field, amount);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		ts = sdf.format(currTime.getTime());
		// tools.print(ts);
		return ts;
	}

	public static String getdbdate(String clname, String sql) {
		DbPool db = DbPool.getInstance();
		Connection c = db.getOneConnection();
		String s = db.ResultToString(db.executeQuery(sql, null, c), clname);
		return s;
	}

	public static boolean isIP(String ip) {
		if (ip == null || ip.equals("")) {
			return false;
		}
		if (ip.length() > 15) {
			return false;
		} else if (ip.charAt(ip.length() - 1) == '.') {
			return false;
		}
		if (!isNumber(ip.replace(".", ""))) {
			return false;
		}
		ip = ip.replace('.', '=');
		String a[] = ip.split("=");
		Tools.print(a.length + "---" + ip);
		if (a.length != 4) {
			return false;
		}
		for (int m = a.length - 1; m >= 0; m--) {
			if (Integer.parseInt(a[m]) > 255) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumber(String s) {
		if (s == null || s.equals("")) {
			return false;
		}
		char[] sc = s.toCharArray();
		for (int i = sc.length - 1; i >= 0; i--) {
			if (sc[i] < '0' || sc[i] > '9') {
				return false;
			}
		}
		return true;
	}

	public static void print(Object content) {
		System.out.println(content);
	}

	public static String getCurrentDateTime() {
		Calendar cr = Calendar.getInstance();
		cr.setTimeInMillis(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(cr.getTime());
	}

	public static String getCurrentDate() {
		Calendar cr = Calendar.getInstance();
		cr.setTimeInMillis(System.currentTimeMillis());
		return DateFormat.getDateInstance().format(cr.getTime());
	}

	public static boolean after(String date) {
		Calendar cr = Calendar.getInstance();
		cr.setTimeInMillis(System.currentTimeMillis());
		try {
			return cr.getTime().after(SimpleDateFormat.getDateInstance().parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String processURL(String requestURL, String page) {

		while (requestURL.indexOf("/") >= 0) {
			requestURL = requestURL.replaceFirst("/", "");
			page = "../" + page;
			// tools.print(page);
		}
		return page;
	}
}
