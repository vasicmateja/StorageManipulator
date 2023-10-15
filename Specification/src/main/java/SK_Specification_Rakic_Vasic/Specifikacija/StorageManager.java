package SK_Specification_Rakic_Vasic.Specifikacija;

public class StorageManager {
    private static SpecificationWrapper storage = null;

    public static void setStorage(SpecificationWrapper specificationImplementation){
        storage = specificationImplementation;
    }

    public static SpecificationWrapper getStorage(String root){
        storage.createStorage(root);
        return storage;
    }


}
