package SK_Specification_Rakic_Vasic.MyThings;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
@Getter
@Setter
public class MyFile {
    private String name;
    private String path;
    private long size;
    private long dateCreated;
    private long lastModified;
    private File localFile;


    public MyFile(String fileName,String filePath,long fileSize,long fileCreated, long fileModified){
        name = fileName;
        path = filePath;
        size = fileSize;
        dateCreated = fileCreated;
        lastModified = fileModified;
    }

    public MyFile(File file){
        localFile = file;
        name = file.getName();
        path = file.getPath();
        size = file.length();
        try {
            BasicFileAttributes file_att = Files.readAttributes(
                    file.toPath(), BasicFileAttributes.class);
            dateCreated = file_att.creationTime().toMillis();
            lastModified = file_att.lastModifiedTime().toMillis();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public MyFile(){}

    public void setName(String name) {
        name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getLocalFile() {
        return localFile;
    }
}
