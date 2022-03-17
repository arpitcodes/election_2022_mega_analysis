import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.Collections.*;


//
//https://results.eci.gov.in/ResultAcGenMar2022/ConstituencywiseS0510.htm?ac=10
//
//        *each questions are statewise
//        *output should be name of constituency, candidate name, and vote number/percentage or whatever is the deciding factor, dump all the data in excel
//        with column(all column+state+constituency name).
//
//
//        1. get the candidate which has got the maximum vote in each state with their constituency name.
//        2. get the candidate which has got the maximum percentage of vote in each state with their constituency name.(percentage)
//        3. candidate who won with maximum vote difference.
//        4. candidate who won with maximum vote percentage difference.
//        5. candidate who won with minimum vote difference.
//        6. candidate who won with minimum vote percentage difference.
//        7. total count of candidate who have got less vote than nota.
//        8. total count of candidates who have got greater than 50% vote.
//        9. name of candidate who have got minimum vote in each state.  DOONE




public class selenium_mega_assignment {

    public static String Baseurl = "https://results.eci.gov.in/ResultAcGenMar2022/ConstituencywiseS0510.htm?ac=10";
    public static String chromDriverProp = "webdriver.chrome.driver";
    public static String chromDriverPath = "C://Users//ar.mishra//Downloads//chromedriver_win32//chromedriver.exe";

    static Thread threadUP = new Thread(){
        public void  run(){
            try {
                data_of_UP_ConstWise();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    static Thread threadUK = new Thread(){
        public void run(){

            try {
                data_of_UK_ConstWise();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    static Thread threadMP = new Thread(){
        public void  run(){
            try {
                data_of_MP_ConstWise();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    static Thread threadGA = new Thread(){
        public void  run(){
            try {
                data_of_GA_ConstWise();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    static Thread threadPB = new Thread(){
        public void  run(){
            try {
                data_of_PB_ConstWise();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    public static void main(String[] args) throws InterruptedException, IOException {
//        create_Excel_file("State_wise_result");
//        create_Excel_Sheet("State_wise_result.xlsx");
//        threadMP.start();
        threadGA.start();
//        threadUK.start();
//        threadPB.start();
//        threadUP.start();

    }
    public static int SUM(List<Integer> list){
        int sum = 0;
        for(int i =0;i<list.size();i++){
            sum+=list.get(i);
        }
        return sum;
    }
    public static void create_Excel_file(String fileName) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();

        FileOutputStream out = new FileOutputStream(
                new File("C:\\Users\\ar.mishra\\IdeaProjects\\selenium_mega_assignment_mvn\\" + fileName + ".xlsx"));
        workbook.write(out);
        out.close();
        System.out.println(fileName + ".xlsx written successfully");
    }
    public static void create_Excel_Sheet(String file_Path) throws IOException {
        String excelFilePath = file_Path;
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);

            //sheet1 creation
            Sheet sheet1 = workbook.createSheet("candidates_with_MaxVote");
            sheet1.setColumnWidth(0, 6000);
            sheet1.setColumnWidth(1, 4000);
            Row header1 = sheet1.createRow(0);
            CellStyle headerStyle1 = workbook.createCellStyle();
            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Arial");
            font.setBold(true);
            headerStyle1.setFont(font);
            Cell headerCell1 = header1.createCell(0);
            headerCell1.setCellValue("State Name");
            headerCell1 = header1.createCell(1);
            headerCell1.setCellValue("Constituency Name");
            headerCell1 = header1.createCell(2);
            headerCell1.setCellValue("Candidate Name");
            headerCell1 = header1.createCell(3);
            headerCell1.setCellValue("Total Votes");
            //sheet2 creation
            Sheet sheet2 = workbook.createSheet("candidates_with_MinVote");
            sheet2.setColumnWidth(0, 6000);
            sheet2.setColumnWidth(1, 4000);
            Row header2 = sheet2.createRow(0);
            CellStyle headerStyle2 = workbook.createCellStyle();
            headerStyle2.setFont(font);
            Cell headerCell2 = header2.createCell(0);
            headerCell2.setCellValue("State Name");
            headerCell2 = header2.createCell(1);
            headerCell2.setCellValue("Constituency Name");
            headerCell2 = header2.createCell(2);
            headerCell2.setCellValue("Candidate Name");
            headerCell2 = header2.createCell(3);
            headerCell2.setCellValue("Total Votes");

            Sheet sheet3 = workbook.createSheet("maximum_vote_difference_margin");
            sheet3.setColumnWidth(0, 6000);
            sheet3.setColumnWidth(1, 4000);
            Row header3 = sheet3.createRow(0);
            CellStyle headerStyle3 = workbook.createCellStyle();
            headerStyle3.setFont(font);
            Cell headerCell3 = header3.createCell(0);
            headerCell3.setCellValue("State Name");
            headerCell3 = header3.createCell(1);
            headerCell3.setCellValue("Constituency Name");
            headerCell3 = header3.createCell(2);
            headerCell3.setCellValue("Winner Name");
            headerCell3 = header3.createCell(3);
            headerCell3.setCellValue("Winner Total Votes");
            headerCell3 = header3.createCell(4);
            headerCell3.setCellValue("Runner-Up Name");
            headerCell3 = header3.createCell(5);
            headerCell3.setCellValue("Runner-Up Total Votes");
            headerCell3 = header3.createCell(6);
            headerCell3.setCellValue("Total Margin");

            Sheet sheet4 = workbook.createSheet("minimum_vote_difference_margin");
            sheet4.setColumnWidth(0, 6000);
            sheet4.setColumnWidth(1, 4000);
            Row header4 = sheet4.createRow(0);
            CellStyle headerStyle4 = workbook.createCellStyle();
            headerStyle4.setFont(font);
            Cell headerCell4 = header4.createCell(0);
            headerCell4.setCellValue("State Name");
            headerCell4 = header4.createCell(1);
            headerCell4.setCellValue("Constituency Name");
            headerCell4 = header4.createCell(2);
            headerCell4.setCellValue("Winner Name");
            headerCell4 = header4.createCell(3);
            headerCell4.setCellValue("Winner Total Votes");
            headerCell4 = header4.createCell(4);
            headerCell4.setCellValue("Runner-Up Name");
            headerCell4 = header4.createCell(5);
            headerCell4.setCellValue("Runner-Up Total Votes");
            headerCell4 = header4.createCell(6);
            headerCell4.setCellValue("Total Margin");

            Sheet sheet5 = workbook.createSheet("maximum_vote_percentage_difference_margin");
            sheet5.setColumnWidth(0, 6000);
            sheet5.setColumnWidth(1, 4000);
            Row header5 = sheet5.createRow(0);
            CellStyle headerStyle5 = workbook.createCellStyle();
            headerStyle5.setFont(font);
            Cell headerCell5 = header5.createCell(0);
            headerCell5.setCellValue("State Name");
            headerCell5 = header5.createCell(1);
            headerCell5.setCellValue("Constituency Name");
            headerCell5 = header5.createCell(2);
            headerCell5.setCellValue("Winner Name");
            headerCell5 = header5.createCell(3);
            headerCell5.setCellValue("Winner Total Votes Percentage");
            headerCell5 = header5.createCell(4);
            headerCell5.setCellValue("Runner-Up Name ");
            headerCell5 = header5.createCell(5);
            headerCell5.setCellValue("Runner-Up Total Votes Percentage");
            headerCell5 = header5.createCell(6);
            headerCell5.setCellValue("Total Percentage Margin");

            Sheet sheet6 = workbook.createSheet("minimum_vote_percentage_difference_margin");
            sheet6.setColumnWidth(0, 6000);
            sheet6.setColumnWidth(1, 4000);
            Row header6 = sheet6.createRow(0);
            CellStyle headerStyle6 = workbook.createCellStyle();
            headerStyle6.setFont(font);
            Cell headerCell6 = header6.createCell(0);
            headerCell6.setCellValue("State Name");
            headerCell6 = header6.createCell(1);
            headerCell6.setCellValue("Constituency Name");
            headerCell6 = header6.createCell(2);
            headerCell6.setCellValue("Winner Name");
            headerCell6 = header6.createCell(3);
            headerCell6.setCellValue("Winner Total Votes Percentage");
            headerCell6 = header6.createCell(4);
            headerCell6.setCellValue("Runner-Up Name ");
            headerCell6 = header6.createCell(5);
            headerCell6.setCellValue("Runner-Up Total Votes Percentage");
            headerCell6 = header6.createCell(6);
            headerCell6.setCellValue("Total Percentage Margin");

            Sheet sheet7 = workbook.createSheet("maximum_vote_percentage_winner");
            sheet7.setColumnWidth(0, 6000);
            sheet7.setColumnWidth(1, 4000);
            Row header7 = sheet7.createRow(0);
            CellStyle headerStyle7 = workbook.createCellStyle();
            headerStyle7.setFont(font);
            Cell headerCell7 = header7.createCell(0);
            headerCell7.setCellValue("State Name");
            headerCell7 = header7.createCell(1);
            headerCell7.setCellValue("Constituency Name");
            headerCell7 = header7.createCell(2);
            headerCell7.setCellValue("Candidate Name");
            headerCell7 = header7.createCell(3);
            headerCell7.setCellValue("Total Votes Percent");


            Sheet sheet8 = workbook.createSheet("minimum_vote_percentage_winner");
            sheet8.setColumnWidth(0, 6000);
            sheet8.setColumnWidth(1, 4000);
            Row header8 = sheet8.createRow(0);
            CellStyle headerStyle8 = workbook.createCellStyle();
            headerStyle8.setFont(font);
            Cell headerCell8 = header8.createCell(0);
            headerCell8.setCellValue("State Name");
            headerCell8 = header8.createCell(1);
            headerCell8.setCellValue("Constituency Name");
            headerCell8 = header8.createCell(2);
            headerCell8.setCellValue("Candidate Name");
            headerCell8 = header8.createCell(3);
            headerCell8.setCellValue("Total Votes Percent");


            Sheet sheet9 = workbook.createSheet("miscellaneous_Data");
            sheet9.setColumnWidth(0, 6000);
            sheet9.setColumnWidth(1, 4000);
            Row header9 = sheet9.createRow(0);
            CellStyle headerStyle9 = workbook.createCellStyle();
            headerStyle9.setFont(font);
            Cell headerCell9 = header9.createCell(0);
            headerCell9.setCellValue("State Name");
            headerCell9 = header9.createCell(1);
            headerCell9.setCellValue("Count of Less Than NOTA");
            headerCell9 = header9.createCell(2);
            headerCell9.setCellValue("Count Candidate Greater than 50% Votes");




            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }

    }

    public static void write_Into_excel(String fileName,String StateName, String SheetName, String ConstName, String CandName, String Vote) {


        String excelFilePath = fileName+".xlsx";
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheet(SheetName);

            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            Cell cell = row.createCell(0);
            cell.setCellValue(StateName);
            cell = row.createCell(1);
            cell.setCellValue(ConstName);
            cell = row.createCell(2);
            cell.setCellValue(CandName);
            cell = row.createCell(3);
            cell.setCellValue(Vote);
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }
    public static void write_Into_excel_type_2(String fileName,String StateName, String SheetName,String ConstName,String WinnerName, String Vote,String runnerUpName,String runnerUpVotes,String margin){
        String excelFilePath = fileName+".xlsx";
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheet(SheetName);

            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            Cell cell = row.createCell(0);
            cell.setCellValue(StateName);
            cell = row.createCell(1);
            cell.setCellValue(ConstName);
            cell = row.createCell(2);
            cell.setCellValue(WinnerName);
            cell = row.createCell(3);
            cell.setCellValue(Vote);
            cell = row.createCell(4);
            cell.setCellValue(runnerUpName);
            cell = row.createCell(5);
            cell.setCellValue(runnerUpVotes);
            cell = row.createCell(6);
            cell.setCellValue(margin);
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }
    public static void write_Into_excel_misc(String fileName,String StateName,String SheetName,int less_than_NOTA, int greater_Than_50_percentage){
        String excelFilePath = fileName+".xlsx";
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheet(SheetName);

            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            Cell cell = row.createCell(0);
            cell.setCellValue(StateName);
            cell = row.createCell(1);
            cell.setCellValue(less_than_NOTA);
            cell = row.createCell(2);
            cell.setCellValue(greater_Than_50_percentage);

            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }

    public static void max_Vote_in_State(String stateName, List<String> constiName, List<String> winningCand, List<Integer> Votes) {
        int idx = Votes.indexOf(max(Votes));
        System.out.println(constiName.get(idx) + "  " + winningCand.get(idx) + "  " + Votes.get(idx));
        write_Into_excel("State_wise_result",stateName,"candidates_with_MaxVote",constiName.get(idx),winningCand.get(idx),Votes.get(idx).toString());

    }
    public static void min_Vote_in_State(String stateName, List<String> constiName, List<String> winningCand, List<Integer> Votes) {
        int idx = Votes.indexOf(min(Votes));
        System.out.println(constiName.get(idx) + "  " + winningCand.get(idx) + "  " + Votes.get(idx));
        write_Into_excel("State_wise_result",stateName,"candidates_with_MinVote",constiName.get(idx),winningCand.get(idx),Votes.get(idx).toString());

    }

    public static void max_Vote_in_State_Percentage(String stateName, List<String> constiName, List<String> winningCand, List<Double> VotesPercentage) {
        int idx = VotesPercentage.indexOf(max(VotesPercentage));
        System.out.println(constiName.get(idx) + "  " + winningCand.get(idx) + "  " + VotesPercentage.get(idx));
        write_Into_excel("State_wise_result",stateName,"maximum_vote_percentage_winner",constiName.get(idx),winningCand.get(idx),VotesPercentage.get(idx).toString());
    }
    public static void min_Vote_in_State_Percentage(String stateName, List<String> constiName, List<String> winningCand, List<Double> VotesPercentage) {
        int idx = VotesPercentage.indexOf(min(VotesPercentage));
        System.out.println(constiName.get(idx) + "  " + winningCand.get(idx) + "  " + VotesPercentage.get(idx));
        write_Into_excel("State_wise_result",stateName,"minimum_vote_percentage_winner",constiName.get(idx),winningCand.get(idx),VotesPercentage.get(idx).toString());
    }

    public static int count_of_candidate_greater_than_50_percent(String stateName, List<Double> VotesPercentage) {
        int count = 0;
        for (int i = 0; i < VotesPercentage.size(); i++) {
            if (VotesPercentage.get(i) >= 50.00) {
                ++count;
            }
        }

        return count;
    }

    public static void max_Vote_difference_in_State(String stateName, List<String> constiName, List<String> winningCand, List<Integer> Votes, List<String> runnerUpName, List<Integer> runnerUpVotes) {
        int idx;
        List<Integer> differenceList = new ArrayList<Integer>();
        for (int i = 0; i < Votes.size() && i < runnerUpVotes.size(); i++) {
            differenceList.add(Votes.get(i) - runnerUpVotes.get(i));
        }
//        System.out.println("difference list " + differenceList.toString());
        idx = differenceList.indexOf(max(differenceList));
        System.out.println(stateName+"  "+constiName.get(idx)+"  "+winningCand.get(idx)+"  "+Votes.get(idx)+"  "+runnerUpName.get(idx)+"  "+runnerUpVotes.get(idx)+"  "+differenceList.get(idx));
        write_Into_excel_type_2("State_wise_result",stateName,"maximum_vote_difference_margin",constiName.get(idx),winningCand.get(idx),Votes.get(idx).toString(),runnerUpName.get(idx),runnerUpVotes.get(idx).toString(),differenceList.get(idx).toString());
    }
    public static void min_Vote_difference_in_State(String stateName, List<String> constiName, List<String> winningCand, List<Integer> Votes, List<String> runnerUpName, List<Integer> runnerUpVotes) {
        int idx;
        List<Integer> differenceList = new ArrayList<Integer>();
        for (int i = 0; i < Votes.size() && i < runnerUpVotes.size(); i++) {
            differenceList.add(Votes.get(i) - runnerUpVotes.get(i));
        }
//        System.out.println("difference list " + differenceList.toString());
        idx = differenceList.indexOf(min(differenceList));
        System.out.println(stateName+"  "+constiName.get(idx)+"  "+winningCand.get(idx)+"  "+Votes.get(idx)+"  "+runnerUpName.get(idx)+"  "+runnerUpVotes.get(idx)+"  "+differenceList.get(idx));
        write_Into_excel_type_2("State_wise_result",stateName,"minimum_vote_difference_margin",constiName.get(idx),winningCand.get(idx),Votes.get(idx).toString(),runnerUpName.get(idx),runnerUpVotes.get(idx).toString(),differenceList.get(idx).toString());

    }

    public static void max_Vote_Percentage_difference_in_State(String stateName, List<String> constiName, List<String> winningCand, List<Double> VotesPercentage, List<String> runnerUpName, List<Double> runnerUpVotesPercentage) {
        int idx;
        List<Double> differenceList = new ArrayList<Double>();
//        System.out.println(runnerUpVotesPercentage.toString());
//        System.out.println(VotesPercentage.toString());
        for (int i = 0; i < VotesPercentage.size() && i < runnerUpVotesPercentage.size(); i++) {
            differenceList.add(VotesPercentage.get(i) - runnerUpVotesPercentage.get(i));
        }
//        System.out.println("difference list " + differenceList.toString());
        idx = differenceList.indexOf(max(differenceList));
        System.out.println(stateName+"  "+constiName.get(idx)+"  "+winningCand.get(idx)+"  "+VotesPercentage.get(idx)+"%  "+runnerUpName.get(idx)+"  "+runnerUpVotesPercentage.get(idx)+"%  "+differenceList.get(idx)+"% ");
        write_Into_excel_type_2("State_wise_result",stateName,"maximum_vote_percentage_differe",constiName.get(idx),winningCand.get(idx),VotesPercentage.get(idx).toString(),runnerUpName.get(idx),runnerUpVotesPercentage.get(idx).toString(),differenceList.get(idx).toString());

    }
    public static void min_Vote_Percentage_difference_in_State(String stateName, List<String> constiName, List<String> winningCand, List<Double> VotesPercentage, List<String> runnerUpName, List<Double> runnerUpVotesPercentage) {
        int idx;
        List<Double> differenceList = new ArrayList<Double>();
//        System.out.println(runnerUpVotesPercentage);
//        System.out.println(VotesPercentage);

        for (int i = 0; i < VotesPercentage.size() && i < runnerUpVotesPercentage.size(); i++) {

            differenceList.add((VotesPercentage.get(i) - runnerUpVotesPercentage.get(i)));
        }
//        System.out.println("difference list " + differenceList.toString());
        idx = differenceList.indexOf(min(differenceList));
        System.out.println(stateName+"  "+constiName.get(idx)+"  "+winningCand.get(idx)+"  "+VotesPercentage.get(idx)+"%  "+runnerUpName.get(idx)+"  "+runnerUpVotesPercentage.get(idx)+"%  "+differenceList.get(idx)+"% ");
        write_Into_excel_type_2("State_wise_result",stateName,"minimum_vote_percentage_differe",constiName.get(idx),winningCand.get(idx),VotesPercentage.get(idx).toString(),runnerUpName.get(idx),runnerUpVotesPercentage.get(idx).toString(),differenceList.get(idx).toString());
    }


    public static void data_of_UP_ConstWise() throws InterruptedException, IOException {
        String stateName = "Uttar Pradesh";
        System.setProperty(chromDriverProp, chromDriverPath);
        WebDriver driver = new ChromeDriver();
        driver.get(Baseurl);
//        create_Excel_Sheet(stateName);
        Select drpState = new Select(driver.findElement(By.xpath("//select[@id='ddlState']")));
        drpState.selectByVisibleText(stateName);
        int CountOfConstituency = driver.findElements(By.xpath("//*[@id='ddlAC']/option")).size();
        Select drpConst = new Select(driver.findElement(By.xpath("//select[@id='ddlAC']")));
        List<String> ConstituencyName = new ArrayList<String>();
        List<String> candidateNameMax = new ArrayList<String>();
        List<String> runnerUpName = new ArrayList<String>();
        List<Double> candidateWithMaxVotePercentage = new ArrayList<Double>();
        List<Integer> runnerUpVote = new ArrayList<Integer>();
        List<Double> runnerUpVotePercentage = new ArrayList<Double>();
        List<Integer> candidatesWithMaxVote = new ArrayList<Integer>();
        List<Integer> candidate_with_less_than_Nota = new ArrayList<Integer>();

        for(int con = 1;con<=CountOfConstituency-1;con++) {
//        for (int con = 1; con <= 5; con++) {
            drpConst.selectByIndex(con);
            List<String> candidateName = new ArrayList<String>();
            List<Integer> candidateVote = new ArrayList<Integer>();
            List<Integer> runnerupVote = new ArrayList<Integer>();
            List<Double> candidateVotePercentage = new ArrayList<Double>();
            List<Double> runnerupVotePercentage = new ArrayList<Double>();

            String constName = driver.findElement(By.xpath("//*[@id='ddlAC']/option[" + (con + (int) 1) + "]")).getText();
            ConstituencyName.add(constName);
            int totalRows = driver.findElements(By.xpath("//*[@id='div1']/table[1]/tbody/tr")).size();
            int NotaVote = Integer.parseInt(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + (totalRows - (int) 1) + "]/td[6]")).getText());
            int count_Less_than_Nota = 0;
//            System.out.println("NOTA Vote  " + NotaVote);
            for (int i = 4; i <= totalRows - 1; i++) {
                candidateName.add(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[2]")).getText());
                int No_Of_Votes = Integer.parseInt(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[6]")).getText());
                if (No_Of_Votes < NotaVote) {
                    ++count_Less_than_Nota;
                }
                candidateVote.add(No_Of_Votes);
                runnerupVote.add(No_Of_Votes);
                candidateVotePercentage.add(Double.parseDouble(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[7]")).getText()));
            }
            candidate_with_less_than_Nota.add(count_Less_than_Nota);
            int idx = candidateVote.indexOf(max(candidateVote));
            Collections.sort(runnerupVote);
            int secondHighest = runnerupVote.get(runnerupVote.size() - 2);
            int runnerUpIdx = candidateVote.indexOf(secondHighest);
//            System.out.println(runnerUpIndex);
            runnerUpVote.add(candidateVote.get(runnerUpIdx));
            runnerUpName.add(candidateName.get(runnerUpIdx));
            runnerUpVotePercentage.add(candidateVotePercentage.get(runnerUpIdx));
            candidateNameMax.add(candidateName.get(idx));
            candidatesWithMaxVote.add(candidateVote.get(idx));
            candidateWithMaxVotePercentage.add(candidateVotePercentage.get(idx));
//            System.out.println(stateName + "   " + constName + "  " + candidateName.get(idx) + "   " + candidateVote.get(idx) + " " + candidateVotePercentage.get(idx));
//            write_Into_excel(stateName, constName, candidateName.get(idx), candidateVote.get(idx));
//            System.out.println("runner up values");
//            System.out.println(constName + "  " + candidateName.get(runnerUpIdx) + "  " + candidateVote.get(runnerUpIdx) + "  " + candidateVotePercentage.get(runnerUpIdx));
            drpConst = new Select(driver.findElement(By.xpath("//select[@id='ddlAC']")));
        }
//        int maxVoteIdx = candidatesWithMaxVote.indexOf(max(candidatesWithMaxVote));

//        System.out.println(ConstituencyName.get(maxVoteIdx)+"  "+candidateNameMax.get(maxVoteIdx)+"  "+candidatesWithMaxVote.get(maxVoteIdx));
        System.out.println("\nUTTARPRADESH:::::\n");
        System.out.println("Max Vote Candidate");
        max_Vote_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote);
        System.out.println("Min Vote Candidate");
        min_Vote_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote);
        System.out.println("Candidate with maximum Vote difference Margin:");
        max_Vote_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote, runnerUpName, runnerUpVote);
        System.out.println("Candidate with minimum Vote difference Margin:");
        min_Vote_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote, runnerUpName, runnerUpVote);
        System.out.println("Candidate with maximum Vote Percentage difference Margin:");
        max_Vote_Percentage_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage, runnerUpName, runnerUpVotePercentage);
        System.out.println("Candidate with minimum Vote Percentage difference Margin:");
        min_Vote_Percentage_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage, runnerUpName, runnerUpVotePercentage);
        System.out.println("Max Vote Percentage winning candidate");
        max_Vote_in_State_Percentage(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage);
        System.out.println("Min Vote percentage wining candidate");
        min_Vote_in_State_Percentage(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage);
        System.out.println("Count less than nota   " + SUM(candidate_with_less_than_Nota));
        int total_count_Greater_than_50 = count_of_candidate_greater_than_50_percent(stateName, candidateWithMaxVotePercentage);
        System.out.println("candidate greater than 50% : " + total_count_Greater_than_50);
        write_Into_excel_misc("State_wise_result",stateName,"miscellaneous_Data",SUM(candidate_with_less_than_Nota),total_count_Greater_than_50);
        Thread.sleep(5000);
        driver.quit();
    }
    public static void data_of_UK_ConstWise() throws IOException, InterruptedException {
        String stateName = "Uttarakhand";
        System.setProperty(chromDriverProp, chromDriverPath);
        WebDriver driver = new ChromeDriver();
        driver.get(Baseurl);
//        create_Excel_Sheet(stateName);
        Select drpState = new Select(driver.findElement(By.xpath("//select[@id='ddlState']")));
        drpState.selectByVisibleText(stateName);
        int CountOfConstituency = driver.findElements(By.xpath("//*[@id='ddlAC']/option")).size();
        Select drpConst = new Select(driver.findElement(By.xpath("//select[@id='ddlAC']")));
        List<String> ConstituencyName = new ArrayList<String>();
        List<String> candidateNameMax = new ArrayList<String>();
        List<String> runnerUpName = new ArrayList<String>();
        List<Double> candidateWithMaxVotePercentage = new ArrayList<Double>();
        List<Integer> runnerUpVote = new ArrayList<Integer>();
        List<Double> runnerUpVotePercentage = new ArrayList<Double>();
        List<Integer> candidatesWithMaxVote = new ArrayList<Integer>();
        List<Integer> candidate_with_less_than_Nota = new ArrayList<Integer>();

        for(int con = 1;con<=CountOfConstituency-1;con++) {
//        for (int con = 1; con <= 5; con++) {
            drpConst.selectByIndex(con);
            List<String> candidateName = new ArrayList<String>();
            List<Integer> candidateVote = new ArrayList<Integer>();
            List<Integer> runnerupVote = new ArrayList<Integer>();
            List<Double> candidateVotePercentage = new ArrayList<Double>();
            List<Double> runnerupVotePercentage = new ArrayList<Double>();

            String constName = driver.findElement(By.xpath("//*[@id='ddlAC']/option[" + (con + (int) 1) + "]")).getText();
            ConstituencyName.add(constName);
            int totalRows = driver.findElements(By.xpath("//*[@id='div1']/table[1]/tbody/tr")).size();
            int NotaVote = Integer.parseInt(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + (totalRows - (int) 1) + "]/td[6]")).getText());
            int count_Less_than_Nota = 0;
//            System.out.println("NOTA Vote  " + NotaVote);
            for (int i = 4; i <= totalRows - 1; i++) {
                candidateName.add(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[2]")).getText());
                int No_Of_Votes = Integer.parseInt(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[6]")).getText());
                if (No_Of_Votes < NotaVote) {
                    ++count_Less_than_Nota;
                }
                candidateVote.add(No_Of_Votes);
                runnerupVote.add(No_Of_Votes);
                candidateVotePercentage.add(Double.parseDouble(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[7]")).getText()));
            }
            candidate_with_less_than_Nota.add(count_Less_than_Nota);
            int idx = candidateVote.indexOf(max(candidateVote));
            Collections.sort(runnerupVote);
            int secondHighest = runnerupVote.get(runnerupVote.size() - 2);
            int runnerUpIdx = candidateVote.indexOf(secondHighest);
//            System.out.println(runnerUpIndex);
            runnerUpVote.add(candidateVote.get(runnerUpIdx));
            runnerUpName.add(candidateName.get(runnerUpIdx));
            runnerUpVotePercentage.add(candidateVotePercentage.get(runnerUpIdx));

            candidateNameMax.add(candidateName.get(idx));
            candidatesWithMaxVote.add(candidateVote.get(idx));
            candidateWithMaxVotePercentage.add(candidateVotePercentage.get(idx));
//            System.out.println(stateName + "   " + constName + "  " + candidateName.get(idx) + "   " + candidateVote.get(idx) + " " + candidateVotePercentage.get(idx));
//            write_Into_excel(stateName, constName, candidateName.get(idx), candidateVote.get(idx));
//            System.out.println("runner up values");
//            System.out.println(constName + "  " + candidateName.get(runnerUpIdx) + "  " + candidateVote.get(runnerUpIdx) + "  " + candidateVotePercentage.get(runnerUpIdx));
            drpConst = new Select(driver.findElement(By.xpath("//select[@id='ddlAC']")));
        }
//        int maxVoteIdx = candidatesWithMaxVote.indexOf(max(candidatesWithMaxVote));

//        System.out.println(ConstituencyName.get(maxVoteIdx)+"  "+candidateNameMax.get(maxVoteIdx)+"  "+candidatesWithMaxVote.get(maxVoteIdx));
        System.out.println("\nUttarakhand:::::\n");
        System.out.println("Max Vote Candidate");
        max_Vote_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote);
        System.out.println("Min Vote Candidate");
        min_Vote_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote);
        System.out.println("Candidate with maximum Vote difference Margin:");
        max_Vote_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote, runnerUpName, runnerUpVote);
        System.out.println("Candidate with minimum Vote difference Margin:");
        min_Vote_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote, runnerUpName, runnerUpVote);
        System.out.println("Candidate with maximum Vote Percentage difference Margin:");
        max_Vote_Percentage_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage, runnerUpName, runnerUpVotePercentage);
        System.out.println("Candidate with minimum Vote Percentage difference Margin:");
        min_Vote_Percentage_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage, runnerUpName, runnerUpVotePercentage);
        System.out.println("Max Vote Percentage winning candidate");
        max_Vote_in_State_Percentage(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage);
        System.out.println("Min Vote percentage wining candidate");
        min_Vote_in_State_Percentage(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage);
        System.out.println("Count less than nota   " + SUM(candidate_with_less_than_Nota));
        int total_count_Greater_than_50 = count_of_candidate_greater_than_50_percent(stateName, candidateWithMaxVotePercentage);
        System.out.println("candidate greater than 50% : " + total_count_Greater_than_50);
        write_Into_excel_misc("State_wise_result",stateName,"miscellaneous_Data",SUM(candidate_with_less_than_Nota),total_count_Greater_than_50);

        Thread.sleep(5000);
        driver.quit();


    }
    public static void data_of_MP_ConstWise() throws InterruptedException, IOException {
        String stateName = "Manipur";
        System.setProperty(chromDriverProp, chromDriverPath);
        WebDriver driver = new ChromeDriver();
        driver.get(Baseurl);
//        create_Excel_Sheet(stateName);
        Select drpState = new Select(driver.findElement(By.xpath("//select[@id='ddlState']")));
        drpState.selectByVisibleText(stateName);
        int CountOfConstituency = driver.findElements(By.xpath("//*[@id='ddlAC']/option")).size();
        Select drpConst = new Select(driver.findElement(By.xpath("//select[@id='ddlAC']")));
        List<String> ConstituencyName = new ArrayList<String>();
        List<String> candidateNameMax = new ArrayList<String>();
        List<String> runnerUpName = new ArrayList<String>();
        List<Double> candidateWithMaxVotePercentage = new ArrayList<Double>();
        List<Integer> runnerUpVote = new ArrayList<Integer>();
        List<Double> runnerUpVotePercentage = new ArrayList<Double>();
        List<Integer> candidatesWithMaxVote = new ArrayList<Integer>();
        List<Integer> candidate_with_less_than_Nota = new ArrayList<Integer>();

        for(int con = 1;con<=CountOfConstituency-1;con++) {
//        for (int con = 1; con <= 5; con++) {
            drpConst.selectByIndex(con);
            List<String> candidateName = new ArrayList<String>();
            List<Integer> candidateVote = new ArrayList<Integer>();
            List<Integer> runnerupVote = new ArrayList<Integer>();
            List<Double> candidateVotePercentage = new ArrayList<Double>();
            List<Double> runnerupVotePercentage = new ArrayList<Double>();

            String constName = driver.findElement(By.xpath("//*[@id='ddlAC']/option[" + (con + (int) 1) + "]")).getText();
            ConstituencyName.add(constName);
            int totalRows = driver.findElements(By.xpath("//*[@id='div1']/table[1]/tbody/tr")).size();
            int NotaVote = Integer.parseInt(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + (totalRows - (int) 1) + "]/td[6]")).getText());
            int count_Less_than_Nota = 0;
//            System.out.println("NOTA Vote  " + NotaVote);
            for (int i = 4; i <= totalRows - 1; i++) {
                candidateName.add(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[2]")).getText());
                int No_Of_Votes = Integer.parseInt(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[6]")).getText());
                if (No_Of_Votes < NotaVote) {
                    ++count_Less_than_Nota;
                }
                candidateVote.add(No_Of_Votes);
                runnerupVote.add(No_Of_Votes);
                candidateVotePercentage.add(Double.parseDouble(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[7]")).getText()));
            }
            candidate_with_less_than_Nota.add(count_Less_than_Nota);
            int idx = candidateVote.indexOf(max(candidateVote));
            Collections.sort(runnerupVote);
            int secondHighest = runnerupVote.get(runnerupVote.size() - 2);
            int runnerUpIdx = candidateVote.indexOf(secondHighest);
//            System.out.println(runnerUpIndex);
            runnerUpVote.add(candidateVote.get(runnerUpIdx));
            runnerUpName.add(candidateName.get(runnerUpIdx));
            runnerUpVotePercentage.add(candidateVotePercentage.get(runnerUpIdx));

            candidateNameMax.add(candidateName.get(idx));
            candidatesWithMaxVote.add(candidateVote.get(idx));
            candidateWithMaxVotePercentage.add(candidateVotePercentage.get(idx));
//            System.out.println(stateName + "   " + constName + "  " + candidateName.get(idx) + "   " + candidateVote.get(idx) + " " + candidateVotePercentage.get(idx));
//            write_Into_excel(stateName, constName, candidateName.get(idx), candidateVote.get(idx));
//            System.out.println("runner up values");
//            System.out.println(constName + "  " + candidateName.get(runnerUpIdx) + "  " + candidateVote.get(runnerUpIdx) + "  " + candidateVotePercentage.get(runnerUpIdx));
            drpConst = new Select(driver.findElement(By.xpath("//select[@id='ddlAC']")));
        }
//        int maxVoteIdx = candidatesWithMaxVote.indexOf(max(candidatesWithMaxVote));

//        System.out.println(ConstituencyName.get(maxVoteIdx)+"  "+candidateNameMax.get(maxVoteIdx)+"  "+candidatesWithMaxVote.get(maxVoteIdx));
        System.out.println("\nManipur:::::\n");

        System.out.println("Max Vote Candidate");
        max_Vote_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote);
        System.out.println("Min Vote Candidate");
        min_Vote_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote);
        System.out.println("Candidate with maximum Vote difference Margin:");
        max_Vote_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote, runnerUpName, runnerUpVote);
        System.out.println("Candidate with minimum Vote difference Margin:");
        min_Vote_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote, runnerUpName, runnerUpVote);
        System.out.println("Candidate with maximum Vote Percentage difference Margin:");
        max_Vote_Percentage_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage, runnerUpName, runnerUpVotePercentage);
        System.out.println("Candidate with minimum Vote Percentage difference Margin:");
        min_Vote_Percentage_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage, runnerUpName, runnerUpVotePercentage);
        System.out.println("Max Vote Percentage winning candidate");
        max_Vote_in_State_Percentage(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage);
        System.out.println("Min Vote percentage wining candidate");
        min_Vote_in_State_Percentage(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage);
        System.out.println("Count less than nota   " + SUM(candidate_with_less_than_Nota));
        int total_count_Greater_than_50 = count_of_candidate_greater_than_50_percent(stateName, candidateWithMaxVotePercentage);
        System.out.println("candidate greater than 50% : " + total_count_Greater_than_50);
        write_Into_excel_misc("State_wise_result",stateName,"miscellaneous_Data",SUM(candidate_with_less_than_Nota),total_count_Greater_than_50);
        Thread.sleep(5000);
        driver.quit();
    }
    public static void data_of_PB_ConstWise() throws IOException, InterruptedException {
        String stateName = "Punjab";
        System.setProperty(chromDriverProp, chromDriverPath);
        WebDriver driver = new ChromeDriver();
        driver.get(Baseurl);
//        create_Excel_Sheet(stateName);
        Select drpState = new Select(driver.findElement(By.xpath("//select[@id='ddlState']")));
        drpState.selectByVisibleText(stateName);
        int CountOfConstituency = driver.findElements(By.xpath("//*[@id='ddlAC']/option")).size();
        Select drpConst = new Select(driver.findElement(By.xpath("//select[@id='ddlAC']")));
        List<String> ConstituencyName = new ArrayList<String>();
        List<String> candidateNameMax = new ArrayList<String>();
        List<String> runnerUpName = new ArrayList<String>();
        List<Double> candidateWithMaxVotePercentage = new ArrayList<Double>();
        List<Integer> runnerUpVote = new ArrayList<Integer>();
        List<Double> runnerUpVotePercentage = new ArrayList<Double>();
        List<Integer> candidatesWithMaxVote = new ArrayList<Integer>();
        List<Integer> candidate_with_less_than_Nota = new ArrayList<Integer>();

        for(int con = 1;con<=CountOfConstituency-1;con++) {
//        for (int con = 1; con <= 5; con++) {
            drpConst.selectByIndex(con);
            List<String> candidateName = new ArrayList<String>();
            List<Integer> candidateVote = new ArrayList<Integer>();
            List<Integer> runnerupVote = new ArrayList<Integer>();
            List<Double> candidateVotePercentage = new ArrayList<Double>();
            List<Double> runnerupVotePercentage = new ArrayList<Double>();

            String constName = driver.findElement(By.xpath("//*[@id='ddlAC']/option[" + (con + (int) 1) + "]")).getText();
            ConstituencyName.add(constName);
            int totalRows = driver.findElements(By.xpath("//*[@id='div1']/table[1]/tbody/tr")).size();
            int NotaVote = Integer.parseInt(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + (totalRows - (int) 1) + "]/td[6]")).getText());
            int count_Less_than_Nota = 0;
//            System.out.println("NOTA Vote  " + NotaVote);
            for (int i = 4; i <= totalRows - 1; i++) {
                candidateName.add(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[2]")).getText());
                int No_Of_Votes = Integer.parseInt(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[6]")).getText());
                if (No_Of_Votes < NotaVote) {
                    ++count_Less_than_Nota;
                }
                candidateVote.add(No_Of_Votes);
                runnerupVote.add(No_Of_Votes);
                candidateVotePercentage.add(Double.parseDouble(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[7]")).getText()));
            }
            candidate_with_less_than_Nota.add(count_Less_than_Nota);
            int idx = candidateVote.indexOf(max(candidateVote));
            Collections.sort(runnerupVote);
            int secondHighest = runnerupVote.get(runnerupVote.size() - 2);
            int runnerUpIdx = candidateVote.indexOf(secondHighest);
//            System.out.println(runnerUpIndex);
            runnerUpVote.add(candidateVote.get(runnerUpIdx));
            runnerUpName.add(candidateName.get(runnerUpIdx));
            runnerUpVotePercentage.add(candidateVotePercentage.get(runnerUpIdx));

            candidateNameMax.add(candidateName.get(idx));
            candidatesWithMaxVote.add(candidateVote.get(idx));
            candidateWithMaxVotePercentage.add(candidateVotePercentage.get(idx));
//            System.out.println(stateName + "   " + constName + "  " + candidateName.get(idx) + "   " + candidateVote.get(idx) + " " + candidateVotePercentage.get(idx));
//            write_Into_excel(stateName, constName, candidateName.get(idx), candidateVote.get(idx));
//            System.out.println("runner up values");
//            System.out.println(constName + "  " + candidateName.get(runnerUpIdx) + "  " + candidateVote.get(runnerUpIdx) + "  " + candidateVotePercentage.get(runnerUpIdx));
            drpConst = new Select(driver.findElement(By.xpath("//select[@id='ddlAC']")));
        }
//        int maxVoteIdx = candidatesWithMaxVote.indexOf(max(candidatesWithMaxVote));

//        System.out.println(ConstituencyName.get(maxVoteIdx)+"  "+candidateNameMax.get(maxVoteIdx)+"  "+candidatesWithMaxVote.get(maxVoteIdx));
        System.out.println("\nPunjab::::::\n");
        System.out.println("Max Vote Candidate");
        max_Vote_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote);
        System.out.println("Min Vote Candidate");
        min_Vote_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote);
        System.out.println("Candidate with maximum Vote difference Margin:");
        max_Vote_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote, runnerUpName, runnerUpVote);
        System.out.println("Candidate with minimum Vote difference Margin:");
        min_Vote_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote, runnerUpName, runnerUpVote);
        System.out.println("Candidate with maximum Vote Percentage difference Margin:");
        max_Vote_Percentage_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage, runnerUpName, runnerUpVotePercentage);
        System.out.println("Candidate with minimum Vote Percentage difference Margin:");
        min_Vote_Percentage_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage, runnerUpName, runnerUpVotePercentage);
        System.out.println("Max Vote Percentage winning candidate");
        max_Vote_in_State_Percentage(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage);
        System.out.println("Min Vote percentage wining candidate");
        min_Vote_in_State_Percentage(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage);
        System.out.println("Count less than nota   " + SUM(candidate_with_less_than_Nota));
        int total_count_Greater_than_50 = count_of_candidate_greater_than_50_percent(stateName, candidateWithMaxVotePercentage);
        System.out.println("candidate greater than 50% : " + total_count_Greater_than_50);
        write_Into_excel_misc("State_wise_result",stateName,"miscellaneous_Data",SUM(candidate_with_less_than_Nota),total_count_Greater_than_50);
        Thread.sleep(5000);
        driver.quit();

    }
    public static void data_of_GA_ConstWise() throws InterruptedException, IOException {
        String stateName = "Goa";
        System.setProperty(chromDriverProp, chromDriverPath);
        WebDriver driver = new ChromeDriver();
        driver.get(Baseurl);
//        create_Excel_Sheet(stateName);
        Select drpState = new Select(driver.findElement(By.xpath("//select[@id='ddlState']")));
        drpState.selectByVisibleText(stateName);
        int CountOfConstituency = driver.findElements(By.xpath("//*[@id='ddlAC']/option")).size();
        Select drpConst = new Select(driver.findElement(By.xpath("//select[@id='ddlAC']")));
        List<String> ConstituencyName = new ArrayList<String>();
        List<String> candidateNameMax = new ArrayList<String>();
        List<String> runnerUpName = new ArrayList<String>();
        List<Double> candidateWithMaxVotePercentage = new ArrayList<Double>();
        List<Integer> runnerUpVote = new ArrayList<Integer>();
        List<Double> runnerUpVotePercentage = new ArrayList<Double>();
        List<Integer> candidatesWithMaxVote = new ArrayList<Integer>();
        List<Integer> candidate_with_less_than_Nota = new ArrayList<Integer>();

        for(int con = 1;con<=CountOfConstituency-1;con++) {
//        for (int con = 1; con <= 5; con++) {
            drpConst.selectByIndex(con);
            List<String> candidateName = new ArrayList<String>();
            List<Integer> candidateVote = new ArrayList<Integer>();
            List<Integer> runnerupVote = new ArrayList<Integer>();
            List<Double> candidateVotePercentage = new ArrayList<Double>();
            List<Double> runnerupVotePercentage = new ArrayList<Double>();

            String constName = driver.findElement(By.xpath("//*[@id='ddlAC']/option[" + (con + (int) 1) + "]")).getText();
            ConstituencyName.add(constName);
            int totalRows = driver.findElements(By.xpath("//*[@id='div1']/table[1]/tbody/tr")).size();
            int NotaVote = Integer.parseInt(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + (totalRows - (int) 1) + "]/td[6]")).getText());
            int count_Less_than_Nota = 0;
//            System.out.println("NOTA Vote  " + NotaVote);
            for (int i = 4; i <= totalRows - 1; i++) {
                candidateName.add(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[2]")).getText());
                int No_Of_Votes = Integer.parseInt(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[6]")).getText());
                if (No_Of_Votes < NotaVote) {
                    ++count_Less_than_Nota;
                }
                candidateVote.add(No_Of_Votes);
                runnerupVote.add(No_Of_Votes);
                candidateVotePercentage.add(Double.parseDouble(driver.findElement(By.xpath("//*[@id='div1']/table[1]/tbody/tr[" + i + "]/td[7]")).getText()));
            }
            candidate_with_less_than_Nota.add(count_Less_than_Nota);
            int idx = candidateVote.indexOf(max(candidateVote));
            Collections.sort(runnerupVote);
            int secondHighest = runnerupVote.get(runnerupVote.size() - 2);
            int runnerUpIdx = candidateVote.indexOf(secondHighest);
//            System.out.println(runnerUpIndex);
            runnerUpVote.add(candidateVote.get(runnerUpIdx));
            runnerUpName.add(candidateName.get(runnerUpIdx));
            runnerUpVotePercentage.add(candidateVotePercentage.get(runnerUpIdx));

            candidateNameMax.add(candidateName.get(idx));
            candidatesWithMaxVote.add(candidateVote.get(idx));
            candidateWithMaxVotePercentage.add(candidateVotePercentage.get(idx));
//            System.out.println(stateName + "   " + constName + "  " + candidateName.get(idx) + "   " + candidateVote.get(idx) + " " + candidateVotePercentage.get(idx));
//            write_Into_excel(stateName, constName, candidateName.get(idx), candidateVote.get(idx));
//            System.out.println("runner up values");
//            System.out.println(constName + "  " + candidateName.get(runnerUpIdx) + "  " + candidateVote.get(runnerUpIdx) + "  " + candidateVotePercentage.get(runnerUpIdx));
            drpConst = new Select(driver.findElement(By.xpath("//select[@id='ddlAC']")));
        }
//        int maxVoteIdx = candidatesWithMaxVote.indexOf(max(candidatesWithMaxVote));

//        System.out.println(ConstituencyName.get(maxVoteIdx)+"  "+candidateNameMax.get(maxVoteIdx)+"  "+candidatesWithMaxVote.get(maxVoteIdx));

        System.out.println("\nGoa::::::\n");
        System.out.println("Max Vote Candidate");
        max_Vote_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote);
        System.out.println("Min Vote Candidate");
        min_Vote_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote);
        System.out.println("Candidate with maximum Vote difference Margin:");
        max_Vote_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote, runnerUpName, runnerUpVote);
        System.out.println("Candidate with minimum Vote difference Margin:");
        min_Vote_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidatesWithMaxVote, runnerUpName, runnerUpVote);
        System.out.println("Candidate with maximum Vote Percentage difference Margin:");
        max_Vote_Percentage_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage, runnerUpName, runnerUpVotePercentage);
        System.out.println("Candidate with minimum Vote Percentage difference Margin:");
        min_Vote_Percentage_difference_in_State(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage, runnerUpName, runnerUpVotePercentage);
        System.out.println("Max Vote Percentage winning candidate");
        max_Vote_in_State_Percentage(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage);
        System.out.println("Min Vote percentage wining candidate");
        min_Vote_in_State_Percentage(stateName, ConstituencyName, candidateNameMax, candidateWithMaxVotePercentage);
        System.out.println("Count less than nota   " + SUM(candidate_with_less_than_Nota));
        int total_count_Greater_than_50 = count_of_candidate_greater_than_50_percent(stateName, candidateWithMaxVotePercentage);
        System.out.println("candidate greater than 50% : " + total_count_Greater_than_50);
        write_Into_excel_misc("State_wise_result",stateName,"miscellaneous_Data",SUM(candidate_with_less_than_Nota),total_count_Greater_than_50);

        Thread.sleep(2000);
        driver.quit();

    }
}
