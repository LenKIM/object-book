# Type

변수가 무엇일까?  
: 메모리 주소의 별명.

데이터 타입은 무엇일까?  
: 얼만큼의 데이터의 길이를 차지할까?



***객체지향에서 타입은 굉장히 중요한 역할을 한다.***

![image-20191124154829717](https://tva1.sinaimg.cn/large/006y8mN6gy1g994pharfzj31fh0u0dkz.jpg)



객체지향에서는 Type으로 나타낼수 없으면 사용 할 수 없다. 그만큼 Type은 중요하다.



**Type은 어떻게 활용 될 수 있을까?**

- Role - 형을 통해 역할을 묘사
- Responsiblity - 형을 통해 로직을 표현

- Message - 형을 통해 메세지를 공유
- Protocol - 양자 객체 간 계약을 형을 통해 공유함 (swift에서는 객체가 아닌 프로토콜이라는 내용이 나옴)



값은 불변, Type은 불변이 아니다.



그렇다면, JVM 기반으로 도용 가능한 타입 3가지

![image-20191124155425196](https://tva1.sinaimg.cn/large/006y8mN6gy1g994vlgcc9j31p40u0ju0.jpg)



- 단 1개의 intance - **static**

그러나, 주의해야 될 점은 static은 쓰레드 세이프 하지 않기 때문에, 동시성 문제에 대해서 주의깊게 생각해야 한다.

어려운 문제다. 코드를 봐도 싱크로나이즈드로 떡칠되어 있다.

- enum 

enum에 기술되어 있는 이름을 사전에 명시된 인스턴스화로 만든 후에 진행된다. 이는 동시성 문제가 확보되어 있다. 생성에 대한 동시성 문제를 해결해준다.

- class

몃 개의 인스턴스를 만들어야 하는지 모를 때는 class를 쓴다.

![image-20191124155800419](https://tva1.sinaimg.cn/large/006y8mN6gy1g994zc6v74j31ro0u079i.jpg)



enum은 대체로 형을 대체로 쓸만한 도구가 아니다. 주력으로 class나 interface를 사용한다.

### static은 우리의 적이다. 왠만하면 쓰지 않아야 한다.

- 팩토리나 유틸리티인 경우에만 사용하도록 하자.



이책에서는 메서드에 대한 정의를 4개로 나눈다.



> tip,
> **유틸리티 함수와 메서드의 함수를 구분하는 방법은?**
>
> 해당 함수안에 this 또는 self가 없다면 이 함수는 유틸리티 함수이다.
>
> 어떤 Context를 쓰고 있다면 메서드 함수.



# Condition (조건)

![image-20191124160252114](https://tva1.sinaimg.cn/large/006y8mN6gy1g9954ef4h2j31p30u0ahl.jpg)

: 특정 상태에 대한 분기를 할 때 Condition을 사용.



그러나 슬픈 사실은 우리 코드에 `if`문을 제거할 수는 없다.

2단계까지는 약 60%정도의 사람들이 감당하고, 그외는 감당할 수 없다. 경우의 수를 까먹게 된다.



조건 분기에 대한 전략은 딱 두가지 뿐

- 내부에서 응집성있게 모아두는 방식


  
  ![image-20191124161053324](https://tva1.sinaimg.cn/large/006y8mN6gy1g995csbgkvj31070u00yw.jpg)

- 외부에 분기를 위임하고 경우의 수 만큼 처리기를 만드는 방식  
  : 가장 중요한 방식이고 디자인 패턴에 나오는 거의 모든 얘기이다.  
  ![image-20191124161114249](https://tva1.sinaimg.cn/large/006y8mN6gy1g995d3oyr9j31gm0u012h.jpg)  



**객체지향에서는 항상 후자를 달성시키고자 한다.**



복잡함을 해결해서 외부에서 Injection해준다.



# 책임 기반의 개발

![image-20191124161547280](https://tva1.sinaimg.cn/large/006y8mN6gy1g995hul7eaj31xu0u041p.jpg)





![image-20191124162244059](https://tva1.sinaimg.cn/large/006y8mN6gy1g995p2ezkkj31df0u00yz.jpg)



- **가치가 무엇인가에 대해서 고민해봐야 한다.**
- 영화예매를 예로 들으면, 책임이라는 건 그 가치 돈으로 환원될 수 있는 부분이라는 것에 동의되어야 한다.
- 시스템 차원의 책임을 더 작은 단위의 책임으로 분할 해야 한다.
- 해당 책임을 추상화하여 역할을 정의한다.
- 역할에 따라 협력이 정의된다.



![image-20191124163020979](https://tva1.sinaimg.cn/large/006y8mN6gy1g995x074zmj31020m6tu9.jpg)



`Runnable` 에 집중하자. 해당 `Runnable` 라고 추상화에 성공했기 때문에 역할에 따라 협력이 정의되어 진다.



케이스의 공통점을 찾아내야지만, 우리는 추상화를 시켜서 동작시킬 수 있다.



***추상화과정은 위로 올라기도 하지만, 반대로 아래로 내려가기도 한다.***



>  회사에서 돌아가는 비지니스 로직을 좋아하냐, 좋아하지 않냐에 따라 회사의 충성심이 결정된다.



이제 코드로 가보자.

![image-20191124163632027](https://tva1.sinaimg.cn/large/006y8mN6gy1g9963fk3zvj311q0igmy7.jpg)

![image-20191124163642644](https://tva1.sinaimg.cn/large/006y8mN6gy1g9963la4saj31920iita2.jpg)



**책에서는 Screening이 정보전문가패턴을 따르고 있다.**

*정보 전문가 패턴

- 상태와 가장 관련된 업무를 가진 객체에게 역할을 위임하는 것.

- 이 시스템내에서 가장 많이 알고 있는 얘한테 시키는 것



**타입시스템에서 어떻게 객체간에 분기를 나눌 수 있을까?**



```java
Movie movie = new Movie<AmountDiscount>(
                "spiderman",
                Duration.ofMinutes(120L),
                Money.of(5000.0),
                new SequenceAmountDiscount(Money.of(1000.0), 1),
                new SequenceAmountDiscount(Money.of(1000.0), 1),
                new SequenceAmountDiscount(Money.of(1000.0), 1)
        );
// 값으로 인식하면 안되고 subtype으로 갖는것
```



제네릭은 자바에서 하위형을 넣을 수 있다.



Main.java 코드를 참조하자.