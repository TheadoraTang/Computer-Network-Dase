package lab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

enum Color {
    RED(1),
    GREEN(2),
    BLUE(3);
    int type;

    Color(int _type) {
        this.type = _type;
    }
}
public class lab {
        public static void main(String[] args) {
            ArrayList<lab3.Color> list = new ArrayList<>();
            for(int i=1;i<=3;i++){
                Collections.addAll(list, lab3.Color.values());
            }
            Random r = new Random(1234567);
            Collections.shuffle(list, r);
            for(int i=0;i<list.size();i++){
                lab3.Color c = list.get(i);
// TODO
                switch (c){
                    case RED:
                        System.out.print("RED ");
                        break;
                    case GREEN:
                        System.out.print("GREEN ");
                        break;
                    case BLUE:
                        System.out.print("BLUE ");
                        break;
                    default:
                        System.out.print("UNKNOWN COLOR");

                }
            }
        }
    }
