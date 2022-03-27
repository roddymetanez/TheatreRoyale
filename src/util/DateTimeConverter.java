package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {

	public DateTimeConverter() {
		// empty constructor
	}

	/**
	 * Date parser - String into date
	 *
	 * @param strDate
	 * @return Date format
	 * @throws ParseException
	 */
	public Date stringToDate(String strDate) throws ParseException {
		Date date1 = null;
		date1 = new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
		return date1;
	}

	/**
	 * Sql date parser - String into SQL date to match the tables
	 *
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public SimpleDateFormat stringToSqLDate(String strDate) throws ParseException {
		SimpleDateFormat date1 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		// java.util.Date date = sdf.parse(strDate); // Deleted as not sure if required
		// Date sqlDate = new Date(date.getTime());
		return sdf;
	}

//	public SimpleDateFormat stringToCalendar(String strDate) throws ParseException {
//
//		//https://stackoverflow.com/questions/29750861/convert-between-localdate-and-sql-date
//		
//		strDate.getTimestamp("value").toLocalDateTime()
//		
//		
//
//		sql.Date.toLocalDate
//		time.Calendar calendar = Calendar.getInstance();
//		Date newDate = calendar.setTime(strDate);
//
//	}

}