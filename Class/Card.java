package    Class;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.security.MessageDigest;
import java.util.Arrays;

// เรื่อง Late Binding Lec8
// Late Binding คือการตัดสินใจเลือกเรียกเมธอดของอ็อบเจกต์จริง (Object) ในขณะ Runtime
// ไม่ใช่ดูจากชนิดของตัวแปรในขณะ Compile Time
// เช่น หากมีคลาสลูกที่ Override เมธอด toString() เมื่อเรียกผ่านตัวแปรประเภท Card
// จะเรียก toString() ของคลาสลูก (ตาม Object จริง) นั่นคือ Late Binding

public abstract class Card {
    private int id;
    private String ownerName;
    private String accessLevel;
    private LocalDateTime creationTime;

    public Card(int id, String ownerName, String accessLevel) {
        this.id = id;
        this.ownerName = ownerName;
        this.creationTime = LocalDateTime.now();

        // ในส่วน Access Level ที่มีการเข้ารหัส เมื่อมีการสร้าง Card ส่วนของ Attribute level จะมี parameter ที่รับเข้ามาคือ
        // accessLevel เป็นไปได้ 3 แบบคือ low , medium , high , none
        // เมื่อเราต้องการปกปิดข้อมูลโดยการเข้ารหัส เราก็จะทำการนำข้อความที่อาจจะเป็น low medium high มาเข้ารหัส โดยใช้ key นั่นคือเวลาปัจจุบัน
        // และเก็บ Key ไว้ในตัวแปรอื่นเพื่อนำมาถอดรหัสในภายหลังเมื่อต้องการเห็นค่าที่แท้จริง
        this.accessLevel = encrypt(accessLevel, creationTime.format(DateTimeFormatter.ISO_DATE_TIME));
        // เก็บ Log การสร้าง Card
        CardEditHistoryList.addHistory(new CardEditHistory(this, "creation", "N/A", "Card Created"));
    }

    public int getId() {
        return id;
    }

    public void setOwnerName(String ownerName) {
        String oldValue = this.ownerName;
        this.ownerName = ownerName;
        // เก็บ Log การแก้ไข Card
        CardEditHistoryList.addHistory(new CardEditHistory(this, "ownerName", oldValue, ownerName));
    }

    public void setAccessLevel(String accessLevel) {
        String oldValue = decrypt(this.accessLevel, creationTime.format(DateTimeFormatter.ISO_DATE_TIME));
        this.accessLevel = encrypt(accessLevel, creationTime.format(DateTimeFormatter.ISO_DATE_TIME));
        // เก็บ Log การแก้ไข Card
        CardEditHistoryList.addHistory(new CardEditHistory(this, "accessLevel", oldValue, accessLevel));
    }

    public void setId(int id) {
        String oldValue = String.valueOf(this.id);
        this.id = id;
        // เก็บ Log การแก้ไข Card
        CardEditHistoryList.addHistory(new CardEditHistory(this, "id", oldValue, String.valueOf(id)));
    }

    public String getAccessLevel() {
        return decrypt(accessLevel, creationTime.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    public String getEncryptedAccessLevel() {
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

    // ฟังก์ชันเข้ารหัส
    private String encrypt(String data, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(getFixedLengthKey(key), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ฟังก์ชันถอดรหัส ใช้ในการถอดรหัสเพื่อดูค่าที่แท้จริงของ Access Level
    private String decrypt(String data, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(getFixedLengthKey(key), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // เป็นส่วนเพิ่มเติมที่ช่วยให้การเข้ารหัสสมบูรณ์มากขึ้น
    private byte[] getFixedLengthKey(String key) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(key.getBytes("UTF-8"));
        return Arrays.copyOf(keyBytes, 16);
    }


}
