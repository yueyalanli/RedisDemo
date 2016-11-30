import com.redis.service.classes.ClassesInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/applicationContext-redis.xml")
public class ClassesInfoServiceTest {
    @Autowired
    private ClassesInfoService classesInfoService;
    //新增一个班级
    @Test
    public void TestDel(){
        System.out.println(classesInfoService.insertClasses("4"));
    }
    //往某班级里添加新成员
    @Test
    public void TestaddMemberstoClass(){
        List<String> sids = new ArrayList<String>();
        String cid = "3";
        sids.add("12123215");
        System.out.println("班级"+cid + "的班级成员为："+classesInfoService.addMemberstoClass(cid,sids));
    }

}
