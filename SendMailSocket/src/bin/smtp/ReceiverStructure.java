package bin.smtp;

public class ReceiverStructure {
    private final String folderPath, fileName, email;
    private ERROR error;

    public ReceiverStructure(String folderPath, String fileName, String email) {
        this.folderPath = folderPath;
        this.fileName = fileName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        if (folderPath == null || fileName == null) return null;
        if (System.getProperty("os.name").startsWith("Windows")) {
            return folderPath + (char) 92 + fileName;
        }
        return folderPath + (char) 47 + fileName;
    }

    public ERROR getError() {
        return error;
    }

    public void setError(ERROR error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ReceiverStructure{" +
                "folderPath='" + folderPath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", email='" + email + '\'' +
                ", error=" + error +
                '}';
    }
}
