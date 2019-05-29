package us.analytiq.knime.qvx.reader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.knime.core.node.NodeLogger;

public class QvxReaderUtil {
	
    private static final NodeLogger LOGGER = NodeLogger.getLogger(QvxReaderUtil.class);
    
	private static final long SECONDS_PER_DAY = 86400;
	private static final long MILLISECONDS_PER_DAY = 86400000;
	private static Date epoch;
	private static Date startDate;
	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("EDT"));
		try {
			epoch = dateFormat.parse("1 1 1970");
			startDate = dateFormat.parse("12 30 1899");
		}catch(ParseException e) {
			LOGGER.error("Error reading qvx file; could not parse date");
		}
	}
	
	private QvxReaderUtil() {
		// Hides the implicit private constructor (squid:S1118)
	}
	
	public static byte[] combineByteArrays(byte[] a, byte[] b) {
		byte[] returnValue = new byte[a.length + b.length];
		for(int i = 0; i < a.length; i++) {
			returnValue[i] = a[i];
		}
		for(int i = 0; i < b.length; i++) {
			returnValue[a.length + i] = b[i];
		}
		return returnValue;
	}
	
	public static Calendar getDateFromString(String s) {
		
		//If s can be converted to a Calendar format, return that calendar. Otherwise, return null.
		
		String dateSeps = "-/";
		String formatA = "^[0-9]{1,2}[" + dateSeps + "][0-9]{1,2}[" + dateSeps + "][0-9]{4}$";
		String formatB = "^[0-9]{4}[" + dateSeps + "][0-9]{1,2}[" + dateSeps + "][0-9]{1,2}$";
		String formatC = "^[0-9]{1,2}[" + dateSeps + "][0-9]{1,2}[" + dateSeps + "][0-9]{1,3}$";
		
		Pattern patternA = Pattern.compile(formatA);
		Pattern patternB = Pattern.compile(formatB);
		Pattern patternC = Pattern.compile(formatC);
		
		Matcher matcherA = patternA.matcher(s);
		Matcher matcherB = patternB.matcher(s);
		Matcher matcherC = patternC.matcher(s);
		boolean matchA = matcherA.find();
		boolean matchB = matcherB.find();
		boolean matchC = matcherC.find();
		
		if (matchA || matchB || matchC) {
			//If one of the date formats is matched by the current string, create a new Calendar
			
			//Find separator
			String sep = "";
			for(int i = 0; i < s.length(); i++) {
				if (dateSeps.contains("" + s.charAt(i))) {
					sep = "" + s.charAt(i);
					break;
				}
			}
			
			String[] dateParts = s.split(sep);
			int month = 0;
			int dayOfMonth = 0;
			int year = 0;
			if (matchA || matchC) {
				month = Integer.parseInt(dateParts[0]) - 1;
				dayOfMonth = Integer.parseInt(dateParts[1]);
				year = Integer.parseInt(dateParts[2]);
			}else { //matchB
				year = Integer.parseInt(dateParts[0]);
				month = Integer.parseInt(dateParts[1]) - 1;
				dayOfMonth = Integer.parseInt(dateParts[2]);
			}
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EDT"));
			cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, year);
			return cal;
		}else { //If none of the date formats is matched
			return null;
		}
	}
	
	public static Calendar getDateFromQvxReal(double daysSince) {
		
		long fullDaysSince = Math.round(daysSince);
		double partialDaysSince = daysSince - fullDaysSince;
		long remainingSeconds = Math.round(partialDaysSince*SECONDS_PER_DAY);
		
		long dateInMilliseconds = (startDate.getTime()-epoch.getTime()) +
				(fullDaysSince*MILLISECONDS_PER_DAY) +
				(remainingSeconds*1000);
		
		Date date = new Date(dateInMilliseconds);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EDT"));
		cal.setTime(date);
		
		return cal;
	}
	
	public static Calendar getTimeFromString(String s) {
		
		//If s can be converted to a Calendar format, return that calendar. Otherwise, return null.

		String formatA = "^[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$";
		String formatB = "^[0-9]{1,2}:[0-9]{1,2}$";
		
		Pattern patternA = Pattern.compile(formatA);
		Pattern patternB = Pattern.compile(formatB);
		
		Matcher matcherA = patternA.matcher(s);
		Matcher matcherB = patternB.matcher(s);
		boolean matchA = matcherA.find();
		boolean matchB = matcherB.find();
		
		if (matchA || matchB) {
			//If one of the time formats is matched by the current string, create a new Calendar
			
			String[] timeParts = s.split(":");
			Integer hours = Integer.parseInt(timeParts[0]);
			Integer minutes = Integer.parseInt(timeParts[1]);
			Integer seconds = null;
			if (matchA) {
				seconds = Integer.parseInt(timeParts[2]);
			}
			
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EDT"));
			cal.set(Calendar.HOUR_OF_DAY, hours);
			cal.set(Calendar.MINUTE, minutes);
			if (seconds != null) {
				cal.set(Calendar.SECOND, seconds);
			}
			cal.set(Calendar.MILLISECOND, 0);
			return cal;
		}else { //If none of the time formats is matched
			return null;
		}
	}
	
	public static String objectToString(Object obj) {
		
		if (obj.getClass().equals(java.lang.String.class)) {
			return (String)obj;
		}else if (obj.getClass().equals(java.lang.Double.class)){
			return Double.toString((double)obj);
		}else {
			return "";
		}
	}
	
	public static String removeSuffix(String s, String suffix) {
		if (s.endsWith(suffix)) {
			return s.substring(0, s.lastIndexOf(suffix));
		}else {
			return s;
		}
	}
	
	public static String toTitleCase(String s) {
		/* Return a copy of s with the first letter capitalized */
		
		if (s.length() == 0) {
			return s;
		}
		
		return ("" + s.charAt(0)).toUpperCase() + s.substring(1, s.length());
	}
}
