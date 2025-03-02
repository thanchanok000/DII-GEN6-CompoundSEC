package   Class;

import java.util.List;

public class EmployeeCard extends Card {

    public EmployeeCard(int id, String ownerName, String accessLevel) {
        super(id, ownerName, "low");
    }

    public void create(Card card) {
        System.out.println("Employee Card Created: " + card);
    }

    // เรื่อง Method Overload Lec8
    // Method Overload คือการสร้างเมธอดชื่อเดียวกัน แต่มีพารามิเตอร์ที่แตกต่างกัน
    // (จำนวนหรือประเภท)
    // เช่น เมธอด create() นี้มีอยู่ 2 เวอร์ชัน (Overload กัน):
    // 1. create(Card card)
    // 2. create(int id, String ownerName, String accessLevel)
    // การ Overload จะเกิดขึ้นได้เมื่อชื่อเมธอดเหมือนกัน
    // แต่พารามิเตอร์ต่างกันเท่านั้น
    public void create(int id, String ownerName, String accessLevel) {
        Card card = new EmployeeCard(id, ownerName, accessLevel);
        System.out.println("Employee Card Created: " + card);
    }

    public Card read(int id) {
        return null;
    }

    // Method Overload อีกตัวอย่าง เมธอด read() มี 2 เวอร์ชัน
    // 1. read(int id)
    // 2. read(String ownerName)
    // แม้ชื่อเหมือนกัน แต่ชนิดของพารามิเตอร์ต่างกัน จึงถือเป็นการ Overload
    public Card read(String ownerName) {
        return null;
    }

    public Card update(Card card) {
        return null;
    }

    // Overload เมธอด update() เช่นกัน
    // 1. update(Card card)
    // 2. update(int id, String ownerName, String accessLevel)
    public Card update(int id, String ownerName, String accessLevel) {
        Card card = new EmployeeCard(id, ownerName, accessLevel);
        return card;
    }

    public void updateAccessLevel(String level) {
        setAccessLevel(level);
    }

    @Override
    public String getAccessLevel() {
        return "low";
    }

    public boolean delete(int id) {
        return false;
    }

    // Overload เมธอด delete()
    // 1. delete(int id)
    // 2. delete(String ownerName)
    public boolean delete(String ownerName) {
        return false;
    }

    public List<Card> getAll() {
        return List.of();
    }
}
