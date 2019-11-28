import java.util.Scanner;

public class Main_11_28_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String str = scanner.nextLine();
            char[]code = new char[26];
            char[]ym = new char[26];
            int j = 0 ;
            for(char i = 'A'; i<='Z' ;i++){
                code[j++] = i;
            }
            j =  0;
            for(char i = 'V' ; i <='Z' ; i++){
                ym[j++] = i;
            }
            for(char i = 'A' ; i <='U' ; i++){
                ym[j++] = i;
            }
            StringBuffer res= new StringBuffer();
            int index = 0;
            for(int i = 0 ; i < str.length() ; i++){
                char c = str.charAt(i);
                   if(c == ' ')
                    res.append(' ');
                for(j = 0 ; j < code.length ; j++){
                    if(code[j] == c){
                        index = j;
                        res.append(ym[index]);
                    }
                }
            }
            System.out.println(res);
        }
    }
}
