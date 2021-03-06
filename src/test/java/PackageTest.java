import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;
import com.mobiquityinc.packer.PackerItem;
import com.mobiquityinc.packer.PackerLine;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PackageTest {

    private static String trueFilePath;

    private static String falseFilePath;

    private static String moreThanHundredPath;

    private static String itemWeightMoreThanHundredPath;

    private static String itemCountMoreThanLimitPath;

    @BeforeClass
    public static void readFileBeforeClass() throws Exception {
        trueFilePath = "C:\\inputText1.txt";
        falseFilePath = "C:\\noFile.txt";
        moreThanHundredPath = "C:\\moreThan100.txt";
        itemWeightMoreThanHundredPath = "C:\\itemWeightMoreThan100.txt";
        itemCountMoreThanLimitPath = "C:\\itemMoreThan15.txt";
    }

    @Test(expected = APIException.class)
    public void testPackFileError() throws APIException {
        //Normally this test scenario must be driven throughly by IOException
        //But in Assignment pack() method has been limited to APIException
        Packer packer = new Packer();
        packer.pack(falseFilePath);
    }


    @Test(expected = APIException.class)
    public void testPackMoreThanHundredError() throws APIException {
        Packer packer = new Packer();
        packer.pack(moreThanHundredPath);

    }

    @Test(expected = APIException.class)
    public void testPackItemWeightMoreThanHundredError() throws APIException  {
        Packer packer = new Packer();
        String s= packer.pack(itemWeightMoreThanHundredPath);

    }

    @Test(expected = APIException.class)
    public void testPackItemCountMoreThanLimitError() throws APIException  {
        Packer packer = new Packer();
        String s= packer.pack(itemCountMoreThanLimitPath);

    }

    @Test
    public void testPackSuccessful() throws APIException  {
        Packer packer = new Packer();
        String s= packer.pack(trueFilePath);
        assertThat(s, is(equalTo("4\n" +
                "-\n" +
                "2,7\n" +
                "8,9\n")));

    }

    @Test
    public void testTotalCostSuccessful(){
        List<PackerItem> packerItemsTest = new ArrayList<PackerItem>();
        for(int i=0;i<5;i++){
            PackerItem packerItem = new PackerItem();
            packerItem.setCost(5*i);
            packerItemsTest.add(packerItem);
        }
        int totalCost = Packer.getTotalCost(packerItemsTest);
        assertThat(totalCost, is(equalTo(50)));
    }

    @Test
    public void testTotalWeightSuccessful(){
        List<PackerItem> packerItemsTest = new ArrayList<PackerItem>();
        for(int i=0;i<5;i++){
            PackerItem packerItem = new PackerItem();
            packerItem.setWeight(5*i);
            packerItemsTest.add(packerItem);
        }
        double totalCost = Packer.getTotalWeight(packerItemsTest);
        assertThat(totalCost, is(equalTo(50.0)));
    }


    @Test
    public void testFindOptimalPackageSuccessful(){
        PackerLine packerLine = new PackerLine();
        List<PackerItem> packerItemsTest = new ArrayList<PackerItem>();
        double[] testWeightData = {5.88,11.9,66.7,40.5,43.78,60,72.2,67.3,9.66,91.03};
        int[] testCostData = {10,20,30,40,50,60,71,80,90,91};

        for(int i=0;i<10;i++){
            PackerItem packerItem = new PackerItem();
            packerItem.setItemNumber(i+1);
            packerItem.setWeight(testWeightData[i]);
            packerItem.setCost(testCostData[i]);
            packerItemsTest.add(packerItem);
        }
        packerLine.setItemList(packerItemsTest);
        packerLine.setMaxWeight(77);
        List <PackerItem> optimalList = Packer.findOptimalPackage(packerLine);
        String bestPackage = "";
        if (optimalList.size() != 0) {
            for (PackerItem packerItem : optimalList) {
                bestPackage = bestPackage + packerItem.getItemNumber() + ",";
            }
        } else {
            bestPackage = bestPackage + "-,";
        }
        bestPackage = bestPackage.substring(0, bestPackage.length() - 1) + "\n";
        assertThat(bestPackage, is(equalTo("1,2,5,9\n")));
    }

}