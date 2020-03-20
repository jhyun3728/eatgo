package kr.co.fastcampus.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component  //RestaurantRepository를 Spring이 관리 할 수 있도록 @Component를 붙여준다.  //인강 의존성 주입 Part
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepositoryImpl() {
        restaurants.add(new Restaurant(1004L, "Bob zip", "Seoul"));     //이 값들은 repository를 만들때 넣어주면 좋으니까 옮기자
        restaurants.add(new Restaurant(2020L, "Cyber food", "Seoul"));
    }

    /***의존성 주입 part //같은 방식으로 작동하는 여러 객체들을 관리하기 해 Repository의 findAll과 findById를 인터페이스로 분리***/
    /***우클릭 Refactor -> Extract Interface -> 이름: RestaurantRepository -> 메서드 체크 -->>> @Override가 메서드마다 붙고 클래스에 인터페이스가 상속이 된다***/
    @Override
    public List<Restaurant> findAll() {
//        List<Restaurant> restaurants = new ArrayList<>();   //findById과 중복되므로 field로 옮겨 하나로 만든다.

//        restaurants.add(new Restaurant(1004L, "Bob zip", "Seoul"));     //이 값들은 repository를 만들때 넣어주면 좋으니까 옮기자
//        restaurants.add(new Restaurant(2020L, "Cyber Food", "Seoul"));

        return restaurants;
    }

    @Override
    public Restaurant findById(Long id) {
//        List<Restaurant> restaurants = new ArrayList<>();   //findAll과 중복되므로 field로 옮겨 하나로 만든다.

//        Restaurant restaurant = restaurants.stream()
        return restaurants.stream()  //restaurant이 field에 있는걸 사용하기 때문에 따로 추가 선언없이 return에다가 바로 사용해준다.
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .get();

    }
}
