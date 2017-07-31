package com.deathbydeco.utility;

        import java.io.File;
        import java.io.IOException;
        import java.lang.reflect.Array;
        import java.net.URISyntaxException;
        import java.util.ArrayList;

/**
 * Created by Tony on 28/03/2017.
 */
public class LoadDataSets {
    /*private ArrayList<DataSet> dataList = new ArrayList<DataSet>();

    public LoadDataSets() {
        System.out.println("\nLoadDataSets begins working");
        try {


        File file = new File("src/resources/datasets");
        File[] fileListing = file.listFiles();
        for (File fileChild : fileListing) {
            if (fileChild.getName().matches(".*\\binput.*") && fileChild.getName().matches(".*.muf\\b.*")){
                dataList.add(new DataSet(fileChild.getName(), fileChild.getPath()));
                System.out.println("\n successfully processed " + fileChild.getName());
                fileChild.delete();
            }
        }
        ArrayList<DataSet> thisList = getDataList();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    /*public ArrayList<DataSet> getDataList() {
        return dataList;
    }*/
}
