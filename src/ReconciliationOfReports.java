public class ReconciliationOfReports { // класс сверки отчётов
    void reportsCheck(YearlyReport yearRep, MonthlyReport monRep){
        /*
        Блок с if, else if - проверки, считал ли пользователь отчёты
         */
        if(yearRep.lines == null && monRep.monthData.isEmpty()){
            System.out.println("Пожалуйста, считайте файл с годовым отчётом и файлы с месячными отчётами.");
        }
        else if(yearRep.lines == null) {
            System.out.println("Пожалуйста, считайте файл с годовым отчётом.");
        }
        else if(monRep.monthData.isEmpty()){
            System.out.println("Пожалуйста, считайте файлы с месячными отчётами.");
        }
        else {
            monRep.incomeData(); // прибыль в месячных отчётах
            monRep.expenditureData(); // траты в месячных отчётах
            yearRep.incomePerMonth(); // прибыль в годовых отчётах
            yearRep.expenditurePerMonth(); // траты в годовых отчётах
            /*
            Далее - блок проверок на расхождение в отчетах по месяцам. Наверное, можно сделать массив с названиями
            месяцев и циклом по нему, но в реализации на три месяца, как мне показалось, можно и так оставить.
             */
            if(!monRep.monthIncomeData.get("Январь").equals(yearRep.monthIncomeData.get("Январь"))
                    && !monRep.monthExpenditureData.get("Январь").equals(yearRep.monthExpenditureData.get("Январь"))){
                System.out.println("Несоответствие в Январе." +
                        " Пожалуйста, проверьте правильность составления отчётов");
            }
            else if(!monRep.monthIncomeData.get("Февраль").equals(yearRep.monthIncomeData.get("Февраль"))
                    && !monRep.monthExpenditureData.get("Февраль").equals(yearRep.monthExpenditureData.get("Февраль"))){
                System.out.println("Несоответствие в Феврале." +
                        " Пожалуйста, проверьте правильность составления отчётов");
            }
            else if(!monRep.monthIncomeData.get("Март").equals(yearRep.monthIncomeData.get("Март"))
                    && !monRep.monthExpenditureData.get("Март").equals(yearRep.monthExpenditureData.get("Март"))){
                System.out.println("Несоответствие в Марте." +
                        " Пожалуйста, проверьте правильность составления отчётов");
            }
            else{
                System.out.println("Отчёты успешно сверены!");
            }
        }
    }
}
