package Class;

import java.util.ArrayList;
import java.util.List;

//Singleton pattern
//เป็น design pattern ที่ใช้สำหรับการสร้าง class ที่สามารถมี instance ได้แค่หนึ่งเดียวในระบบตลอดการทำางานของโปรแกรม
//โดยที่ instance เดียวนี้จะถูกใช้งานทั่วทั้งโปรแกรม ไม่ว่าจะเป็นที่ไหนก็สามารถเข้าถึง instance นี้ได้จากทุกส่วนของโปรแกรม
//- สร้าง List เก็บข้อมูลไว้ที่เดียวแล้วเรียกใช้ คือ (RoomList, CardList)

//private static instance): ทั้ง CardList และ RoomList มีตัวแปร private static ที่ใช้เก็บอินสแตนซ์เดียวของคลาสนั้นๆ ซึ่งทำให้มั่นใจได้ว่าจะมีแค่หนึ่งอินสแตนซ์
//private constructor ทำให้ไม่สามารถสร้างอินสแตนซ์ใหม่จากภายนอก
//public static getInstance() ใช้ควบคุมการสร้างและคืนค่าอินสแตนซ์เดิม
//ใช้ static final List<> ทำให้ข้อมูลถูกแชร์ทั่วทั้งโปรแกรม

public class CardList {
    // Private static instance
    private static CardList instance;
    private static final List<Card> cards = new ArrayList<>();

    // Private constructor to prevent instantiation
    private CardList() {
        // Initialize the list or perform any setup needed
    }

    // Public static method to get the singleton instance
    public static CardList getInstance() {
        if (instance == null) {
            instance = new CardList(); // Initialize only once
        }
        return instance;
    }

    // Methods for adding, updating, and removing cards
    public void addCard(Card card) {
        cards.add(card);
        System.out.println("Card Added: " + card);
    }

    public void updateCard(int index, Card newCard) {
        Card oldCard = cards.get(index);
        cards.set(index, newCard);
        System.out.println("Card Updated: " + newCard);

        // Save history of card update
        CardEditHistoryList.addHistory(new CardEditHistory(oldCard, "update", oldCard.toString(), newCard.toString()));
    }

    public static List<Card> getCards() {
        return new ArrayList<>(cards); // Return a copy of the list
    }

    public void displayCards() {
        for (Card card : cards) {
            System.out.println("Card [ID=" + card.getId() + ", Name=" + card.getOwnerName() + ", Access Level=" + card.getEncryptedAccessLevel() + "]");
        }
    }
}