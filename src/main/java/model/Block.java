package model;

import util.Hasher;
import java.time.LocalDateTime;

public class Block {
    private String studentName;
    private String subject;
    private int marks;
    private String previousHash;
    private String hash;
    private LocalDateTime timestamp;

    public Block(String studentName, String subject, int marks, String previousHash) {
        this.studentName = studentName;
        this.subject = subject;
        this.marks = marks;
        this.previousHash = previousHash;
        this.timestamp = LocalDateTime.now();
        this.hash = generateHash();
    }

    private String generateHash() {
        String data = studentName + subject + marks + previousHash + timestamp;
        return Hasher.generateHash(data);
    }

    public String getStudentName() { return studentName; }
    public String getSubject() { return subject; }
    public int getMarks() { return marks; }
    public String getPreviousHash() { return previousHash; }
    public String getHash() { return hash; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
