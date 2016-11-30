import com.redis.service.model.StudentModel;
import com.redis.service.student.StudentInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/applicationContext-redis.xml")
public class StudentInfoServiceTest {
    @Autowired
    private StudentInfoService studentInfoService;
    //对学生信息进行批量插入
    @Test
    public void TestInsert(){
//        StudentModel model = new StudentModel();
//        model.setSid("12123214");
//        model.setSex("girl");
//        model.setName("shi");
//        model.setClasses("2");
        StudentModel model1 = new StudentModel();
        model1.setSid("12123216");
        model1.setSex("boy");
        model1.setName("sqj");
        model1.setClasses("6");
        List<StudentModel> ids = new ArrayList<StudentModel>();
        //ids.add(model);
        ids.add(model1);
        studentInfoService.insertStudentInfo(ids);
    }
    //对学生信息进行批量删除
    @Test
    public void TestDel(){
        List<String> Sids = new ArrayList<String>();
        Sids.add("12123215");
        studentInfoService.delStudentInfo(Sids);
    }

    //对学生信息进行修改
    @Test
    public void TestEdit(){
        String Sid = "12123211";
        String editContent = "judy,girl,7";
        studentInfoService.editStudentInfo(Sid,editContent);
    }
    //批量查询
    @Test
    public void TestSelectByIds(){
        List<String> sids = new ArrayList<String>();
        sids.add("12123211");
        sids.add("12123216");
        System.out.println(studentInfoService.selectStudentInfoByIds(sids));
    }
    //查询全部
    @Test
    public void TestselectAll(){
        System.out.println(studentInfoService.selectAllStudentInfo());
    }
    //判断某学生是否存在
    @Test
    public void TestifExists(){
        String ids = "12123217";
        System.out.println("判断该学生是否在student表内："+studentInfoService.ifExitsts(ids));
    }
    //
}
