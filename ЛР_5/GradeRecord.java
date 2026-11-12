package ua.duikt.holoshchuk.lr5;

public class GradeRecord {
    private final String studentName;
    private final String subject;
    private final int grade;

    public GradeRecord(String studentName, String subject, int grade) throws RecordParseException {
        if (studentName == null || studentName.isBlank()) {
            throw new RecordParseException("ПІБ студента не може бути порожнім");
        }
        if (subject == null || subject.isBlank()) {
            throw new RecordParseException("назва предмета не може бути порожньою");
        }
        if (grade < 0 || grade > 100) {
            throw new RecordParseException("оцінка " + grade + " поза межами 0..100");
        }
        this.studentName = studentName.trim();
        this.subject = subject.trim();
        this.grade = grade;
    }

    public static GradeRecord parse(String line) throws RecordParseException {
        if (line == null || line.isBlank()) {
            throw new RecordParseException("порожній рядок");
        }
        String[] parts = line.split(";", -1);
        if (parts.length != 3) {
            throw new RecordParseException("очікувалось 3 поля, отримано " + parts.length);
        }
        String name = parts[0].trim();
        String subject = parts[1].trim();
        String gradeStr = parts[2].trim();
        int parsedGrade;
        try {
            parsedGrade = Integer.parseInt(gradeStr);
        } catch (NumberFormatException e) {
            throw new RecordParseException(""" + gradeStr + "" не є цілим числом", e);
        }
        return new GradeRecord(name, subject, parsedGrade);
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSubject() {
        return subject;
    }

    public int getGrade() {
        return grade;
    }
}
