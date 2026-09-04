package model;

public class Notice {
    private String id;
    private String title;
    private String date;
    private String category; // "Academic", "Exam", "Event", "Urgent"
    private String content;
    private String author;

    public Notice(String id, String title, String date, String category, String content, String author) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.category = category;
        this.content = content;
        this.author = author;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String toFileString() {
        return id + "#" + title + "#" + date + "#" + category + "#" + content + "#" + author;
    }

    public static Notice fromFileString(String line) {
        if (line == null || line.trim().isEmpty() || line.startsWith("//")) return null;
        String[] parts = line.split("#");
        if (parts.length >= 6) {
            return new Notice(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim()
            );
        }
        return null;
    }
}
