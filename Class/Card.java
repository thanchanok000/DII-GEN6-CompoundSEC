package Class;

// เรื่อง Late Binding Lec8
// Late Binding คือการตัดสินใจเลือกเรียกเมธอดของอ็อบเจกต์จริง (Object) ในขณะ Runtime
// ไม่ใช่ดูจากชนิดของตัวแปรในขณะ Compile Time
// เช่น หากมีคลาสลูกที่ Override เมธอด toString() เมื่อเรียกผ่านตัวแปรประเภท Card
// จะเรียก toString() ของคลาสลูก (ตาม Object จริง) นั่นคือ Late Binding

public abstract class Card {
    private int id;
    private String ownerName;
    private String accessLevel;

    public Card(int id, String ownerName, String accessLevel) {
        this.id = id;
        this.ownerName = ownerName;
        this.accessLevel = accessLevel;
    }

    public int getId() {
        return id;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    // เรื่อง Object Class Lec8
    // เมธอด toString() นี้เป็นการ Override (เขียนทับ) เมธอด toString() จาก Object
    // Class
    // Object Class เป็นคลาสแม่ของทุกคลาสใน Java โดยทุกคลาสจะสืบทอดจาก Object Class
    // โดยอัตโนมัติ
    // เมธอด toString() ใน Object Class ดั้งเดิม จะคืนค่าเป็น ชื่อคลาส + @ +
    // แฮชโค้ดของอ็อบเจกต์ เช่น Card@1a2b3c
    // แต่เมื่อเราทำการ Override เราสามารถกำหนดรูปแบบข้อความที่ต้องการแสดงผลได้เอง
    // ตัวอย่างนี้คือการแสดง ID, ชื่อเจ้าของ และระดับการเข้าถึงของบัตร
    @Override
    public String toString() {
        return "Card [ID=" + id + ", Name=" + ownerName + ", Access Level=" + accessLevel + "]";
    }

    public String getOwnerName() {
        return ownerName;
    }
}
