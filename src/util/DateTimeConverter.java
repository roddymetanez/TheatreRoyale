package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeConverter {
	private static final SimpleDateFormat DDMMYY_DateType = new SimpleDateFormat("dd-MM-yy");
	private static final SimpleDateFormat YYYYMMDD_DateType = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat YYYYMMDD_DateFmt = YYYYMMDD_DateType;
	private DateFormat DDMMYYDateFmt = DDMMYY_DateType;

	public DateTimeConverter() {
		// empty constructor
	}

	/**
	 * Date parser - String of YY-MM-DD hh:mm:ss into String of DD-MM-YY and vice
	 * versa Selected on length,
	 *
	 * @param strDate
	 * @return Date format
	 * @throws ParseException
	 */
	public String stringToDate(String strDate) throws ParseException {
		if (strDate.isEmpty()) {
			return null;
		}
		if (strDate.length() > 8) {
			return YYYYMMDD_to_DDMMYY_Date(strDate).toString();
		}
		if (strDate.length() <= 8) {
			return DDMMYY_to_YYYYMMDD_Date(strDate).toString();
		}
		else {
			return null;
		}
	}

	/**
	 * Sql date parser - turns YYYYMMDD date to match the tables DD - MM - YY DD
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public String YYYYMMDD_to_DDMMYY_Date(String strDate) throws ParseException {
		Date tmpDate = YYYYMMDD_DateFmt.parse(strDate);
		String convDateStr = DDMMYYDateFmt.format(tmpDate);
		return convDateStr;
	}

	/**
	 * Sql date parser - turns DDMMYY into String date to match the tables DD - MM -
	 * YY DD
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public String DDMMYY_to_YYYYMMDD_Date(String strDate) throws ParseException {
		Date tmpDate = DDMMYY_DateType.parse(strDate);
		String convDateStr = YYYYMMDD_DateType.format(tmpDate);
		// This is a kludge as the proper way to do it seems very complex
		// https://stackoverflow.com/a/10732308/14775100
		convDateStr.replaceFirst("-(\\d{2})$", "-20$1");
		return convDateStr;
	}

	/**
	 * get Date from SQL string, returning a java.util.date type object
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public Date getDateFromSql(String strDate) throws ParseException {
		Date tmpDate = YYYYMMDD_DateFmt.parse(strDate);
		return tmpDate;
	}

	/**
	 * check to see if date (as String) is 7 days hence from localDate time of NOW
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public boolean compareDate7dyHence(String strDate) throws ParseException {
		Date tmpDate = YYYYMMDD_DateFmt.parse(strDate);
		LocalDate then = tmpDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		LocalDate then = LocalDate.parse(strDate);
		// add seven days
		LocalDate now7 = LocalDate.now().plusDays(7);
		return now7.isBefore(then);
	}
}