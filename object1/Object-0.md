# 첫번째 - Object

왜 코딩을 하는가?

코딩이라는 학문차원에서 왜 그렇게 코딩을 해야되는가?

## 0. 이론

***합리적인 사고를 통해서 합의를 보거나, 상대적인 위치에서 합의를 보려고 한다.***

합리주의 / 상대주의

유연한 사고를 가지는 것이 중요하다.





## Value - 캔트백이 말하는 가치

*코딩할 때는 다음과 같은 가치를 가져야 한다.*

Communication - 커뮤니케이션이 잘되야 한다.

Simplicity - 되도록이면 간단하게 짜야 한다. 

Flexibility - 유연하게 짜야 한다. 

## Principle

*원칙을 벗어난 것은 금방 발견할 수 있기 때문에 원칙이 존재하는 것이다.*

그리고 그 원칙은 여러 Layer를 통해서 생겨난다. 캔트박이 말하는 원칙

**Local consequences** - 변수의 생명주기를 짫게 가져야한다.

**Minimize repetition** - 중복을 최소화 해야되 / 중복은 반복이 아니라 발견해야 한다.

**Symmetry** - 짝을 맞쳐라- Get/Set 맞추 듯, 되도록이면 짝을 맞쳐야 한다.

**Convention** - 컨벤션을 지켜라- 

##Xoriented 

OOP : SOLID, DRY.. Reactive / Functional / ..

위 유형을 반복하다보면 **Pattern**이 만들어진다.



코드를 작성하는건 20% 밖에 안되고 ***50%는 디버깅하는데 사용된다.***

![image-20190816205215694](http://ww4.sinaimg.cn/large/006tNc79gy1g61rirzypwj316i0oi0xh.jpg)

## 01. Abstraction - 추상화

일반화 - modeling, function, algorithm

연관화 - reference, dependence

집단화 - group, category



**Data Ab** - 동작이 없고, 행위가 없다. 

- Modeling - 특정 목적에 맞쳐, 추려진 내용으로 가득한 것을 말한다.
- Categorization - 카테고리를 잘 만들어서 인식하게 하는 것.
- Grouping - 그냥 모아 둔 것.

**Procedural Ab** - 프로시저? 절차적? **아니다!**  메소드가 아닌 함수! **어떤 함수에게 어떤 데이터를 넘기고 처리를 맡긴다.** 

- Generalization
- Capsulization

**OOP Ab**

- Generalization
- Realization
- Dependency
- Association
- Directed Association
- Aggregation
- Composition



---

## 02. OOP base System

값과 식별자

**객체의 식별자로 비교하는게 자바에서의 .equal()** 

**객체의 값을 비교하는게 자바에서는 ==**

== 값 비교 

=== 식별자 비교

![image-20190816211334979](http://ww2.sinaimg.cn/large/006tNc79gy1g61s4wd54sj315c0he0vk.jpg)

​	

***늘 값과 식별자를 판단하는 자세를 가져야 한다.***



### Polymorphism

OOP를 잘 짜기위해서는 다형성이 제공되어야 한다.
아래 두 가지를 충족해서 Polymorphism이라고 할 수 있다.

**Subsition - 대체 가능성**

**Interal identity - 내적동질설**

다형성을 하면 Point of Point를 사용하기 때문에 느릴 수 밖에 없다. 다형성은 공짜가 아니다.

![image-20190816212004737](http://ww3.sinaimg.cn/large/006tNc79gy1g61sbn9yuyj30x20na438.jpg)

***입구가 여러개 만들어 진것이라고 생각하면 된다.***



3번째 줄에서 worker는 무슨 Run을 말하는 걸까? (Worker, Runnable, HardWorker 총 3개의 인스턴스를 갖는다.)

바로 `HardWorking` 왜냐하면, OOP시스템에서 정의한 내적동질성을 가리킨다.



### Object

Encapsulation of Functionality - 기능의 캡슐화

Maintenace of State - 상태를 관리 ( 은닉 ) - 남에게 안보여주고 나만 써야해-

***우리가 이런것을 하는 이유는 바로 격리를 하기 위해서***

## Isolation 을 위해서-

*어떤 안건을 처리했을 때 격리가 되었는지 확인하려면, 다른 파일을 건들였는가?아닌가? 를 판단하면 된다.*

---

# Theater

![image-20190816213038404](http://ww2.sinaimg.cn/large/006tNc79gy1g61smo5iuhj315m0m6jsn.jpg)



역할 모델을 바꾸는 행위를 할 것이다.



이게 괜찮아 보이지만,

![image-20190816213219145](http://ww4.sinaimg.cn/large/006tNc79gy1g61sodrzcfj314c0m675j.jpg)



사실 엉망이다.



그래서 이렇게 바꿔보자.

![image-20190816213352262](http://ww1.sinaimg.cn/large/006tNc79gy1g61sq10jwxj316i0nujsn.jpg)



Theater는 당연히 티켓을 알고 있겠고, 티켓오피스는 티켓을 알고 있고, 인비테이션은 극장이 만들어내야 하고-



요 그림대로 코딩하자.



누가 누구한테 주는 지 항상 염두하면서 코딩하라-



***어떤걸 짜야될지 모르겠다면, 먼저 Main부터 짜보는거 어떠할까?***

TDD 를 짜는 것과 비슷하게 클라이언트와 비슷하게 짜보는게 좋다.



