import java.util.Scanner;

public class Main_11_28_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            int month = scanner.nextInt();
            if(month < 3){
                System.out.println(1);
                return;
            }
            int m1 = 1 ,m2 = 0 ,old = 1 , young = 1;
            int i = 4;
          while(i<=month){
                old += m2;
                m2 = m1;
                m1 = old;
                young = m1 + m2;
                i++;
            }
            System.out.println(old+young);
        }
        scanner.close();
    }
}
