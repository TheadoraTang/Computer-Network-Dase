package lab2;
import java.util.Arrays;
import java.io.PrintWriter;
import java.util.Scanner;

public class Tangxiaohui_10215501437lab2 {
//    public static void main(String[] args) {
//        char c1 = '中';
//        char c2 = '哈哈';
//        float f1 = 54.321;
//        boolean bo1 = 1;
//        System.out.println(bo1);
//    }
//public static int temp(int a, int b){
//    return a == 0 ? b : temp(b % a, a);
//}
//    public static void main(String[] args){
//        for(int j = 1; j <= 5;j++){
//            for (int i = 1; i <= j; i++){
//                System.out.print("*");
//            }
//            System.out.print("\n");
//        }
//
//    }
//        public static void main(String[] args) {
//            int a[] = new int[] { 18, 62, 68, 82, 65, 9 };
//            System.out.println("排序之前 :");
//            System.out.println(Arrays.toString(a));
//            Arrays.sort(a);
//            System.out.println("排序之后:");
//            System.out.println(Arrays.toString(a));
//            System.out.println(Arrays.binarySearch(a,68));
//        }
//    public static void main(String[] args) {
//        String str1 = "the light";
//        String str2 = str1;
//        String str3 = new String(str1);
//        String str4 = "the light";
//        String str5 = "the "+"light";
//        System.out.println( str1 == str2);
//        System.out.println( str1 == str3);
//        System.out.println( str1 == str4);
//        System.out.println( str1 == str5);
//        System.out.println( str1.equals(str4));
//    }
public static void main(String[] args) {
//    Scanner sc = new Scanner(System.in);
////使用PrintWriter
//    PrintWriter pw = new PrintWriter(System.out);
//    while (sc.hasNext()) {
//        String line = sc.next();
//        pw.println("pw收到输入的字符串为:" + line);
////        pw.flush();//试试将flush注释掉
//        System.out.println("out收到输入的字符串为:"+line);
//    }
        int[] array = new int[10];
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out, true);
        for (int i = 0; i < 10; i++) {
            array[i] = sc.nextInt();
        }
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
        pw.println("排序后的结果：");
        for (int i = 0; i < 10; i++) {
            pw.print(array[i] + " ");
            pw.flush();
        }
    }
}

