import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;


public class MonthlyReport {
    /*
    Класс месячных отчётов. Ниже инициализируются три хэш таблицы: monthData - соответствие
    "Месяц - строки отчета из этого месяца", monthIncomeData и monthExpenditureData - данные помесячных доходов и трат
    соответственно.
     */
    HashMap<String, String[]> monthData = new HashMap<>();
    HashMap<String, Integer> monthIncomeData = new HashMap<>();
    HashMap<String, Integer> monthExpenditureData = new HashMap<>();

    void readingMonthlyReport(String path){ // чтение месячных отчетов
        for (int i = 1; i < 4; i++) {
            try {
                String report = Files.readString(Path.of(path + i + ".csv"));
                String[] lines = report.split("\\n");
                String month;
                if (i == 1) {
                    month = "Январь";
                    monthData.put(month, lines);
                } else if (i == 2) {
                    month = "Февраль";
                    monthData.put(month, lines);
                } else {
                    month = "Март";
                    monthData.put(month, lines);
                }
                System.out.println("Месячный рассчет за "+ month +" считан!");
            } catch (IOException e) {
                System.out.println("Невозможно прочитать файлы с месячными отчётами. " +
                        "Возможно, файл не находится в нужной директории.");
                monthData = null;
            }
        }
   }

   void incomeData() { // заполнение таблицы помесячных доходов
       for (String key : monthData.keySet()) {
           for (int i = 1; i < monthData.get(key).length; i++) {
               String line = monthData.get(key)[i];
               String[] lineContents = line.split(",");
               int sumOfOne = Integer.parseInt(lineContents[3]);
               int quantity = Integer.parseInt(lineContents[2]);
               if (lineContents[1].equals("FALSE") && !monthIncomeData.containsKey(key)) {
                   monthIncomeData.put(key, sumOfOne * quantity);
               } else if (lineContents[1].equals("FALSE") && monthIncomeData.containsKey(key))
                   monthIncomeData.put(key, monthIncomeData.get(key) + sumOfOne * quantity);
           }
       }
   }

   void expenditureData(){ // заполнение таблицы помесячных расходов
        for (String key: monthData.keySet()){
            for (int i=1; i<monthData.get(key).length; i++){
                String line = monthData.get(key)[i];
                String[] lineContents = line.split(",");
                int sumOfOne = Integer.parseInt(lineContents[3]);
                int quantity = Integer.parseInt(lineContents[2]);
                if (lineContents[1].equals("TRUE") && !monthExpenditureData.containsKey(key)){
                    monthExpenditureData.put(key,sumOfOne*quantity);
                }
                else if(lineContents[1].equals("TRUE") && monthExpenditureData.containsKey(key))
                    monthExpenditureData.put(key,monthExpenditureData.get(key)+sumOfOne*quantity);
            }
        }
    }

   void mostExpenseInMonth(String month){ //нахождение самого дорогого пункта расходов в месяце
        int mostExpense = 0;
        String itemName = null;
        for (String line : monthData.get(month)){
            String[] lineContents = line.split(",");
            if (lineContents[1].equals("TRUE")){
                int sumOfOne = Integer.parseInt(lineContents[3]);
                int quantity = Integer.parseInt(lineContents[2]);
                if (sumOfOne*quantity > mostExpense){
                    mostExpense = sumOfOne*quantity;
                    itemName = lineContents[0];
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
        for (String line : monthData.get(month)){
            String[] lineContents = line.split(",");
            if (lineContents[1].equals("FALSE")){
                int sumOfOne = Integer.parseInt(lineContents[3]);
                int quantity = Integer.parseInt(lineContents[2]);
                if (sumOfOne*quantity > mostProfit){
                    mostProfit = sumOfOne*quantity;
                    itemName = lineContents[0];
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
