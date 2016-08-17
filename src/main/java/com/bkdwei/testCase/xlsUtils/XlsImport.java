/**
 *
 */
package com.bkdwei.testCase.xlsUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bkdwei.core.thread.XlsImportThread;
import com.bkdwei.model.Classes;
import com.bkdwei.model.Family;
import com.bkdwei.model.Student;
import com.bkdwei.testCase.one2many.service.ClassesService;
import com.bkdwei.testCase.one2many.service.FamilyService;
import com.bkdwei.testCase.one2many.service.StudentService;

/**
 * @author bkd
 * @Date 2016年8月5日
 */
@Service
public class XlsImport {
    @Autowired
    private ClassesService classesService;
    @Autowired
    private FamilyService  familyService;
    @Autowired
    private StudentService studentService;

    /**
     * 得到Excel表中的值
     *
     * @param hssfCell
     *            Excel中的每一个格子
     * @return Excel中每一个格子中的值
     */
    private String getValue(final HSSFCell hssfCell) {
        if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    public void readXls(final File file) throws IOException, InterruptedException {
        final InputStream is = new FileInputStream(file);
        final HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            final HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }

            final int threadCount = (int) Math.ceil(hssfSheet.getLastRowNum() / 500);
            final ExecutorService exe = Executors.newFixedThreadPool(threadCount);
            for (int i = 1; i <= threadCount; ++i) {
                exe.submit(new XlsImportThread(hssfSheet, classesService, studentService,
                        familyService));
            }
            exe.shutdown();
        }
    }

    public List<Classes> readXls1(final File file) throws IOException {
        final InputStream is = new FileInputStream(file);
        final HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        final List<Classes> list = new ArrayList<Classes>();

        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            final HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            final HashMap classesMap = new HashMap<String, Classes>();
            //从第二行开始，第一行作为标题，不存放数据
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                Classes classes = null;
                Student student = null;
                final HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }

                // 循环列Cell
                // 0班级 1姓名 2学院 3课程名 4 成绩
                // for (int cellNum = 0; cellNum <=4; cellNum++) {
                final HSSFCell banji = hssfRow.getCell(0);
                if (banji == null) {
                    continue;
                }

                final HashMap<String, String> map = new HashMap<String, String>(1);
                final String sBanji = getValue(banji);
                classes = (Classes) classesMap.get(sBanji);
                if (classes == null) {
                    map.put("className", sBanji);
                    classes = classesService.findObjectByMap(map);
                }

                if (classes == null) {
                    classes = new Classes();
                    classes.setClassName(getValue(banji));
                }

                final HSSFCell xingming = hssfRow.getCell(1);
                if (xingming == null) {
                    continue;
                }

                //student.setClasses(classes);
                final String sStudentName = getValue(xingming);
                map.clear();
                map.put("studentName", sStudentName);
                //map.put("classes", classes.getClassId().toString());
                student = studentService.findObjectByMap(map);
                //此处应该还需要优化，查询多的一方时，如何避免重复？
                if (student == null || student.getClasses().getClassId() != classes.getClassId()) {
                    student = new Student();
                    student.setStudentName(sStudentName);
                    student.setClasses(classes);
                } else {
                    student.setClasses(classes);
                }
                final HSSFCell age = hssfRow.getCell(2);
                if (age != null) {
                    student.setAge((int) age.getNumericCellValue());
                }
                final HSSFCell sex = hssfRow.getCell(3);
                if (sex != null) {
                    student.setSex(getValue(sex));
                }

                final HSSFCell hometown = hssfRow.getCell(4);
                if (hometown != null) {
                    student.setHometown(getValue(hometown));
                }

                final HSSFCell phone = hssfRow.getCell(5);
                if (hometown != null) {
                    student.setPhone(getValue(phone));
                }
                final HSSFCell birthday = hssfRow.getCell(6);
                if (birthday != null) {

                }

                studentService.saveOrUpdate(student);
                final HSSFCell familyName = hssfRow.getCell(7);
                if (familyName != null) {
                    final String sFamilyName = getValue(familyName);
                    if (!StringUtils.isEmpty(sFamilyName)) {
                        final Family family = new Family();
                        final HSSFCell familyAge = hssfRow.getCell(8);
                        final HSSFCell familySex = hssfRow.getCell(9);
                        final HSSFCell relationShip = hssfRow.getCell(10);
                        family.setName(sFamilyName);
                        family.setAge((int) familyAge.getNumericCellValue());
                        family.setSex(getValue(familySex));
                        family.setRelationship(getValue(relationShip));
                        family.setStudent(student);
                        familyService.save(family);
                    }
                }
                list.add(classes);
            }
        }
        /*
         * for (final Classes classes : list) {
         * classesService.saveOrUpdate(classes);
         * }
         */
        hssfWorkbook.close();
        return list;
    }
}
