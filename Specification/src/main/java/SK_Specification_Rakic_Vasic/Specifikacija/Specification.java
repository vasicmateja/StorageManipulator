package SK_Specification_Rakic_Vasic.Specifikacija;


import SK_Specification_Rakic_Vasic.config.Config;
import SK_Specification_Rakic_Vasic.MyThings.MyFile;

import java.util.List;

public interface Specification {

    //konfiguracija
    public void configureMaxSize(long size);

    public void setExtensions(List<String> extensions);

    public void removeExtensions(List<String> extensions);
    public void configureMaxNumberOfFiles(int maxNumberOfFiles);

    public void configureFolder(String folder,int maxFilesNum,int maxSize,List<String> extensions);
    public MyFile config(Config config);

    public boolean check(MyFile file);

    //Osnovne operacije
    public boolean createStorage(String path);
    public boolean createStorage(String name,String path);

    public boolean makeFolder(String path, String name);

    public boolean makeMultipleFolders(String path, String name,int num);

    public boolean addFiles(List<String> files, String path);
    //public boolean addFiles(List<String> filesPath,String path,);

    public boolean deleteFile(String path);

    public boolean rename(String path, String newName);

    public boolean move(String srcPath, String dstPath);


    public boolean download(String pathFileToDownload, String dstPath);


    //Pretrazivanje
    public List<MyFile> searchInDir(String dirPath,String wantedFileName);

    public List<MyFile> twoLevelSearch(String dirPath,String search);

    public List<MyFile> searchToBottom(String dirPath,String search,int flag);

    public List<MyFile> searchByExtension(String dirPath,String extension);

    public List<MyFile> serchFileThatBeginsWith(String dirPath,String substring);

    public List<MyFile> serchFileThatEndsWith(String dirPath,String substring);

    public List<MyFile> serchFileWhichNameContains(String dirPath,String substring);

    public boolean dirHasFilesWithNames(String dirPath,List<String> fileNames);

    public List<MyFile> golbalSearchForFile(String fileName);


    //Sortiranje

    public List<MyFile> sortByName(List<MyFile> files,int order);

    public List<MyFile>sortByCreationDate(List<MyFile> files,int order);

    public List<MyFile> sortByLastModified(List<MyFile> files,int order);


    public List<MyFile> findByDate(String dirPath, String filename, String fromDate, String toDate);

    public List<String> printFiles(List<MyFile> fileslist,boolean path,boolean size,boolean created, boolean modified);
}
