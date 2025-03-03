package    Class;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.security.MessageDigest;
import java.util.Arrays;


public abstract class Card {
    private int id;
    private String ownerName;
    private String accessLevel;
    private LocalDateTime creationTime;

    public Card(int id, String ownerName, String accessLevel) {
        this.id = id;
        this.ownerName = ownerName;
        this.creationTime = LocalDateTime.now();

        // คลาส Card จะเปลี่ยนให้เก็บค่า role จากเดิมที่เก็บเป็น low, medium, high ,none เป็นรูปแบบที่เข้ารหัส เช่น "X7fA9B...zK2Qm"
        // ทำให้ผู้ที่เห็นบัตรไม่สามารถรู้ได้ว่าบัตรนี้ใช้เข้าห้องไหนได้บ้าง หากไม่ลองใช้งานจริง โดยกระบวนการเข้ารหัสจะใช้ เวลาที่สร้างบัตร เป็นส่วนหนึ่งของกระบวนการ
        //  เมื่อมีการใช้บัตร ระบบต้องถอดรหัส role กลับมาเพื่อทำการตรวจสอบสิทธิ์
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
//setAccessLevel() - เปลี่ยนระดับสิทธิ์การเข้าถึง
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
