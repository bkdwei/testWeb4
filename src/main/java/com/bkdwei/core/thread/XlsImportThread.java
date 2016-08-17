/**
 *
 */
package com.bkdwei.core.thread;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.StringUtils;

import com.bkdwei.model.Classes;
import com.bkdwei.model.Family;
import com.bkdwei.model.Student;
import com.bkdwei.testCase.one2many.dao.StudentDao;
import com.bkdwei.testCase.one2many.service.ClassesService;
import com.bkdwei.testCase.one2many.service.FamilyService;
import com.bkdwei.testCase.one2many.service.StudentService;

/**
 * 使用线程导入多条excel记录
 * 
 * @author bkd
 * @Date 2016年8月9日
 */
public class XlsImportThread implements Runnable {

    private static int                               endRow     = 0;;
    private static int                               startRow   = 0;
    private final ConcurrentHashMap<String, Classes> classesMap = new ConcurrentHashMap<String, Classes>();
    private final ClassesService                     classesService;

    private final int                                count;
    private final FamilyService                      familyService;
    private final HSSFSheet                          hssfSheet;
    private StudentDao                               studentDao;
    private final StudentService                     studentService;

    /**
     * @param
     */
    public XlsImportThread(final HSSFSheet hssfSheet, final ClassesService classesService,
            final StudentService studentService, final FamilyService familyService) {
        super();
        this.hssfSheet = hssfSheet;
        this.classesService = classesService;
        this.studentService = new StudentService();
        final StudentDao studentDao = new StudentDao();
        this.familyService = familyService;
        endRow = hssfSheet.getLastRowNum();
        count = (int) Math.round(Math.random() * 1000);
    }

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

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        while (true) {
            synchronized (this) {
                if (startRow == endRow) {
                    System.out.println("线程" + count + "正在退出" + startRow);
                    break;
                } else if (startRow + 200 <= endRow) {
                    startRow = startRow + 200;
                } else if (startRow + 200 > endRow) {
                    startRow = endRow;
                }
            }
            System.out.println("\n线程" + count + "正在处理" + (startRow - 200) + "至" + startRow);

            for (int rowNum = startRow - 200; rowNum <= startRow; rowNum++) {
                Classes classes = null;
                Student student = null;
                if (rowNum % 30 == 0) {
                    System.out.print("\t线程" + count + ":" + rowNum);
                }
                if (rowNum % 200 == 0) {
                    System.out.println();
                }
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
                classes = classesMap.get(sBanji);
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
                //   synchronized (studentService) {
                studentDao.saveOrUpdate(student);
                //  }

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
                        /*
                         * synchronized (familyService) {
                         * familyService.save(family);
                         * }
                         */

                    }
                }
            }
        }
    }
}
