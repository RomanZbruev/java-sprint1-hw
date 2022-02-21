import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class YearlyReport {
    /*
    Класс месячных отчётов. Ниже инициализируются две хэш таблицы: monthIncomeData и monthExpenditureData
     - данные помесячных доходов и трат соответственно.
     */
    String[] lines; /* переменная для работы со считанным отчётом (использую сразу массив, а не строку исходного
    файла, чтобы избежать повторения метода split в методах */
    String year; // переменная для хранения года считанного отчёта
    HashMap<String, Integer> monthIncomeData = new HashMap<>();
    HashMap<String, Integer> monthExpenditureData = new HashMap<>();
    
    void readingYearlyReport (String path){ // считывание годового отчёта
        try {
            System.out.println("Годовой отчёт считан!");
            String report = Files.readString(Path.of(path));
            lines = report.split("\\n");
            int indexStart = path.lastIndexOf('/') + 3; // индекс начала записи года (знаем формат имени файла)
            int indexEnd = path.lastIndexOf('.'); // индекс конца записи года+1 (знаем формат имени файла)
            year = path.substring(indexStart, indexEnd);// записываем значение года

        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории.");
            lines = null;
        }
    }
    
    int profit (String month){ // считываем прибыль за месяц доход за месяц
        int sumOfProfit = 0;
        for (String line : lines){
            String[] lineContents = line.split(",");
            if (lineContents[0].equals(month) && lineContents[2].equals("false")) {
                sumOfProfit += Integer.parseInt(lineContents[1]);
            }
            else if(lineContents[0].equals(month) && lineContents[2].equals("true")){
                sumOfProfit -= Integer.parseInt(lineContents[1]);
            }
        }
        return sumOfProfit;
    }
    
    void incomePerMonth(){ //  доходы за месяц
        for (String line : lines){
            String[] lineContents = line.split(",");
            if (lineContents[2].equals("false") && lineContents[0].equals("01")){
                monthIncomeData.put("Январь",Integer.parseInt(lineContents[1]));
            }
            else if(lineContents[2].equals("false") && lineContents[0].equals("02")){
                monthIncomeData.put("Февраль",Integer.parseInt(lineContents[1]));
            }
            else if(lineContents[2].equals("false") && lineContents[0].equals("03")){
                monthIncomeData.put("Март",Integer.parseInt(lineContents[1]));
            }
        }
    }
    
    void expenditurePerMonth(){ // расходы за месяц
        for (String line : lines){
            String[] lineContents = line.split(",");
            if (lineContents[2].equals("true") && lineContents[0].equals("01")){
                monthExpenditureData.put("Январь",Integer.parseInt(lineContents[1]));
            }
            else if(lineContents[2].equals("true") && lineContents[0].equals("02")){
                monthExpenditureData.put("Февраль",Integer.parseInt(lineContents[1]));
            }
            else if(lineContents[2].equals("true") && lineContents[0].equals("03")){
                monthExpenditureData.put("Март",Integer.parseInt(lineContents[1]));
            }
        }
    }
    
    double averageMonthIncome (){ // рассчитываем среднемесячный доход (можно было объединить доход и расход в
        // один метод, если передавать параметр String "false"/"true", но подумал, что так хуже для читаемости
        int counterOfMonthsWithIncome = 0;
        double sumOfIncome = 0;
        for (String line : lines){
            String[] lineContents = line.split(",");
            if (lineContents[2].equals("false")){
                counterOfMonthsWithIncome += 1;
                sumOfIncome += Double.parseDouble(lineContents[1]);
            }
        }
    return sumOfIncome/counterOfMonthsWithIncome;
    }

    double averageMonthExpenditure (){ // рассчитываем среднемесячный расход
        int counterOfMonthsWithExpenditure = 0;
        double sumOfExpenditure = 0;
        for (String line : lines){
            String[] lineContents = line.split(",");
            if (lineContents[2].equals("true")){
                counterOfMonthsWithExpenditure += 1;
                sumOfExpenditure += Double.parseDouble(lineContents[1]);
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
