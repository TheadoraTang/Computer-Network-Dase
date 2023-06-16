//package lab3;
//
//public class test {
//    1.IndexOutOfBoundsException：
//
//            try {
//        int[] arr = new int[]{1,2,3};
//        System.out.println(arr[3]);
//    }catch (IndexOutOfBoundsException e){
//        System.out.println("数组越界异常");
//    }
//
//2.NullPointerException：
//
//            try {
//        String str = null;
//        System.out.println(str.length());
//    } catch (NullPointerException e) {
//        System.out.println("空指针异常");
//    }
//
//3.ClassCastException：
//
//            try {
//        Object obj = new Integer(1);
//        System.out.println((String)obj);
//    } catch (ClassCastException e) {
//        System.out.println("类型转换异常");
//    }
//
//4.IllegalArgumentException：
//
//            try {
//        int a = -1;
//        if(a < 0) {
//            throw new IllegalArgumentException();
//        }
//    } catch (IllegalArgumentException e) {
//        System.out.println("非法参数异常");
//    }
//
//5.NumberFormatException：
//
//            try {
//        String str = "abc";
//        int num = Integer.parseInt(str);
//    } catch (NumberFormatException e) {
//        System.out.println("数字格式异常");
//    }
//}
