package SK_RemoteImplementation_Rakic_Vasic;

import SK_Specification_Rakic_Vasic.MyThings.MyFile;
import SK_Specification_Rakic_Vasic.Specifikacija.SpecificationWrapper;
import SK_Specification_Rakic_Vasic.config.Config;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriveImplementation extends SpecificationWrapper {

    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    public Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();


    //**
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    /**
     * Global instance of the JSON factory.
     */
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public DriveImplementation() throws GeneralSecurityException, IOException {
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = DriveImplementation.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow.Builder builder = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES);
        builder.setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)));
        builder.setAccessType("offline");
        GoogleAuthorizationCodeFlow flow = builder
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }
    public void stampaj() throws IOException {
        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<com.google.api.services.drive.model.File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (com.google.api.services.drive.model.File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean createStorage(String path) {

        super.setRoot(path);
        super.setConfig(new Config(path));


        List<com.google.api.services.drive.model.File> files = new ArrayList<>();
        String pageToken;
        do {
            FileList result = null;
            try {
                result = service.files().list()
                        .setQ("'"+super.getRoot()+"' in parents and name = 'config.json'")
                        .setFields("files(id, name, size, modifiedTime, createdTime,parents,mimeType)")
                        .execute();
            } catch (IOException e) {
                System.out.println("Error with google drive response");
            }
            if(result == null){
                config(super.getConfig());

            }
            files.addAll(result.getFiles());
            pageToken = result.getNextPageToken();
        }while (pageToken!= null);
        if(files == null || files.isEmpty()){
            config(super.getConfig());
        }

        com.google.api.services.drive.model.File jsonconfigfile = files.get(0);
        download(jsonconfigfile.getId(),super.getRoot());
        File file = new File((Paths.get(System.getProperty("user.home"),"Downloads")).toString(),jsonconfigfile.getName());
        if(file.exists()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                StringBuilder stringBuilder = new StringBuilder();

                while((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }
                reader.close();
                String s = stringBuilder.toString();
                Config conf = new Gson().fromJson(s,Config.class);
                super.setConfig(conf);
            }catch (Exception e){
                System.out.println("Error with found config.json deploying default values");
                config(super.getConfig());
            }
            file.delete();
        }
        config(super.getConfig());

        return super.createStorage(path);
    }

    @Override
    public boolean createStorage(String name, String path) {
        return super.createStorage(name, path);
    }

    @Override
    public boolean makeFolder(String path, String name) {
        return super.makeFolder(path, name);
    }

    @Override
    public boolean makeMultipleFolders(String path, String name, int num) {
        return super.makeMultipleFolders(path, name, num);
    }

    @Override
    public boolean addFiles(List<String> files, String path) {
        return super.addFiles(files, path);
    }

    @Override
    public boolean deleteFile(String path) {
        return super.deleteFile(path);
    }

    @Override
    public boolean rename(String path, String newName) {
        return super.rename(path, newName);
    }

    @Override
    public boolean move(String srcPath, String dstPath) {
        return super.move(srcPath, dstPath);
    }


    @Override
    public boolean download(String pathFileToDownload, String dstPath) {
        return super.download(pathFileToDownload, dstPath);
    }

    @Override
    public List<MyFile> searchInDir(String dirPath, String wantedFileName) {
        return super.searchInDir(dirPath, wantedFileName);
    }

    @Override
    public List<MyFile> twoLevelSearch(String dirPath, String search) {
        return super.twoLevelSearch(dirPath, search);
    }

    @Override
    public List<MyFile> searchToBottom(String dirPath, String search, int flag) {
        return super.searchToBottom(dirPath, search, flag);
    }

    @Override
    public List<MyFile> searchByExtension(String dirPath, String extension) {
        return super.searchByExtension(dirPath, extension);
    }

    @Override
    public List<MyFile> serchFileThatBeginsWith(String dirPath, String substring) {
        return super.serchFileThatBeginsWith(dirPath, substring);
    }

    @Override
    public List<MyFile> serchFileThatEndsWith(String dirPath, String substring) {
        return super.serchFileThatEndsWith(dirPath, substring);
    }

    @Override
    public List<MyFile> serchFileWhichNameContains(String dirPath, String substring) {
        return super.serchFileWhichNameContains(dirPath, substring);
    }

    @Override
    public boolean dirHasFilesWithNames(String dirPath, List<String> fileNames) {
        return super.dirHasFilesWithNames(dirPath, fileNames);
    }

    @Override
    public List<MyFile> golbalSearchForFile(String fileName) {
        return super.golbalSearchForFile(fileName);
    }

    @Override
    public List<MyFile> findByDate(String dirPath, String filename, String fromDate, String toDate) {
        return super.findByDate(dirPath, filename, fromDate, toDate);
    }

    @Override
    public List<String> printFiles(List<MyFile> fileslist, boolean path, boolean size, boolean created, boolean modified) {
        return super.printFiles(fileslist, path, size, created, modified);
    }






    //////////////////////////////////////////////////////////////////////////////

}
