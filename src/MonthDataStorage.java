public class MonthDataStorage {
    /*
    Класс хранения данных в месячных отчётах. Переменные названы аналогично названиям в представленных заказчиком
    таблицах CSV. Один экземпляр класса хранит одну строчку таблицы.
     */
    String itemName;
    String isExpense;
    int quantity;
    int sumOfOne;
    public MonthDataStorage(String itemNameFile, String isExpenseFile, String quantityFile, String sumOfOneFile ){
        itemName = itemNameFile;
        isExpense = isExpenseFile;
        quantity = Integer.parseInt(quantityFile);
        sumOfOne = Integer.parseInt(sumOfOneFile);

    }
}
