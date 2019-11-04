# 상속과 코드 재사용

- 코드를 재사용
- 추상화를 이용한다.
- 클래스를 재사용하기 위해 새로운 클래스를 추가하는 가장 대표적인 기법인 **상속**
- 재사용 관점에서 상속이란 클래스 안에 정의된 인스턴스 변수와 메서드를 자동으로 새로운 클래스에추가하는 구현 기법



## 상속과 중복코드

- 중복코드는 우리를 주저하게 만들뿐만 아니라 동료들을 의심하게 만든다.
- 악의 근원 중복코드.

### DRY 원칙

- '반복하지 마라' - 모든 지식은 시스탬 내에서 단일하고, 애매하지 않고, 정말로 믿을 만한 표현 양식을 가져야 한다.



### Example 을 통해 중복 코드를 살펴보자.

한 달에 한 번씩 가입자별로 전화 요금을 계산하는 간단한 애플리케이션을 개발하자.

```java
public class Call {
    private LocalDateTime from;
    private LocalDateTime to;

    public Call(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public Duration getDuration() {
        return Duration.between(from, to);
    }

    public LocalDateTime getFrom() {
        return from;
    }
}
```

이제 통화 요금을 계산할 객체가 필요하다.  

```java
public class Phone {
    private Money amount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();

    public Phone(Money amount, Duration seconds) {
        this.amount = amount;
        this.seconds = seconds;
    }

    public void call(Call call) {
        calls.add(call);
    }

    public List<Call> getCalls() {
        return calls;
    }

    public Money getAmount() {
        return amount;
    }

    public Duration getSeconds() {
        return seconds;
    }

    public Money calculateFee() {
        Money result = Money.ZERO;

        for(Call call : calls) {
            result = result.plus(amount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
        }

        return result;
    }
}
```



이때 생성자에 넣어지는 값은 사용자가 '10초당 5원' 이므로,

amount > 5 // secons > 10 된다.

마지막에 `calculateFee` 통해 계산된다.



**여기서 새로운 요구사항이 등장했다고 가정하자. 바로 심야요금제이다. 밤 10시 이후의 통화에 대해 요금을 할인해주는 방식을 넣는다면?**

가장 원초적인 방식의 `NightlyDiscountPhone` 는 Phone의 코드를 복사해 수정하는 것이다.

```java
public class NightlyDiscountPhone {
    private static final int LATE_NIGHT_HOUR = 22;

    private Money nightlyAmount;
    private Money regularAmount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds) {
        this.nightlyAmount = nightlyAmount;
        this.regularAmount = regularAmount;
        this.seconds = seconds;
    }

    public Money calculateFee() {
        Money result = Money.ZERO;

        for(Call call : calls) {
            if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
                result = result.plus(nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            } else {
                result = result.plus(regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            }
        }

        return result;
    }
}
```

이때 생성자에 넣어지는 값은 사용자가 '10초당 5원' 이였다면, 밤 10시 이후에는 '10초당 2원' 이라면

nightlyAmount = 2,  regularAmount = 5, seconds = 10 

이렇게 될 것이다.

CV 기법은 구현시간을 단축시킬 수 있었지만, 치러야할 대가는 거대하다. 언제 터질지 모르는 시한 폭탄을 안고 있는 것이라 생각하면 좋다.

***이제부터 중복코드가 미치는 작업을 해보자.***

 코드 수정에 미치는 영향을 살펴보기 위해 통화 요금에 부과할 세금을 계산하는 요구사항을 추가해보자.

```java
public class NightlyDiscountPhone {
    private static final int LATE_NIGHT_HOUR = 22;

		...
    private double taxRate;

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds, double taxRate) {
	    ...
      this.taxRate = taxRate;
    }

    public Money calculateFee() {
        Money result = Money.ZERO;
	     ...
        return result.minus(result.times(taxRate));
    }
}
```

```java
public class Phone {
		...
    private double taxRate;

    public Phone(Money amount, Duration seconds, double taxRate) {
        this.amount = amount;
        this.seconds = seconds;
        this.taxRate = taxRate;
    }

    ...
    public Money calculateFee() {
        Money result = Money.ZERO;

        for(Call call : calls) {
            result = result.plus(amount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
        }

        return result.plus(result.times(taxRate));
    }
}
```

이렇게 변경되는 사항을 보면, 중복 코드가 가지는 단점을 잘 보여준다. 중복코드는 늘 함께 수정돼어야 하기 때문에 하나라도 빠뜨린다면 버그로 이어질 것이다.



***중복 코드는 새로운 중복 코드를 부른다. 중복 코드를 제거하지 않은 상태에서 코드를 수정할 수 있는 유일한 방법은 새로운 중복 코드를 추가하는 것뿐이다.***



### 타입 코드 사용하기

두 클래스 사이의 중복 코드를 제거하는 또다른 방법은 클래스를 하나로 합치는 것이다.

요금제를 구분하는 타입 코드를 추가하고 타입 코드의 값에 따라 로직을 분기시켜 Phone과 NightlyDiscountPhone을 하나로 합칠 수 있다. 하지만 여전히 타입 코드를 사용하는 클래스는 낮은 응집도와 높은 결합도라는 문제에 시달리게 된다.

```java
public class Phone {
    private static final int LATE_NIGHT_HOUR = 22;
    enum PhoneType { REGULAR, NIGHTLY }

    private PhoneType type;

    private Money amount;
    private Money regularAmount;
    private Money nightlyAmount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();

    public Phone(Money amount, Duration seconds) {
        this(PhoneType.REGULAR, amount, Money.ZERO, Money.ZERO, seconds);
    }

    public Phone(Money nightlyAmount, Money regularAmount,
                 Duration seconds) {
        this(PhoneType.NIGHTLY, Money.ZERO, nightlyAmount, regularAmount,
                seconds);
    }

    public Phone(PhoneType type, Money amount, Money nightlyAmount,
                 Money regularAmount, Duration seconds) {
        this.type = type;
        this.amount = amount;
        this.regularAmount = regularAmount;
        this.nightlyAmount = nightlyAmount;
        this.seconds = seconds;
    }

    public Money calculateFee() {
        Money result = Money.ZERO;

        for(Call call : calls) {
            if (type == PhoneType.REGULAR) {
                result = result.plus(amount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            } else {
                if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
                    result = result.plus(nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
                } else {
                    result = result.plus(regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
                }
            }
        }

        return result;
    }
}
```



### 상속을 이용해서 중복 코드 제거.

- 이미 존재하는 클래스와 유사한 클래스가 필요하다면 코드를 복사하지 말고 상속을 이용해 코드를 재사용 하는 것

```java
public class NightlyDiscountPhone extends Phone {
    private static final int LATE_NIGHT_HOUR = 22;

    private Money nightlyAmount;

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds) {
        super(regularAmount, seconds);
        this.nightlyAmount = nightlyAmount;
    }

    @Override
    public Money calculateFee() {
        // 부모클래스의 calculateFee 호출
        Money result = super.calculateFee();

        Money nightlyFee = Money.ZERO;
        for(Call call : getCalls()) {
            if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
                nightlyFee = nightlyFee.plus(
                        getAmount().minus(nightlyAmount).times(
                                call.getDuration().getSeconds() / getSeconds().getSeconds()));
            }
        }

        return result.minus(nightlyFee);
    }
}
```

위 코드가 단번에 이해되는가?  이 코드에서 시사해야될 점은 개발자의 가정을 **이해하기전에는** 코드를 이해하기 어렵다는 것에 있다.



- 상속을 염두에 두고 설계되지 않은 클래스를 상속을 이용해 재사용하는 것은 생각처럼 쉽지 않다. 개발자는 재사용을 위해 상속 계층 사이에 무수히 많은 가정을 세웠을지도 모른다. 그리고 그 가정은 코드를 이해하기 어렵게 만들 뿐만 아니라 직관에도 어긋날 수 있다.
- **결과적으로, 상속은 결랍도를 높인다. 그리고 상속이 초래하는 부모 클래스와 자식 클래스 사이의 강한 결합이 코드를 수정하기 어렵게 만든다.**



### 강하게 결합된 Phone과 NightyDiscountPhone

위에서 보여준 상속을 이용한 재사용에 대한 문제점을 다시한번 살펴보자.

- NightlyDiscountPhone은 부모 클래스인 Phone의 calculatefee 메서드를 오버라이딩한다. 또한 메서드 안에서 super 참조를 이용해 부모 클래스의 메서드를 호출한다. NightlyDiscountPhone의 calculateFee 메서드는 자신이 오버라이딩한 Phone의 calculateFee 메서드가 모든 통화에 대한 요금의 총합을 반환한다는 사실에 기반한다.



어떻게 강하게 결합되어 있는지 알수 있냐면, 앞에서 헀던 요구사항과 같이 texRate을 추가해보자.

그렇다면, 아래와 같이 Phone 의 코드가 변경될 것이다.

```java
public class Phone {
    private Money amount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();
    private double taxRate;

    public Phone(Money amount, Duration seconds, double taxRate) {
        this.amount = amount;
        this.seconds = seconds;
        this.taxRate = taxRate;
    }

	  ...

    public Money calculateFee() {
        Money result = Money.ZERO;

        for(Call call : calls) {
            result = result.plus(amount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
        }

        return result.plus(result.times(taxRate));
    }

    public double getTaxRate() {
        return taxRate;
    }
}
```

Phone의 코드가 변경되면서 NightyDiscountPhone의 코드도 영향을 미치게 되는데 아래 코드와 같다.

```java
public class NightlyDiscountPhone extends Phone {
    private static final int LATE_NIGHT_HOUR = 22;

    private Money nightlyAmount;

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds, double taxRate) {
        super(regularAmount, seconds, taxRate);
        this.nightlyAmount = nightlyAmount;
    }

    @Override
    public Money calculateFee() {
        // 부모클래스의 calculateFee() 호출
        Money result = super.calculateFee();

        Money nightlyFee = Money.ZERO;
        for(Call call : getCalls()) {
            if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
                nightlyFee = nightlyFee.plus(
                        getAmount().minus(nightlyAmount).times(
                                call.getDuration().getSeconds() / getSeconds().getSeconds()));
            }
        }

        return result.minus(nightlyFee.plus(nightlyFee.times(getTaxRate())));
    }
}

```



생성자 코드에서 super로 taxRate를 넘겨주는 부분의 코드를 주의깊게 살펴보자.



이제 Phone과 NightlyDiscountPhone의 상속계층이 가지는 문제점이 또렷해졌다. NightlyDiscountPhone을 Phone의 자식 클래스로 만든 이유는 Phone의 코드를 재사용하고 중복 코드를 제거하기 위해서다. 하지만 세금을 부과하는 로직을 추가하기 위해 Phone을 수정할 때 유사한 코드를 NightlyDiscountPhone에도 추가해야 한다. **다시 말해서 코드 중복을 제거하기 위해 상속을 사용했음에도 세금을 계산하는 로직을 추가하기 위해 새로운 중복 코드를 만들어야 한다는 것이다. **



> ☠️☠️상속을 위한 경고1☠️☠️
>
> 자식 클래스의 메서드 안에서 super 참조를 이용해 부모 클래스의 메서드를 직접 호출할 경우 두 클래스는 강하게 결합된다. super 호출을 제거할 수 있는 방법을 찾아 결합도를 제거하라.



*이처럼 상속 관계로 연결된 자식 클래스가 부모 클래스의 변경에 취약해지는 현상을 가르켜 **취약한 기반 클래스 문제**라고 부른다.*

 부모 클래스의 변경에 의해 자식 클래스가 영향을 받는 현상을 **취약한 기반 클래스** 라 칭한다.



 ***취약한 기반 클래스 문제는 상속이라는 문맥 안에서 결합도가 초래하는 문제점을 말한다. 그 문제점은 무엇일까?***



- 상속관계를 추가할수록 전체 시스템의 결합도가 높아진다는 사실을 알고 있어야 한다. 상속은 자식 클래스를 점진적으로 추가해서 기능을 확장하는 데는 용이하지만 높은 결합도로 인해 부모 클래스를 점진적으로 개선하는 것은 어렵게 만든다.
- 캡슐화를 약화시키고 결합도를 높인다. 상속은 자식 클래스가 부모 클래스의 구현 세부사항에 의존하도록 만들기 떄문에 캡슐화를 약화시킨다.
- 객체지향의 기반은 캡슐화를 통한 변경의 통제다. 상속은 코드의 재사용을 위해 캡슐화의 장점을 희석시키고 구현에 대한 결합도를 높임으로써 객체지향이 가진 강력함을 반감시킨다.



### 불필요한 인터페이스 상속 문제

자바 초기버전에서 상속을 잘못 사용한 대표적인 사례로 `java.util.Properties` 와 `java.util.Stack`

두 클래스의 공통점은 부모 클래스에서 상속받은 메서드를 사용할 경우 자식 클래스의 규칙이 위반될 수 있다는 것.



Stack은 Vector를 상속받아 구현하게 되면서 문제가 발생한다.

```java
Stack<String> stack = new Stack<>();
stack.push("1st");
stack.push("2st");
stack.push("3st");
stack.push("4st");

stack.add(0, "4th");

assertEquals("4th", stack.pop()); //에러!
```

문제의 원인은 Stack이 규칙을 무너뜨릴 여지가 있는 위험한 Vector의 퍼블릭 인터페이스까지도 함께 상속받았기 때문이다.



`java.util.Properties` 의 경우에도 마찬가지이다.

### 메서드 오버라이딩의 오작용 문제

```java
public class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

//    @Override
//    public boolean addAll(Collection<? extends E> c) {
//        addCount += c.size();
//        return super.addAll(c);
//    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c)
            if (add(e))
                modified = true;
        return modified;
    }

    public int getAddCount() {
        return addCount;
    }
}

```



- 즉, 미래에 발생할지 모르는 위험을 방지하기 위해 코드를 중복시킨 것이다. 게다가 부모 클래스의 코드를 그대로 가져오는 방법이 항상 가능한 것도 아니다. 소스코드에 대한 접근권한이 없을 수도 있고 부모클래스의  메서드에서 private 변수나 메서드를 사용하고 있을 수도 있다.

> ☠️☠️상속을 위한 경고2☠️☠️
>
> 상속받은 부모 클래스의 메서드가 자식 클래스의 내부 구조에 대한 규칙을 깨뜨릴 수 있다.
>
> ☠️☠️상속을 위한 경고3☠️☠️
>
> 자식 클래스가 부모 클래스의 메서드를 오버라이딩할 경우 부모 클래스가 자신의 메서드를 사용하는 방법에 자식 클래스가 결합될 수 있다.

**설계는 트레이드 오프 활동이라는 사실을 기억하라. 상속은 코드 재사용을 위해 캡슐화를 희생한다. 완벽학 캡슐화를 원한다면 코드 재사용을 포기하거나 상속 이외의 다른 방법을 사용해야 한다.**



- 결합도란 다른 대상에 대해 알고 있는 지식의 양이다. 상속은 기본적으로 부모 클래스의 구현을 재사용한다는 기본 전제를 따르기 때문에 자식 클래스가 부모 클래스의 내부에 대해 속속들이 알도록 강요한다. 따라서 코드 재사용을 위한 상속은 부모 클래스와 자식 클래스를 강하게 결합시키기 때문에 함께 수정해야 하는 상황 역시 빈번하게 발생할 수밖에 없는 것이다.

> 다시 말해, 서브클래스는 올바른 기능을 위해 슈퍼클래스의 세부적인 구현에 의존한다. 슈퍼클래스의 구현은 릴리스를 거치면서 변경될 수 있고, 그에 따라 서브클래스의 코드를 변경하지 않더라도 깨질 수 있다. 결과적으로, 슈퍼클래스의 작성자가 확장될 목적으로 특별히 그 클래스를 설계하지 않았다면 서브 클래스는 슈퍼클래스와 보조를 맞쳐서 진화해야 한다. - "조슈야 블로치"

> ☠️☠️상속을 위한 경고4☠️☠️
>
> 클래스를 상속하면 결합도로 인해 자식 클래스와 부모 클래스의 구현을 영원히 변경하지 않거나, 자식 클래스와 부모 클래스를 동시에 변경하거나 둘 중 하나를 선택할 수밖에 없다.



## 상속을 이용한다면 결과로 추상화에 의존하자.

다시 Phone으로 돌아가자.

저자는 코드 중복을 제거하기 위해 상속을 도입할 때 따르는 두가지 원칙이 있다.

- 두 메서드가 유사하게 보인다면 차이점을 메서드로 추출하라. 메서드 추출을 통해 두 메서드를 동일한 형태로 보이도록 만들 수 있다.
- 부모 클래스의 코드를 하위로 내리지말고 자식 클래스의 코드를 상위로 올려라. 부모 클래스의 구체적인 메서드를 자식클래스로 내리는 것보다 자식 클래스의 추상적인 메서드를 부모 클래스로 올리는 것이 재사용성과 응집도 측면에서 더 뛰어난 결과를 얻을 수있다.

### 차이를 메서드로 추출.

- 변하는 것으로부터 변하지 않는 것을 분리하라 / 변하는 부분을 찾고 이를 캡슐화하라.

```java
public abstract class AbstractPhone {
    private List<Call> calls = new ArrayList<>();

    public Money calculateFee() {
        Money result = Money.ZERO;

        for(Call call : calls) {
            result = result.plus(calculateCallFee(call));
        }

        return result;
    }

    abstract protected Money calculateCallFee(Call call);
}
```

```java
public class NightlyDiscountPhone extends AbstractPhone {
    private static final int LATE_NIGHT_HOUR = 22;

    private Money nightlyAmount;
    private Money regularAmount;
    private Duration seconds;

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds) {
        this.nightlyAmount = nightlyAmount;
        this.regularAmount = regularAmount;
        this.seconds = seconds;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
            return nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds());
        } else {
            return regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds());
        }
    }
}
```

```java
public class Phone extends AbstractPhone {
    private Money amount;
    private Duration seconds;

    public Phone(Money amount, Duration seconds) {
        this.amount = amount;
        this.seconds = seconds;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        return amount.times(call.getDuration().getSeconds() / seconds.getSeconds());
    }
}
```

