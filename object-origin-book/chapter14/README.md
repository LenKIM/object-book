 # 일관성 있는 협력



객체는 협력을 위해 존재한다. 협력은 객체가 사용하는 이유와 문맥을 제공한다. 잘 설계된 애플리케이션은 이해하기 쉽고, 수정이 용이하며, 재사용 가능한 협력의 모임이다. 객체지향 설계의 목표는 적절한 책임을 수행하는 객체들의 협력을 기반으로 결합도가 낮고 재사용 가능한 코드 구조를 창조하는 것.



객체지향 패러다임이 장점은 설계를 재사용할 수 있다는 것. 하지만 재사용은 공짜로 얻어지지 않는다. 재사용을 위해서는 객체들의 협력 방식을 일관성 있게 만들어야 한다. 일관성은 설계에 드는 비용을 감소 시킨다.



***가능하면 유사한 기능을 구현하기 위해 유사한 협력 패턴을 사용하라.***

객체들의 협력이 전체적으로 일관성 있는 유사한 패턴을 따른다면 시스템을 이해하고 확장하기 위해 요구되는 정신적은 부담을 크게 줄일 수 있다. 지금 보고 있는 코드가 얼마 전에 봤던 코드와 유사하다는 사실을 아는 순간 새로운 코드가 직관적인 모습으로 다가오는 것을 느끼게 될 것이다. 유사한 기능을 구현하기 위해 유사한 협력 방식을 따를 경우 코드를 이해하기 위해 필요한 것은 약간의 기억력과 적응력뿐이다.



일관성 있는 협력 패턴을 적용하면 여러분의 코드가 이해하기 쉽고 직관적이며 유연해진다는 것.



13장에서 했던 핸드폰 과금 시스템을 돌아가보자.



**기본 정책 확장**

총 4가지로 확장됨 - 고정요금 방식 / 시간대별 방식 / 요일별 방식 / 구간별 방식

기본 정책 확장

고정요금 방식 -  `FixedFeePolicy.java`

시간대별 방식 - `TimeOfDayDiscountPolicy.java`

요일별 방식 - `DayOfWeekDiscountPolicy.java`

구간별 방식 - `DurationDiscountPolicy.java`



시간대별 방식(TimeOfDatDiscountPolicy)을 구현하는 과정에서 핵심은 규칙에 따라 통화 시간을 분할하는 방법을 결정하는 것. 이를 위해 기간을 편하게 관리할 수 있는 `DateTimeInterval ` 클래스를 추가. `DateTimeInterval` 은 시작 시간과 종료 시간을 인스턴스 변수로 포함하며, 객체 생성을 위한 정적 메서드인 `of`, `toMidnight`, `fromMidnight`, `during` 제공.



```java
import java.time.*;
import java.util.*;

public class DateTimeInterval {
    private LocalDateTime from;
    private LocalDateTime to;

    public static DateTimeInterval of(LocalDateTime from, LocalDateTime to) {
        return new DateTimeInterval(from, to);
    }

    public static DateTimeInterval toMidnight(LocalDateTime from) {
        return new DateTimeInterval(from, LocalDateTime.of(from.toLocalDate(), LocalTime.of(23, 59, 59)));
    }

    public static DateTimeInterval fromMidnight(LocalDateTime to) {
        return new DateTimeInterval(LocalDateTime.of(to.toLocalDate(), LocalTime.of(0, 0)), to);
    }

    public static DateTimeInterval during(LocalDate date) {
        return new DateTimeInterval(LocalDateTime.of(date, LocalTime.of(0, 0)),
                LocalDateTime.of(date, LocalTime.of(23, 59, 59)));
    }

    private DateTimeInterval(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public Duration duration() {
        return Duration.between(from, to);
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public List<DateTimeInterval> splitByDay() {
        if (days() > 1) {
            return split(days());
        }

        return Arrays.asList(this);
    }

    private int days() {
        return Period.between(from.toLocalDate(), to.toLocalDate()).plusDays(1).getDays();
    }

    private List<DateTimeInterval> split(int days) {
        List<DateTimeInterval> result = new ArrayList<>();
        addFirstDay(result);
        addMiddleDays(result, days);
        addLastDay(result);
        return result;
    }

    private void addFirstDay(List<DateTimeInterval> result) {
        result.add(DateTimeInterval.toMidnight(from));
    }

    private void addMiddleDays(List<DateTimeInterval> result, int days) {
        for(int loop=1; loop < days; loop++) {
            result.add(DateTimeInterval.during(from.toLocalDate().plusDays(loop)));
        }
    }

    private void addLastDay(List<DateTimeInterval> result) {
        result.add(DateTimeInterval.fromMidnight(to));
    }

    public String toString() {
        return "[ " + from + " - " + to + " ]";
    }
}
```





## 시간대별 방식

: 구현을 4개의 리스트로 구현.

`private List<LocalTime> starts = new ArrayList<>();`

`private List<LocalTime> ends = new ArrayList<>();`

`private List<Duration> durations = new ArrayList<>();`

`private List<Money> amounts = new ArrayList<>();`



## 요일별 방식 구현하기.

: 요일별로 요금 규칙이 다를테니, 각 규칙은 요일의 목록, 단위 시간, 단위 요금이라는 세 가지 요소로 구성. 요일별 방식을 사용하면 월요일부터 금요일까지 10초당 38원을, 토요일과 일요일에는 10초당 19원을 부과하는 식으로 요금 정책 설정.



시간대별 방식이 4개의 리스트라면, 요일병 방식은 `DayOfWeekDiscountRule`이라는 하나의 클래스로 구현하는 것이 더 나은 설계라고 판단.

```java
public class DayOfWeekDiscountPolicy extends BasicRatePolicy {
    private List<DayOfWeekDiscountRule> rules = new ArrayList<>();

    public DayOfWeekDiscountPolicy(List<DayOfWeekDiscountRule> rules) {
        this.rules = rules;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        Money result = Money.ZERO;
        for(DateTimeInterval interval : call.getInterval().splitByDay()) {
            for(DayOfWeekDiscountRule rule: rules) { result.plus(rule.calculate(interval));
            }
        }
        return result;
    }
}
```



```java
public class DayOfWeekDiscountRule {
    private List<DayOfWeek> dayOfWeeks = new ArrayList<>();
    private Duration duration = Duration.ZERO;
    private Money amount = Money.ZERO;

    public DayOfWeekDiscountRule(List<DayOfWeek> dayOfWeeks,
                                 Duration duration, Money  amount) {
        this.dayOfWeeks = dayOfWeeks;
        this.duration = duration;
        this.amount = amount;
    }

    public Money calculate(DateTimeInterval interval) {
        if (dayOfWeeks.contains(interval.getFrom().getDayOfWeek())) {
            return amount.times(interval.duration().getSeconds() / duration.getSeconds());
        }
        return Money.ZERO;
    }
}
```



## 구간별 방식 구현

지금까지의 개발을 다시 살펴보자. 고정요금 방식, 시간대별 방식, 요일별 방식의 구현 클래스에 대해서 서로 다른 방식으로 구현된 것을 알 수 있다. **이 말은 즉, 설계의 일관성이 없다는 것.**



일관성이 없다는 것은 가독성이 떨어진다는 점과 동시에 그 다음 기능을 개발할 때 연관성 없는 것은 더욱 비싼 유지보수 비용을 치러야 한다.



만약에 구간별 방식을 구현한다면?

```java
public class DurationDiscountRule extends FixedFeePolicy {
    private Duration from;
    private Duration to;

    public DurationDiscountRule(Duration from, Duration to, Money amount, Duration seconds) {
        super(amount, seconds);
        this.from = from;
        this.to = to;
    }

    public Money calculate(Call call) {
        if (call.getDuration().compareTo(to) > 0) {
            return Money.ZERO;
        }

        if (call.getDuration().compareTo(from) < 0) {
            return Money.ZERO;
        }

        // 부모 클래스의 calculateFee(phone)은 Phone 클래스를 파라미터로 받는다.
        // calculateFee(phone)을 재사용하기 위해 데이터를 전달할 용도로 임시 Phone을 만든다.
        Phone phone = new Phone(null);
        phone.call(new Call(call.getFrom().plus(from),
                            call.getDuration().compareTo(to) > 0 ? call.getFrom().plus(to) : call.getTo()));

        return super.calculateFee(phone);
    }
}
```

```java
public class DurationDiscountPolicy extends BasicRatePolicy {
    private List<DurationDiscountRule> rules = new ArrayList<>();

    public DurationDiscountPolicy(List<DurationDiscountRule> rules) {
        this.rules = rules;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        Money result = Money.ZERO;
        for(DurationDiscountRule rule: rules) {
            result.plus(rule.calculate(call));
        }
        return result;
    }
}
```



---

## 설계에 일관성 부여하기.

일관성 있는 설게를 만드는 데 가장 훌룡한 조언은 다양한 설계 경험을 익히라는 것. 풍부한 설계경험을 가진 사람은 어떤 변경이 중요한지, 그리고 그 변경을 어떻게 다뤄야 하는지에 대한 통찰력을 가지게 된다. 따라서 설계 경험이 풍부하면 풍부할수록 어떤 위치에서 일관성을 보장해야 하고 일관성을 제공하기 위해 어떤 방법을 사용해야 하는지를 직관적으로 결정할 수 있다.

 일관성 있는 설계를 위한 두 번째 조건은 널리 알려진 디자인 패턴을 학습하고 변경이라는 문맥 안에서 디자인 패턴을 적용해 보라는 것.

디자인 패턴이 반복적으로 적용할 수 있는 설계 구조를 제공하다고 하더라도 모든 경우에 적합한 패턴을 찾을 수 있는 것은 아니다. 

- 변하는 개념을 변하지 않는 개념으로부터 분리하라.
- 변하는 개념을 캡슐화하라.



```
애플리케이션에서 달라지는 부분을 찾아내고, 달라지지 않은 부분으로부터 분리시킨다. 이것은 여러 설계 원칙 중에서 첫 번째 원칙이다. 즉, 코드에서 새로운 요구사항이 있을 때마다 바뀌는 부분이 있다면 그 행동을 바뀌지 않는 다른 부분으로부터 골라내서 분리해야 한다는 것을 알 수 있다. 이 원칙은 다음과 같은 식으로 생각 할 수도 잇다.
 "바뀌는 부분을 따로 뽑아서 캡슐화한다. 그렇게 하면 나중에 바뀌지 않는 부분에는 영향을 미치지 않은 채로 그 부분만 고치거나 확장할 수 있다." - Freeman04
```



### 조건 로직 대 객체 탐색

```java
public class ReservationAgency {
  public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
    for(DiscountCondition condition : movie.getDiscountConditions()){
      if(condition.getType() == DiscountConditionType.PERIOD){
        //기간 조건 경우
      } else {
        //회사 조건 경우
      }
    }
    if(discountable){
      switch(movie.getMovieType()){
        case AMOUNT_DISCOUNT:
          //금액 할인 정책 경우
        case PRERCENT_DISCOUNT:
          //비율 할인 정책 경우
        case NONE_DISCOUNT:
          //할인 정책이 없는 경우
      }
    } else {
      // 할인 적용 불가 경우
    }
  }
}
```



위 코드는 if 문으로 변경을 좌지우지 한다. 즉, 절차 지향 프로그램에서 변경을 처리하는 전통적인 방법은 이처럼 조건문의 분기를 추가하거나 개별 분기 로직을 수정하는 것.



이걸 객체지향은 조금 다른 접근방법을 취한다. 객체지향에서 변경을 다루는 전통적인 방법은 조건 로직을 객체 사이의 이동으로 바꾸는 것.



**조건 로직을 객체 사이의 탐색 과정으로 변경한 객체지향 설계**

```java
abstract class DiscountPolicy {

    private Set<DiscountCondition> conditions = new HashSet<>();

    public void addCondition(DiscountCondition condition) {
        conditions.add(condition);
    }

    public void copyCondition(DiscountPolicy policy) {
        policy.conditions.addAll(conditions);
    }

    public Money calculateFee(Screening screening, int count, Money fee) {
        for (DiscountCondition condition : conditions) {
            if (condition.isSatisfiedBy(screening, count)) return calculateFee(fee);
        }
        return fee;
    }

    protected abstract Money calculateFee(Money fee);
}
```



```java
public class Movie {

    private final DiscountPolicy policy;

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy policy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.policy = policy;
    }
    Money calculateFee(Screening screening, int count){
        return policy.calculateFee(screening, count, fee);
    }
}
```

다형성은 바로 이런 조건 로직을 객체지향이 제공하는 설계 기법이다. 할인 금액을 계산하는 구체적인 방법은 메시지를 수신하는 `discountPolicy`의 구체적인 타입에 따라 결정된다. `Movie` 는 `discountPolicy`가 자신의 요청을 잘 처리해줄 것이라고 믿고 메시지를 전송할 뿐이다.



하지만, **실제로 협력에 참여하는 주체는 구체적인 객체.** 이 객체들은 협력 안에서 DiscountPoclicy와 DiscounCondition 을 대체.



- 클래스는 명확히 단 하나의 이유에 의해서만 변경돼야 하고 클래스 안의 모든 코드는 함께 변경돼야 한다. 간단하게 말해서 단일 책임 원칙을 따르도록 클래스를 분리해야 한다는 것.
- 큰 메서드 안에 뭉쳐있던 조건 로직들을 변경의 압력에 맞춰 작은 클래스들로 분리하고 나면 인스턴스들 사이의 협력 패턴에 일관성을 부여하기가 더 쉬워진다. 
- 유사한 행동을 수행하는 작은 클래스들이 자연스럽게 역할이라는 추상화로 묶이게 되고 역할 사이에서 이뤄지는 협력 방식이 전체 설계의 일관성을 유지할 수 있게 이끌어주기 때문.





> 협력을 일관성 있게 만들기 위해 따라야 하는 지침
>
> 01.변하는 개념을 변하지 않는 개념으로부터 분리하라.
>
> 02.변하는 개념을 캡슐화하라.



- DiscountPolicy 와 같이 훌륭한 추상화를 찾아 추상화에 의존하도록 만드는 것. 추상화에 대한 의존은 결합도를 낮추고 결과적으로 대체 가능한 역할로 구성된 협력을 설계할 수 있게 해준다. 
- **따라서, 선택하는 추상화의 품질이 캡슐화의 품질을 결정.**

- 변경에 초점을 맞추고 캡슐화의 관점에서 설계를 바라보면 일관성 있는 협력 패턴을 얻을 수 있음.



```
구성 요소를 캡슐화하는 실행 지침은 객체지향의 핵심 덕목 중 하나다: 시스템을 책임을 캡슐화한 섬들로 분리하고 그 섬들 간의 결합도를 제한하라.

이 실행 지침이 드러나는 또 다른 주제가 패턴이다. GOD에 의하면 인터페이스에 대해 설계해야 한다고 조언하는데, 이것은 결합도가 느슨해질 수 있도록 엔티티 사이의 관계가 추상적인 수준에서 정해져야 한다는 사실을 다르게 표현한 것이다. 이 특성이 패턴들의 공통적인 경향이라는 것을 알게 될 것이다. 패턴은 매우 빈번하게 요소들이 관계를 맺을 수 있는 대상을 추상적인 기반 타입으로 제한한다.[Bain08]
```



### 캡슐화 다시 생각하기.



**데이터 은닉**: 오직 외부에 공개된 메서드를 통해서만 객체의 내부에 접근할 수 있게 제한함으로써 객체 내부의 상태 구현을 숨기는 기법을 가리킴.



그러나, 캡슐화는 데이터 은닉이상의 의미를 가진다.

GOF의 디자인 패턴에는 이런 조언이 있다.

```
설계에서 무엇이 변화될 수 있는지 고려하라.
 이 접근법은 재설계의 원인에 초점을 맞추는 것과 반대되는 것이다. 설계에 변경을 강요하는 것이 무엇인지에 대해 고려하기보다는 재설계 없이 변경할 수 있는 것이 무엇인지 고려하라. 여기서의 초점은 많은 디자인 패턴의 주제인 변화하는 개념을 캡슐화하는 것이다. 
```



이 말은 즉슨, 캡슐화란 단순히 데이터를 감추는 것이 아니다. 소프트웨어 안에서 변할 수 있는 모든 '개념'을 감추는 것. 여기서 **개념은 변하는 모든 것.**

다시한번 강조하면, 캡슐화란 단순히 데이터를 감추는 것 이상의 의미를 가진다.

- 데이터 캡슐화
- 메서드 캡슐화
- 객체 캡슐화
- 서브타입 캡슐화



캡슐화란 단지 데이터 은닉을 의미하는 것은 아닌, **코드 수정으로 인한 파급효과를 제어할 수 있는 모든 기법이 캡슐화의 일종.**

일반적으로 `데이터 캡슐화`와 `메서드 캡슐화`는 개별 객체에 대한 변경을 관리하기 위해 사용하고,

`객체 캡슐화` 와 `서브타입 캡슐화` 는 협력에 참여하는 객체들의 관계에 대한 변경을 관리하기 위해 사용.



변경을 캡슐화할 수 있는 다양한 방법이 존재하지만 협력을 일관성 있게 만들기 위해 가장 일반적으로 사용하는 방법은 서브타입 캡슐화와 객체 캡슐화를 조합하는 것.



> \> 변하는 부분을 분리해서 타입 계층을 만든다.
>
> \> 변하지 않는 부분의 일부로 타입 계층을 합성한다.

---

# 일관성 있는 기본 정책 구현

## 변경 분리.



일관성 있는 협력을 만들기 위해 첫 번째 단계는 변하는 개념과 변하지 않는 개념을 분리하는 것.

다시 기본 정책을 구성하는 방식으로 돌아가보면,

- 기본 정책은 한 개 이상의 '규칙'으로 구성된다.
- 하나의 '규칙'은 '적용조건'과 '단위요금'의 조합이다.

ex) `00시~19시까지` - 적용조건 / `10초당 18원` - 단위 요금

 `00시~19시까지 10초당 18원` - 규칙

- 공통점은 변하지 않는 부분 / 차이점은 변하는 부분
- 우리의 목적은 변하지 않는 부분과 변하는 것을 분리하는 것.
- 즉, 변하지 않는 `규칙`으로부터 변하는`'적용조건'`을 분리해야 한다.



## 변경 캡슐화 하기.

여서 변하지 않는 것은 `규칙` . 변하는 것은 `적용조건` . 따라서 `규칙` 으로부터 `적용조건` 을 분리해서 추상화하는 것. 이제 변하지 않는 부분이 오직 이 추상화에만 의존하도록 관계를 제한하면 변경을 캡슐화할 수 있게 된다.

`규칙` 으로부터 `적용조건` 을 분리해서 추상화한 후 시간대별, 요일별, 구간별 방식을 이 추상화의 서브타입으로 만든다. 이것이 서브타입 캡슐화. 그 후에 규칙이 적용조건을 표현하는 추상화를 합성 관계로 연결한다. 이것이 객체 캡슐화.



`BasicRatePolicy` > `FeeRule` > `FeeCondition`

변경을 캡슐화하는 기본 정책과 관련된 초기 도메인 모델.



`FeeRule` 은 '규칙'을 구현하는 클래스이며 '단위요금'은  FeeRule의 인스턴스 변수인 feePerDuration에 저장.

`FeeCondition` 은 `적용조건` 을 구현하는 인터페이스이며 변하는 부분을 캡슐화하는 추상화.



각 기본 정책별로 달라지는 부분은 각각의 서브타입으로 구현.

`TimeOfDayFeeCondtion` - 시간대별 방식

`DayOfWeekFeeCondtion` - 요일별 방식

`DurationFeeCondtion` - 구간별 방식



`FeeRule`이` FeeCondtion`을 합성 관계로 연결하고 있다는 점을 주목



## 협력 패턴 설계.

변하는 부분과 변하지 않는 부분을 분리하고, 변하는 부분을 적절히 추상화하고 나면 변하는 부분을 생략한 채 변하지 않는 부분만을 이용해 객체 사이의 협력을 이야기할 수 있다. 추상화만으로 구성한 협력은 추상화를 구체적인 사례로 대체함으로써 다양한 상황으로 확장할 수 있게 된다.

`즉, 재사용 가능한 협력 패턴이 선명하게 드러나는 것.`



정말 잘 협력하는지 알고 싶다면 유일한 방법은 구현해 보는 것 뿐.



## 추상화 수준에서 협력 패턴 구현

```java
import java.util.List;

public interface FeeCondition {
    List<DateTimeInterval> findTimeIntervals(Call call);
}

/// FeeCondtion을 합성관계로 갖는다. 라는 포인트가 중요하다.
public class FeeRule {
    private FeeCondition feeCondition;
    private FeePerDuration feePerDuration;

    public FeeRule(FeeCondition feeCondition, FeePerDuration feePerDuration) {
        this.feeCondition = feeCondition;
        this.feePerDuration = feePerDuration;
    }

    public Money calculateFee(Call call) {
        return feeCondition.findTimeIntervals(call)
                .stream()
                .map(each -> feePerDuration.calculate(each))
                .reduce(Money.ZERO, (first, second) -> first.plus(second));
    }
}

```

`FeeRule` 은 `단위요금(feePerDuration)` 과 `적용조건(feeCondition)` 을 저장하는 두 개의 인스턴스 변수로 구성.

`FeeRule` 의 `calculateFee` 메서드는  `FeeCondition`에게 `findTimeIntervals` 메시지를 전송해서 조건을 만족하는 시간의 목록을 반환받은 후  `feePerDuration` 의 값을 이용해 요금을 계산



여기서도 알 수 있듯이, 인터페이스 중에서도 가장 좋은 인터페이스는 메서드가 하나뿐인 메서드이다.



```java
public class FeePerDuration {
    private Money fee;
    private Duration duration;

    public FeePerDuration(Money fee, Duration duration) {
        this.fee = fee;
        this.duration = duration;
    }

    public Money calculate(DateTimeInterval interval) {
        return fee.times(interval.duration().getSeconds() / duration.getSeconds());
    }
}
```

`FeePerDuration` 클래스는 "단위 시간당 요금" 이라는 개념을 표현하고 이 정보를 이용해 일정 기간 동안의 요금을 계산하는 calculate 메서드를 구한다.



원래 `BasicRatePolicy` 는

```java
public abstract class BasicRatePolicy implements RatePolicy {
    @Override
    public Money calculateFee(Phone phone) {
        Money result = Money.ZERO;

        for(Call call : phone.getCalls()) {
            result.plus(calculateCallFee(call));
        }

        return result;
    }

    protected abstract Money calculateCallFee(Call call);
}
```

라면

```java
public final class BasicRatePolicy implements RatePolicy {
    private List<FeeRule> feeRules = new ArrayList<>();

    public BasicRatePolicy(FeeRule ... feeRules) {
        this.feeRules = Arrays.asList(feeRules);
    }

    @Override
    public Money calculateFee(Phone phone) {
        return phone.getCalls()
                .stream()
                .map(call -> calculate(call))
                .reduce(Money.ZERO, (first, second) -> first.plus(second));
    }

    private Money calculate(Call call) {
        return feeRules
                .stream()
                .map(rule -> rule.calculateFee(call))
                .reduce(Money.ZERO, (first, second) -> first.plus(second));
    }
}
```



와 같이 변경되었다.

 

- 변하지 않는 요소와 추상적인 요소만으로도 요금 계산에 필요한 전체적인 협력 구조를 설명할 수 있다는 것.
- 변하는 것과 변하지 않는 것을 분리하고 변하는 것을 캡슐화한 코드는 오로지 변하지 않는 것과 추상화에 대한 의존성만으로도 전체적인 협력을 구현할 수 있다. 
- 변하는 것은 추상화 뒤에 캡슐화되어 숨겨져 있기 때문에 전체적인 협력의 구조에 영향을 미치지 않는다.

## 구체적인 협력 구현

### 시간대별 정책

```java
public class TimeOfDayFeeCondition implements FeeCondition {
    private LocalTime from;
    private LocalTime to;

    public TimeOfDayFeeCondition(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public List<DateTimeInterval> findTimeIntervals(Call call) {
        return call.getInterval().splitByDay()
                .stream()
                .map(each ->
                        DateTimeInterval.of(
                                LocalDateTime.of(each.getFrom().toLocalDate(), from(each)),
                                LocalDateTime.of(each.getTo().toLocalDate(), to(each))))
                .collect(Collectors.toList());
    }

    private LocalTime from(DateTimeInterval interval) {
        return interval.getFrom().toLocalTime().isBefore(from) ?
                from : interval.getFrom().toLocalTime();
    }

    private LocalTime to(DateTimeInterval interval) {
        return interval.getTo().toLocalTime().isAfter(to) ?
                to : interval.getTo().toLocalTime();
    }
}
```

### 요일별 정책

```java
public class DayOfWeekFeeCondition implements FeeCondition {
    private List<DayOfWeek> dayOfWeeks = new ArrayList<>();

    public DayOfWeekFeeCondition(DayOfWeek ... dayOfWeeks) {
        this.dayOfWeeks = Arrays.asList(dayOfWeeks);
    }

    @Override
    public List<DateTimeInterval> findTimeIntervals(Call call) {
        return call.getInterval()
                .splitByDay()
                .stream()
                .filter(each ->
                        dayOfWeeks.contains(each.getFrom().getDayOfWeek()))
                .collect(Collectors.toList());
    }
}
```

### 구간별 정책

```java
public class DurationFeeCondition implements FeeCondition {
    private Duration from;
    private Duration to;

    public DurationFeeCondition(Duration from, Duration to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public List<DateTimeInterval> findTimeIntervals(Call call) {
        if (call.getInterval().duration().compareTo(from) < 0) {
            return Collections.emptyList();
        }

        return Arrays.asList(DateTimeInterval.of(
                call.getInterval().getFrom().plus(from),
                call.getInterval().duration().compareTo(to) > 0 ?
                        call.getInterval().getFrom().plus(to) :
                        call.getInterval().getTo()));
    }
}
```



- 유사한 기능에 대해 유사한 협력 패턴을 적용하는 것은 객체지향 시스템에서 `개념적 무결성` 을 유지할 수 있는 가장 효과적인 방법.

```
저자는 개념적 무결성(Conceptual Integrity)이 시스템 설계에서 가장 중요하다고 감히 주장한다. 좋은 기능들이긴 하지만 서로 독립적이고 조화되지 못한 아이디어들을 담고 있는 시스템보다는 여러 가지 다양한 기능이나 갱신된 내용은 비록 빠졌더라도 하나로 통합된 일련의 설계 아이디어를 반영하는 시스템이 휠씬 좋다.
```



# 결론

패턴을 찾아라.



일관성 있는 협력의 핵심은 변경을 분리하고 캡슐화 하는 것이다. 변경을 캡슐화하는 방법이 협력에 참여하는 객체들의 역할과 책임을 결정하고 이렇게 결정된 협력이 코드의 구조를 결정. 따라서 훌룡한 설계자가 되는 첫걸음은 변경의 방향을 파악할 수 있는 날카로운 감각을 기르는 것.



그리고 이 변경에 탄력적으로 대응할 수 있는 다양한 캡슐화 방법과 설계 방법을 익히는 것 역시 중요.



 애플리케이션에서 유사한 기능에 대한 변경이 지속적으로 발생하고 있다면 변경을 캡슐화할 수 있는 적절한 추상화를 찾은 후, 이 추상화에 변하지 않는 공통적인 책임을 할당하라. 현재의 구조가 변경을 캡슐화하기에 적합하지 않다면 코드를 수정하지 않고도 원하는 변경을 수용할 수 있도록 협력과 코드를 리팩토링하라. 변경을 수용할 수 있는 적절한 역할과 책임을 찾다 보면 협력의 일관성이 서서히 윤곽을 드러낼 것이다.



```
 객체지향 설계는 객체의 행동과 그것을 지원하기 위한 구조를 게속 수정해 나가는 작업을 반복해 나가면서 다듬어진다. 

`객체, 역할, 책임`은 계속 진화해 나가는 것이다. 협력자들 간에 부하를 좀 더 균형있게 배분하는 방법은 새로 만들어내면 나눠줄 책임이 바꾸게 된다. 

만약 객체들이 서로 통신하는 방법을 개선해낸다면 이들 간의 상호작용은 재정의돼야 한다. 

이 같은 과정을 거치면서 객체들이 자주 통신하는 경로는 더욱 효율적이게 되고, 주어진 작업을 수행하는 표준 방안이 정착된다. 협력 패턴이 드러나는 것이다.
```
