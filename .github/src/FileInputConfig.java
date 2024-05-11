public class FileInputConfig implements InputConfig{
    
    private final String fileName;
    private final char delimiter;

    public FileInputConfig(String fileName, char delimiter){

        this.fileName  = fileName;
        this.delimiter = delimiter;
    }

    public String getFileName(){
        return fileName;
    }

    public char getDelimiter() {return delimiter; }
}
//done