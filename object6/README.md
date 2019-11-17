# 합성과 의존성

의존성 없이는 객체망이 생겨나지 않을 것.

적절하게 관리한다?

`적절하게 관리한다` 라는 말 따위는 없다-

양방향의 고리를 끊는 것은 중요하다.

## 템플릿 메소드 패턴

DiscountPolicy class에서 

```java
abstract class DiscountPolicy {
  private Set<DiscountCondition> conditions = new HashSet<>();
  public void addCondition(DiscountCondition condition {
    conditions.add(condition);
  }                          
  public Money calculateFee(Screening screening, int count, Money fee){
    	for(DiscountCondition condition:conditions){
        if(condition.isSatisfiedBy(screening, count)) 
          return calculateFee(fee);
	}
    return fee; 
  }
  protected abstract Money calculateFee(Money fee); 
}
```



객체지향에서 부모가 자식을 아는 것이 다운캐스팅인데, `이는 다운캐스팅은 좋지 못한 것이다.`

우리는 리스코프 치환원칙을 지키기 위해서는늘 `업캐스팅` 만 존재해야 한다. 



자식이 부모를 가르키고, 건들이게되고 의존하게된다. 이게 근본적으로 잘 못 된 것이다.

만약, 부모를 건들이면 영향을 받는 곳이 여러 곳이다. 의존성이 엄청 퍼진다. 겁나서 우리는 코드를 고칠 수 없다.



위 코드에서 보면 부모가 자식을 알고 있는데, 어떻게 리스코프치환원칙을 지킬 수 있었을까? 

`return calculateFee(fee);` 이 코드가 그 증거이다.



부모의 의존성 방향이 반대가 된다. 부모가 자식이 미래에 구현하게될 부분(calculateFee)을 fix하게 된다.

이것이 바로 템플릿 메소드의 장점이다.

***부모의 변화가 자식의 변화를 일으키지 않는 것이 좋은 것이다.***

![image-20191012134900863](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vblx493jj31a80ikq6x.jpg)



`Hook`을 사용하는 것이 바로 템플릿 메소드 패턴

`AmountPocliy` 를 보면 확정된 프로토콜을 통해 결정.

![image-20191012135141342](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vbon8uhjj319y0iqtbl.jpg)

상속을 쓰는 것이 나쁜것이 아니지만, 케스케이딩 할 수 있는 상황을 고려해야 한다.



***부모가 자식을 사용할 때 하나하나 인식할 수는 없으니까 프로토콜로 인식한다.***

= 단일 의존 포인트



 ***의존성의 무게는 의존하는(아는얘가) 많을 수록 고치기 힘들어진다는 것이 상속이라면, 그 반대로 의존을 역전시켜 해결한다.***



9,10,11장 모두 상속에 대한 이야기.



## 위 똑같은 코드를 전략패턴으로 바꾸면?

어느정도 안정화된다면 전략 패턴으로 

```java
public class DiscountPolicy {
	private final Set<DiscountCondition> conditions = new HashSet<>();
💡private final Calculator calculator;💡
	public DiscountPolicy(Calculator calculator){
    this.calculator = calculator;
  } 
  public void addCondition(DiscountCondition condition { 
    conditions.add(condition);
  } 
  public Money calculateFee(Screening screening, int count, Money fee){
    for(DiscountCondition condition:conditions){
      if(condition.isSatisfiedBy(screening, count)) 
        return;
    }
    return fee; 
  }
}
```



전략패턴은 합성을 사용한다.   
더 이상 상속을 받지 않는 단일 클래스로 변경 된다.



상속을 사용하지 않고 주입을 사용한다.

![image-20191012140118726](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vbyue5b6j319q0i4wiz.jpg)



전략패턴은 부모처럼 쓰지말고 `인터페이스처럼` 쓰라는 말이다.

![image-20191012140938965](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vc7cbhljj319s0i2ad9.jpg)

부모를 짤 때 전략이나 템플릿 메서드 패턴으로 변경해보면 안다. 잘 바꿀 수 있다면 잘 만든 것이다.



그래서.

![image-20191012141145487](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vc9jb220j31aa0ikq6n.jpg)

 의존성 관계를 해결하는데, 전략 패턴의 경우 Calculator가 완충역할을 하는 반면에서 템플릿메서드 패턴의 경우에는 의존성 뱡향을 변경한다.



중간에 Calculator 가 있기 때문에, 변화가 많이 일어날 경우 도움이 될 수 있다. 그러나 Calculatoreh 무거워 질 수 있다. 



# 이제 이 두 개를 비교해보자.

![image-20191012141713494](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vcfbqvp7j317c0icacv.jpg)





그러나 여기에도 문제가 존재하게 되는데, 조합폭발이 일어나는 만큼, 그만큼 class를 만들어야 한다. 감당이 안될 수 있다.구제불능!   

*그러나 전략패턴은 의존성 폭발이 일어날 수 있다.* 

![image-20191012142229286](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vckoxthfj319y0jc0wz.jpg)



템플릿 패턴에서 발생하는 조합폭발은 구제불가 하지만 전략 패턴에서의 의존성 폭발은 해결 가능하다.

***세상에서 가장 좋은 템플릿 메소드의 훅은 하나만 존재하는 것이 가장 좋은 것이다.***

전략패턴은 Hook 대신, 두 개의 전략 객체를 가진다. 

---

# 생성사용패턴과 팩토리

`만들어내는 위한 코드`와 `사용하는 코드`를 병행해서 쓰지 말라. **그러면 관리하기 힘든다.**

생명주기도 틀려진다.

코드가 변화에 따라 달라지게 하고싶다.



생성사용패턴은 

![image-20191012143022438](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vcswfe9fj314u0ik75l.jpg)



어떻게?

![image-20191012143038665](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vct5p7fyj30x20is0u4.jpg)



클라이언트 생성코드를 사용코드에 주입시킨다.

![image-20191012143051034](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vcteopwuj30x80ikabm.jpg)

 `얼마나- 클라이언트에서 생성쪽 코드를 밀어냈냐?`에 따라 코드의 실력이 나눠진다. 

### 직접 해보면 어렵다.

 생성 사용패턴은 떡진 알고리즘의 일부를 형으로 바꿔서 생성해서 이용하는 코드로 분리하는 것부터 시작한다.

어떤 코드를 `역할을 생성하는 코드`와 `이용하는 코드`로 나눠서 `이용하는 코드`를 클라이언트로 밀어 낸다.



### 그럼 `Inject`을 조금 더 생각해보자.

![image-20191012143537060](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vcycok3tj319w0i2n17.jpg)



**Injection은 과연 좋은 걸까?**

*한 가지 예로 ''시도때도 없이 쑤셔넣으면 좋은걸까?' 주입당하는 행위를 당하고 있기 때문에 좋다고 말할 수 없다.*

![image-20191012143648203](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vczl0zi3j31a60istde.jpg)

pushed가 아닌 pull을 하고 싶다.

내가 필요할 때 가져와서 사용하고 싶다면?

이런 제어권을 바꾸기 위해서 Factory를 만들 수 있다.

![image-20191012143811594](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vd11d8mtj31a80ikdjv.jpg)

위와 같이 만들 수 있는데, Factory 클래스는 전체를 준 게 아니라,  `마이크` 와 같이 일부분을 준 것과 같다.



사실 Push와 Pull은 같은 것이다. Pull을 먼저하든, Push를 먼저 하든 동일하다.

![image-20191012144045141](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vd3p9b2ij31a60is79p.jpg)

드디어 원할 때 Lazy하게 Pull을 받을 수 있게 코드를 수정했다.



***Factory를 사용하는 것이 의존성을 역전하는 것은 아니지만, 사용성을 달라지게 할 수 있다.***



그러나 여기서는 디미터의 법칙을 위반할 수 밖에 없다.

![image-20191012144348922](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vd6wk1maj31a20ng44t.jpg)

그리고, Factory 패턴은 무조건 디미터법칙을 위반할 수 밖에 없다.



그러므로, 우리에게 2가지 선택사항이 있다. 

1. factory와 calculator를 알게
2. factory만 알게.



현재 아래와 같이 순환참조가 발생했다.

![image-20191012145428407](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vdhzc7egj31a20iugs4.jpg)



 이 문제를 해결하기 위해서는 아래와 같아야하는데, 어떻게?

![image-20191012144559043](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vd94s4sfj31a80kgdm9.jpg)

calculatorFee까지 위임한다면? 문제는 해결할 수 있다.



위임된 팩토리의 정체는 구상 CalculatorFactory 그 자체구나.

원래 그 인터페이스를 팩토리로 구현할수 있다. 



코드는 똑같지만 인터페이스가 달라진다.

팩토리를 만든건지 못만든건지 헷갈리기 시작한다.



위임된 팩토리는 위임된 부분이 보이지 않는다.

![image-20191012145820589](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vdlzxbh1j31a60iw0xn.jpg)



![image-20191012145834199](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vdm7ronwj31am0iywja.jpg)



![image-20191012145847423](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vdmkvic7j31ac0iogqe.jpg)





![image-20191012145933029](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vdn9odnyj31a00jw79c.jpg)



이랬던 참조가 



![image-20191012150020162](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vdo5cvjoj31ae0js79m.jpg)



이렇게  된다.



## 추상 팩토리 메서드 패턴

![image-20191012150519531](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vdta172nj31ae0pob09.jpg)



이제  DiscountCondition으로 부터 가져올 거니까 윗 부분을 삭제.



![image-20191012150601975](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vdu0tud4j319y0iktcg.jpg)



인터페이스로 바꿔서 바깥에서 생성하게 만드는 것이다.

![image-20191012151015230](https://tva1.sinaimg.cn/large/006y8mN6gy1g7vdyedbj3j319u0ledne.jpg)



의존성 화살표를 줄이기 위해서 추상 팩토리메서드 패턴을 사용한다. 

![image-20191012151320441](https://tva1.sinaimg.cn/large/006y8mN6gy1g7ve1ori18j319w0im428.jpg)



![image-20191012151417542](https://tva1.sinaimg.cn/large/006y8mN6gy1g7ve2on9pgj319y0jcq78.jpg)



그럼 어떻게 해야될까? 아래 부분까지 위임해야 한다. 

![image-20191012151506038](https://tva1.sinaimg.cn/large/006y8mN6gy1g7ve3i7wosj31a20io429.jpg)





아래와 같이 default 메서드로 만든다.

![image-20191012151527332](https://tva1.sinaimg.cn/large/006y8mN6gy1g7ve3scclcj31a40iotbr.jpg)



조합폭발을 팩토리에 밀어냄으로써 조합폭발을 해결한다. 진정한 의미에서 사용코드만 남는 것이다.



**아키텍처상 가장 먼저 고려해야될 부분은 어떤 부분을 보호해야 되는지에 대한 부분을 집중적으로 생각해야 한다.**

설계요령은 변화하지 않는 얘를 기준으로 설정해야 한다. 마법은 없다. 



우리는 `Point of Point`를 활용하기 위해서 객체 지향을 활용한다.

변하지 않는 부분을 확정한다.

![image-20191012152155394](https://tva1.sinaimg.cn/large/006y8mN6gy1g7veajk9grj319o0ki43r.jpg)



(끝 부분을 다시한번 집중해서 들을 필요가 있음.)