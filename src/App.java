import java.io.*;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.Integer.parseInt;

public class App {
    public void run() throws IOException {
        File yourFile = new File("balance.txt");
        if(!yourFile.exists()){
            yourFile.createNewFile(); // if file already exists will do nothing
            FileOutputStream oFile = new FileOutputStream(yourFile, false);
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("balance.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Path fileName = Paths.get("balance.txt");
        String line = Files.readAllLines(fileName).get(0);

        int balance = 10;
        if(line != null){
            balance = parseInt(line);
        }
        int bet = 1;
        System.out.println("Ваш баланс составляет "+balance+" евро-монет");
        Random rnd = new Random();
        boolean isRunning = true;
        do{
            System.out.println("Текущая ставка: " + bet + " евро-монет. Изменить ставку(w), Начать игру(ANY KEY)");
            Scanner scanner = new Scanner(System.in);
            if(scanner.nextLine().equals("w")){
                System.out.println("Какая ставка?: ");
                try{
                    bet = parseInt(scanner.nextLine());
                }catch (NumberFormatException ex){
                    System.out.println("Неправильный ввод! Ставка будет 10 евро-монет");
                }
            }

            if(balance < bet) {
                System.out.println("Balance lower that bet!. Change the bet");
                continue;
            }
            System.out.println("Задумано число в диапазоне от 0 до 10, угадай какое: ");
            int randomized = rnd.nextInt(11);
            for(int i = 0; i < 3; i++){
                int num = 0;
                while(true){
                    try{
                        num = parseInt(scanner.nextLine());
                        break;
                    }catch (NumberFormatException ex){
                        System.out.println("Неправильный ввод. Пропишите число ещё раз: ");
                    }
                }


                if(randomized == num){
                    System.out.println("Вы победили!");
                    balance += bet;
                    break;
                }else if(i < 2) System.out.println("Неправильно! Осталось попыток " + (2-i) + "\nПодсказка! - число " + (randomized > num ? "больше" : "меньше"));
                else{
                    System.out.println("Вы проиграли! Правильное число было - "+randomized);
                    balance -= bet;
                }

            }
            System.out.println("Текущий баланс: "+balance+" евро-монет");
            System.out.println("Хотите повторить/пополнить баланс(y/n/w): ");
            String str = scanner.nextLine();
            isRunning = str.equals("y") || str.equals("w");
            if(str.equals("w")){
                System.out.println("Введите сумму пополнения: ");
                int money = parseInt(scanner.nextLine());
                balance += money;
                System.out.println("После поплнения ваш баланс составляет "+balance+" евро-монет");
            }
        }while(isRunning);
        FileWriter myWriter = new FileWriter("balance.txt");
        myWriter.write(balance + "");
        myWriter.close();
    }
}
