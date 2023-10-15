package SK_Specification_Rakic_Vasic.Specifikacija;

import SK_Specification_Rakic_Vasic.config.Config;
import SK_Specification_Rakic_Vasic.MyThings.MyFile;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public abstract class SpecificationWrapper implements Specification{
    static private String root;
    static private Config config;

    /**
     * Configures maximum size of file
     *
     * @param size Maximum size of a file
     */
    @Override
    public void configureMaxSize(long size) {
        config.setMaxSize(size);
    }

    /**
     * Sets forbidden extensions to the list
     *
     * @param extensions Forbidden extensions
     */
    @Override
    public void setExtensions(List<String> extensions) {
            for(String extension : extensions){
                if(!config.getExtensions().contains(extension)){
                    config.addExtension(extension);
                }
            }
    }
    /**
     * Removes forbidden extensions from the list
     *
     * @param extensions Forbidden extensions, not anymore
     */
    @Override
    public void removeExtensions(List<String> extensions){
        for (String extension : extensions) {
            if(!config.getExtensions().contains(extension))
                config.removeExtension(extension);
        }
    }

    /**
     * Configures maximum number of files in a Folder
     *
     * @param maxFilesNum Maximum size of a file
     */
    @Override
    public void configureMaxNumberOfFiles(int maxFilesNum) {
                config.setMaxFilesNum(maxFilesNum);
    }

    /**
     * Adds restriction to the folder
     *
     * @param folder Path to the wanted folder
     * @param maxFilesNum Max number of files
     * @param maxSize Max size of a folder
     * @param extensions Forbidden extensions
     */

    @Override
    public void configureFolder(String folder,int maxFilesNum,int maxSize,List<String> extensions){
                config.addRestrictionOnFolder(folder,maxFilesNum,maxSize,extensions);
    }

    /**
     * Creates config file and saves it in storage
     *
     * @param config Configuration parameters
     */
    @Override
    public MyFile config(Config config) {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File(root+"\\config.json");
        try {
            try {
                if(!jsonFile.exists())
                    jsonFile.createNewFile();
            } catch (Exception e){
                System.out.println("Error while making json file");
            }
            objectMapper.writeValue(jsonFile, config);
            MyFile myFile = new MyFile(jsonFile);
            addConfigFile(myFile,getRoot());
            return myFile;
        } catch (StreamWriteException e) {
            System.out.println("ERROR! file in use");
        } catch (DatabindException e) {
            System.out.println("ERROR! Wrong JSON format - coulnt format config file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean addConfigFile(MyFile myfile, String path) {
        return false;
    }


    /**
     * Checks if certain action is allowed on the file
     *
     * @param file MyFile file that we are checking
     * @return boolean if action is valid
     */
    @Override
    public boolean check(MyFile file) {
        if(file.getSize()>getConfig().getMaxSize())
            return false;
        for (String extension : config.getExtensions()) {
            if(file.getName().endsWith(extension))
                return false;
        }

        return true;
    }

    /**
     *Sets storage on wanted location and creates config file if it doesn't exist
     * @param path The path where storage will be made
     *
     * @return Boolean if storage is created successfully
     */
    @Override
    public boolean createStorage(String path){
        try {
            setRoot(path);
            setConfig(new Config(getRoot()));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     *Sets storage on wanted location and creates config file if it doesn't exist
     * @param name Name of the Storage
     * @param path The path where storage will be made
     *
     *
     * @return Boolean if storage is created successfully
     */
    @Override
    public boolean createStorage(String name, String path) {
        return false;
    }


    /**
     *Makes new folder in wanted location with wanted name
     * @param path The path where folder will be made
     * @param name The name of the folder
     *
     * @return Boolean if folder is created successfully
     */
    @Override
    public boolean makeFolder(String path, String name) {
        return false;
    }

    /**
     *Makes multiple new folders in wanted location named 1...n
     * @param path The path where folder will be made
     * @param name The name of the folder
     *
     * @return Boolean if folders is created successfully
     */

    @Override
    public boolean makeMultipleFolders(String path, String name, int num) {
        return false;
    }


    /**
     *Imports files to storage on desired location
     * @param files Paths to the files we want to import
     * @param path The path where folder will be made
     *
     * @return Boolean if files are moved to storage successfully
     */

    @Override
    public boolean addFiles(List<String> files, String path) {
        return false;
    }

    /**
     * Deletes desired file
     * @param path Path to the file
     * @return Boolean if file is deleted successfully
     */

    @Override
    public boolean deleteFile(String path) {
        return false;
    }

    /**
     * Renames desired file
     * @param path Path to the file
     * @param newName New name of the file
     *
     * @return Boolean if file is renamed successfully
     */
    @Override
    public boolean rename(String path, String newName) {return false;}

    /**
     * Moves file from folder to folder
     * @param srcPath Old location of the file
     * @param dstPath New location of the file
     * @return Boolean if file is moved successfully
     */
    @Override
    public boolean move(String srcPath, String dstPath) {
        return false;
    }

    /**
     * Downloads file from storage to the location on PC
     * @param pathFileToDownload path to the file inside the storage
     * @param dstPath Desired location of the file on PC
     * @return Boolean if file is downloaded successfully
     */

    @Override
    public boolean download(String pathFileToDownload, String dstPath) {
        return false;
    }

    /**
     * Search given folder for file
     *
     * @param dirPath Path to the folder we want to search
     * @param wantedFileName Full name of a file we want to search for
     * @return List of files we found
     */

    @Override
    public List<MyFile> searchInDir(String dirPath, String wantedFileName) {
        return null;
    }

    /**
     * Search given folder and its subfolders for file
     *
     * @param dirPath Path to the folder we want to search
     * @param search Full name of a file we want to search for
     * @return  List of files we found
     */

    @Override
    public List<MyFile> twoLevelSearch(String dirPath, String search) {
        return null;
    }

    /**
     * Search given folder and all subfolder in it and its children for file
     *
     * @param dirPath Path to the folder we want to search
     * @param search Full name of a file we want to search for
     * @return  List of files we found
     */
    @Override
    public List<MyFile> searchToBottom(String dirPath, String search,int flag) {
        return null;
    }

    /**
     * Search given folder for all files with desired extension
     *
     * @param dirPath Path to the folder we want to search
     * @param extension Extension that we are looking fir
     * @return List of files we found
     */
    @Override
    public List<MyFile> searchByExtension(String dirPath, String extension) {
        return null;
    }

    /**
     * Search given folder for all files that start with a substring
     *
     * @param dirPath Path to the folder we want to search
     * @param substring Substring that we are looking fir
     * @return  List of files we found
     */
    @Override
    public List<MyFile> serchFileThatBeginsWith(String dirPath, String substring) {
        return null;
    }

    /**
     * Search given folder for all files that end with a substring
     *
     * @param dirPath Path to the folder we want to search
     * @param substring Substring that we are looking fir
     * @return  List of files we found
     */
    @Override
    public List<MyFile> serchFileThatEndsWith(String dirPath, String substring) {
        return null;
    }

    /**
     * Search given folder for all files that contain the substring
     *
     * @param dirPath Path to the folder we want to search
     * @param substring Substring that we are looking fir
     * @return  List of files we found
     */

    @Override
    public List<MyFile> serchFileWhichNameContains(String dirPath, String substring) {
        return null;
    }


    /**
     * Checks if directory has files with curtain names
     *
     * @param dirPath Path to the folder we want to search
     * @param fileNames Names that we are looking for
     * @return  List of files we found
     */
    @Override
    public boolean dirHasFilesWithNames(String dirPath, List<String> fileNames) {
        return false;
    }

    /**
     * Search for a file with name in the whole storage
     *
     *
     * @param fileName File name that we are looking for
     * @return  List of files we found
     */
    @Override
    public List<MyFile> golbalSearchForFile(String fileName) {
        return null;
    }

    /**
     * Search for a file based on creation/modification Date
     *
     * @param dirPath Folder that we are searching in
     * @param filename Name of the file that we are looking for
     * @param fromDate Start date
     * @param toDate   End date
     *
     * @return List of files we found
     */

    @Override
    public List<MyFile> findByDate(String dirPath, String filename, String fromDate, String toDate) {
        return null;
    }


    /**
     * Prints all the files
     *
     * @param fileslist List that we want to print
     * @param path Marker to set if path should be printed
     * @param size Marker to set if size should be printed
     * @param created Marker to set if creation date should be printed
     * @param modified Marker to set if modification date should be printed
     * @return List of the strings to print
     */
    @Override
    public List<String> printFiles(List<MyFile> fileslist, boolean path, boolean size, boolean created, boolean modified) {
        return null;
    }

    /**Sorts given list by name in desired order
     *
     * @param files List of file we want to sort
     * @param order Order of sorting
     * @return List of sorted files
     */
    @Override
    public List<MyFile> sortByName(List<MyFile> files,int order){
        files.sort(new Comparator<MyFile>() {
            @Override
            public int compare(MyFile o1, MyFile o2) {
                if(order == 0) {
                    return -(o1.getName().compareTo(o2.getName()));
                } else {
                    return o1.getName().compareTo(o2.getName());
                }}});
        return files;
    }

    /**Sorts given list by creation date in desired order
     *
     * @param files List of file we want to sort
     * @param order Order of sorting
     * @return List of sorted files
     */
    @Override
    public List<MyFile>sortByCreationDate(List<MyFile> files,int order){
        files.sort(new Comparator<MyFile>() {
            @Override
            public int compare(MyFile file1, MyFile file2) {
                if(order == 0){
                    if(file1.getDateCreated()<file2.getDateCreated()){
                        return -1;
                    }
                        return 1;
                }else{
                    if(file1.getDateCreated()<file2.getDateCreated()){
                        return 1;
                    }
                    return -1;
                }
            }
        });
        return files;
    }

    /**Sorts given list by modification date in desired order
     *
     * @param files List of file we want to sort
     * @param order Order of sorting
     * @return List of sorted files
     */
    @Override
    public List<MyFile> sortByLastModified(List<MyFile> files,int order){
        files.sort(new Comparator<MyFile>() {
            @Override
            public int compare(MyFile file1, MyFile file2) {
                if(order == 0){
                    if(file1.getLastModified()<file2.getLastModified()){
                        return -1;
                    }
                    return 1;
                }else{
                    if(file1.getLastModified()<file2.getLastModified()){
                        return 1;
                    }
                    return -1;
                }
            }
        });
        return files;
    }

    public static String getRoot() {
        return root;
    }

    public static void setRoot(String root) {
        SpecificationWrapper.root = root;
    }

    public static Config getConfig() {
        return config;
    }

    public static void setConfig(Config config) {
        SpecificationWrapper.config = config;
    }
}
