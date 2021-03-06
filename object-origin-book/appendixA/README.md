# 계약에 의한 설계



## 00. 왜 계약에 의한 설계 ?

 이 책에서 `의도를 나타내는 인터페이스`를 작성하도록 유도하고 있다. 그러나, 클라이언트와 서버간에 객체의 행동을 인터페이스만으로는 다양한 관점을 전달하기 어렵다는 것이다. 그러므로, 우리에게 필요한 것은 명령의 부수효과를 쉽고 명확하게 표현할 수 있는 커뮤니케이션 수단이다.

 이 시점이 되면 `계약에 의한 설계(Design By Contract)` 가 주는 이점을 활용하는 것을 생각하자.



계약에 의한 설계를 사용할 경우

- 협력에 필요한 다양한 제약과 부수효과를 명시적으로 정의하고 문서화할 수 있다. 
- 이는 클라이언트 개발자는 오퍼레이션의 구현을 살펴보지 않더라도 객체의 사용법을 쉽게 이해할 수 있다.
- 실행 가능하기 때문에 구현에 동기화돼 있는지 여부를 런타임에 검증할 수 있다.



***결과적으로 클래스의 부수효과를 명시적으로 문서화하고 명확하게 커뮤니케이션할 수 있을뿐만 아니라 실행 가능한 검증 도구로써 사용할 수 있다.***

## 01. 협력과 계약

 객체지향 프로그래밍은 클래스로서 타입을 정의하고, 타입에 따른 객체를 생성한다. 그리고 생성된 객체에는 정의된 메소드에 따라 상호 동작되는 메세지를 전달한다. 

메세지의 이름과 파라미터 목록은 시그니처를 통해 전달할 수 있지만 협력을 위해 필요한 약속과 제약은 인터페이스를 통해 전달할 수 없기 때문에 **협력과 관련된 상당한 내용**이 암시적인 상태로 남게 된다.



한가지 코드 예로 살펴보면

```java
Class Event{
  
  public boolean isSatisfied(... ...){ ... }
  public void reschedule(... ... ){... }
  
  // if 문을 사용한 일반적인 파라미터 체크 방식
	public main(){
    if(isSatisfied()){
			reschedule();
    }
  }
  
  // Contract 문을 사용한 일반적인 파라미터 체크 방식
	public main(){
		Contract.Requires(isSatisfied())
    }
  }
}
```



if문 했을 때 차이점은 `문서화`. 일반적인 정합성 체크 로직은 코드의 구현 내부에 숨겨져 있어 실제로 코드를 분석하지 않는 한 정확하게 파악하기가 쉽지 않다. 



이렇게 작성된 계약은 문서화로 끝나는 것이 아니라 제약 조건의 만족 여부를 실행 중에 체크할 수 있다.

 

`계약`이란 무슨말 일까?

- 각 계약 당사자는 계약으로부터 `이익` 을 기대하고 이익을 얻기 위해 의무를 이행한다.
- 각 계약 당사자의 이익과 의무는 계약서에 `문서화`된다.



이걸 버트란드 마이어는 계약이라는 관점을

- 협력에 참여하는 각 객체는 계약으로부터 `이익`을 기대하고 이익을 얻기 위해 `의무`를 이행한다.
- 협력에 참여하는 각 객체의 이익과 의무는 객체의 인터페이스 상에 `문서화`된다.
- 계약에 의한 설계 개념은 "인터페이스에 대해 프로그래밍하라" 는 원칙을 확장.
- 오퍼레이션의 시그니처를 구성하는 다양한 요소들을 이용해 협력에 참여하는 객체들이 지켜야 하는 제약 조건을 명시. 이 제약 조건을 인터페이스의 일부로 만듦으로써 코드를 분석하지 않고도 인터페이스의 사용법을 이해.



***오퍼레이션 시그니처에 명시된 제약 조건***

협력을 위한 다양한 정보를 제공.

```java
public(가시성) Reservation(반환 타입) reserve(메서드 이름)(
					Customer(파라미터 타입) customer(파라미터 이름,
					int audienceCount)
```

위 코드를 통해서 메서드의 이름과 매개변수의 이름을 통해 오퍼레이션이 클라이언트에게 어떤 것을 제공하려고하는지를 충분히 설명할 수 있다. 

`의도를 드러내는 인터페이스` 를 만들면 오퍼레이션의 시그니처만으로도 어느 정도까지는 클라이언트와 서버가 협력을 위해 수행해야 하는 제약조건을 명시할 수 있다.



`계약` 이라는 단어의 의미는 여기서 더욱 나아간다.

`reserve` 메서드를 호출할 때, `customer` 가 null로 전달 될 수 있고, `audienceCount`가 음수로 전달될 수 있다. 그러므로, 이런 것들도 `아니어야 한다.` 라는 제약 조건을 명시해야 한다.



서버는 자신이 처리할 수 있는 범위의 값들을 클라이언트가 전달할 것이라고 기대한다. - 사전조건

클라이언트는 자신이 원하는 값을 서버가 반환할 것이라고 예쌍한다. - 사후조건

클라이언트는 메시지 전송 전과 후의 서버의 상태가 정상일 것이라고 기대한다. - 불변식



자세히 보면,

**사전조건(precondition)**

메서드가 호출되기 위해 만족돼야 하는 조건. 이것은 메서드의 요구사항을 명시한다. 사전조건이 만족되지 않을 경우 메서드가 실행돼서는 안 된다. 사전조건을 만족시키는 것은 메서드를 실행하는 클라이언트의 의무다.

**사후조건(postcondition)**

 메서드가 실행된 후에 클라이언트에게 보장해야 하는 조건. 클라이언트가 사전조건을 만족시켰다면 메서드는 사후조건에 명시된 조건을 만족시켜야 한다. 만약 클라이언트가 사전조건을 만족시켰는데도 사후조건을 만족시키지 못한 경우에는 클라이언트에게 예외를 던저야 한다. **사후조건을 만족시키는 것은 서버의 의무다.**

**불변식(invariant)**

 항상 참이라고 보장되는 서버의 조건. 메서드가 실행되는 도중에는 불변식을 만족시키지 못할 수도 있지만 **메서드를 실행하기 전이나 종류된 후에 불변식은 항상 참이여야 한다.**



## 코드로 살펴보는 사전/사후/불변식

```c#
public Reservation reserve(Customer customer, int audienceCount){
  Contract.Requires(customer != null);
  Contract.Requires(audienceCount >= 1);
  Contract.Ensures(Contract.Result<Reservation>() != null);
  return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
}
```



### 사전조건

- 메서드에 전달된 인자의 정합성을 체크하기 위해 사용
- 위 코드에서 `Contract.Requries(...)` 와 같은 사전조건으로 정의함으로써 메서드가 잘못된 값을 기반으로 실행되는 것을 방지.
- 만약 사전조건을 만족시키지 못한다면, 최대한 빨리 실패해서 클라이언트에게 버그가 있다는 사실을 알려야 한다.



### 사후조건

- 사후조건을 만족시키는 함수는 `Contract.Ensures` 메서드를 활용

- `reserve` 메소드의 사후조건은 Reservation 인스턴스가 null이어서는 안 된다는 것이다.

사후조건을 사용하는 용도는 다음 세 가지가 있다.

- 인스턴스 변수의 상태가 올바른지를 서술하기 위해.
- 메서드에 전달된 파라미터의 값이 올바르게 변경됐는지를 서술하기 위해
- 반환값이 올바른지를 서술하기 위해



계약에 의한 설계의 장점를 사용하면 계약만을 위해 준비된 전용 표기법을 사용해 계약을 명확하게 표현할 수 있다. 또한 계약을 일반 로직과 분리해서 서술함으로써 계약을 좀 더 두드러지게 강조할 수 있다. 또한 계약이 메서드의 일부로 실행되도록 함으로써 계약을 강제할 수 있다.



### 불변식

사전조건과 사후조건은 각 메소드마다 달라지는 데 반해 불변식은 인스턴스 생명주기 전반에 걸쳐 지켜져야 하는 규칙을 명세.

- 불변식은 클래스의 모든 인스턴스가 생성된 후에 만족돼야 한다. 이것은 클래스에 정의된 모든 생성자는 불변식을 준수해야 한다는 것을 의미한다.
- 불변식은 클라이언트에 의해 호출 가능한 모든 메서드에 의해 준수돼야 한다. 메서드가 실행되는 중에는 객체의 상태가 불안전한 상태로 빠질 수 있개 때문에 불변식을 만족시킬 필요는 없지만 메서드 실행 전과 종류 후에는 항상 불변식을 만족하는 상태가 유지돼야 한다.
- 불변식은 생성자 실행 후, 메서드 실행 전, 메서드 실행 후에 호출돼야 한다.
- 모든 생성자의 마지막 위치와, 메서드 시작 지점, 메서드 종료 지점에 불변식을 호출하도록 일일이 코드를 작성.



## 계약에 의한 설계와 서브타이핑 규칙

**계약규칙(contract rules)**

- 서브타입에 더 강력한 사전조건을 정의할 수 없다.
- 서브타입에 더 완화된 사후조건을 정의할 수 없다.
- 슈퍼타입의 불변식은 서브타입에서도 반드시 유지돼야 한다.

**가변성 규칙(variance rules)**

- 서브타입의 메서드 파라미터는 반공변성을 가져야 한다.
- 서브타입의 리턴 타입은 공변성을 가져야 한다.
- 서브타입은 슈퍼타입이 발생시키는 예외와 다른 타입의 예외를 발생시켜서는 안 된다.



> [cf]
>
> **공변성**
>
>  S와 T사이의 서브타입 관계가 그대로 유지된다. 이 경우 해당 위치에서 서브타입인 S가 슈퍼타입 T대신 사용될 수 있다. 우리가 흔히 이야기하는 리스코프 치환 원칙은 공변성과 관련된 원칙이라고 생각하면 된다.
>
> **반공변성**
>
>  S와 T 사이의 서브타입 관계가 역전된다. 이 경우 해당 위치에서 슈퍼타입인 T가 서브타입인 S대신 사용될 수 있다.
>
> **무공변성**
>
>  S와 T사이에는 아무런 관계도 존재하지 않는다. 따라서 S대신 T를 사용하거나 T 대신 S를 사용할 수 없다.





