package datou;

import java.util.Comparator;

public class MissComparator implements Comparator<Miss> {
	@Override
	public int compare(Miss o1, Miss o2) {
		return (o1.getAverageMiss()<o2.getAverageMiss()?-1:(o1.getAverageMiss()==o2.getAverageMiss()?0:1));
	}

}
