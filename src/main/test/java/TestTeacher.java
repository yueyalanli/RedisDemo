import com.redis.service.model.TeacherModel;
import com.redis.service.teacher.TeacherRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 杨阳
 * @Date 2016/11/29 - 17:32
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/applicationContext-redis.xml")
public class TestTeacher {

    @Autowired
    private TeacherRedisService teacherRedis;

    @Test
    public void testInsert(){
        List<TeacherModel> list = new ArrayList<TeacherModel>();
        TeacherModel model = new TeacherModel();
        model.setTId("30002");
        model.setName("alice");
        model.setCourse("Chinese");
        model.setSex("woman");
        list.add(model);
        TeacherModel model1 = new TeacherModel();
        model1.setTId("30003");
        model1.setName("john");
        model1.setCourse("math");
        model1.setSex("man");
        list.add(model1);
        TeacherModel model2 = new TeacherModel();
        model2.setTId("30004");
        model2.setName("judy");
        model2.setCourse("history");
        model2.setSex("man");
        list.add(model2);
        TeacherModel model3 = new TeacherModel();
        model3.setTId("30005");
        model3.setName("matina");
        model3.setCourse("science");
        model3.setSex("woman");
        list.add(model3);
        teacherRedis.addTeacherList(list);
    }

    @Test
    public void testDel(){
        List<String> tids = new ArrayList<String>();
        tids.add("30001");
        tids.add("30002");
        System.out.println( teacherRedis.deleteTeacherList(tids));
    }

    @Test
    public void testEdit(){
        Map<String,TeacherModel> map = new HashMap<String, TeacherModel>();
        TeacherModel model = new TeacherModel();
        model.setSex("no");
        model.setTId("30001");
        model.setCourse("english");
        model.setName("bob");
        map.put("30001",model);
        teacherRedis.editTeacherList(map);
    }

    @Test
    public void testGet(){
        List<String> tids = new ArrayList<String>();
        tids.add("30001");
        tids.add("30002");
        System.out.println(teacherRedis.getTeacherListByTIds(tids));
        System.out.println(teacherRedis.getAllTeahcer());

    }



}
