### 7~8장

모으는거, 합치는 거

# Composition VS Assamble

![image-20190925000605736](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0agpxbrj31gm0u0ab7.jpg)



`Assamble`은 레고같은거라면,

`Composition`은 자동차의 트랜스미션와 같다.

만약 자동차의 트랜스 미션을 빼면 다른 트랜스미션 넣으면 되는 것과 같은 이치이다.

다시 말해, 격리, 모듈화가 잘 되어있는 것을 뜻한다. 



여기서 분해는 `composition` 을 의미한다.

`이 복잡한 세계를 어떻게 decomposition 할 수 있을까?` 

인지과부화, 복잡성 폭발 등은 어느수준이 넘어가면 이해할 수 없다.



`if`가 2단계 넘어가면 복잡성 폭발이라고 생각할 수있다.

***3단이 넘어가는 건 안된다. 기계가 되어야 한다.***



*책에서는 총 3가지의 복잡한 내용을 분해하는 방법에 대해서 소개합니다.*



그중 첫번째는 아래와 같습니다.

![image-20190925001845827](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0nl632vj30ug0fwq3u.jpg)



***함수(기능적)로 분해한다.***

어떤 명령단위로 분해할 수 있는데, 이 때 각 명령을 flow로 바라볼수 있다고 한다.

사람의 뇌는 flow처리를 선호하기 때문에 어떤 문제를 해결하기 위해서 flow로 처리하려는 경향이 있다.



마치 오늘의 Todo를 정하고 해치는 듯한 것과 같이-



`flow chart` 기법의 단점은, 사람은 학습한다는 점이다. 

예를 들어 설명해보면, 어떤 주황색 flow를 처리하는데-

![image-20190925002335422](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0slw9i7j31520b8t91.jpg)

파란색 변수가 발생하면

![image-20190925002412070](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0t8lk0uj314q0ba754.jpg)

순간 `나는 파란색이 필요한데, 여기가 아닌  다른 곳에서도 필요하구나-` 라고 생각을 한다.

![image-20190925002453674](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0tygvl2j313s0bejsc.jpg)

*하면서 파란색이 앞으로 가면서 flow-chart를 오염시킨다.*



그러므로, 오염이 되었기 때문에 다시 코드를 짜야할 것이다.



이런 flow를 처리하고 나서, 

![image-20190925002553392](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0uzioitj31800daabj.jpg)

특정 조건에 맞는 분기를 나눠야 하는데,  이때 초록색 원을 통해서 분기를 나눈다.

![image-20190925002611000](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0vadrvvj311y0bmta3.jpg)

이때 초록색 원이 절차적으로 나누는 분기 외에 Scope를 지정하는데 활용할 수 있다.

![image-20190925002702290](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0w6x4drj315u0dm40e.jpg)

이후에, 

![image-20190925002750473](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0x12qdtj31740dcgn3.jpg)

빨간색이 필요함을 알았다.

![image-20190925002809931](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0xcyl59j318i0e0762.jpg)

그럼 결과적으로, 

![image-20190925002826054](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b0xmokcsj318u0e6wga.jpg)

전체가 오염되는 상황이 발생했다. 이것이 우리가 늘 코딩할 때 밤을 새는 이유이다.



***이런 식의 코드는 딱 필요한 부분만 짜기 때문에 compact하게 코드를 딱딱 맞게 쓸 수 있지만, 이건 잘못된 것이다.***



## 2. 추상 데이터 타입

![image-20190925003500515](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b14he1r5j31780igab0.jpg)

위 데이터 타입은 `데이터를 기준`으로 세상을 바라보기 시작한다.

그래고, 리스코프 치환원칙을 이야기하기 전에는 객체지향원칙이 발달하지 않았다.



데이터에 따라서 함수를 만든다면, 다시 해야될 일을 데이터에 맞쳐서 함수를 만든다면, 아래와 같을 것이다.

![image-20190925004046932](/Users/lenkim/Library/Application Support/typora-user-images/image-20190925004046932.png)

그런데, 이런 함수도 계속 만들다보면 

![image-20190925004104730](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1askqhuj30my0iw0u2.jpg)

하는일이 비슷한 함수들이 있다는 것을 발견하게 될 것이다.

예를 들어, 똑같은 노동자이지만, 정규직, 시간제에 따라 Tax를 계산하는 것은 똑같다. 즉, 해야될 일이 비슷하다는 것을 말한다.



 이러한 시각으로 추상화된 레벨에서 바라봐야 한다는 점이다. `Flow 안에서 공통점을 찾을 수 있느냐 없느냐-`

(어떤 데이터이든지 나는 `이걸` 해줄 것이다 라는 것이 ADT의 특징이다.)

![image-20190925004320351](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1d5nwjtj30os0iata3.jpg)

어떤 행위를 추상화시킬 수 있음을 말하고 있다.

![image-20190925004431842](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1ee5wnej30ly0jewfx.jpg)

 이렇게 되면,  `지식을 한 곳에 모을 수 있다` 있으므로, 코드의 응집도가 높아진다. 반대로, 단점은 케이스가 늘어날때마다 하나씩 커진다.



만약, 외부에 이걸 하나의 형으로 노출하고 싶다 라면, `하늘색 주황색 빨간색 을 하나라고 퉁칠 수 있다.`

 이 장점은 외부에 노출시키지않고 내부에 모든 문제를 해결할 것이다.

![image-20190925004737362](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1hm2bdyj30o00li0uf.jpg)

 ***ADT가 좋다라는 논리를 깨기위해서는 객체지향 프로그래밍을 잘 알아야한다.*** 



만약, 데이터가 하나 생긴다면, 아래 그림을 보자.

![image-20190925004847338](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1ixdsofj30o40ieabp.jpg)



![image-20190925004912336](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1j91bmtj30pi0lwdhs.jpg)



 모든 클래스에 새로생긴 데이터에 대해서 구현해야되는 단점이 존재한다.

기능이 추가되지 않는 선에서 ADT가 좋을 수 있다. **그러나** 상태가 추가되거나 변형이 일어날 때는 그만큼의 코드가 추가된다.

![image-20190925005024288](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1kih5omj30t00l8dhz.jpg)

***이 문제를 아예 풀 수가 없다.***



ADT가 충족되는 조건은

1.상태가 더이상 확장되지 않고, 경우의수가 확정된 경우.

2.안에 소속된 모든 메소드가 모든 상태에 해당되는 상태를 반환할 때.



  사람들이 메소드를 구분해서 다르게 가져가야하기 때문에 어려울 수 있다. 

`그런거는 원래 초록색일 때 동작하는 거야 ` 라는 식의 논리는 잘못된 것.



ADT는 처음에는 잘 만들었다고 생각할 수 있지만, 나중에는 거의 다 깨먹는다.

안타깝게도, 대부분의 클래스를 우리는 ADT로 짠다.

그러므로, ADT가 무엇인지 명확하게 이해하는게 중요한다.



## Object Oriented

추상화라는 것이 연산을 먼저 정해놓고, 연산을 상속받는 무엇가를 구현한다.



![image-20190925005818913](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1sq90o1j30y60k6jsv.jpg)

객체지향에서는 상태가 없는 수준에서 추상화를 먼저 시키고, 상속에 따라서 더많은 형을 늘려간다.

ADT는 형을 줄여가는 것에 반해, Object Oriented는 형이 늘어난다.

![image-20190925005828286](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1svzqosj313i0ma766.jpg)

형이 늘었냐, 줄었냐를 통해서 이것이 ADT인지, Object Oriented인지 알 수 있다.

클래스가 다형적으로 많아지고 있다면 Object Oriented 일 수 있다.

![image-20190925005950395](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1ubj8swj314s0kw0uv.jpg)

압도적으로 유리한데, 만약, 초록색이 추가될 때 그 만큼의 클래스를 만들면 되기 때문에 확장에 유리하다.



#### *2단 if일 때는 전략 패턴으로 2개를 만들면 된다.*



변화에 막혀있는 것이 `ADT / 상태추가`에 강하다.



근데, 기능을 추가하면 다같이 변화가 됨.

![image-20190925010508033](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b1zvaqmlj316o0lowgz.jpg)



그러므로, 성급한 추상화가 가장 위험하다. ***무조건 피해야 한다.*** 추상화를 계속 정교화해서 Key case를 만들어야 한다.

자바에서는 이를 해결할 수 있는 방법이 없는데, 코틀린에서는 sheld class를 통해서 상속의 범위를 막을 수 있다.



리스코프 치환원칙의 d가 추가될 때 Objecct Oriented도 문제를 피할 수 없다.

![image-20190925010749082](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b22mrzupj31760litb9.jpg)





---

< 실습 >

객체지향으로 짰던 코드를 ADT로 옮겨볼 것.

![image-20190925010901857](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b23w60fwj30x80kata4.jpg)

`모든 소프트웨어는 변한다.` 라는 원칙에 의하면 

***ADT는 변경에 취약하다.***

![image-20190925011008828](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b251htf6j31000iiab3.jpg)



이전에 짰던 코드를 다시보자.

![image-20190925011059392](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b25xei7kj319w0i2gp7.jpg)



![image-20190925011130700](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b26hnfwoj31a40ja43i.jpg)



이 코드를 ADT로 변경해본다면?

![image-20190925011145004](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b26p8o16j30ok0k23zz.jpg)



![image-20190925011159742](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b26z8gw6j31a80mgtfa.jpg)



![image-20190925011438735](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b29qvowzj31a40mcjy2.jpg)



ServerClient 가 숨겨져 있어서 외부에 감춰진 상태로 나갈 것, 그럼 우리는 context를 어떻게 판별할 수 있는가?



***팀 내에서 코드를 까서 찾는다. <- 이런건 노하우. 지식이 안니다. 잘못된 ADT***

![image-20190925011822959](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b2dnhgfxj31aa0kegrh.jpg)



![image-20190925011848351](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b2e2dln6j31ag0kmtdg.jpg)



![image-20190925011900987](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b2eajx8ej313o0lsq9q.jpg)



한 곳에 모든 기능을 모았기 때문에 이름을 길게 쓸 수 밖에 없다. 코딩을 하면서 `변수명`부터 벌써 충돌이 발생한다. 통합하면서 코드가 변화된다.



클래스 2개 이상을 하나로 통합했다. 모든 지식이 한 곳에 모여  잘된것처럼 보인다.

![image-20190925012428180](https://tva1.sinaimg.cn/large/006y8mN6gy1g7b2jyxphjj311k0k6wl0.jpg)

Programmer가 ADT는 상태를 감추기 때문에,  상대에게 기능을 요청해서 처리해야만 할 것이다.

