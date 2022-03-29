package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
	private SimpleDateFormat DDMMYY_DateType = new SimpleDateFormat("dd-MM-yy");
	private SimpleDateFormat YYYYMMDD_DateType = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat YYYYMMDD_DateFmt = YYYYMMDD_DateType;
	private DateFormat DDMMYYDateFmt = DDMMYY_DateType;

	public DateTimeConverter() {
		// empty constructor
	}

	/**
	 * Date parser - String of YY-MM-DD into String of DD-MM-YY and vice versa
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
	 * Sql date parser - YYYYMMDD date to match the tables DD - MM - YY DD
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
	 * Sql date parser - DDMMYY into String date to match the tables DD - MM - YY DD
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
}