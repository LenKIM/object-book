# 유연한 설계

## 다양한 의존성 관리 기법을 원칙이라는 관점에서 정리해보자.



# 01. 개방-폐쇄 원칙

확장 가능하고 변화에 유연하게 대응할 수 있는 설게를 만들 수 있는 원칙 중 하나



### 컴파일타임 의존성을 고정시키고 런타임 의존성을 변경하라.

- 런타임 의존성은 실행시에 협력에 참여하는 객체들 사이의 관계.

- 컴파일타임 의존성은 코드에서 드러나는 클래스들 사이의 관계.



**컴파일타임과 런타임 의존성은 같지 않다.**



***개방-폐쇄 원칙을 수용하는 코드는 컴파일타임 의존성을 수정하지 않고도 런타임 의존성을 쉽게 변경할 수 있다.***



### 추상화가 핵심이다.

개발-폐쇄 원칙의 핵심은 **추상화에 의존하는 것**

여기서 `추상화`와 `의존` 이라는 두 개념 모두가 중요하다.



추상화란 핵심적인 부분만 남기고 불필요한 부분은 생략함으로써 복잡성을 극복하는 기법이므로, 개방-폐쇄 원칙의 관점에서 추상화가 잘된 코드는 생략되지 않고 남겨지는 부분은 다양하 상황에서의 공통점을 반영한 추상화의 결과물이다. 



- 추상화는 확장을 가능하게 하고 추상화에 대한 의존은 폐쇄를 가능하게 한다. 
- 명시적 의존성과 의존성 해결 방법을 통해 컴파일타임 의존성을 런타임 의존성으로 대체함으로써 실행 시에 객체의 행동을 확장할 수 있다. 
- 그러나, 추상화를 했다고 해서 모든 수정에 대해 설계가 폐쇄되는 것은 아니라는 것이다. 수정에 대해 닫혀 있고 확장에 대해 열려 있는 설계는 공짜로 얻어지지 않는다. 변경에 의한 파급효과를 피하기 위해서는 변하는 것과 변하지 않는 것이 무엇인지를 이해하고 이를 추상화의 목적으로 삼아야만 한다. 추상화가 수정에 대해 닫혀 있을 수있는 이유는 변경되지 않을 부분을 신중하게 결정하고 올바른 추상화를 주의 깊게 선택했기 떄문이라는 사실을 기억하라.



# 02. 생성 사용 분리

어느 코드든지, 객체 생성을 피할 수는 없다. 어딘가에서는 반드시 객체를 생성해야 한다. 문제는 객체 생성이 아니다. 부적절한 곳에서 객체를 생성한다는 것이 문제다. 

```java
public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = new AmountDiscountPolicy(...);
    }

    public Money getFee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
```

위 코드의 문제점은 한 클래스내에서 생성과 사용이 공존한다는 사실에 입각해 발생하는 문제이다.



유연하고 재사용 가능한 설계를 원한다면 객체와 관련된 두 가지 책임을 서로 다른 객체로 분리해야 한다. 하나는 객체를 생성하는 것이고, 다른 하나는 객체를 사용하는 것이다.



*한 마디로 말해서 객체에 대한 **생성과 사용을 분리(separation use from creation)***



그렇다면, 사용으로부터 생성을 분리하는 데 사용되는 가장 보편적인 방법은 객체를 생성할 책임을 클라이언트로 옮기는 것이다.

클라이언트로 어떻게 옮길수 있는가?



### FACTORY 추가해서 분리하기.

오직 객체 생성에 특화된 객체를 FACTORY라고 부른다.

```java
public class Client {
    private Factory factory;

    public Client(Factory factory) {
        this.factory = factory;
    }

    public Money getAvatarFee() {
        Movie avatar = factory.createAvatarMovie();
        return avatar.getFee();
    }
}
```

```java
public class Factory {
    public Movie createAvatarMovie() {
        return new Movie("아바타",
                Duration.ofMinutes(120),
                Money.wons(10000),
                new AmountDiscountPolicy(
                    Money.wons(800),
                    new SequenceCondition(1),
                    new SequenceCondition(10)));
    }
}
```

오직 Client class는 생성과 사용에 초점을 맞쳐져 있지만, 그중에 Factory 클래스가 생성을 담당하게 된다.

# PURE FABRICATION 패턴 (순수한 가공물에게 책임 할당하기)

책임을 할당하기 위해 창조되는 도메인과 무관한 인공적인 객체를 PURE FABRICATION이라 한다. 어떤 행동을 추가하려고 하는데 이 행동을 칙임질 마땅한 도메인 개념이 존재하지 않는다면 위 코드중 FACTORY와 같은 객체를 만들고, 그 객체에 책임을 할당한다.

그것이, 바로 PURE FABRICATION 이다.



***먼저 도메인의 본질적인 개념을 표현하는 추상화를 이용해 애플리케이션을 구축하기 시작하자. 만약 도메인이 개념이 만족스럽지 못한다면 주저하지 말고 인공적인 객체를 생성하라. 객체지향이 실세계를 모방헤야 한다는 헛된 주장에 현혹될 필요가 없다. 우리가 애플리케이션을 구축하는 것은 사용자들이 원하는 기능을 제공하기 위해서지 실세계를 모방하거나 시뮬레이션하기 위한 것이 아니다. 도메인을 반영하는 애플리케이션의 구조라는 제약 안에서 실용적인 창조성을 발휘할 수 있는 능력은 휼륭한 설계자가 갖춰야 할 기본적인 자질.***



# 03. 의존성 주입

- 생성과 사용을 분리하면 Movie에는 오로지 인스턴스를 사용하는 책임만 남게 된다. 이것은 외부의 다른 객체가 Movie에게 생성된 인스턴스를 전달해야 한다는 것을 의미.
- 사용하는 객체가 아닌 외부의 독립적인 객체가 인스턴스를 생성한 후 이를 전달해서 의존성을 해결하는 방법이 바로 의존성 주입



의존성을 해결하는 세 가지 방법을 가리키는 용어

- 생성자 주입 - 객체를 생성하는 시점에 생성자를 통한 의존성 해결
- setter 주입 - 객체 생성 후 setter 메서드를 통한 의존성 해결
- 메서드 주입 - 메서드 실행 시 인자를 이용한 의존성 해결



그 외에도 의존성을 해결할 수 있는 방법중 널리 사용되는 대표적인 방법은 **SERVICE LOCATOR 패턴**

### 숨겨진 의존성은 나쁘다.

```java
public class ServiceLocator {
    private static ServiceLocator soleInstance = new ServiceLocator();
    private DiscountPolicy discountPolicy;

    public static DiscountPolicy discountPolicy() {
        return soleInstance.discountPolicy;
    }

    public static void provide(DiscountPolicy discountPolicy) {
        soleInstance.discountPolicy = discountPolicy;
    }

    private ServiceLocator() {
    }
}
```

```java
public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = ServiceLocator.discountPolicy();
    }

    public Money getFee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
```



위 Serivce Locator 패턴의 가장 큰 단점은 위존성을 감춘다는 것이다. Movcie는 DiscountPolicy에 의존하고 있지만 Movie의 퍼블릭 인터페이스 어디에도 이 의존성에 대한 정보가 표시돼 있지 않다. 의존성은 암시적이며 코드 깊숙한 곳에 숨겨져 있다.



만약,

```java
Movie avatar = new Movie("아바타",
                        Duration.ofMinutes(120),
                        Moneys.wons(100000));
```

이라고 한다면 널포인트 익셉션이 날 것. 사용하는 측에서는 온전히 만들어질것이라 기대하지만,

```java
ServiceLocator.provider(new PercentDiscountPolicy(...));
```

의존성을 주입해주지 않으면, 해결되지 않을 것이다. 



숨겨진 의존성이 이해하기 어렵고 디버깅하기 어려운 이유는 문제점을 발견할 수 있는 시점을 코드 작성 시점이 아니라 실행 시점으로 미루기 때문이다.



# 04. 의존성 역전 원칙



***추상화를 통해서 의존성을 역전시키자.***

왜 마틴파울러는 역전이라는 표현을 썼는가?  전통적인 소프트웨어 개발 방법에서는 대부분 하위 모듈에 의존하는 경향이 있기 떄문이다.



이 부분은 **추상 템플릿메서드 패턴**을 참조하면 이해하기 쉽다.

유연하고 재사용 가능하며 컨테스트에 독립적인 설계는 전통적인 패러다임이 고수하는 의존성의 방향을 역전시킨다. 전통적인 패러다임에서는 상위 수준 모듈이 하위 수준 모듈에 의존했다면 객체지향 패러다임에서는 상위 수준 모듈과 하위 수준 모듈이 모두 추상화에 의존한다.



결과

# 05. 유연성에 대한 조언

## 유연한 설계는 유연성이 필요할 때만 옳다.

 유연하고 재사용 가능한 설계란 런타임 의존성과 컴파일타임 의존성의 차이를 인식하고 동일한 컴파일 타임 의존성으로부터 다양한 런타임 의존성을 만들 수 있는 코드 구주로를 가지는 설계를 의미한다.

**유연한 설계라는 말의 이면에는 복잡한 설계라는 의미가 숨어있다.** 유연한 설계의 이런 양면성은 객관적으로 설계를 판다하기 어렵게 만든다. 이 설계가 복잡한 이유는 무엇인가? 어떤 변경에 대비하기 위해 설계를 복잡하기 만들었는가? 정말 유연성이 필요한가? 정보가 제한적인 상황에서 이런 질문에 대답하는 것은 공학이라기보다는 심리학에 가깝다.변경은 예상이 아니라 현실이어야 한다.

**유연성은 항상 복잡성을 수반한다.** 유연하지 않은 설계는 단순하고 명확하다. 유연한 설계는 복잡하고 암시적이다. 

설계가 유연할수록 클래스 구조와 객체 구조 사이의 거리는 점점 멀어진다. 따라서 유연함은 단순성과 명확성의 희생 위에서 자라난다.

불필요한 유연성은 불필요한 복잡성을 낳는다. 단순하고 명확한 해법이 그런대로 만족스럽다면 유연성을 제거하라.

## 협력과 책임이 중요하다.

이 책에서 늘 말하는 것이 다시한번 반복됐다. 객체의 협력과 책임이 중요하다는 것. 지금까지 클래스를 중심으로 구현 메커니즘 관점에서 의존성을 설명했지만 설계를 유연하게 만들기 위해서는 협력에 참여하는 객체가 다른 객체에게 어떤 메시지를 전송하는지가 중요하다.

