package SK_LocalImplementation_Rakic_Vasic;

import SK_Specification_Rakic_Vasic.MyThings.MyFile;
import SK_Specification_Rakic_Vasic.Specifikacija.SpecificationWrapper;
import SK_Specification_Rakic_Vasic.Specifikacija.StorageManager;
import SK_Specification_Rakic_Vasic.config.Config;
import SK_Specification_Rakic_Vasic.config.FolderRestriction;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocalImplementation extends SpecificationWrapper {

    private String storagePath = System.getProperty("user.dir");

    private String path;

    static {
        StorageManager.setStorage(new LocalImplementation());
    }

    @Override
    public boolean createStorage(String path){
        super.setRoot(path);
        Config config1 = new Config(path);
        super.setConfig(config1);
        File configFile = new File(super.getRoot() + "\\config.json");
        if (configFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(configFile));
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                String read = stringBuilder.toString();
                Config config = new Gson().fromJson(read,Config.class);
                super.setConfig(config);
            } catch (Exception e) {
                System.out.println("Error while reading config.json ");
                config(super.getConfig()); //setujemo defaultne vrednosti za config
                return false;
            }
            return true;
        }else {
            storagePath = path;
            File storage = new File(super.getRoot());
            boolean exists = storage.exists();

            if(exists){
                super.setRoot(super.getRoot());
            }else {
                String path1 = storagePath + super.getRoot();
                storage = new File(path1);
                storage.mkdir();
                super.setRoot(path1);
            }
            System.out.println(super.getRoot());
            config(super.getConfig());
            return true;
        }
    }

    @Override
    public boolean check(MyFile file) {
        if(!super.check(file)) return false;
        String[] filePath = file.getPath().split("\\\\");
        String dirPath = file.getPath().substring(0,file.getPath().length()-filePath[filePath.length-1].length()-1).toLowerCase();

        List<FolderRestriction> restrictions = super.getConfig().getRestrictionsFolder();

        if(restrictions.isEmpty()) return true;

        int maxNumOfFiles=-1;
        for (FolderRestriction restriction: restrictions){
            if(restriction.getFolderPath().equalsIgnoreCase(dirPath))
                maxNumOfFiles = restriction.getMaxFilesNum();
        }
        if(new File(dirPath).listFiles() == null) return true;

        if(new File(path).listFiles().length >= maxNumOfFiles && maxNumOfFiles != -1) return false;

        return true;
    }
    @Override
    public boolean createStorage(String name, String path) {

        //TODO:VIDETI STA CEMO SA name-om
        super.setRoot(path);
        super.setConfig(new Config(path));
        File configFile = new File(super.getRoot() + "\\config.json");
        if (configFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(configFile));
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                String read = stringBuilder.toString();
                Config config = new Gson().fromJson(read, Config.class);
                super.setConfig(config);
            } catch (Exception e) {
                System.out.println("Error while reading config.json ");
                config(super.getConfig()); //setujemo defaultne vrednosti za config
                return false;
            }
            return true;
        }else {
            storagePath = path;
            File storage = new File(super.getRoot());
            boolean exists = storage.exists();

            if(exists){
                super.setRoot(super.getRoot());
            }else {
                String path1 = storagePath + super.getRoot();
                storage = new File(path1);
                storage.mkdir();
                super.setRoot(path1);
            }
            System.out.println(super.getRoot());
            config(super.getConfig());
            return true;
        }
    }

    @Override
    public boolean makeFolder(String path, String name) {
        //System.out.println(getRoot());
        File folder = new File(getRoot()+"\\"+path+"\\"+name);

        if(!check(new MyFile(folder.getName(),folder.getPath(),0,0,0)))
            return false;

        return folder.mkdir();
    }

    @Override
    public boolean makeMultipleFolders(String path, String name, int num) {
        for(int i = 0 ; i < num ;i++){
            File folder = new File(getRoot()+"\\"+path+"\\"+name+Integer.toString(i));
            if(!check(new MyFile(folder.getName(),folder.getPath(),0,0,0)))
                return false;
            if(!folder.mkdir()) return false;
        }
        return true;
    }

    @Override
    public boolean addFiles(List<String> files, String path) {
        /*
        System.out.println("Usao u metoduu");
        System.out.println(files);
        System.out.println(path);
         */
        for (String myfilepath : files) {

            File sourceFile = new File(myfilepath);
            MyFile myfile = new MyFile(sourceFile);

            if (check(myfile)) {
                String filePath = getRoot()+ "\\" + path + "\\" + myfile.getName();
                File destinationFile = new File(filePath.replaceAll("]",""));


                System.out.println("SOURCE FOLDER  " + sourceFile.toPath());
                System.out.println();
                System.out.println("DESTINATION FOLDER  " + destinationFile.toPath());
                try {
                    Files.copy(sourceFile.toPath(), destinationFile.toPath());
                } catch (IOException e) {
                    System.out.println("File already exists");
                    return false;
                }
            } else {
                System.out.println("Fajl" + myfile.getName() + " ne prolazi proveru!");
            }
        }
        return true;
    }

    @Override
    public boolean addConfigFile(MyFile myfile, String path) {

        if(check(myfile)){
            String filePath = path + "\\" + myfile.getName();
            //System.out.println("Putanja   " +filePath);
            File file = new File(filePath);
            if(myfile.getLocalFile().exists()){
                file = myfile.getLocalFile();
            }
            myfile.setPath(path);

            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("File already exists");
                return false;
            }
        } else {
            System.out.println("Fajl" +myfile.getName() + " ne prolazi proveru!");
        }
        return true;
    }

    @Override
    public boolean deleteFile(String path) {
        File file = null;
        try {
            file = new File(path);
            file.delete();
        } catch (Exception e){

                System.out.println("Error while deleting file " + path);

            return false;
        }
        return true;
    }

    @Override
    public boolean rename(String path, String newName) {
        String[] folders = path.split("\\\\");
        File file = new File(getRoot()+ "\\\\" + path);
        folders[folders.length-1]=newName;
        String name = String.join("\\",folders);
        File renamedFile = new File(getRoot()+"\\"+name);
        //if(check(new MyFile(renamedFile)))
           // return file.renameTo(renamedFile);
        return file.renameTo(renamedFile);
    }

    @Override
    public boolean move(String srcPath, String dstPath){
        File oldFile = new File(getRoot(),srcPath);
        String[] srcSplitted = srcPath.split("\\\\");
        File newFile = new File(getRoot()+"\\"+dstPath,srcSplitted[srcSplitted.length-1]);
        if(!check(new MyFile(newFile)))
            return false;
        if(oldFile.exists() && !newFile.exists()) {
            try {
                Files.move(oldFile.toPath(), newFile.toPath());
            } catch (Exception e) {
                if (oldFile.listFiles().length > 0) {
                    System.out.println("File has children operation unsuported");
                } else {
                    System.out.println("File in use");
                }
            }
        }
        return true;
    }


    @Override
    public boolean download(String pathFileToDownload, String dstPath) {
        File sourceFile = new File( getRoot() + "\\"+ pathFileToDownload);
        //System.out.println( getRoot() + "\\"+ pathFileToDownload );
        MyFile myfile = new MyFile(sourceFile);
        File destinationFile = new File(dstPath + "\\" + myfile.getName());

        try {
            Files.copy(sourceFile.toPath(),destinationFile.toPath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    //Searches
    @Override
    public List<MyFile> searchInDir(String dirPath, String wantedFileName) {
        ArrayList<MyFile> myFiles = new ArrayList<>();
        File folder = new File(getRoot()+"\\"+dirPath);
        File[] files = folder.listFiles();
        for(File file:files){
            if(file.getAbsolutePath().toLowerCase().endsWith(wantedFileName.toLowerCase())){
                MyFile myFile = new MyFile(file);
                myFiles.add(myFile);
            }
        }

        if(myFiles.isEmpty()){
            System.out.println("Search is unsuccessful, try again");
            return null;
        }
        //TODO: print
        return myFiles;
    }

    @Override
    public List<MyFile> twoLevelSearch(String dirPath, String search) {
        ArrayList<MyFile> myFiles = new ArrayList<>();
        File folder = new File(getRoot()+"\\"+dirPath);
        File[] files = folder.listFiles();
        for(File file : files){
            if(file.isDirectory()){
                File[] newfiles = file.listFiles();
                for(File file1 : newfiles){
                    if(file1.getAbsolutePath().toLowerCase().endsWith(search.toLowerCase())){
                        MyFile myFile = new MyFile(file1);
                        myFiles.add(myFile);
                    }
                }
            } else if(file.getAbsolutePath().toLowerCase().endsWith(search.toLowerCase())){
                MyFile myFile = new MyFile(file);
                myFiles.add(myFile);
            }

        }
        if(myFiles.isEmpty()){
            System.out.println("Search is unsuccessful, try again");
            return null;
        }
        return myFiles;
    }

    @Override
    public List<MyFile> searchToBottom(String dirPath, String search,int flag) {
        ArrayList<MyFile> myFiles = new ArrayList<>();
        File folder = new File(dirPath);
        if(flag == 0)  folder = new File(getRoot()+"\\"+dirPath);
        File[] files = folder.listFiles();
        if(files != null)
            for(File file : files){
                if(file.isDirectory())
                    myFiles.addAll(searchToBottom(file.getAbsolutePath(),search,1));
                if(file.getAbsolutePath().toLowerCase().endsWith(search.toLowerCase())){
                    MyFile myFile = new MyFile(file);
                    myFiles.add(myFile);
                }

            }

        return myFiles;
    }

    @Override
    public List<MyFile> searchByExtension(String dirPath, String extension) {
        ArrayList<MyFile> myFiles = new ArrayList<>();
        File folder = new File(getRoot()+"\\"+dirPath);
        File[] files = folder.listFiles();
        for(File file : files){
            if(file.getAbsolutePath().toLowerCase().endsWith(extension.toLowerCase())){
                MyFile myFile = new MyFile(file);
                myFiles.add(myFile);
            }

        }
        return myFiles;
    }

    @Override
    public List<MyFile> serchFileThatBeginsWith(String dirPath, String substring) {
        ArrayList<MyFile> myFiles = new ArrayList<>();
        File folder = new File(getRoot()+"\\"+dirPath);
        File[] files = folder.listFiles();
        for(File file : files){
            String[] filePaths = file.getAbsolutePath().split("\\\\");
            String fileName = filePaths[filePaths.length-1];
            if(fileName.toLowerCase().startsWith(substring.toLowerCase())){
                MyFile myFile = new MyFile(file);
                myFiles.add(myFile);
            }
        }
        return myFiles;
    }

    @Override
    public List<MyFile> serchFileThatEndsWith(String dirPath, String substring) {
        ArrayList<MyFile> myFiles = new ArrayList<>();
        File folder = new File(getRoot()+"\\"+dirPath);
        File[] files = folder.listFiles();
        for(File file : files){
            String[] filePaths = file.getAbsolutePath().split("\\\\");
            String fileName = filePaths[filePaths.length-1];
            if(fileName.toLowerCase().endsWith(substring.toLowerCase())){
                MyFile myFile = new MyFile(file);
                myFiles.add(myFile);
            }
        }
        return myFiles;
    }

    @Override
    public List<MyFile> serchFileWhichNameContains(String dirPath, String substring) {
        ArrayList<MyFile> myFiles = new ArrayList<>();
        File folder = new File(getRoot()+"\\"+dirPath);
        File[] files = folder.listFiles();
        for(File file : files){
            String[] filePaths = file.getAbsolutePath().split("\\\\");
            String fileName = filePaths[filePaths.length-1];
            if(fileName.toLowerCase().contains(substring.toLowerCase())){
                MyFile myFile = new MyFile(file);
                myFiles.add(myFile);
            }
        }
        return myFiles;
    }

    @Override
    public boolean dirHasFilesWithNames(String dirPath, List<String> fileNames) {
       ArrayList<MyFile> myFiles = new ArrayList<>();
       File folder = new File(getRoot()+"\\"+dirPath);
       File[] files = folder.listFiles();
       for (File file :files){
           String[] filePaths = file.getAbsolutePath().split("\\\\");
           String fileName = filePaths[filePaths.length-1];
           if(fileNames.contains(fileName)){
               MyFile myFile = new MyFile(file);
               myFiles.add(myFile);
           }
       }
        return !myFiles.isEmpty();
    }

    @Override
    public List<MyFile> golbalSearchForFile(String fileName) {
        List<MyFile> myFiles;
        ArrayList<MyFile> results = new ArrayList<>();
        myFiles = searchToBottom(getRoot(),fileName,1);
        for(MyFile file : myFiles){
            if(file.getName().equalsIgnoreCase(fileName)){
                results.add(file);
            }
        }
        return results;
    }

    @Override
    public List<MyFile> findByDate(String dirPath, String fileName, String fromDate, String toDate) {
        List<MyFile> files = new ArrayList<>();
        files = searchInDir(dirPath,fileName);

        long fromDateLong = 0;
        long toDateLong = 0;
        try {
            fromDateLong= Long.parseLong(fromDate);
            toDateLong = Long.parseLong(toDate);
        }catch (Exception e){
            System.out.println("You have enterd date wrongly");
        }

        for (MyFile myFile : files){
            if(myFile.getDateCreated()>fromDateLong && myFile.getDateCreated()<toDateLong){
                files.remove(myFile);
            }
        }

        return files;
    }

    @Override
    public List<String> printFiles(List<MyFile> fileslist, boolean path, boolean size, boolean created, boolean modified) {
        ArrayList<String> output = new ArrayList<>();
        String line;
        for(MyFile myFile : fileslist){
            line = myFile.getName();
            if(path)
                line = line +" "+myFile.getPath();
            if(size)
                line = line +" "+Long.toString(myFile.getSize())+"B";
            if(created) {
                Date date = new java.util.Date(myFile.getDateCreated());
                SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1"));
                String formattedDate = dateFormat.format(date);
                line = line + " " + formattedDate;
            }
            if(modified) {
                Date date = new java.util.Date(myFile.getLastModified());
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1"));
                String formattedDate = sdf.format(date);
                line = line + " " + formattedDate;
            }
            output.add(line);
        }
        return output;
    }

}
