import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;


public class MonthlyReport {
    /*
    Класс месячных отчётов. Ниже инициализируются три хэш таблицы: monthData - соответствие
    "Месяц - строки отчета из этого месяца", monthIncomeData и monthExpenditureData - данные помесячных доходов и трат
    соответственно.
     */
    HashMap<String, ArrayList<MonthDataStorage>> monthData = new HashMap<>(); //храним таблицу "Месяц - данные на месяц"
    HashMap<String, Integer> monthIncomeData = new HashMap<>(); //данные о доходах по месяцам
    HashMap<String, Integer> monthExpenditureData = new HashMap<>();// данные о расходах по месяцам

    void readingMonthlyReport(String path){ // чтение месячных отчетов
        for (int i = 1; i < 4; i++) {
            try {
                String report = Files.readString(Path.of(path + i + ".csv"));
                String[] lines = report.split("\\n");
                String month;
                if (i == 1) {
                    month = "Январь";
                    ArrayList<MonthDataStorage> stringData = new ArrayList<>(); // список данных на данный месяц
                    for (int j=1; j< lines.length; j++){
                        String[] lineContents = lines[j].split(",");
                        MonthDataStorage yds = new MonthDataStorage(lineContents[0],lineContents[1],
                                lineContents[2],lineContents[3]);
                        stringData.add(yds); //заносим данные в список данных на этот месяц
                    }
                    monthData.put(month, stringData); //заносим даные в хэш-таблицу. Далее -аналогично
                } else if (i == 2) {
                    month = "Февраль";
                    ArrayList<MonthDataStorage> stringData = new ArrayList<>();
                    for (int j=1; j< lines.length; j++){
                        String[] lineContents = lines[j].split(",");
                        MonthDataStorage yds = new MonthDataStorage(lineContents[0],lineContents[1],
                                lineContents[2],lineContents[3]);
                        stringData.add(yds);
                    }
                    monthData.put(month, stringData);
                } else {
                    month = "Март";
                    ArrayList<MonthDataStorage> stringData = new ArrayList<>();
                    for (int j=1; j< lines.length; j++){
                        String[] lineContents = lines[j].split(",");
                        MonthDataStorage yds = new MonthDataStorage(lineContents[0],lineContents[1],
                                lineContents[2],lineContents[3]);
                        stringData.add(yds);
                    }
                    monthData.put(month, stringData);
                }
                System.out.println("Месячный рассчет за "+ month +" считан!");
            } catch (IOException e) {
                System.out.println("Невозможно прочитать файлы с месячными отчётами. " +
                        "Возможно, файл не находится в нужной директории.");
            }
        }
   }

   void incomeData() { // заполнение таблицы помесячных доходов
       for (String key : monthData.keySet()) {
           for (MonthDataStorage mds : monthData.get(key)) {
               if (mds.isExpense.equals("FALSE") && !monthIncomeData.containsKey(key)) {
                   monthIncomeData.put(key, mds.sumOfOne * mds.quantity);
               } else if (mds.isExpense.equals("FALSE") && monthIncomeData.containsKey(key)) {
                   monthIncomeData.put(key, monthIncomeData.get(key) + mds.sumOfOne * mds.quantity);
               }
           }
       }
   }

   void expenditureData(){ // заполнение таблицы помесячных расходов
        for (String key: monthData.keySet()){
            for (MonthDataStorage mds : monthData.get(key)){
                if (mds.isExpense.equals("TRUE") && !monthExpenditureData.containsKey(key)){
                    monthExpenditureData.put(key,mds.sumOfOne * mds.quantity);
                }
                else if(mds.isExpense.equals("TRUE") && monthExpenditureData.containsKey(key))
                    monthExpenditureData.put(key,monthExpenditureData.get(key)+mds.sumOfOne * mds.quantity);
            }
        }
    }

   void mostExpenseInMonth(String month){ //нахождение самого дорогого пункта расходов в месяце
        int mostExpense = 0;
        String itemName = null;
        for (MonthDataStorage mds : monthData.get(month)){
            if (mds.isExpense.equals("TRUE")){
                if (mds.sumOfOne * mds.quantity > mostExpense){
                    mostExpense = mds.sumOfOne * mds.quantity;
                    itemName = mds.itemName;
                }
            }
        }
        if (itemName != null){
            System.out.println("Самая большая трата: " + itemName + ". Сумма: " + mostExpense);
        }
        else {
            System.out.println("В этом месяце трат нет");
        }
   }

    void mostProfitInMonth(String month){ // самый прибыльный пункт доходов в месяце
        int mostProfit = 0;
        String itemName = null;
        for (MonthDataStorage mds : monthData.get(month)){
            if (mds.isExpense.equals("FALSE")){
                if (mds.sumOfOne * mds.quantity > mostProfit){
                    mostProfit = mds.sumOfOne * mds.quantity;
                    itemName = mds.itemName;
                }
            }
        }
        if (itemName != null){
            System.out.println("Самая большая прибыль: " + itemName + ". Сумма: " + mostProfit);
        }
        else {
            System.out.println("В этом месяце прибыли нет");
        }
    }

   void monthsInfo(){ /* печать информации по месячным отчетам. Из-за использования хэш-таблиц, хронологический порядок
   месяцев нарушается. В теории можно было использовать SortedMap, написать компаратор, но мы пока их не проходили.
   */
       for (String key : monthData.keySet()) {
           System.out.println(key + ":");
           mostExpenseInMonth(key);
           mostProfitInMonth(key);
       }
   }

}
