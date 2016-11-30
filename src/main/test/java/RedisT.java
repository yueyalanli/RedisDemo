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
public class RedisT {
    @Autowired
    private StudentInfoService studentInfoService;
    //对学生信息进行批量插入
    @Test
    public void TestInsert(){
        StudentModel model = new StudentModel();
        model.setSid("12123214");
        model.setSex("girl");
        model.setName("shi");
        model.setClasses("2");
        StudentModel model1 = new StudentModel();
        model1.setSid("12123215");
        model1.setSex("boy");
        model1.setName("sqj");
        model1.setClasses("4");
        List<StudentModel> ids = new ArrayList<StudentModel>();
        ids.add(model);
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

}
