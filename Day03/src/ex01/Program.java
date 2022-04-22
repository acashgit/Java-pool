package ex01;

public class Program {

    public static void main(String[] args) throws InterruptedException {
        Threads egg;
        Threads hen;
        int count = 0;

        if (!args[0].contains("="))
            return;
        for (int index = args[0].indexOf('=') + 1; index < args[0].length();index++){
            count = count * 10 + (args[0].charAt(index) - '0');
        }

        Printer print = new Printer();
        egg = new Threads(print, count, "Egg");
        hen = new Threads(print, count, "Hen");
        egg.start();
        hen.start();
        egg.join();;
        hen.join();
        for (int i = 0; i < count; i++)
            System.out.println("Human");
    }
}
