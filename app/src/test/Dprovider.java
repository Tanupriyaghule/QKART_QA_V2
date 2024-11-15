package QKART_TESTNG;
import org.testng.annotations.DataProvider;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
public class DProvider {
    @DataProvider(name = "data-provider")
    public Object[][] dpMethod(Method method) throws IOException {
        String testSheetName = method.getName();
    Object[][] testData=null;
    try{
            FileInputStream file = new FileInputStream(new File("/home/crio-user/workspace/ghuletanupriya-ME_QKART_QA_V2/app/src/test/resources/Dataset.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(testSheetName); // Change sheet name as needed
    int totalRows=sheet.getPhysicalNumberOfRows();
    System.out.println("totaRows = " + totalRows);

    // System.out.println(totalRows);
    Row row=sheet.getRow(0);
    int totalCells=row.getLastCellNum();
    System.out.println("totalCells = " + totalCells);
    DataFormatter formatter=new DataFormatter();
    testData=new Object[totalRows-1][totalCells-1];
    for(int i=1;i<totalRows;i++)
    {
        row = sheet.getRow(i);
        for(int j=1;j<totalCells;j++)
        {
            testData[i-1][j-1]=formatter.formatCellValue(row.getCell(j));
        }}

            workbook.close();
            file.close();
    }catch(IOException e)
    {
        e.printStackTrace();
    }
            return testData;
        }
}
