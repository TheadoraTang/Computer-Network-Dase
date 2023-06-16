package lab4;

public class TestBattle {
    public static void main(String[] args) {
        BattleObject bo1 = new BattleObject();
        bo1.name = "Object1";
        bo1.hp = 600;
        bo1.attack = 50;
        BattleObject bo2 = new BattleObject();
        bo2.name = "Object2";
        bo2.hp = 500;
        bo2.attack = 40;
        Battle battle = new Battle(bo1, bo2);
        Thread thread = new Thread(battle);
        thread.start();
    }
}