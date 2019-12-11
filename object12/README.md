# 테스트 주도 개발

![image-20191211152513738](https://tva1.sinaimg.cn/large/006tNbRwgy1g9srkit3cdj31920ocaaw.jpg)





![image-20191211152635542](https://tva1.sinaimg.cn/large/006tNbRwgy1g9srlx1om3j30vw0nq0wc.jpg)



## 동작하는 골격작성

""테스트를 먼저 만들지마, 먼저 동작하는 골격을 만들어.""

각각의 요소를 구분를 구현하는 가장 얇게 구현해!



## 그리고 빌드, 배포, 테스트 방법 파악

- 배포 시스템을 처음부터 관리  
  : 이 말은 배포시스템을 무조건 명백하게 확인해야 한다는 말.

위 말은 처음부터 무조건 테스트를 먼저 만들라는 것이 아님.



이 단계까지가 우리가 알을 만든거라 생각하면 된다.



이후 **최초 인수테스트 작성** 을 한다는 것은 이제 닭을 만든다고 생각하면 된다.

- 현재 가능한 최대범위에서 시작
- 점진적으로 전구간 테스트로 확대



마지막으로 테스트의 명확성 보다 골격 구조에 집중.



# 이슈를 치는 솔루션의 미니버전을 만드는 것이 이해도가 가장 높다.

자신의 속해있는 솔루션을 부트스트랩으로 만들어보는 것이 좋다.



---

시스템 목적을 이해하고 이를 바탕으로 아키텍처의 베이스를 잡습니다.



![image-20191211154239065](https://tva1.sinaimg.cn/large/006tNbRwgy1g9ss2m5151j30o60iewfv.jpg)





객체지향을 이해하는 가장 큰 범주 2가지는  **대체가능성, 내적일관성** 이다.



인수테스트란, 진짜 어렵다. 사람이 기계가하는 모든 경우를 대체하기는 여전히 힘들다.



대부분 우리는 도구에 의존하려고 한다.

![image-20191211155023238](https://tva1.sinaimg.cn/large/006tNbRwgy1g9ssanf1zlj30wg0heq4u.jpg)





![image-20191211155114440](https://tva1.sinaimg.cn/large/006tNbRwgy1g9ssbjjumlj30zu0liwha.jpg)



우리는 배포를 빨리빨리 해야 한다. 그래야지만 피드백을 빨리 받아서, 여기서 피드백은 도메인전문가, 개발팀, 배포협렵팀 등에 있다.



MVxx 이런 패턴이 좋은 이유는 우리가 시스템을 부분별로 Open 할 수 있기 때문에, 그런 관점에서 좋다.



**실제 고객사의 최종은 실제 기능구현 일정 예측**



***짬과 상관없이, 우리의 최종목적은 다음과 같은 일련의 작업을 계속해서 해봐야 한다. 설계란 돈을 아낄려는 행위를 말하는 것이다.***





![image-20191211160203598](https://tva1.sinaimg.cn/large/006tNbRwgy1g9ssmsrrtsj315y0p6go2.jpg)



수하에 개발자 3명을 두고 있다고 가정하자.



![image-20191211160245610](https://tva1.sinaimg.cn/large/006tNbRwgy1g9ssniw8cuj30ry0ecdhb.jpg)

회귀 테스트가 깨지는 이유는 격리있게 코드를 짜지 못했기 때문이다.

그렇기 때문에 캔트백이 말하는 TDD는 아예 애초부터 위와같은 이슈를 가정해서 테스트를 걸어버리라는 것이다.



다음 그림은 각 테스트에 대한 장단점에 대해서 이야기해봅시다.

![image-20191211161120287](https://tva1.sinaimg.cn/large/006tNbRwgy1g9sswg84xyj30sa0ccta5.jpg)



인수테스트를 늘 하지 않아서 많은 문제가 발생한다.



![image-20191211162016280](https://tva1.sinaimg.cn/large/006tNbRwgy1g9st5r1srcj311w0okn05.jpg)



단위 테스트에 절대 몰입하지 않아야 한다.

행위를 테스트한다는 건 - 책임과 역할을 드러낼 수 있는 테스트



오른쪽 이두박근 테스트가 많아지는건 함수에 인수가 많아졌다는 증거이다.

그러므로, 이 문제를 해결하기위해서는 타입을 받는 객체를 활용해야 한다.



**객체지향은 값을 쓰지 않는다. 그러므로, 값을 활용한 테스트는 하지 않아야 한다.**



Right bicep을 많이 쓴다는건 스스로 경고를 줘야 한다.



![image-20191211162409110](https://tva1.sinaimg.cn/large/006tNbRwgy1g9st9rw9gpj30mq0e6gm8.jpg)



단위테스트 목적은 코드 커버리지를 완성시키기 위한 용도이다.



그래서 실패하는 테스트를 보고, 할일 목록 작성과  목적을 이해한 기초코드 구현을 해야한다.



![image-20191211162554792](https://tva1.sinaimg.cn/large/006tNbRwgy1g9stbm03mfj30yw0c6q3x.jpg)



![image-20191211162605492](https://tva1.sinaimg.cn/large/006tNbRwgy1g9stbrsup9j31680m63zb.jpg)



![image-20191211162711697](https://tva1.sinaimg.cn/large/006tNbRwgy1g9stcxyc36j30t40h8wg1.jpg)



![image-20191211162726800](https://tva1.sinaimg.cn/large/006tNbRwgy1g9std73etlj30wc0i6acs.jpg)



단일 책임 원칙을 객체망으로 분리해서 문제를 해결한다.

관계를 다르게 얘기하면 `메세지 전송 이유` 가 될 수 있다.



그러므로, 학습하는 방법은 `외우기` 이다.

외워서 객체통신망을 하는게 좋다.



**체계화와 언어화를 시켜야 한다.**

내가 한 짓이 무엇인지, 한국말로 바꿀수 있어야 하고, 의도를 이해할 수 있어야 한다.

![image-20191211163349105](https://tva1.sinaimg.cn/large/006tNbRwgy1g9stjughu3j30sm0kyt9e.jpg)





![image-20191211163522309](https://tva1.sinaimg.cn/large/006tNbRwgy1g9stlgpp0nj31240nu0wy.jpg)



![image-20191211172850769](https://tva1.sinaimg.cn/large/006tNbRwgy1g9sv540m3wj30tw0n4n0n.jpg)



DB의 트랜잭션을 꼭 구현해봐라!!! 어떻게 격리했는지에 대해서도 구현해봐라~!!!

구현해봐야 안다.

![image-20191211173159775](https://tva1.sinaimg.cn/large/006tNbRwgy1g9sv8f5349j30uk0n0q6l.jpg)



![image-20191211173627256](https://tva1.sinaimg.cn/large/006tNbRwgy1g9svd0n55pj30yk0n2jvl.jpg)