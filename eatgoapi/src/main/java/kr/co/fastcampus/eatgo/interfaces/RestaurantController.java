package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController     //@RestConroller도 @Component 일종임
public class RestaurantController {

    //UI레이어는 사용자와 도메인 모델들이 서로 상관없도록 중간 징검다리 역할만 하게끔 만들어주고 실제로 동작하는 기능들은 domain에 구현해준다.

    //private RestaurantRepository repository = new RestaurantRepository();
//    @Autowired //@Autowired하면 RestaurantController를 생성할때 Spring이 알아서 repository를 생성해준다. //의존성 주입 part
//    private RestaurantRepositoryImpl restaurantRepository;
//
//    @Autowired
//    private MenuItemRepository menuItemRepository;
    /**RestaurantService에서 기본 정보 + 메뉴 정보를 다 처리할것이기 때문에 다른 repository는 필요없다. -->> 지우려다가 공부용으로 주석처리함**/

    @Autowired
    private RestaurantService restaurantSerivce;


    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        //list()와 detail()의 바디가 똑같다 --> 하나로 관리하면 더 효율적
        //restaurant의 collection(저장소 / repository)라고 부르고 UI에 속하지 않고 Domain에 따로 관리한다.

//        List<Restaurant> restaurants = new ArrayList<>();
//
//        restaurants.add(new Restaurant(1004L, "Bob zip", "Seoul"));
//        restaurants.add(new Restaurant(2020L, "Cyber Food", "Seoul"));

//        /***우리가 원하는 restaurantRepository 모양***/  /**restaurantService에서 다 해결할것이므로 알맞게 바꿔준다.**/
//        List<Restaurant> restaurants = restaurantRepository.findAll();

        List<Restaurant> restaurants = restaurantSerivce.getRestaurants();

        return restaurants;
    }


    /**** 가게 상세정보를 얻는 API 구현****/
    @GetMapping("/restaurants/{id}")  //{id} 부분에 1,2,3... 일일이 입력해줄 수 가 없다 그러므로 {id} 변수를 넣는다.
    public Restaurant detail(@PathVariable("id") Long id) {  //Mapping안의 주소 변수 {id}를 받을 수 있게 매개변수에 선언한다.

        //Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");

        /*  비효율적인 방
        Restaurant restaurant = null;

        if (id == 1004L){
            restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        }
        if (id == 2020L){
            restaurant = new Restaurant(2020L, "Cyber Food", "Seoul");
        }
        */

        /*****아주 효율적인 방법!!!!*****/
//        List<Restaurant> restaurants = new ArrayList<>();     //list()와 중복되는 코드를 RestaurantRepository에 하나로 관리!!!!!!!!
//
//        restaurants.add(new Restaurant(1004L, "Bob zip", "Seoul"));
//        restaurants.add(new Restaurant(2020L, "Cyber Food", "Seoul"));

        /***우리가 원하는 restaurantRepository 모양***/
        /**but!! 밑에 stream부분은 repository에 있는 내용들을 통째로 가져와서 검색하는 기능인데 여기 Controller가 할 일이 아니다!!**/
        /**repository.findById(id)를 해서 id에 해당하는 restaurant만 얻게하면 된다. ---> 수정 **/
//        List<Restaurant> restaurants = repository.findAll();
//
//        Restaurant restaurant = restaurants.stream()    //스트림의 활용법
//                .filter(r -> r.getId().equals(id))  //찾길 원하는거(getId)를 비교하고자하는 거(id)랑 같은지 비교
//                .findFirst()    //그중에 첫번째꺼를 찾아서
//                .get();     //값을 얻는다    //즉, 레스토랑에 있는거중에 id가 같은것을 찾아서 얻는다.
//                //.get() 대신 .orElse(null)하면 없는 것은 null 처리 --> 예외처리느낌

        Restaurant restaurant = restaurantSerivce.getRestaurant(id);
        //기본 정보 + 메뉴 정보
        //이 두 정보를 한꺼번에 돌려줄때 쓰는 객체가 restaurantService
        //기존의 repository는 collection과 같은 역할이였다. 레스토랑의 목록을 가지고 있고 그걸 얻었다면 restaurantService는 이런 복잡한 처리들이 한꺼번에 일어나는 새로운 객체이다.
        //이런 객체가 애플레케이션 레이어 중간에 하나 들어가는 것이다.

        /**RestaurantService에서 기본 정보 + 메뉴 정보를 다 처리할것이기 때문에 다른 repository는 필요없다. -->> 지우려다가 공부용으로 주석처리함**/
//        Restaurant restaurant = restaurantRepository.findById(id);
//
        /**이부분은 RestaurantService에 넣자**/
//        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
//        restaurant.setMenuItems(menuItems);

        return restaurant;
    }
}
