package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Historic {
	private static List<Integer> sleeping = new ArrayList<Integer>(Arrays.asList(2, 12, 22, 32));
	private static List<Integer> bathing = new ArrayList<Integer>(Arrays.asList(3, 13, 23, 33));
	private static List<Integer> cooking = new ArrayList<Integer>(Arrays.asList(4, 14, 24, 34));
	private static List<Integer> eating = new ArrayList<Integer>(Arrays.asList(5, 15, 25, 35));
	private static List<Integer> washingDISHES = new ArrayList<Integer>(Arrays.asList(6, 16, 26, 36));
	private static List<Integer> reading = new ArrayList<Integer>(Arrays.asList(7, 17, 27, 37));
	private static List<Integer> watchingTV = new ArrayList<Integer>(Arrays.asList(8, 18, 28, 38));
	private static List<Integer> workingWithPC = new ArrayList<Integer>(Arrays.asList(9, 19, 29, 39));
	private static List<Integer> cleaningTheHOUSE = new ArrayList<Integer>(Arrays.asList(10, 20, 30, 40));
	private static List<Integer> leaving = new ArrayList<Integer>(Arrays.asList(11, 21, 31, 41));
	private static File file = new File("src/main/resources/sheet.xls");
	private static FileInputStream fileIn;
	private static HSSFWorkbook workbook;
	private static Map<Integer, Map<LocalTime, Activity>> historic;

	public Historic() {
		createHistoric();
		formateToMinutes();
	}

	private static void createHistoric() {
		historic = new TreeMap<Integer, Map<LocalTime, Activity>>();
		Map<LocalTime, Activity> maptmp = new TreeMap<LocalTime, Activity>();
		try {
			fileIn = new FileInputStream(file);
			workbook = new HSSFWorkbook(fileIn);
			HSSFSheet sheet = workbook.getSheetAt(0);
			workbook.setSheetName(0, "dados");
			int startRow = 1;
			int startColumn = 2;
			int lastRow = sheet.getPhysicalNumberOfRows();
			int lastColumn = sheet.getRow(0).getPhysicalNumberOfCells();
			HSSFRow row = null;
			HSSFCell cell = null;
			for (int line = startRow; line < lastRow; line++) {
				row = sheet.getRow(line);
				for (int column = startColumn; column < lastColumn; column++) {
					cell = row.getCell(column);
					if (cell != null) {
						String cellValue = cell.getStringCellValue();
						System.out.println(cellValue);
						int indexCell = cell.getColumnIndex();
						String hoursTmp = "";
						String minutsTmp = "";
						String[] hours = cellValue.split(", ");
						LocalTime time;
						for (int k = 0; k < hours.length; k++) {
							System.out.println("line = " + line + ", column = " + column + ", k = " + k);
							hoursTmp = hours[k].substring(0, 2);
							minutsTmp = hours[k].substring(3, 5);
							time = LocalTime.of(Integer.parseInt(hoursTmp), Integer.parseInt(minutsTmp));
							if (sleeping.contains(indexCell)) {
								maptmp.put(time, Activity.SLEEPING);
							} else if (bathing.contains(indexCell)) {
								maptmp.put(time, Activity.BATHING);
							} else if (cooking.contains(indexCell)) {
								maptmp.put(time, Activity.COOKING);
							} else if (eating.contains(indexCell)) {
								maptmp.put(time, Activity.EATING);
							} else if (washingDISHES.contains(indexCell)) {
								maptmp.put(time, Activity.WASHING_DISHES);
							} else if (reading.contains(indexCell)) {
								maptmp.put(time, Activity.READING);
							} else if (watchingTV.contains(indexCell)) {
								maptmp.put(time, Activity.WATCHING_TV);
							} else if (workingWithPC.contains(indexCell)) {
								maptmp.put(time, Activity.WORKING_WITH_PC);
							} else if (cleaningTheHOUSE.contains(indexCell)) {
								maptmp.put(time, Activity.CLEANING_THE_HOUSE);
							} else if (leaving.contains(indexCell)) {
								maptmp.put(time, Activity.LEAVING);
							}
						}
					}
				}
				historic.put(line, maptmp);
			}
			fileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printHistoric(int user_id) {
		System.out.println("o histórico do usuário " + user_id + " é: ");
		Map<LocalTime, Activity> mtmp = historic.get(user_id);
		List<LocalTime> ttmp = new ArrayList<LocalTime>(mtmp.keySet());
		for (int x = 0; x < ttmp.size(); x++) {
			System.out.println(ttmp.get(x) + "-" + mtmp.get(ttmp.get(x)));
		}

	}

	public Map<Integer, Map<LocalTime, Activity>> getHistoric() {
		return historic;
	}

	private static void formateToMinutes() {
		List<LocalTime> minuts;
		int timeMax;
		Map<LocalTime, Activity> userMap;
		LocalTime now;
		for (int user = 0; user < historic.size(); user++) {
			userMap = historic.get(user);
			timeMax = userMap.size();
			minuts = new ArrayList<LocalTime>(userMap.keySet());
			for (int time = 0; time < timeMax; time++) {
				now = minuts.get(time);
				switch (now.getMinute()) {
				case 00:
					userMap.put(LocalTime.of(now.getHour(), 1), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 2), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 3), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 4), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 5), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 6), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 7), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 8), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 9), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 10), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 11), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 12), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 13), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 14), userMap.get(now));
					break;
				case 15:
					userMap.put(LocalTime.of(now.getHour(), 16), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 17), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 18), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 19), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 20), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 21), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 22), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 23), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 24), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 25), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 26), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 27), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 28), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 29), userMap.get(now));
					break;
				case 30:
					userMap.put(LocalTime.of(now.getHour(), 31), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 32), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 33), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 34), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 35), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 36), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 37), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 38), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 39), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 40), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 41), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 42), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 43), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 44), userMap.get(now));
					break;
				case 45:
					userMap.put(LocalTime.of(now.getHour(), 46), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 47), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 48), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 49), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 50), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 51), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 52), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 53), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 54), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 55), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 56), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 57), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 58), userMap.get(now));
					userMap.put(LocalTime.of(now.getHour(), 59), userMap.get(now));
					break;
				}
			}
		}
	}

}
