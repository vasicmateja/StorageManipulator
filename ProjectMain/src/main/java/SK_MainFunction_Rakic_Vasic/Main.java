package SK_MainFunction_Rakic_Vasic;

import SK_Specification_Rakic_Vasic.MyThings.MyFile;
import SK_Specification_Rakic_Vasic.Specifikacija.SpecificationWrapper;
import SK_Specification_Rakic_Vasic.Specifikacija.StorageManager;
import SK_Specification_Rakic_Vasic.config.Config;

import java.io.File;
import java.util.*;

public class Main {

    static Config config;
    static List<MyFile> foundFiles;
    static SpecificationWrapper storage;
    static String storageType;

    public static void main(String[] args) throws Exception{

        if(args.length != 2){
            throw new Exception("Expected 2 args, 1st should be type of Storage and 2nd where you want storage to be made");

        }

        Scanner input = new Scanner(System.in);
        storage = createStorage(args);

        System.out.println("Type help to get the list of commands");
        interactWithSoftware(storage, input);

    }

    private static SpecificationWrapper createStorage(String[] args) throws Exception{

        String type = args[0];
        String path = args[1];
        String nameForClass = null;
        foundFiles = new ArrayList<>();

        if(type.equalsIgnoreCase("Remote")){
            nameForClass = "SK_RemoteImplementation_Rakic_Vasic.DriveImplementation";
            storageType = "REMOTE";
        }else if(type.equalsIgnoreCase("Local")){
            nameForClass = "SK_LocalImplementation_Rakic_Vasic.LocalImplementation";
            storageType = "LOCAL";
        }else{
            System.out.println("Error when choosing storage");
        }

        if(nameForClass == null){
            throw new Exception("Error when choosing storage");
        }
        Class.forName(nameForClass);
        return StorageManager.getStorage(path);
    }

    private static MyFile[] toArray(List<MyFile> listFiles){
        MyFile[] arg = new MyFile[listFiles.size()];
        for (int i = 0 ; i< arg.length; i++) {
            arg[i] = listFiles.get(i);
        }
        return arg;
    }

    private static void interactWithSoftware(SpecificationWrapper storage, Scanner input){
        String line = input.nextLine();
        line = String.join("\\\\",line.split("/"));
        String command = getCommand(line);
        String[] arguments;

        while (true){
            if (command == null){
                line = input.nextLine();
                command = getCommand(line);
            }

            switch (command){
                case "help" :{
                    if(storageType.equals("LOCAL")){
                        System.out.println("help - shows list of commands");
                        System.out.println("exit - exits the program");
                        System.out.println("configureMaxSize long(size) - ");
                        System.out.println("setExtensions List<String>(extensions) - ");
                        System.out.println("removeExtensions List<String>(extensions) - ");
                        System.out.println("configureMaxNumberOfFiles int(maxFilesNum) - ");
                        System.out.println("configureFolder String(folder) int(maxFilesNum) int(maxSize) List<String>(extensions) - ");
                        System.out.println("config Config config - ");
                        System.out.println("check MyFile(file) - ");
                        System.out.println("createStorage String(path) - ");
                        System.out.println("makeFolder String(path) String(name) - ");
                        System.out.println("makeMultipleFolders String(path) String(name) int(num) - ");
                        System.out.println("addFiles List<String>(files) String(path) - ");
                        System.out.println("deleteFile String(path) - deletes file at given path");
                        System.out.println("rename String(path) String(newName) - renames file for path with newName");
                        System.out.println("move String(srcPath) String(dstPath) - ");
                        System.out.println("download String(pathFileToDownload) String(dstPath) - ");
                        System.out.println("searchByDir String(dirPath) String(wantedFileName) - ");
                        System.out.println("twoLevelSearch String(dirPath) String(search) - ");
                        System.out.println("searchToBottom String(dirPath) String(search) int(flag) - ");
                        System.out.println("searchByExtension String(dirPath) String(extension) - ");
                        System.out.println("searchFileThatBeginsWith String(dirPath) String(substring) - ");
                        System.out.println("searchFileThatEndsWith String(dirPath) String(substring) - ");
                        System.out.println("searchFileWhichNameContains String(dirPath) String(substring) - ");
                        System.out.println("dirHasFilesWithNames String(dirPath) List<String>(fileNames) - ");
                        System.out.println("globalSearchForFile String(fileName) - ");
                        System.out.println("findByDate String(dirPath) String(fileName) String(fromDate) String(toDate) - ");
                        System.out.println("printFiles List<MyFile>(filesList) bool(path) bool(size) bool(created) bool(modified) - ");
                        System.out.println("sortByName List<MyFile>(filesList) int(order) - ");
                        System.out.println("sortByCreationDate List<MyFile>(filesList) int(order) - ");
                        System.out.println("sortByLastModified List<MyFile>(filesList) int(order) - ");
                    }else {
                        System.out.println("help - shows list of commands");
                        System.out.println("exit - exits the program");
                        System.out.println("configureMaxSize long(size) - ");
                        System.out.println("setExtensions List<String>(extensions) - ");
                        System.out.println("removeExtensions List<String>(extensions) - ");
                        System.out.println("configureMaxNumberOfFiles int(maxFilesNum) - ");
                        System.out.println("configureFolder String(folder) int(maxFilesNum) int(maxSize) List<String>(extensions) - ");
                        System.out.println("config Config config - ");
                        System.out.println("check MyFile(file) - ");
                        System.out.println("createStorage String(path) - ");
                        System.out.println("makeFolder String(path) String(name) - ");
                        System.out.println("makeMultipleFolder String(path) String(name) int(num) - ");
                        System.out.println("addFiles List<MyFile>(files) String(path) - ");
                        System.out.println("deleteFile String(path) - deletes file at given path");
                        System.out.println("rename String(path) String(newName) - renames file for path with newName");
                        System.out.println("move String(srcPath) String(dstPath) - ");
                        System.out.println("download String(pathFileToDownload) String(dstPath) - ");
                        System.out.println("searchByDir String(dirPath) String(wantedFileName) - ");
                        System.out.println("twoLevelSearch String(dirPath) String(search) - ");
                        System.out.println("searchToBottom String(dirPath) String(search) int(flag) - ");
                        System.out.println("searchByExtension String(dirPath) String(extension) - ");
                        System.out.println("searchFileThatBeginsWith String(dirPath) String(substring) - ");
                        System.out.println("searchFileThatEndsWith String(dirPath) String(substring) - ");
                        System.out.println("searchFileWhichNameContains String(dirPath) String(substring) - ");
                        System.out.println("dirHasFilesWithNames String(dirPath) List<String>(fileNames) - ");
                        System.out.println("globalSearchForFile String(fileName) - ");
                        System.out.println("findByDate String(dirPath) String(fileName) String(fromDate) String(toDate) - ");
                        System.out.println("printFiles List<MyFile>(filesList) bool(path) bool(size) bool(created) bool(modified) - ");
                        System.out.println("sortByName List<MyFile>(filesList) int(order) - ");
                        System.out.println("sortByCreationDate List<MyFile>(filesList) int(order) - ");
                        System.out.println("sortByLastModified List<MyFile>(filesList) int(order) - ");

                    }
                    break;
                }
                case "exit":{
                    System.out.println("Thank you, bye!");
                    System.exit(0);
                    break;
                }
                case "configuremaxsize":{
                    arguments = getArguments(line);
                    long maxSize;
                    if(arguments.length != 1){
                        System.out.println("1 argument is expected");
                        break;
                    }
                    try {
                        maxSize = Long.parseLong(arguments[0]);
                    }catch (Exception exception){
                        System.out.println("It has to be long!");
                        break;
                    }
                    storage.configureMaxSize(maxSize);
                    break;
                }
                case "setextensions":{
                    arguments = getArguments(line);
                    List<String> args = new ArrayList<>() ;
                    if(arguments.length != 1){
                        System.out.println("1 argument is expected");
                        System.out.println("If you want to have more than one you have to separate them with commas");
                        break;
                    }

                    String[] putanje = arguments[0].replaceAll(">","").replaceAll("<","").split("&");
                    // System.out.println(putanje[0]);
                    for(String putanja: putanje){
                        args.add(putanja);
                    }
                    storage.setExtensions(args);
                    break;
                }
                case "removeextensions":{
                    arguments = getArguments(line);
                    List<String> args = new ArrayList<>();
                    if(arguments.length != 1){
                        System.out.println("1 argument is expected");
                        System.out.println("If you want to have more than one you have to separate them with commas");
                        break;
                    }

                    String[] putanje = arguments[0].replaceAll(">","").replaceAll("<","").split("&");
                   // System.out.println(putanje[0]);
                    for(String putanja: putanje){
                        args.add(putanja);
                    }
                    storage.removeExtensions(args);
                    break;
                }
                case "configuremaxnumberoffiles":{
                    arguments = getArguments(line);
                    int maxNumber;
                    if(arguments.length != 1){
                        System.out.println("1 arguments expected");
                        break;
                    }
                    try {
                        maxNumber = Integer.parseInt(arguments[0]);
                    }catch (Exception exception){
                        System.out.println("It has to be int!");
                        break;
                    }
                    storage.configureMaxNumberOfFiles(maxNumber);
                    break;
                }
                case "configurefolder":{
                    arguments = getArguments(line);
                    int maxNumber;
                    int maxSize;
                    String folder;
                    if(arguments.length != 4){
                        System.out.println("4 arguments expected");
                        break;
                    }
                    ArrayList<String> ekstenzije = new ArrayList<>();
                    String[] eksString;
                    try {
                        folder = arguments[0];
                        maxNumber = Integer.parseInt(arguments[1]);
                        maxSize = Integer.parseInt(arguments[2]);
                         eksString = arguments[3].replaceAll(">", "").replaceAll("<", "").split("&");
                    }catch (Exception exception){
                        System.out.println("It has to be int!");
                        break;
                    }
                    for (String eks:eksString){
                        ekstenzije.add(eks);
                    }
                    storage.configureFolder(folder, maxNumber, maxSize, ekstenzije);
                    break;
                }
                case "config":{
                    config = storage.getConfig();
                    MyFile configFile = storage.config(config);
                    if(configFile!= null)
                        storage.addConfigFile(configFile, storage.getRoot());
                    break;
                }
                case "makefolder":{

                    String path;
                    String name;
                    arguments = getArguments(line);
                   // System.out.println("Arg1 = " + arguments[0]);
                    //System.out.println("Arg2 = " + arguments[1]);
                    if(arguments.length == 2){
                        path = arguments[0].replaceAll("@","");
                        name = arguments[1];
                    }else {
                        System.out.println("2 arguments are expected!");
                        System.out.println("Enter help if you need help!");
                        break;
                    }
                    storage.makeFolder(path,name);
                    break;
                }
                case "makemultiplefolders":{
                    System.out.println("USAO U MULTIPLE FOLDERS");
                    String path;
                    String name;
                    int numberOfFolders = 0;
                    arguments = getArguments(line);
                    System.out.println(arguments.length);
                    if(arguments.length == 3){
                        path = arguments[0];
                        name = arguments[1];
                        numberOfFolders = Integer.parseInt(arguments[2]);
                    }else {
                        System.out.println("3 arguments are expected!");
                        System.out.println("Enter help if you need help!");
                        break;
                    }

                    if(numberOfFolders == 0){
                        storage.makeFolder(path,name);
                        break;
                    }

                    storage.makeMultipleFolders(path,name,numberOfFolders);
                    break;
                }
                case "addfiles":{
                    arguments = getArguments(line);
                    if(storageType.equalsIgnoreCase("LOCAL")) {
                        if (arguments.length != 2) {
                            System.out.println("2 arguments expected");
                            break;
                        }
                        List<String> filePaths = new ArrayList<>();

                        String[] putanje = arguments[0].replaceAll(">","").replaceAll("<","").split("&");
                        System.out.println(putanje[0]);
                        for(String putanja: putanje){
                            filePaths.add(putanja);
                        }

                        if (filePaths.isEmpty()) {
                            System.out.println("Enter file paths for files to work");
                            break;
                        }

                        String path = arguments[1].replaceAll("@","");
                        storage.addFiles(filePaths, path);

                    }else {
                        /*
                        if (arguments.length != 1) {
                            System.out.println("1 argument expected - Needed file path that you want to upload");
                            break;
                        }
                        String path = arguments[0];
                        for(int i = 1 ; i< arguments.length ; i++){
                            foundFiles.add(new MyFile(new File(arguments[i])));
                        }
                        List<String> filePaths = new ArrayList<>();

                        storage.addFiles(filePaths, path);

                         */
                    }
                    break;
                }
                case "deletefile":{
                    arguments = getArguments(line);
                    if(arguments.length != 1){
                        System.out.println("1 argument expected");
                        break;
                    }
                    String path = arguments[0].replaceAll("@","");
                    storage.deleteFile(path);
                    break;
                }
                case "rename":{
                    arguments = getArguments(line);
                    if(arguments.length != 2){
                        System.out.println("2 arguments expected");
                        break;
                    }
                    String path = arguments[0].replaceAll("@","");
                    String newName = arguments[1];
                    storage.rename(path,newName);
                    break;
                }
                case "move":{
                    arguments = getArguments(line);
                    if(arguments.length != 2){
                        System.out.println("2 arguments expected");
                        break;
                    }
                    String srcPath = arguments[0].replaceAll("@","");
                    String dstPath = arguments[1].replaceAll("@","");
                    storage.move(srcPath,dstPath);
                    break;
                }
                case "download":{
                    arguments = getArguments(line);
                    if(arguments.length != 2){
                        System.out.println("2 arguments expected");
                        break;
                    }
                    String pathFileToDownload = arguments[0].replaceAll("@","");
                    String dstPath = arguments[1];
                    storage.download(pathFileToDownload,dstPath);
                    break;
                }
                case "searchindir":{
                    arguments = getArguments(line);
                    if(arguments.length != 2){
                        System.out.println("2 arguments expected");
                        break;
                    }
                    String dirPath = arguments[0].replaceAll("@","");
                    String wantedFileName = arguments[1];
                    foundFiles = storage.searchInDir(dirPath,wantedFileName);
                    System.out.println(foundFiles);
                    break;
                }
                case "twolevelsearch":{
                    arguments = getArguments(line);
                    if(arguments.length != 2){
                        System.out.println("2 arguments expected");
                        break;
                    }
                    String dirPath = arguments[0].replaceAll("@","");
                    String search = arguments[1];
                    foundFiles = storage.twoLevelSearch(dirPath,search);
                    System.out.println(foundFiles);
                    break;
                }
                case "searchtobottom":{
                    String dirPath;
                    String search;
                    int flag;
                    arguments = getArguments(line);
                    if(arguments.length == 2){
                        dirPath = arguments[0].replaceAll("@","");
                        search = arguments[1];
                    }else {
                        System.out.println("3 arguments are expected!");
                        System.out.println("Enter help if you need help!");
                        break;
                    }

                    foundFiles = storage.searchToBottom(dirPath,search, 0);
                    System.out.println(foundFiles);
                    break;
                }
                case "searchbyextension":{
                    arguments = getArguments(line);
                    if(arguments.length != 2){
                        System.out.println("2 arguments expected");
                        break;
                    }
                    String dirPath = arguments[0].replaceAll("@","");
                    String extension = arguments[1];
                    foundFiles = storage.searchByExtension(dirPath,extension);
                    System.out.println(foundFiles);
                    break;
                }
                case "searchfilethatbeginswith":{
                    arguments = getArguments(line);
                    if(arguments.length != 2){
                        System.out.println("2 arguments expected");
                        break;
                    }
                    String dirPath = arguments[0].replaceAll("@","");
                    String substring = arguments[1];
                    foundFiles = storage.serchFileThatBeginsWith(dirPath,substring);
                    System.out.println(foundFiles);
                    break;
                }
                case "searchfilethatendswith":{
                    arguments = getArguments(line);
                    if(arguments.length != 2){
                        System.out.println("2 arguments expected");
                        break;
                    }
                    String dirPath = arguments[0].replaceAll("@","");
                    String substring = arguments[1];
                    foundFiles = storage.serchFileThatEndsWith(dirPath,substring);
                    System.out.println(foundFiles);
                    break;
                }
                case "searchfilewhichnamecontains":{
                    arguments = getArguments(line);
                    if(arguments.length != 2){
                        if(arguments.length == 1){
                            foundFiles = storage.serchFileWhichNameContains(arguments[0],"");
                            break;
                        }else {
                            System.out.println("2 arguments expected");
                            break;
                        }
                    }
                    String dirPath = arguments[0].replaceAll("@","");
                    String substring = arguments[1];
                    foundFiles = storage.serchFileWhichNameContains(dirPath,substring);
                    System.out.println(foundFiles);
                    break;
                }
                case "dirhasfileswithnames":{
                    arguments = getArguments(line);
                    if(arguments.length != 2){
                        System.out.println("2 arguments expected");
                        break;
                    }
                    String dirPath = arguments[0];
                    String[] fileNamesString=arguments[1].replaceAll(">", "").replaceAll("<", "").split("&");
                    List<String> fileNames = new ArrayList<>();
                    for(String name:fileNamesString){
                        fileNames.add(name);
                    }
                    System.out.println(storage.dirHasFilesWithNames(dirPath, fileNames));
                    break;
                }
                case "globalsearchforfile":{
                    arguments = getArguments(line);
                    if(arguments.length != 1){
                        System.out.println("1 argument expected");
                        break;
                    }
                    String fileName = arguments[0];
                    foundFiles = storage.golbalSearchForFile(fileName);
                    System.out.println(foundFiles);
                    break;
                }
                case "findbydate":{
                    arguments = getArguments(line);
                    String dirPath;
                    String fileName;
                    String fromDate;
                    String toDate;
                    if(arguments.length != 4){
                        System.out.println("4 arguments expected");
                        break;
                    }
                    dirPath = arguments[0].replaceAll("@","");
                    fileName = arguments[1];
                    fromDate = arguments[2];
                    toDate = arguments[3];
                    foundFiles = storage.findByDate(dirPath,fileName,fromDate,toDate);
                    break;

                }
                case "printfiles":{
                    boolean path = false;
                    boolean size = false;
                    boolean created = false;
                    boolean modified = false;
                    arguments = getArguments(line);
                    if(arguments.length != 4){
                        System.out.println("4 arguments expected");
                        break;
                    }
                    if(arguments[0].equalsIgnoreCase("t")||arguments[0].equalsIgnoreCase("true")||arguments[0].equalsIgnoreCase("y")){
                        path = true;
                    }if(arguments[1].equalsIgnoreCase("t")||arguments[1].equalsIgnoreCase("true")||arguments[1].equalsIgnoreCase("y")){
                        size = true;
                    }if(arguments[2].equalsIgnoreCase("t")||arguments[2].equalsIgnoreCase("true")||arguments[2].equalsIgnoreCase("y")){
                        created = true;
                    }if(arguments[3].equalsIgnoreCase("t")||arguments[3].equalsIgnoreCase("true")||arguments[3].equalsIgnoreCase("y")){
                        modified = true;
                    }
                    if(foundFiles.isEmpty()){
                        System.out.println("FoundFiles is empty");
                        break;
                    }
                    List<String> print = storage.printFiles(foundFiles,path,size,created,modified);
                    for(String s:print){
                        System.out.println(s);
                    }
                    break;
                }
                case "sortbyname":{
                    arguments = getArguments(line);
                    int order;
                    if(arguments.length != 1){
                        System.out.println("1 argument expected");
                        break;
                    }else{
                        order = Integer.parseInt(arguments[0]);
                    }

                    try {
                        order = Integer.parseInt(arguments[0]);
                    }catch (Exception e){
                        System.out.println("Error in arguments, please visit help");
                    }

                    if(foundFiles.isEmpty()){
                        System.out.println("FoundFiles is empty");
                        break;
                    }

                    foundFiles = storage.sortByName(foundFiles,order);
                    break;
                }
                case "sortbycreationdate":{
                    arguments = getArguments(line);
                    int order;
                    if(arguments.length != 1){
                        System.out.println("1 argument expected");
                        break;
                    }else{
                        order = Integer.parseInt(arguments[3]);
                    }

                    try {
                        order = Integer.parseInt(arguments[3]);
                    }catch (Exception e){
                        System.out.println("Error in arguments, please visit help");
                    }

                    if(foundFiles.isEmpty()){
                        System.out.println("FoundFiles is empty");
                        break;
                    }

                    foundFiles = storage.sortByCreationDate(foundFiles,order);
                    break;
                }
                case "sortbylastmodified":{
                    arguments = getArguments(line);
                    int order;
                    if(arguments.length != 1){
                        System.out.println("1 argument expected");
                        break;
                    }else{
                        order = Integer.parseInt(arguments[3]);
                    }

                    try {
                        order = Integer.parseInt(arguments[3]);
                    }catch (Exception e){
                        System.out.println("Error in arguments, please visit help");
                    }

                    if(foundFiles.isEmpty()){
                        System.out.println("FoundFiles is empty");
                        break;
                    }

                    foundFiles = storage.sortByLastModified(foundFiles,order);
                    break;
                }
                default:{
                    System.out.println("Command: "+command+" doesn't exists, type help to visit help!");
                }
            }
            line = input.nextLine();
            command = getCommand(line);

        }


    }

    private static String getCommand(String commandInput){
        String[] commands = commandInput.split(" ");
        if (commands.length == 0){
            System.out.println("Error, you have not entered any command");
            return null;
        }
        return commands[0].trim().toLowerCase();
    }

    private static String[] getArguments(String argumentsInput){
        String[] argumentsArray = argumentsInput.split(",");
        List<String> argumentsList = new ArrayList<>(Arrays.asList(argumentsArray).subList(1, argumentsArray.length));
        String[] arguments = new String[argumentsList.size()];
        for (int i = 0 ; i< arguments.length; i++) {
            if(argumentsList.get(i).equals(".")){
                arguments[i] = "";
            } else {
                arguments[i] = argumentsList.get(i);
            }
        }

        return arguments;
    }



}