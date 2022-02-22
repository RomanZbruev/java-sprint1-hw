public class YearDataStorage {
    /*
    Класс хранения данных в годовых отчётах. Переменные названы аналогично названиям в представленных заказчиком
    таблицах CSV. Один экземпляр класса хранит одну строчку таблицы.
     */
    String month;
    int amount;
    String isExpense;
    public YearDataStorage(String monthFile, String amountFile, String isExpenseFile){
        month = monthFile;
        amount = Integer.parseInt(amountFile);
        isExpense = isExpenseFile;
    }
}
