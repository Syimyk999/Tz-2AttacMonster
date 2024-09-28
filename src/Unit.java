import java.util.Scanner;

public class Unit {
    private String name;
    private int hp;
    private int hpMax;
    private int attack;
    private int defence;
    private float critAttackChance;
    private boolean defStance;

    public Unit() {
    }

    public Unit(String name, int hpMax, int attack, int defence) {
        this.name = name;
        this.hpMax = hpMax;
        this.hp = this.hpMax;
        this.attack = attack;
        this.defence = defence;
        this.critAttackChance = 0.2f;
    }

    public void attack(Unit target) {
        int actualAttack = attack;
        if (Math.random() <= critAttackChance) {
            actualAttack *= 2;
        }
        int resultedAttackAmount = target.takeDamage(actualAttack);
        System.out.println("Персонаж " + name + " наносит врагу " + target.name + " " + resultedAttackAmount + " ед. урона ");
        target.printInfo();
    }

    public void defenceStance() {
        defStance = true;
        System.out.println(name + " защищается ");
    }

    public void beginTurn() {
        System.out.println();
        System.out.println(" Ход персаножа: " + name);
        printInfo();
        defStance = false;
    }

    public int takeDamage(int amount) {
        int actualDefence = defence;
        if (defStance) {
            actualDefence *= 1.5f;
        }
        amount -= actualDefence;
        if (amount < 0) {
            amount = 0;
        }
        hp -= amount;
        return amount;
    }

    public void cure(int amount) {
        hp += amount;
        if (hp > hpMax) {
            hp = hpMax;
        }
    }
    public static void battle(Unit player, Unit enemy) {
        Scanner scanner = new Scanner(System.in);
        while (player.hp > 0 && enemy.hp > 0) {
            player.beginTurn();

            System.out.println("Выберите действие:");
            System.out.println("1. Атака");
            System.out.println("2. Защита");
            System.out.println("3. Лечение");

            int choice = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice >= 1 && choice <= 3) {
                        validInput = true;
                    } else {
                        System.out.println("Неверный выбор." +
                                "Введите число 1, 2 или 3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неверный выбор. Введите число ");
                }
            }
            switch (choice) {
                case 1:
                    player.attack(enemy);
                    break;
                case 2:
                    player.defenceStance();
                    break;
                case 3:
                    player.cure(10);
                    break;
            }

            if (enemy.hp > 0) {
                enemy.beginTurn();
                enemy.attack(player);
            }
        }

        if (player.hp <= 0) {
            System.out.println("Вы проиграли!");
        } else {
            System.out.println("Вы победили!");
        }
        scanner.close();
    }
    public void printInfo() {
        System.out.println(name + " HP: " + hp + "/" + hpMax + " ATTACK: " + attack + " DEFSTANCE: " + defStance);
    }
}



