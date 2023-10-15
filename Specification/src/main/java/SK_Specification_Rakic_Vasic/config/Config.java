package SK_Specification_Rakic_Vasic.config;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Long.MAX_VALUE;


@Getter
@Setter
public class Config {

    private String root;
    private long maxSize=MAX_VALUE;
    private List<String> extensions = new ArrayList<>();
    private int maxFilesNum = 50;

    private List<FolderRestriction> restrictionsFolder = new ArrayList<>();

    public Config(String root) {
        this.root=root;
    }

    public void addExtension(String ext){
        extensions.add(ext);
    }

    public void removeExtension(String ext){
        extensions.remove(ext);
    }

    public void addRestrictionOnFolder(String path,int maxFilesNum,int maxSize,List<String> extensions){
        removeRestriction(path);
        if( maxFilesNum !=0 && maxSize !=0 && !extensions.isEmpty()){
            restrictionsFolder.add(new FolderRestriction(path,maxFilesNum,maxSize,extensions));
            return;
        }

        if( maxFilesNum ==0 ){
            if(maxSize == 0){
                restrictionsFolder.add(new FolderRestriction(path,extensions));
                return;
            }else {
                restrictionsFolder.add(new FolderRestriction(path,maxSize,extensions));
                return;
            }
        }else {
            if(maxSize == 0){
                restrictionsFolder.add(new FolderRestriction(path,maxFilesNum,extensions));
                return;
            }
            restrictionsFolder.add(new FolderRestriction(path,maxFilesNum,maxSize,extensions));
            return;

        }




    }
    public void removeRestriction(String path){
        List<FolderRestriction> removes = new ArrayList<>();
        for(FolderRestriction restriction: restrictionsFolder){
            if (restriction.getFolderPath().equalsIgnoreCase(path))
                removes.add(restriction);
        }
        restrictionsFolder.removeAll(removes);
    }



}
