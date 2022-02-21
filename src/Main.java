import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        YearlyReport yearRep = new YearlyReport(); // инициализируем класс годовых отчётов
        MonthlyReport monRep = new MonthlyReport(); // инициализируем класс годовых отчётов
        ReconciliationOfReports rop = new ReconciliationOfReports(); // инициализируем класс для сверки отчётов
        Scanner scanner = new Scanner(System.in); // к
        System.out.println("Добрый день, уважаемый пользователь!");
        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                monRep.readingMonthlyReport("./resources/m.20210");
            }
            else if (command == 2) {
                yearRep.readingYearlyReport("./resources/y.2021.csv");
            }
            else if (command == 3) {
                rop.reportsCheck(yearRep,monRep); // сверка происходит в отдельном классе
            }
            else if (command == 4) {
                if (!monRep.monthData.isEmpty()) { //проверка, считаны ли месячные отчёты
                    System.out.println("Информация о месячных отчётах:");
                    monRep.monthsInfo();
                }
                else{
                    System.out.println("Пожалуйста, считайте файлы с месячными отчётами.");
                }
            }
            else if (command == 5) {
                if (yearRep.lines != null){ // проверка,считаны ли годовые отчёты
                    System.out.println("Информация о годовом отчёте:");
                    yearRep.yearInfo();
                }
                else{
                    System.out.println("Пожалуйста, считайте файл с годовым отчётом.");
                }
            }
            else if (command == 0) {
                System.out.println("Завершение работы. Хорошего дня!");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    public static void printMenu() { //реализация консольного интерфейса
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выйти из приложения");
    }
}

