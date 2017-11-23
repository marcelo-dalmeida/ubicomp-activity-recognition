package br.com.uff.ubicomp.activityrecognition.client.smarthome;

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
List<LocalTime> times;
		//int timeMax;
		Map<LocalTime, Activity> userMap;
//		LocalTime now;
//		for (int user = 0; user < historic.size(); user++) {
for (Integer user : historic.keySet()) {
		userMap = historic.get(user);
//			timeMax = userMap.size();
times = new ArrayList<LocalTime>(userMap.keySet());
//			for (int time = 0; time < timeMax; time++) {
for (LocalTime time : times){
	//now = time;
				switch (time.getMinute()) {
				case 00:
					userMap.put(LocalTime.of(time.getHour(), 1), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 2), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 3), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 4), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 5), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 6), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 7), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 8), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 9), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 10), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 11), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 12), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 13), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 14), userMap.get(time));
					break;
				case 15:
					userMap.put(LocalTime.of(time.getHour(), 16), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 17), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 18), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 19), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 20), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 21), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 22), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 23), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 24), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 25), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 26), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 27), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 28), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 29), userMap.get(time));
					break;
				case 30:
					userMap.put(LocalTime.of(time.getHour(), 31), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 32), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 33), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 34), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 35), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 36), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 37), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 38), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 39), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 40), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 41), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 42), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 43), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 44), userMap.get(time));
					break;
				case 45:
					userMap.put(LocalTime.of(time.getHour(), 46), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 47), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 48), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 49), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 50), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 51), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 52), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 53), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 54), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 55), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 56), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 57), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 58), userMap.get(time));
					userMap.put(LocalTime.of(time.getHour(), 59), userMap.get(time));
					break;
				}
			}
		}
	}

}
