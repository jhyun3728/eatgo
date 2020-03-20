package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItemRepositoryImpl;
import kr.co.fastcampus.eatgo.domain.RestaurantRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest { //테스트하면 에러 난다. 테스트하기 위해 WebMvc를 테스트했을때 제대로 된 저장소를 사용할 수 없기 떄문
                                // --> 테스트를 진행하기 위해 컨트롤러에 직접 의존성을 주입해줘야한다.  --->>> @SpyBean
                                //의존성을 주입하면 우리가 사용하려는 객체를 다양하게 변경할 수 있다는 장점이 있다.

    //같은 방식으로 작동하는 여러 객체들을 관리하는 방법 --> 만들었던 Repository를 인터페이스로 변경하고 만들었던 부분을 실제 구현으로 변경해준다

   @Autowired
    private MockMvc mvc;


   /**RestaurantService로 다 해결해서 repository들은 필요가 없어짐 -->> repository를 없애니까 에러나서 다시 일단 복구시켰음**/
    @SpyBean(RestaurantRepositoryImpl.class) //어떤 구현을 사용할 것인지(매개변수) 명시해주지 않으면 에러가 난다.
    private RestaurantRepositoryImpl restaurantRepository;      //restaurantRepository를 주입하겠다고 선언

    @SpyBean(MenuItemRepositoryImpl.class)
    private MenuItemRepositoryImpl menuItemRepository;

    @SpyBean(RestaurantService.class)
    private RestaurantService restaurantService;

    @Test
    public void List() throws Exception {  //List에 대한 테스트
        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())  //status가 Ok인걸 기대한다.
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")));
    }

    @Test
    public void detail() throws Exception {
        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk()) //이상태로 Test하면 404에러가 난다 --> RestaurantController에 가서
                                            // @GerMapping("/restaurants/1")하고 public Restaurant detail() { return ***;} 을 만들어준다.
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\""))) //여기까지해서 Test하면 원하는 정보가 없으므로 에러 --> 원하는거 추가
                .andExpect(content().string(containsString("Kimchi")));

        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":2020")))
                .andExpect(content().string(containsString("\"name\":\"Cyber food\"")));
    }

}