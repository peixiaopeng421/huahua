import com.an.mq.MqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//生产者
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqApplication.class)
public class MqSendTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void testSend(){
        //rabbitTemplate.convertAndSend("huahua","","我是你永远得不到的爸爸");
        rabbitTemplate.convertAndSend("info","我是你永远得不到的爸爸");
    }


    @Test
    public void testSend2(){

        rabbitTemplate.convertAndSend("huahuaTopic","article.log","下雨了");
    }




}