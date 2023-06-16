package lab3;

import java.util.Random;
import java.util.Arrays;

public interface Animal {
    void eat();
    void travel();
    public class Fish implements Animal, Comparable<Fish>{
        public void eat(){
            System.out.println("Fish eats");
        }
        public void travel(){
            System.out.println("Fish travels");
        }
        public void move(){
            System.out.println("Fish moves");
        }
        int size;
        public Fish(){
            Random r = new Random();
            this.size = r.nextInt(100);
        }
        void print(){
            System.out.print(this.size + " < ");
        }
        public int compareTo(Fish o) {
            return this.size - o.size;
        }
        public static void main(String args[]){
            Fish[] fishs = new Fish[10];
            for(int i = 0; i < 10; i++){
                fishs[i] = new Fish();
            }
            Arrays.sort(fishs);
            for(Fish f : fishs){
                f.print();
            }
        }
    }
}