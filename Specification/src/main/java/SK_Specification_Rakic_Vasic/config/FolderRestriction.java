package SK_Specification_Rakic_Vasic.config;

import SK_Specification_Rakic_Vasic.MyThings.MyFile;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class FolderRestriction {

    private String folderPath;

    private long maxSize;
    private List<String> extensions = new ArrayList<>();
    private int maxFilesNum;

    public FolderRestriction(String folder,int maxFilesNum){
        this.folderPath = folder;
        this.maxFilesNum = maxFilesNum;
    }

    public FolderRestriction(String folder,long maxSize){
        this.folderPath = folder;
        this.maxSize = maxSize;
    }

    public FolderRestriction(String folder,List<String> extensions){
        this.folderPath = folder;
        this.extensions = extensions;
    }

    public FolderRestriction(String folder,int maxFilesNum,int maxSize,List<String> extensions){
        this.folderPath = folder;
        this.extensions = extensions;
        this.maxSize = maxSize;
        this.maxFilesNum=maxFilesNum;
    }

    public FolderRestriction(String path, int maxFilesNum, List<String> extensions) {
        this.folderPath = path;
        this.maxFilesNum = maxFilesNum;
        this.extensions = extensions;
    }
}
