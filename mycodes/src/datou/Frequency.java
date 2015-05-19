package datou;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import publicbeans.LogicPublic;

public class Frequency {

	public static void main(String[] args) {
		Frequency f = new Frequency();
		// System.out.println("统计各个红球出现的次数并排序");
		// f.getRedTimes();// 统计各个红球出现的次数并排序；
		// System.out.println("统计各个篮球出现的次数并排序");
		// f.getBlueTimes();// 统计各个篮球出现的次数并排序；
		f.getBlueMissInfo();
		f.getRedMissInfo();
	}

	private final String FSQL = "select count(*) as times from mycodes.oldcodes "
			+ " where a=? or b=?  or c=? or d=? or e=? or f=?;";
	private final String FSQL2 = "select count(*) as times from mycodes.oldcodes "
			+ " where blue=?;";
	private final String FSQL3 = "select count(*) as times from mycodes.oldcodes ";

	private final String MISSSQL = "select * from mycodes.oldcodes order by number";

	public void getRedTimes() {
		String sql = "";
		int[] sortTimes = new int[10000];
		for (int n = 1; n <= 33; n++) {
			sql = FSQL;
			sql = sql.replace("?", "" + n);
			ArrayList<HashMap<String, String>> data = LogicPublic.instance.dbmd
					.getDataByString(sql);
			int times = Integer.parseInt(data.get(0).get("times"));
			sortTimes[times] = n;
		}
		ArrayList<HashMap<String, String>> count = LogicPublic.instance.dbmd
				.getDataByString(FSQL3);
		BigDecimal t = new BigDecimal(count.get(0).get("times"));
		t = t.multiply(new BigDecimal(6)).divide(new BigDecimal(33),
				new MathContext(5, RoundingMode.HALF_DOWN));
		for (int n = 0, m = sortTimes.length; n < m; n++) {
			if (sortTimes[n] > 0)
				System.out.println("球号：" + sortTimes[n] + "\t\t出现次数：" + n
						+ "\t\t理论次数：" + t.toString());
		}
	}

	public void getBlueTimes() {
		String sql = "";
		int[] sortTimes = new int[10000];
		for (int n = 1; n <= 16; n++) {
			sql = FSQL2;
			sql = sql.replace("?", "" + n);
			ArrayList<HashMap<String, String>> data = LogicPublic.instance.dbmd
					.getDataByString(sql);
			int times = Integer.parseInt(data.get(0).get("times"));
			sortTimes[times] = n;
		}
		ArrayList<HashMap<String, String>> count = LogicPublic.instance.dbmd
				.getDataByString(FSQL3);
		BigDecimal t = new BigDecimal(count.get(0).get("times"));
		t = t.divide(new BigDecimal(16), new MathContext(5,
				RoundingMode.HALF_DOWN));
		for (int n = 0, m = sortTimes.length; n < m; n++) {
			if (sortTimes[n] > 0)
				System.out.println("球号：" + sortTimes[n] + "\t\t出现次数：" + n
						+ "\t\t理论次数：" + t.toString());
		}
	}

	public void getBlueMissInfo() {
		ArrayList<HashMap<String, String>> missInfo = LogicPublic.instance.dbmd
				.getDataByString(MISSSQL);
		Map<Integer, Miss> maxMiss = new HashMap<Integer, Miss>();
		Miss one;
		for (int n = 0, m = missInfo.size(); n < m; n++) {
			int id = Integer.parseInt(missInfo.get(n).get("blue"));
			one = maxMiss.get(id);
			if (one == null) {
				one = new Miss(id, 15);
				maxMiss.put(id, one);
			}
			one.setTotalTimes(one.getTotalTimes() + 1);
			if (one.getLastIndex() == 0) {
				one.setLastIndex(n + 1);
			} else {
				int oneMiss = n - one.getLastIndex();
				if (oneMiss > one.getMaxMiss())
					one.setMaxMiss(oneMiss);
				one.getAllMiss().add(oneMiss);
				one.setLastIndex(n + 1);
			}
		}
		Miss[] ArrayForMaxMiss = new Miss[maxMiss.size()];
		int n = 0;
		for (Miss onetemp : maxMiss.values()) {
			int temp = missInfo.size() - onetemp.getLastIndex();
			onetemp.setLastMiss(temp);
			onetemp.processForAverageMiss();
			ArrayForMaxMiss[n] = onetemp;
			n++;
		}
		Arrays.sort(ArrayForMaxMiss);
		System.out.println("篮球遗漏分析，最大遗漏排序");
		for (int m = ArrayForMaxMiss.length - 1; m >= 0; m--) {
			one = ArrayForMaxMiss[m];
			System.out.println("球号：" + one.getId() + "\t本次遗漏："
					+ one.getLastMiss() + "\t最大遗漏次数：" + one.getMaxMiss()
					+ "\t平均遗漏次数：" + String.format("%.5f",one.getAverageMiss()) + "\t理论遗漏次数："
					+ one.PERFECTMISS+"\t出现次数："+one.getTotalTimes()+"\t理论出现次数："+String.format("%.5f",missInfo.size()*(6.0/33.0)));
		}
		Arrays.sort(ArrayForMaxMiss, new MissComparator());
		System.out.println("篮球遗漏分析，平均遗漏排序");
		for (int m = ArrayForMaxMiss.length - 1; m >= 0; m--) {
			one = ArrayForMaxMiss[m];
			System.out.println("球号：" + one.getId() + "\t本次遗漏："
					+ one.getLastMiss() + "\t最大遗漏次数：" + one.getMaxMiss()
					+ "\t平均遗漏次数：" + String.format("%.5f",one.getAverageMiss()) + "\t理论遗漏次数："
					+ one.PERFECTMISS+"\t出现次数："+one.getTotalTimes()+"\t理论出现次数："+String.format("%.5f",missInfo.size()*(6.0/33.0)));
		}
	}

	public void getRedMissInfo() {
		ArrayList<HashMap<String, String>> missInfo = LogicPublic.instance.dbmd
				.getDataByString(MISSSQL);
		Map<Integer, Miss> maxMiss = new HashMap<Integer, Miss>();
		String[] redBalls = { "a", "b", "c", "d", "e", "f" };
		Miss one;
		for (int n = 0, m = missInfo.size(); n < m; n++) {
			for (int rd = 0, end = redBalls.length; rd < end; rd++) {
				int id = Integer.parseInt(missInfo.get(n).get(redBalls[rd]));
				one = maxMiss.get(id);
				if (one == null) {
					one = new Miss(id,33.0/6.0);
					maxMiss.put(id, one);
				}
				one.setTotalTimes(one.getTotalTimes() + 1);
				if (one.getLastIndex() == 0) {
					one.setLastIndex(n + 1);
				} else {
					int oneMiss = n - one.getLastIndex();
					if (oneMiss > one.getMaxMiss())
						one.setMaxMiss(oneMiss);
					one.getAllMiss().add(oneMiss);
					one.setLastIndex(n + 1);
				}
			}
		}
		Miss[] ArrayForMaxMiss = new Miss[maxMiss.size()];
		int n = 0;
		for (Miss onetemp : maxMiss.values()) {
			int temp = missInfo.size() - onetemp.getLastIndex();
			onetemp.setLastMiss(temp);
			onetemp.processForAverageMiss();
			ArrayForMaxMiss[n] = onetemp;
			n++;
		}
		Arrays.sort(ArrayForMaxMiss);
		System.out.println("紅球遗漏分析，最大遗漏排序");
		for (int m = ArrayForMaxMiss.length - 1; m >= 0; m--) {
			one = ArrayForMaxMiss[m];
			System.out.println("球号：" + one.getId() + "\t本次遗漏："
					+ one.getLastMiss() + "\t最大遗漏次数：" + one.getMaxMiss()
					+ "\t平均遗漏次数：" + String.format("%.5f", one.getAverageMiss())+ "\t理论遗漏次数："
					+ one.PERFECTMISS+"\t出现次数："+one.getTotalTimes()+"\t理论出现次数："+String.format("%.5f",missInfo.size()*(6.0/33.0)));
		}
		Arrays.sort(ArrayForMaxMiss, new MissComparator());
		System.out.println("红球遗漏分析，平均遗漏排序");
		for (int m = ArrayForMaxMiss.length - 1; m >= 0; m--) {
			one = ArrayForMaxMiss[m];
			System.out.println("球号：" + one.getId() + "\t本次遗漏："
					+ one.getLastMiss() + "\t最大遗漏次数：" + one.getMaxMiss()
					+ "\t平均遗漏次数：" + String.format("%.5f", one.getAverageMiss()) + "\t理论遗漏次数："
					+ one.PERFECTMISS+"\t出现次数："+one.getTotalTimes()+"\t理论出现次数："+String.format("%.5f",missInfo.size()*(6.0/33.0)));
		}
	}

}
