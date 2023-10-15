package SK_Specification_Rakic_Vasic.MyThings;

import java.util.ArrayList;
import java.util.List;

public class MyFolder extends MyFile {

    private List<MyFile> children = new ArrayList<>();

    public MyFolder(){

    }

    public MyFolder(String fileName,String filePath,long fileSize,long fileCreated, long fileModified){
        super(fileName,filePath,fileSize,fileCreated,fileModified);
    }

    public MyFolder(java.io.File file){
        super(file);
        java.io.File[] children = file.listFiles();
        for(java.io.File f : children){
            addChild(new MyFile(f));
        }
    }

    public void addChild(MyFile myFile){
        this.children.add(myFile);
    }
}
