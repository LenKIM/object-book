# 13장 - Contract

안정적인 코드를 짜기 위해서는 어떻게 해야 되는가?

경우의 수를 없애는 것이 정답일까? 경우의 수를 없애기 위해서는 코드에서 들어내야 한다.



*계약은 메세지를 통해 주고 받는다.*

![image-20191117210946818](https://tva1.sinaimg.cn/large/006y8mN6gy1g91dnku9qrj31260ikgod.jpg)

## 처음 규격을 맞쳐서 보내야 한다. - 전달받은 메세지의 규격 (precondition)

**sender가 아닌 receiver가 데이터를 검증한다.**



## 전달할 메세지의 규격 - postcondtion

**receiver 안에서 돌려주기 전에 데이터를 검증한다,**



## 객제 자신의 규격 - class invariant (불변성) 

**상태에 의한 검증**

**예를 들면, 네트워크를 하는 브릿지라면, 내 안에 정보가 아닌 프록시해서 가져오는 클래스라면 두 상태가 사전에 연결되 있어야 한다.** 

## 위임된 책임의 컨텍스트

**나혼자 이상한 짓인가? 앞 뒤의 장단을 잘 맞쳐주고 있는가?**



*객체간의 계약을 하려면 또는 제대로된 통신을 하기 위해서는 적어도 위 4가지가 충족시켜야 한다.*

이것이 바로 계약이다.



![image-20191117211610049](https://tva1.sinaimg.cn/large/006y8mN6gy1g91au89krnj30s20ksaaj.jpg)

Invariant - 불변성

이것은 이뮤터닐인가? 아니, 객체 초기상태, `객체의 메세지 수신 전 상태` 라고 해야 한다. 

사전과 다른 의미로 IT에서만 쓰이는 뜻이다.



![image-20191117211700388](https://tva1.sinaimg.cn/large/006y8mN6gy1g91dnq6qtcj312q0i4mza.jpg)





![image-20191117212151346](https://tva1.sinaimg.cn/large/006y8mN6gy1g91b059kulj31a60icgqo.jpg)





Calc의 Null을 방지하기 위해 생성자를 무조건 만들어 놓는다.

Null을 없애라. null이 있으면 어딘가에 전염된다.

![image-20191117212240059](https://tva1.sinaimg.cn/large/006y8mN6gy1g91b11ulhgj30pm0ikwhc.jpg)





# precondition

![image-20191117212337527](https://tva1.sinaimg.cn/large/006y8mN6gy1g91b1yw59dj313e0i2ace.jpg)

더 큰 범주에서 화이트리스트라고 불린다.



대표적인 class로 Money 

형이 보장될 수 있도록 중간에 브릿지를 만들 수 있다.

SafetyInteger 라고 불린다.



![image-20191117212558739](https://tva1.sinaimg.cn/large/006y8mN6gy1g91b4hrh22j319y0ia0w5.jpg)



빨간 박스는 Null의 위험이 남아있다.

만사형통의 Null, 마치 만능키 같은 것이 문제이다. 이건 자바의 문제이다.



***컴파일러시 Null이 넘어오는 것을 확인한 뒤에 처리할 수 있다.***

![image-20191117212740108](https://tva1.sinaimg.cn/large/006y8mN6gy1g91b6atylkj319u0j2783.jpg)



 그러나, 명시적으로 Null인 경우에는 확신할 수 있지만, 그 이외에는 이를 확신하기 힘들다.



두번째 방법으로 If를 사용하는 것

![image-20191117212933984](https://tva1.sinaimg.cn/large/006y8mN6gy1g91b8abr33j31a40fsq6e.jpg)



슬그머니 if를 사용해서 null 문제를 피해가는 것



위 장표에서 첫번쨰 함수에서는  void가 아닌 boolean 이여야 한다.

이렇게 부드럽게 넘어가는 것은 어떤 Context 인지에 따라 달라질 수 있다. 

![image-20191117213219556](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bcf7k7pj31a40hc42z.jpg)



명시적으로 밑에 코드가 내려가지 않도록 한다.



***2번째나 3번째나 컴파일 타임에는 이를 알 수 없고, 런타임 시점에 이를 알 수 있다.***



그러므로, 팀 내 정책으로 정해야 한다.

![image-20191117213427082](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bddwp2kj319y0gstda.jpg)



인자가 없는 함수가 좋은데, 인자가 많으면 귀찮아진다.



#  Postcondition

![image-20191117213603524](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bexwuhyj31020gudi5.jpg)



사후검증 로직.



사후검증해야 되는 대상은 `calculateFee` 이다.



![image-20191117213733321](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bgi8adaj30s80fgq6t.jpg)



남이 준 값을 반드시 검증해야 되는 책임을 말한다.



![image-20191117213845633](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bhqef0yj314a0mm758.jpg)



![image-20191117213908246](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bi3p328j319w0i0wle.jpg)





![image-20191117214140681](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bkrqmdoj31a20im457.jpg)



위 코드에서 	`isEmpty()` 는 잘못된 함수.

`묻지말고 시켜라` 에 맞쳐서 



![image-20191117214300220](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bm4ihcxj319o0i8dl1.jpg)



위임해야 한다.

![image-20191117214245024](https://tva1.sinaimg.cn/large/006y8mN6gy1g91blx4vs8j31900ien1k.jpg)

이렇게!



![image-20191117214312881](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bmf23tjj314c0l075d.jpg)



![image-20191117214347890](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bn1ydfrj30y80g6djv.jpg)

위 코드를 보고 판단하컨데, Plan 객체가 result를 검증해야될 의무가 없다.



그러므로, 위 코드를 검증하는건 calculator 가 해야된다.

![image-20191117214522349](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bopm0daj30ur0u0gyn.jpg)



드디어, Plan과 Calc가 성실하게 의무를 이행하겠다는 계약을 명시적으로 한 것이다.



제품이 장난감이 되는 이유는, 위 pre, postcondition을 잘하지 못하기 때문에 발생하는 것이다.



***이를 엄격하게 지키게 되면 70%의 코드가 validation 코드가 된다.***



![image-20191117215130412](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bv66vwjj311y0k6t9m.jpg)





![image-20191117215150630](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bvdi7axj314q0j8q7u.jpg)



위 코드에서는 calls.size() 를 

![image-20191117215325736](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bx8mdjej316g0iiaek.jpg)



위와 같이 하면 calls를 다시 Plan에다가 한 이유는 Calculator와 Plan 사이의 협력을 통해 책임을 분할하기 위해서 옮겼다.



![image-20191117215552337](https://tva1.sinaimg.cn/large/006y8mN6gy1g91bzpao6zj312c0me0tq.jpg)



![image-20191117215703469](https://tva1.sinaimg.cn/large/006y8mN6gy1g91dobe94yj312c0jatd5.jpg)





`check()` 와 `setNext` 를 잘 보고 판단해야 한다.

언제 호출되는지를 생각해보자.



위 함수를 적절하게 활용하기 위해서는 `런타임 시나리오` 를 그려봐야 한다.

![image-20191117220144639](https://tva1.sinaimg.cn/large/006y8mN6gy1g91c5zazsfj315w0iaq7c.jpg)

`calc.check()` 가 잘못됨을 판단했다.

![image-20191117220202279](https://tva1.sinaimg.cn/large/006y8mN6gy1g91c603qvrj312q0is78d.jpg)



![image-20191117220219230](https://tva1.sinaimg.cn/large/006y8mN6gy1g91c6dd3a0j31160ii0xb.jpg)



## 생성 시점에 보장하면 끝난다!

많은 사람들이 라이프사이클을 선택하려고 한다. 순서있는 메서드를 만들려고 하는데, 이는 문제가 있다. 라이프사이클에 의존할 경우 클라이언트는 어떤 가정을 하게 된다. 그러므로 적절하지 않다.



![image-20191117220832985](https://tva1.sinaimg.cn/large/006y8mN6gy1g91ccstynaj311a0m4t9f.jpg)



***계약의 전파?***



계약은 전파된다 라는 뜻은?

어떤 메세지 체인에 참가하는 모든 책임이 가져야 하는데, **검증 로직을 반복적으로 넣는 것이 맞냐?** 라는 것을 묻고 있는 것입니다.



![image-20191117221525603](https://tva1.sinaimg.cn/large/006y8mN6gy1g91ck39fhtj313e0gu77u.jpg)



![image-20191117221604501](https://tva1.sinaimg.cn/large/006y8mN6gy1g91ckk0sm5j311q0h8juw.jpg)



3가지 다 안하고 있는 상태이다. 이런 코드는 장난감 코드이다.



![image-20191117221837995](https://tva1.sinaimg.cn/large/006y8mN6gy1g91cnah82vj31a20icaee.jpg)



최초에 준 코드가 좋은 인자를 준 것이라는 것을 확인할 수 있다. 코드 상으로!!



그러나, 위 코드가 성립하기 위해서는 Plan.calculateFee 는 Calculator.calcCallFee에서 왔다는 것을 보장해야 하며, PricePerTime.calc() 도 보장되어야 한다.

**즉, 좋은 계약을 하기 위해서는 누가 들어오는지(어떤 클래스) 보장해야한다라면?**

패키지 구조를 우리는 좋은 계약을 짜기 위한 하나의 방식이라는 것에 동의할 수 있나요?



컴파일 파일 단위로 링크를 만들었던 옛날에서 내려온 명맥이 클래스의 가시성이 바로 패키지 구조로 내려온 것이다.



이 계약을 확신하기 위해서 외부에서 차단해줘야 한다.

이것이 바로 

![image-20191117222411491](https://tva1.sinaimg.cn/large/006y8mN6gy1g91ct1gr03j313k0ngt9w.jpg)



외부에서 간섭할 수 없도록 패키지에서 보증해야 한다.

![image-20191117222454967](https://tva1.sinaimg.cn/large/006y8mN6gy1g91ctxpw3gj314y0gwdgh.jpg)



따라서, Plan package에 넣음으로써- 계약을 보장한다.



![image-20191117222616553](https://tva1.sinaimg.cn/large/006y8mN6gy1g91cvd2zm7j30u00u9wog.jpg)



위 코드를 보면 `Calculator.calcCallFee` 는 이제 `Plan` 클래스에서 호출될 것이라는 사실을 보장하게 된다.



자바는 가시성 누수를 위해  public 문제를 가시성 문제를 해결 했다.



`package plan` 를 잘 설정한다면 pre, post condition을 잘 보장할 수 있다.

![image-20191117223059007](https://tva1.sinaimg.cn/large/006y8mN6gy1g91d09bi7uj30vi0ik778.jpg)

코드만 계약의 한 부분이 아니라, 가시성도 하나의 계약으로 볼 수 있다.



좋은 부모에 좋은 자식클래스를 만들기 위해서는 abstract가 아닌 protected 관계여야 한다.

![image-20191117223306301](https://tva1.sinaimg.cn/large/006y8mN6gy1g91d2jyr5nj31a20iyjup.jpg)



그러므로, 

![image-20191117223400881](https://tva1.sinaimg.cn/large/006y8mN6gy1g91d38pl0fj312o0io0wn.jpg)



***패키지 구성의 핵심은 계약관계에 달려있다.***

즉 아래와 같은 그림이 나와야 한다. 주황색부분은 pulbic Plan::calculateFee() 를 보장하게 된다.

![image-20191117223547317](https://tva1.sinaimg.cn/large/006y8mN6gy1g91d545zl0j311y0iwn13.jpg)



Pre / Post condition은 코드 뿐만 아니라, 가시성으로도 보장할 수 있다.



***Invariant를 보장해야 한다.***

![image-20191117224150505](https://tva1.sinaimg.cn/large/006y8mN6gy1g91dbere3tj30ve0k8dvy.jpg)



암묵적으로 코드 계약서를 쓰는 것이 아니라,

**코드에 계약서를 써야 한다. 그래야지만, 로직이 보이기 시작한다.**



![image-20191117224522021](https://tva1.sinaimg.cn/large/006y8mN6gy1g91df1k5vqj314s0kq78u.jpg)

