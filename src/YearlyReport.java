import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    /*
    Класс годовых отчётов. Ниже инициализируются две хэш таблицы: monthIncomeData и monthExpenditureData
     - данные помесячных доходов и трат соответственно.
     */
    String year; // переменная для хранения года считанного отчёта
    HashMap<String, Integer> monthIncomeData = new HashMap<>(); // данные о доходах по месяцам
    HashMap<String, Integer> monthExpenditureData = new HashMap<>(); // данные о расходах по месяцам
    ArrayList<YearDataStorage> dataYear = new ArrayList<>(); // список для хранения данных в виде класса YearDataStorage
    
    void readingYearlyReport (String path){ // считывание годового отчёта
        try {
            System.out.println("Годовой отчёт считан!");
            String report = Files.readString(Path.of(path));
            String[] lines = report.split("\\n");
            for (int i=1; i< lines.length; i++){//делаем цикл без учета заглавной строки
                String[] lineContents = lines[i].split(",");
                YearDataStorage yds = new YearDataStorage(lineContents[0],lineContents[1],lineContents[2]);
                dataYear.add(yds); //записываем данные в виде класса в список
            }
            int indexStart = path.lastIndexOf('/') + 3; // индекс начала записи года (знаем формат имени файла)
            int indexEnd = path.lastIndexOf('.'); // индекс конца записи года+1 (знаем формат имени файла)
            year = path.substring(indexStart, indexEnd);// записываем значение года

        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории.");
        }
    }
    
    int profit (String month){ // считываем прибыль за месяц доход за месяц
        int sumOfProfit = 0;
        for (YearDataStorage yds : dataYear){
            if (yds.month.equals(month) && yds.isExpense.equals("false")) {
                sumOfProfit += yds.amount;
            }
            else if(yds.month.equals(month) && yds.isExpense.equals("true")){
                sumOfProfit -= yds.amount;
            }
        }
        return sumOfProfit;
    }
    
    void incomePerMonth(){ //  доходы за месяц
        for (YearDataStorage yds : dataYear){
            if (yds.isExpense.equals("false") && yds.month.equals("01")){
                monthIncomeData.put("Январь",yds.amount);
            }
            else if(yds.isExpense.equals("false") && yds.month.equals("02")){
                monthIncomeData.put("Февраль",yds.amount);
            }
            else if(yds.isExpense.equals("false") && yds.month.equals("03")){
                monthIncomeData.put("Март",yds.amount);
            }
        }
    }
    
    void expenditurePerMonth(){ // расходы за месяц
        for (YearDataStorage yds : dataYear){
            if (yds.isExpense.equals("true") && yds.month.equals("01")){
                monthExpenditureData.put("Январь",yds.amount);
            }
            else if(yds.isExpense.equals("true") && yds.month.equals("02")){
                monthExpenditureData.put("Февраль",yds.amount);
            }
            else if(yds.isExpense.equals("true") && yds.month.equals("03")){
                monthExpenditureData.put("Март",yds.amount);
            }
        }
    }
    
    double averageMonthIncome (){ // рассчитываем среднемесячный доход (можно было объединить доход и расход в
        // один метод, если передавать параметр String "false"/"true", но подумал, что так хуже для читаемости
        int counterOfMonthsWithIncome = 0;
        double sumOfIncome = 0;
        for (YearDataStorage yds : dataYear){
            if (yds.isExpense.equals("false")){
                counterOfMonthsWithIncome += 1;
                sumOfIncome += yds.amount;
            }
        }
    return sumOfIncome/counterOfMonthsWithIncome;
    }

    double averageMonthExpenditure (){ // рассчитываем среднемесячный расход
        int counterOfMonthsWithExpenditure = 0;
        double sumOfExpenditure = 0;
        for (YearDataStorage yds : dataYear){
            if (yds.isExpense.equals("true")){
                counterOfMonthsWithExpenditure += 1;
                sumOfExpenditure += yds.amount;
            }
        }
        return sumOfExpenditure/counterOfMonthsWithExpenditure;
    }
    
    void yearInfo () { // печать информации годового отчета
        System.out.println("Рассматриваемый год: " + year);
        System.out.println("Доход в Январе: " + profit("01")); /*можно сделать
        доп метод с ветвлением для каждого месяца, для вывода помесячного дохода*/
        System.out.println("Доход в Феврале: " + profit("02"));
        System.out.println("Доход в Марте: " + profit("03"));
        System.out.println("Среднемесячный доход за год: " + averageMonthIncome());
        System.out.println("Среднемесячный расход за год: " + averageMonthExpenditure());

    }
}
