import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        var rafflePrizes = new RafflePrizes();
        var exit = true;
        System.out.println("Для розыгрыша игрушки введите r\n" +
                "Для получение выигранной игрушки нажми g\n" +
                "Для предзаполнения списка игрушек нажмите f\n" +
                "Для добавления игрушки нажмите a(английскую)\n" +
                "Для выхода нажмите e(английскую)");
        while (exit){
            String value = in.nextLine();
            if (value.equals("f"))
            {
                rafflePrizes.fillToys(2);
                System.out.println("Список игрушек предзаполнен");
            }
            else if(value.equals("g")) {
                var toy = rafflePrizes.getPrize();
                if (toy!=null)
                    System.out.println("Получена игрушка "+toy.getName());
            }
            else if (value.equals("a")){
                System.out.println("Введите имя игрушки");
                String name = in.nextLine();
                rafflePrizes.addToy(name);
                System.out.println("Игрушка "+name+" добавлена");
            }
            else if (value.equals("r"))
            {
                var toy=rafflePrizes.rollPrize();
                if(toy!=null)
                    System.out.println("Разыграна игрушка "+toy.getName());
            }
            else if (value.equals("e"))
                System.exit(0);
        }
    }
}